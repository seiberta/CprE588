/****************************************************************************
*  Title: sensors.sc
*  Author: Amy Seibert
*  Date: 05/02/2016
*  Description: Specification model for the sensors
****************************************************************************/

import "c_queue";

// Simulates the sensors
behavior accelerometer (i_sender communication)
{

	void main(void) {
		double data = 0;
		while(1)
		{
			//communication.send(&data, sizeof(data));
			// Wait for data to be ready
		}
	}
};

behavior gyroscope (i_sender communication)
{

	void main(void) {
		double data = 0;
		while(1)
		{
			//communication.send(&data, sizeof(data));
			// Wait for data to be ready
		}
	}
};

behavior lidar (i_sender communication)
{

	void main(void) {
		double data = 0;
		while(1)
		{
			//communication.send(&data, sizeof(data));
			// Wait for data to be ready
		}
	}
};

behavior gps (i_sender communication)
{

	void main(void) {
		double data = 0;
		while(1)
		{
			//communication.send(&data, sizeof(data));
			// Wait for data to be ready
		}
	}
};

