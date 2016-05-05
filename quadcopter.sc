/****************************************************************************
*  Title: quadcopter.sc
*  Author: Amy Seibert
*  Date: 05/02/2016
*  Description: Specification model for the quadcopter
****************************************************************************/

import "c_queue";

import "sensors";

// different data rates: 100Kb/s, 400Kb/s, 1Mb/s, 3.2Mb/s
// data rate is in kilobits per second
#define I2C_DATA_RATE 100

// baudrate for the Bluetooth serial connection(bits per second)
// standard baud rates: 1200, 2400, 4800, 9600, 19200, 38400, 57600, and 115200
#define BAUDRATE 9600

// Simulates the quadcopter
behavior quadcopter(i_receiver rc, i_tranceiver bluetooth)
{
	const unsigned long MAX_SIZE = 256;
	c_queue accel_channel(MAX_SIZE);
	c_queue gyro_channel(MAX_SIZE);
	c_queue lidar_channel(MAX_SIZE);
	c_queue gps_channel(MAX_SIZE);
	accelerometer accel_unit(accel_channel);
	gyroscope gyro_unit(gyro_channel);
	lidar lidar_unit(lidar_channel);
	gps gps_unit(gps_channel);

	// counts the waiting time in milliseconds
	unsigned int throttle, pitch, roll, yaw;
	double waiting_time, accel_data, gyro_data, lidar_data, gps_data, position_data;

	void main(void) {
		//---------- Initialization ----------//

		par {
			accel_unit.main();
			gyro_unit.main();
			lidar_unit.main();
			gps_unit.main();
		}

		
		while(1)
		{
			//---------- Get Ground Station Data ----------//
			// Uses Wifi?
			// Receive throttle
			rc.receive(&throttle, sizeof(throttle));
			// Receive pitch
			rc.receive(&pitch, sizeof(pitch));
			// Receive roll
			rc.receive(&roll, sizeof(roll));
			// Receive yaw
			rc.receive(&yaw, sizeof(yaw));

			//---------------- Get Sensors ----------------//
			// Use I2C to get data from sensors
			// different speeds for sending data[100kbits/s | 400kbits/s | 1mbits/s | 3.2mbits/s]
			// assume 7 bit addresses, but still send 8 bits
			// to read in I2C:
			// send start sequence
			// send I2C address on bus(8 bits + 1 ACK bit)
			// send internal address to read(8 bits + 1 ACK bit)
			// resend start sequence
			// send I2C address on bus(8 bits + 1 ACK bit)
			// read data (multiple-of-8 bits plus 1 ACK bit for each byte)

			//accel_channel.receive(&accel_data, sizeof(accel_data));
			// 12 bytes of data
			// total number of bits sent/received = 27 + 12*8 + 12 = 135
			waiting_time = waiting_time + ((double)135 / I2C_DATA_RATE);

			//gyro_channel.receive(&gyro_data, sizeof(gyro_data));
			// 12 bytes of data
			// total number of bits sent/received = 135
			waiting_time = waiting_time + ((double)135 / I2C_DATA_RATE);

			//lidar_channel.receive(&lidar_data, sizeof(lidar_data));
			// 4 bytes of data
			// total number of bits sent/received = 27 + 4*8 + 4 = 27 + 36 = 63
			waiting_time = waiting_time + ((double)63 / I2C_DATA_RATE);

			//gps_channel.receive(&gps_data, sizeof(gps_data));
			// 8 bytes of data
			// total number of bits sent/received = 27 + 8*8 + 8 = 27 + 72 = 99
			waiting_time = waiting_time + ((double)99 / I2C_DATA_RATE);

			//------------- Sensor Processing -------------//


			//------------- Control Algorithm -------------//


			//-------- Actuator Command Processing --------//


			//-------- Send Data to Ground Station --------//
			// Uses Bluetooth(Serial port profile)
			// metadata for each message is 8 bytes each
			// 1 start bit, 1 end bit, 8 bits of data, no parity bit
			// Send throttle
			bluetooth.send(&throttle, sizeof(throttle));
			// total number of bits sent = 80
			// since baudrate is in bits per second, to get the waiting_time in
			// milliseconds, we multiply the result by 1000
			waiting_time = waiting_time + (((double)80 / BAUDRATE) * 1000.0);

			// Send pitch
			bluetooth.send(&pitch, sizeof(pitch));
			// total number of bits sent = 80
			waiting_time = waiting_time + (((double)80 / BAUDRATE) * 1000.0);

			// Send roll
			bluetooth.send(&roll, sizeof(roll));
			// total number of bits sent = 80
			waiting_time = waiting_time + (((double)80 / BAUDRATE) * 1000.0);

			// Send yaw
			bluetooth.send(&yaw, sizeof(yaw));
			// total number of bits sent = 80
			waiting_time = waiting_time + (((double)80 / BAUDRATE) * 1000.0);
			
			waitfor(10);
		}
	}
};
