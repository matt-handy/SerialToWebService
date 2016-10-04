#!/bin/sh
sleep 30
export SIM_LOC=/home/pi/Desktop/SerialToWebService
java -cp $SIM_LOC/jssc.jar:$SIM_LOC/commons-logging-1.2.jar:$SIM_LOC/commons-codec-1.9.jar:$SIM_LOC/org.apache.httpcomponents.httpclient_4.3.4.jar:$SIM_LOC/build/jar/SensorSuiteSerialMonitor.jar serialmonitor.MonitorDriver /dev/ttyACM0 > log.txt