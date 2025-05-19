/**
 * Example use of Introspector with the Deeplearning4j (DL4J) library.
 * To compile the following file:
 * 1) Uncomment the following class.
 * 2) Use the pom-dl4j.xml file to compile the project with maven.
 */

/*
package examples;


import introspector.view.IntrospectorView;
import org.deeplearning4j.nn.conf.*;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.conf.layers.recurrent.Bidirectional;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;


public class DL4JExample {

	public static void main(String[] args) {
		// Define the network configuration
		MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
				.seed(123) // Random seed for reproducibility
				.weightInit(WeightInit.XAVIER) // Weight initialization
				.updater(new Adam(0.001)) // Adam optimizer with learning rate 0.001
				.list()
				// Embedding Layer
				.layer(0, new EmbeddingLayer.Builder()
						.nIn(10000) // Vocabulary size (number of unique integers/tokens)
						.nOut(256) // Embedding dimension (output size)
						.activation(Activation.IDENTITY) // No activation (raw embeddings)
						.build())
				// 8 Bidirectional LSTM layers
				.layer(1, new Bidirectional(new LSTM.Builder()
						.nIn(256) // Input size (256, from the embedding layer)
						.nOut(128) // Number of LSTM units (128 for each direction)
						.activation(Activation.TANH) // Activation function
						.build()))
				.layer(2, new Bidirectional(new LSTM.Builder()
						.nIn(256) // Input size (256, since bidirectional doubles the output)
						.nOut(128)
						.activation(Activation.TANH)
						.build()))
				.layer(3, new Bidirectional(new LSTM.Builder()
						.nIn(256)
						.nOut(128)
						.activation(Activation.TANH)
						.build()))
				.layer(4, new Bidirectional(new LSTM.Builder()
						.nIn(256)
						.nOut(128)
						.activation(Activation.TANH)
						.build()))
				.layer(5, new Bidirectional(new LSTM.Builder()
						.nIn(256)
						.nOut(128)
						.activation(Activation.TANH)
						.build()))
				.layer(6, new Bidirectional(new LSTM.Builder()
						.nIn(256)
						.nOut(128)
						.activation(Activation.TANH)
						.build()))
				.layer(7, new Bidirectional(new LSTM.Builder()
						.nIn(256)
						.nOut(128)
						.activation(Activation.TANH)
						.build()))
				.layer(8, new Bidirectional(new LSTM.Builder()
						.nIn(256)
						.nOut(128)
						.activation(Activation.TANH)
						.build()))
				// 2 Fully Connected (Dense) Layers
				.layer(9, new DenseLayer.Builder()
						.nIn(256) // Input size (256, since bidirectional doubles the output)
						.nOut(512) // Number of neurons
						.activation(Activation.RELU) // ReLU activation
						.build())
				.layer(10, new DenseLayer.Builder()
						.nIn(512)
						.nOut(512)
						.activation(Activation.RELU)
						.build())
				// Dropout Layer
				.layer(11, new DropoutLayer.Builder(0.2) // Dropout factor of 0.2
						.build())
				// Output Layer
				.layer(12, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
						.nIn(512)
						.nOut(21) // Number of output classes
						.activation(Activation.SOFTMAX) // Softmax for classification
						.build())
				.build();

		// Create and initialize the network
		MultiLayerNetwork network = new MultiLayerNetwork(conf);
		network.init();

		new IntrospectorView("DJ4J Introspection", "Deep Neural Network", network);

		// Add a listener to print the loss during training
		network.setListeners(new ScoreIterationListener(10));

		// Print the network architecture
		System.out.println(network.summary());
	}
}

*/