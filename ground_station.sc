/****************************************************************************
*  Title: ground_station.sc
*  Author: Amy Seibert
*  Date: 05/02/2016
*  Description: Specification model for the ground station of the system
****************************************************************************/

import "c_queue";

// Simulates the display of the system
behavior ground_station(i_tranceiver bluetooth)
{

	void main(void) {
		unsigned int throttle, pitch, roll, yaw;
		while(1)
		{
			// Receive throttle
			bluetooth.receive(&throttle, sizeof(throttle));
			// Receive pitch
			bluetooth.receive(&pitch, sizeof(pitch));
			// Receive roll
			bluetooth.receive(&roll, sizeof(roll));
			// Receive yaw
			bluetooth.receive(&yaw, sizeof(yaw));
			
			
			printf("throttle:\t%d\npitch:\t%d\nroll:\t%d\nyaw:\t%d\n\n", throttle,pitch,roll,yaw);
			fflush(stdout);
			
		}
	}
};
