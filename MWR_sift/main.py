import cv2
import numpy as np


def rescale(img, scale_percent):
    width = int(img.shape[1] * scale_percent / 100)
    height = int(img.shape[0] * scale_percent / 100)
    d_size = (width, height)
    img = cv2.resize(img, d_size)
    return img


# loaded images put into a gray-scale
img1 = cv2.imread('liberty1.jpeg', cv2.IMREAD_GRAYSCALE)
img1 = rescale(img1, 20)

img2 = cv2.imread('liberty3.jpeg', cv2.IMREAD_GRAYSCALE)
img2 = rescale(img2, 50)


sift = cv2.xfeatures2d.SIFT_create() # creating a SIFT object

# finding key points both images
key_points_1, descriptors_1 = sift.detectAndCompute(img1, None)
key_points_2, descriptors_2 = sift.detectAndCompute(img2, None)

img1 = cv2.drawKeypoints(img1, key_points_1, None)
img2 = cv2.drawKeypoints(img2, key_points_2, None)

# feature matching
bf = cv2.BFMatcher(cv2.NORM_L1, crossCheck=True)
matches = bf.match(descriptors_1, descriptors_2)

cv2.imshow('image1', img1)
cv2.imshow('image2', img2)


cv2.waitKey(0)
cv2.destroyAllWindows()


