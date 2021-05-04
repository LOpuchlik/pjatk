from controller import *
import numpy as np
import cv2
import struct


TIME_STEP = 16
COEFF = 0.5
robot = Robot()
wheels = []
wheelsNames = ['lMotor', 'rMotor']
velocity = 10

# przechwytywanie obrazu z kamery robota


def process_image():
    cam1 = camera.getImage()

    if cam1 is None:
        return None

    width = camera.getWidth()
    height = camera.getHeight()
    CHUNK = width * height * 4
    cam_int = struct.unpack(str(CHUNK) + 'B', cam1)
    cam_int = np.array(cam_int, dtype=np.uint8).reshape(64, 64, 4)
    cam_int = cam_int[:, :, 0:3]
    return cam_int

# jazda do przodu, w prawo i w lewo i obrot

# na wprost to oba kola z ta sama "moca" - przy pomocy parametru - param nalealoby tu jeszcze sie pobawic i zoptymalizowac predkosc poruszania sie robota w zaleznosci od jego odleglosci od pilki, ale nie mam juz niestety na to czasu


def move_forward(param):
    wheels[0].setVelocity(velocity * COEFF * param)
    wheels[1].setVelocity(velocity * COEFF * param)

# lewe kolo dziala a prawe nie lub lewe dziala mocniej niz prawe
# lewe kolo to wheel[0]
# prawe kolo to wheel[1]


def turn_right():
    wheels[0].setVelocity(velocity * COEFF)
    wheels[1].setVelocity(velocity * 0)

# prawe kolo dziala a lewe nie lub prawe dziala mocniej niz lewe


def turn_left():
    wheels[0].setVelocity(velocity * 0)
    wheels[1].setVelocity(velocity * COEFF)

# kola dzialaja z ta sama "moca", ale w przeciwnych "kierunkach"


def turn_around():
    wheels[0].setVelocity(velocity * COEFF)
    wheels[1].setVelocity(-velocity * COEFF)


for i in range(2):
    wheels.append(robot.getMotor(wheelsNames[i]))
    wheels[i].setPosition(float('inf'))


camera = robot.getCamera('cam1')
camera.enable(TIME_STEP)

while robot.step(TIME_STEP) != -1:

    frame_grabbed = process_image()

    if frame_grabbed is None:
        print("No frame to grab")

    imHSV = cv2.cvtColor(frame_grabbed, cv2.COLOR_BGR2HSV)

    low_red = np.array([175, 70, 0])  # 140, 150, 0   175, 70, 0
    high_red = np.array([180, 255, 255])  # 180, 255, 255   180, 255, 255
    imInRange = cv2.inRange(imHSV, low_red, high_red)  # image mask

    # morphology operations
    cR = 3
    kernel = (cR, cR)
    # close
    imInRange = cv2.dilate(
        imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))
    imInRange = cv2.erode(
        imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))
    # open
    imInRange = cv2.erode(
        imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))
    imInRange = cv2.dilate(
        imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))

    # oMoments
    M = cv2.moments(imInRange)
    # print(M)
    area = M["m00"]

    if area != 0:
        x = int(M["m10"] / area)
        print(f'x: {x}')
        y = int(M["m01"] / area)
        print(f'y: {y}')
        # docs: he shape of an image is accessed by img.shape. It returns a tuple of the number of rows (height = shape[0]), columns (width = shape[1]), and channels (if the image is color):
        half_height = int(frame_grabbed.shape[0] / 2)
        half_width = int(frame_grabbed.shape[1] / 2)

        dx = (x - half_width) / half_width
        dy = (y - half_height) / half_height

        if dx < -0.2:
            turn_left()
        elif dx > 0.2:
            turn_right()
        else:
            move_forward(1.5)
    else:
        turn_around()  # nie rozpoznaje na horyzoncie czerwonej piłki
