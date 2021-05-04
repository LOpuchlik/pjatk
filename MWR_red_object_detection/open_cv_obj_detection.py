import argparse
import cv2
import numpy as np

def nothing(x):
    pass

def parse_arguments():
    parser = argparse.ArgumentParser(description=('Divides video stream into frames and R,G,B channels'))
    parser.add_argument('-i', '--input_video', type=str, required=True, help='Input video file that will be processed')
    return parser.parse_args()

def main(args):
    video = cv2.VideoCapture(args.input_video)
    if video is None:
        print("No video to open")

    if video.isOpened():
        frame_grabbed, frame = video.read()
    else:
        frame_grabbed = False

    # controls
    cv2.namedWindow("Control")

    # Hue (0 - 180)
    cv2.createTrackbar("LowH", "Control", 0, 180, nothing)
    cv2.createTrackbar("HighH", "Control", 180, 180, nothing)

    # Saturation (0 - 255)
    cv2.createTrackbar("LowS", "Control", 0, 255, nothing)
    cv2.createTrackbar("HighS", "Control", 255, 255, nothing)

    # Value (0 - 255)
    cv2.createTrackbar("LowV", "Control", 0, 255, nothing)
    cv2.createTrackbar("HighV", "Control", 255, 255, nothing)

    # Kernel (1-20)
    cv2.createTrackbar("cR", "Control", 1, 20, nothing)

    # trackbars position setters
    cv2.setTrackbarPos("LowH", "Control", 175)
    cv2.setTrackbarPos("HighH", "Control", 180)
    cv2.setTrackbarPos("LowS", "Control",70)
    cv2.setTrackbarPos("HighS", "Control",255)
    cv2.setTrackbarPos("LowV", "Control",0)
    cv2.setTrackbarPos("HighV", "Control",255)
    cv2.setTrackbarPos("cR", "Control",3)

    while frame_grabbed:
        # trackbar position getter
        low_h = cv2.getTrackbarPos("LowH", "Control")
        high_h = cv2.getTrackbarPos("HighH", "Control")
        low_s = cv2.getTrackbarPos("LowS", "Control")
        high_s = cv2.getTrackbarPos("HighS", "Control")
        low_v = cv2.getTrackbarPos("LowV", "Control")
        high_v = cv2.getTrackbarPos("HighV", "Control")
        cR = cv2.getTrackbarPos("cR", "Control")
        print(f'low_h: {low_h}, high_h: {high_h}, low_s: {low_s}, high_s: {high_s}, low_v: {low_v}, high_v: {high_v}, kernel: {cR}')

        frame_grabbed, frame = video.read()
        imHSV = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)   # conversion from rgb to hsv

        # filtr zakresowy dla koloru czerwonego
        low_red = np.array([low_h, low_s, low_v]) # 140, 150, 0   175, 70, 0
        high_red = np.array([high_h, high_s, high_v]) # 180, 255, 255   180, 255, 255
        imInRange = cv2.inRange(imHSV, low_red, high_red) # image mask

        # morphology operations
        kernel = (cR, cR)
        # close
        imInRange = cv2.dilate(imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))
        imInRange = cv2.erode(imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))
        # open
        imInRange = cv2.erode(imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))
        imInRange = cv2.dilate(imInRange, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, kernel))

        cnts = cv2.findContours(imInRange, cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)[-2]
        frame_1 = frame.copy()
        if len(cnts) > 0:
            c = max(cnts, key=cv2.contourArea)
            ((x, y), radius) = cv2.minEnclosingCircle(c)
            M = cv2.moments(c)
            center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
            window_x_center = (int(500/2))
            if radius > 0:
                cv2.circle(frame_1, (int(x), int(y)), int(radius), (0, 255, 0), 2)
                cv2.line(frame_1, (center[0], 10), (window_x_center, 10), (0, 255, 0), 10)


        cv2.namedWindow('transformed', cv2.WINDOW_NORMAL)
        cv2.resizeWindow('transformed', 500, 400)
        cv2.moveWindow('transformed', 220, 0)
        cv2.imshow('transformed', imInRange)

        cv2.namedWindow('original', cv2.WINDOW_NORMAL)
        cv2.resizeWindow('original', 500, 400)
        cv2.moveWindow('original', 730, 0)
        cv2.imshow('original', frame_1)

        if cv2.waitKey(1) == 113: # q to quit
            break

    cv2.destroyAllWindows()
    video.release()

if __name__ == '__main__':
    main(parse_arguments())
