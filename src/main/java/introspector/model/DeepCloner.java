/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * DeepCloner provides a method to create a deep copy of a Node tree.
 */
public class DeepCloner {

	/**
	 * The Kryo object used to serialize and deserialize the objects to undertake deep cloning.
	 */
	private static final Kryo kryo = new Kryo();

	static {
		// Use Objenesis for object instantiation
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		kryo.setRegistrationRequired(false);
	}

	/**
	 * Deep clones a Node tree.
	 * @param original the original Node tree.
	 * @return a deep copy of the original Node tree.
	 */
	public static Node deepClone(Node original) {
		// Serialize the object
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Output output = new Output(outputStream);
		kryo.writeObject(output, original);
		output.close();
		// Deserialize the object
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		Input input = new Input(inputStream);
		Node clonedObject = kryo.readObject(input, (Class<Node>)original.getClass());
		input.close();
		return clonedObject;
	}

	/**
	 * Deep clones an object.
	 * @param original the original object.
	 * @param <T> the type of the object.
	 * @return a deep copy of the original object.
	 */
	public static <T> T deepClone(T original) {
		Node originalNode = NodeFactory.createNode("root", original);
		Node clonedNode = deepClone(originalNode);
		return (T) clonedNode.getValue();
	}

}
