import tensorflow as tf
model = tf.keras.models.load_model("cifar10_model.keras")
model.export("exported_model_dir")
