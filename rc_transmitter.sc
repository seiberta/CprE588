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
		char buf[2];
		while(1)
		{
			printf("Please choose your function\n\n\ta) Increase Throttle\n\tb) Decrease Throttle\n\tc) Increase Pitch\n\td) Decrease Pitch\n\te) Increase Roll\n\tf) Decrease Roll\n\tg) Increase Yaw\n\th) Decrease Yaw\n");
			gets(buf);
			if(buf[0] == 'a')
			{
				throttle += 1;
			}
			if(buf[0] == 'b')
			{
				throttle -= 1;
			}
			if(buf[0] == 'c')
			{
				pitch += 1;
			}
			if(buf[0] == 'd')
			{
				pitch -= 1;
			}
			if(buf[0] == 'e')
			{
				roll += 1;
			}
			if(buf[0] == 'f')
			{
				roll -= 1;
			}
			if(buf[0] == 'g')
			{
				yaw += 1;
			}
			if(buf[0] == 'h')
			{
				yaw -= 1;
			}
			
			// Send throttle
			rc.send(&throttle, sizeof(throttle));
			// Send pitch
			rc.send(&pitch, sizeof(pitch));
			// Send roll
			rc.send(&roll, sizeof(roll));
			// Send yaw
			rc.send(&yaw, sizeof(yaw));
		}
	}
};
