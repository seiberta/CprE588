/****************************************************************************
*  Title: rc_transmitter.sc
*  Author: Amy Seibert
*  Date: 05/02/2016
*  Description: Specification model for the transmitter of the system
****************************************************************************/

#include <stdio.h>
#include <stdlib.h>

import "c_queue";

// Simulates the commands sent to the quadcopter from the RC controller
behavior rc_transmitter(i_sender rc)
{
	void main(void) {
		unsigned int throttle = 1000, pitch = 1000, roll = 1000, yaw = 1000;
		while(1)
		{
			
			// Send throttle
			rc.send(&throttle, sizeof(throttle));
			// Send pitch
			rc.send(&pitch, sizeof(pitch));
			// Send roll
			rc.send(&roll, sizeof(roll));
			// Send yaw
			rc.send(&yaw, sizeof(yaw));
			throttle += 1;
			if(throttle > 2000) throttle = 2000;
			waitfor(1);
		}
	}
};
