package org.example;

import org.tensorflow.SavedModelBundle;

public class ObjectRecognition { 
    private SavedModelBundle model;

    public ObjectRecognition(String path) {
        model = SavedModelBundle.load(path, "serve");
    }

    public static void main(String[] args) {
        ObjectRecognition recognition = new ObjectRecognition("C:/path/to/model");
    }
}
