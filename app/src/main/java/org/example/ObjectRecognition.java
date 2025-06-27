package org.example;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.Result;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.ndarray.NdArray;
import org.tensorflow.ndarray.NdArrays;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.ndarray.buffer.DataBuffers;
import org.tensorflow.types.TFloat32;

public class ObjectRecognition {
    static {
        // System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
        Loader.load(opencv_java.class);
    }
    static String[] classes = {"1", "2", "3", "4", "automobile", "6", "cat", "7", "8", "9"};
    public static void main(String[] args) {
        // Mat originalImage = new Mat();
        // try {
        // Gets image from file and keeps an original
        Mat originalImage = Imgcodecs.imread(
                "C:/Users/jts70/Documents/Personal/ObjectRecognition/app/src/main/java/org/example/frog-1.jpg");
        // } catch (UnsatisfiedLinkError error) {
        // error.printStackTrace();
        // System.out.println("Native lib loaded? " + opencv_core.getVersionString());

        // }
        Mat image = originalImage;
        // Size to resize the image to
        Size imageSize = new Size(32, 32);
        // Actually resizing the image to what we want
        Imgproc.resize(image, image, imageSize);
        // Converts the 224x224 image to a 32 bit float mat (I think) (and also scales
        // values from 0-255 to 0-1)
        image.convertTo(image, CvType.CV_32F, 1.0 / 255.0);

        // According to ChatGPT, we need to convert BGR to RGB, so here is a loop that
        // makes that happen
        // Creates result float
        float[] result = new float[32 * 32 * 3];
        // var we use to cycle through things
        int idx = 0;
        // for each x and y combo under the sun (or at least for this image)
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                // get current selected pixel in BGR format
                double[] pixel = image.get(y, x);

                // do some switching to get the correct format (RGB)
                result[idx++] = (float) pixel[2]; // R
                result[idx++] = (float) pixel[1]; // G
                result[idx++] = (float) pixel[0]; // B
            }
        }
        // System.out.println("Result length: " + result.length);
        // System.out.println(result[1000] + " " + result[1001] + " " + result[1002]);
        // Creates a tensor. Yippee (I have no clue what I'm doing, this is all new to
        // me)
        TFloat32 input = TFloat32.tensorOf(Shape.of(1, 32, 32, 3), data -> data.write(DataBuffers.of(result)));

        // Let's load a model (Unsure what the "serve" bit does)
        SavedModelBundle model = SavedModelBundle
                .load("C:/Users/jts70/Documents/Personal/ObjectRecognition/app/src/main/java/org/example/exported_model_dir",
                        "serve");

        // Feeds in input, fetches output from the model
        Result outputs = model.session().runner()
                .feed("serve_input_layer:0", input)
                .fetch("StatefulPartitionedCall:0")
                .run();

        // Filters the outputs
        try (TFloat32 output = (TFloat32) outputs.get(0)) {
            float[][] out = new float[1][10]; // adjust shape if needed

            NdArray<Float> tensorResult = NdArrays.ofFloats(Shape.of(1, 10));
            // float[] tensorResultFloat = new float[10];
            output.copyTo(tensorResult);

            // for (int i = 0; i < out[0].length; i++) {
            // System.out.printf("Class %d: %.4f\n", i, out[0][i]);
            // }

            for (int i = 0 ; i < 10; i++) {
                float value = tensorResult.getObject(0, i);
                System.out.printf("Class %s: %.4f\n", classes[i], value);
            }
        }
    }
}
