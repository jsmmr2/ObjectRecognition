plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    // TensorFlow
    implementation("org.tensorflow:tensorflow-core-platform:0.5.0")

    // OpenCV
    // implementation("org.bytedeco:opencv:4.10.0-1.5.11")
    implementation("org.bytedeco:opencv-platform:4.10.0-1.5.11")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.ObjectRecognition"
}
