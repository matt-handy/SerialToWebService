from picamera import PiCamera
from time import sleep

sleep(60)

camera = PiCamera()

while True:
	sleep(5)
	camera.capture('/home/pi/Desktop/picDir/image.jpg')
	sleep(60 * 60)
