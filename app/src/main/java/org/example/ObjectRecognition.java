package org.example;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ObjectRecognition {
    public static void main(String[] args) {
        // Gets image from file
        Mat image = Imgcodecs.imread("path/to/image");
        // Creating what will be result image
        Mat resizedImage = new Mat();
        // Size to resize the image to
        Size imageSize = new Size(224, 224);
        // Actually resizing the image to what we want
        Imgproc.resize(image, resizedImage, imageSize);
        // Yippee, another mat in the conversion process
        Mat floatMat = new Mat();
        // Converts the 224x224 image to a 32 bit float mat (I think) (and also scales values from 0-255 to 0-1)
        resizedImage.convertTo(floatMat, CvType.CV_32F, 1.0/255.0);
        
    }
}
