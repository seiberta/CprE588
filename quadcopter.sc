/****************************************************************************
*  Title: quadcopter.sc
*  Author: Amy Seibert
*  Date: 05/02/2016
*  Description: Specification model for the quadcopter
****************************************************************************/

import "c_queue";

import "sensors";


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

	unsigned int throttle, pitch, roll, yaw;
	double accel_data, gyro_data, lidar_data, gps_data, position_data;

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
			// send I2C address on bus(8 bits)
			// send internal address to read(8 bits)
			// resend start sequence
			// send I2C address on bus(8 bits)
			// read data (multiple-of-8 bits)
			accel_channel.receive(&accel_data, sizeof(accel_data));
			gyro_channel.receive(&gyro_data, sizeof(gyro_data));
			lidar_channel.receive(&lidar_data, sizeof(lidar_data));
			gps_channel.receive(&gps_data, sizeof(gps_data));

			//------------- Sensor Processing -------------//


			//------------- Control Algorithm -------------//


			//-------- Actuator Command Processing --------//


			//-------- Send Data to Ground Station --------//
			// Uses Bluetooth
			// Send throttle
			bluetooth.send(&throttle, sizeof(throttle));
			// Send pitch
			bluetooth.send(&pitch, sizeof(pitch));
			// Send roll
			bluetooth.send(&roll, sizeof(roll));
			// Send yaw
			bluetooth.send(&yaw, sizeof(yaw));
			
			waitfor(10);
		}
	}
};
