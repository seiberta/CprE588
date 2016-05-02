/****************************************************************************
*  Title: tb.sc
*  Author: Amy Seibert
*  Date: 02/09/16
*  Description: testbench for vending machine
****************************************************************************/

import "rc_transmitter";
import "ground_station";
import "quadcopter";

import "c_queue";

behavior Main
{
	const unsigned long MAX_SIZE = 256;
	c_queue bluetooth(MAX_SIZE);
	c_queue rc(MAX_SIZE);

	rc_transmitter U0(rc);
	ground_station U1(bluetooth);
	quadcopter U2(rc, bluetooth);

	int main (void)
	{
		par {
			U0.main();
			U1.main();
			U2.main();
		}

		return 0;
	}
};
