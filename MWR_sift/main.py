import argparse
import cv2
import numpy as np


# to run this type in PyCharm's Edit Configurations -> -i "liberty1.jpeg liberty3.jpeg" -> as parameters
def parse_arguments():
    parser = argparse.ArgumentParser(description='Reads in an image')
    parser.add_argument('-i', '--input_images', type=str, required=True, help='Inputs images that will be processes')
    return parser.parse_args()

# skalowanie ma polegac na braniu co drugiego wiersza i co drugiek kolumny macierzy obrazu, a nie zmieniania tylko wielkości obrazu
# TODO
def rescale(img, scale_percent):
    width = int(img.shape[1] * scale_percent / 100)
    height = int(img.shape[0] * scale_percent / 100)
    d_size = (width, height)
    img = cv2.resize(img, d_size)
    return img


def main(args):
    # loaded images put into a gray-scale
    img1 = cv2.imread(str(args.input_images.split(" ")[0]), cv2.IMREAD_GRAYSCALE)
    img1 = rescale(img1, 20)

    img2 = cv2.imread(str(args.input_images.split(" ")[1]), cv2.IMREAD_GRAYSCALE)
    img2 = rescale(img2, 10)
    img2 = cv2.GaussianBlur(img2, (5, 5), 0)
    sift = cv2.xfeatures2d.SIFT_create()  # creating a SIFT object

    # finding key points on both images
    key_points_1, descriptors_1 = sift.detectAndCompute(img1, None)
    key_points_2, descriptors_2 = sift.detectAndCompute(img2, None)

    img1 = cv2.drawKeypoints(img1, key_points_1, None)
    img2 = cv2.drawKeypoints(img2, key_points_2, None)

    # feature matching
    bf = cv2.BFMatcher(cv2.NORM_L1, crossCheck=True)
    matches = bf.match(descriptors_1, descriptors_2)
    #matches = sorted(matches, key=lambda x: x.distance)
    #print(f'Matches length {len(matches)}')

    result = cv2.drawMatches(img1, key_points_1, img2, key_points_2, matches[0:50], None)

    cv2.namedWindow('image1', cv2.WINDOW_NORMAL)
    cv2.moveWindow('image1', 10, 0)
    cv2.imshow('image1', img1)

    cv2.namedWindow('image2', cv2.WINDOW_NORMAL)
    cv2.moveWindow('image2', 300, 0)
    cv2.imshow('image2', img2)

    cv2.namedWindow('results', cv2.WINDOW_NORMAL)
    cv2.moveWindow('results', 300, 300)
    cv2.imshow('results', result)

    cv2.waitKey(0)
    cv2.destroyAllWindows()


if __name__ == "__main__":
    main(parse_arguments())
