#
# Makefile: Vending Machine
#

SCC = scrc

SCCOPT  = -vv -ww -g

all: tb

tb: tb.sc rc_transmitter.sc ground_station.sc quadcopter.sc sensors.sc
	$(SCC) tb $(SCCOPT)

clean:
	-rm -f *~ *.o *.cc *.h
	-rm -f *.si
