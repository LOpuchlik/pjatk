import numpy as np
import cv2
import argparse


def parse_arguments():
    parser = argparse.ArgumentParser(description=('Reads in a video'))
    parser.add_argument('-i', '--input_video', type=str, required=True, help='Input video file that will be processed')
    return parser.parse_args()


def main(args):
    video = cv2.VideoCapture(args.input_video)
    if video is None:
        print("No video to open")

    # params for ShiTomasi corner detection
    feature_params = dict(maxCorners=100,
                          qualityLevel=0.3,
                          minDistance=7,
                          blockSize=7)

    # # Parameters for lucas kanade optical flow
    lk_params = dict(winSize=(15, 15),
                     maxLevel=2,
                     criteria=(cv2.TERM_CRITERIA_EPS | cv2.TERM_CRITERIA_COUNT, 10, 0.03))

    # Take first frame and find corners in it
    ret, old_frame = video.read()
    old_gray = cv2.cvtColor(old_frame, cv2.COLOR_BGR2GRAY)
    p0 = cv2.goodFeaturesToTrack(old_gray, mask=None, **feature_params)

    mask = np.zeros_like(old_frame)

    while True:
        ret, frame = video.read()

        frame_gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

        # calculate optical flow
        p1, st, err = cv2.calcOpticalFlowPyrLK(old_gray, frame_gray, p0, None, **lk_params)

        # Select good points
        if p1 is not None:
            good_new = p1[st == 1]
            good_old = p0[st == 1]

        # draw the tracking lines
        colour = (0, 0, 255) # red in BGR
        for i, (new, old) in enumerate(zip(good_new, good_old)):
            a, b = new.ravel()
            c, d = old.ravel()
            mask = cv2.line(mask, (int(a), int(b)), (int(c), int(d)), colour, 2)
            frame = cv2.circle(frame, (int(a), int(b)), 1, colour, 0) # -1 in the last parameter means filled shape
        img = cv2.add(frame, mask)
        cv2.imshow("Window", img)

        # q for interruption
        if cv2.waitKey(1) == 113:  # q to quit
            break

        # updating previous frames and points
        old_gray = frame_gray.copy()
        p0 = good_new.reshape(-1, 1, 2)

    cv2.destroyAllWindows()
    video.release()


if __name__ == '__main__':
    main(parse_arguments())

