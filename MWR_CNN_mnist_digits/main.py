from tensorflow.keras.datasets import mnist
from tensorflow.keras.layers import Conv2D, Dense, MaxPooling2D, Flatten, Dropout
from matplotlib import pyplot
from tensorflow_core.python.keras import Sequential
from keras.utils import to_categorical

(X_train, y_train), (X_test, y_test) = mnist.load_data()

for i in range(9):
    pyplot.subplot(330 + 1 + i)
    pyplot.imshow(X_train[i], cmap=pyplot.get_cmap('gray'))
pyplot.show()

X_train = X_train.reshape(60000, 28, 28, 1)
X_train = X_train / 255.0  # 0-1 normalization
X_test = X_test.reshape(10000, 28, 28, 1)
X_test = X_test / 255.0  # 0-1 normalization

print(X_train.shape, X_test.shape)

num_classes = 10
y_train = to_categorical(y_train, num_classes)
y_test = to_categorical(y_test, num_classes)

###### My models
# Without dropout
model_1 = Sequential([
    Conv2D(32, kernel_size=(3, 3), activation='relu', padding='same', input_shape=(28, 28, 1)),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Flatten(),
    Dense(num_classes, activation='softmax')
])
#
model_2 = Sequential([
    Conv2D(32, kernel_size=(3, 3), activation='relu', padding='same', input_shape=(28, 28, 1)),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Conv2D(32, kernel_size=(3, 3), activation='relu', padding='same'),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Flatten(),
    Dense(num_classes, activation='softmax')
])
#
model_3 = Sequential([
    Conv2D(32, kernel_size=(3, 3), activation='relu', padding='same', input_shape=(28, 28, 1)),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Conv2D(32, kernel_size=(3, 3), activation='relu', padding='same'),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Conv2D(16, kernel_size=(3, 3), activation='relu', padding='same'),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Flatten(),
    Dense(num_classes, activation='softmax')
])

# With dropout
model_3_dropout = Sequential([
    Conv2D(32, kernel_size=(3, 3), activation='relu', padding='same', input_shape=(28, 28, 1)),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Dropout(0.2),
    Conv2D(32, kernel_size=(3, 3), activation='relu', padding='same'),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Dropout(0.3),
    Conv2D(16, kernel_size=(3, 3), activation='relu', padding='same'),
    MaxPooling2D(pool_size=(2, 2), padding='same'),
    Dropout(0.4),
    Flatten(),
    Dense(num_classes, activation='softmax')
])


def print_model_details(model):
    model.summary()
    print('\n\n')


print("Model_1")
print_model_details(model_1)
print("Model_2")
print_model_details(model_2)
print("Model_3")
print_model_details(model_3)
print("Model_3_dropout")
print_model_details(model_3_dropout)

epochs = 10
batch_size = 128


def compile_fit_evaluate(model):
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    model.fit(X_train, y_train, batch_size=batch_size, epochs=epochs, validation_split=0.1)
    score = model.evaluate(X_test, y_test, verbose=0)
    print('Loss: ', score[0], 'accuracy:', score[1])


print("Model_1")
compile_fit_evaluate(model_1)
print("Model_2")
compile_fit_evaluate(model_2)
print("Model_3")
compile_fit_evaluate(model_3)
print("Model_3_dropout")
compile_fit_evaluate(model_3_dropout)



