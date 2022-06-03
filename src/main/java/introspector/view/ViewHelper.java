/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;

import introspector.controller.ExpandTreeController;
import introspector.controller.ExportTreeController;
import introspector.controller.NodeSelectedController;
import introspector.controller.TreeMouseClickController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Utility class that provides helper functionalities for the application view.
 */
public class ViewHelper extends JFrame {

	/**
	 * Utility class
	 */
	private  ViewHelper() {}

	/**
	 * Writes an error message in a label for SECONDS_SHOWING_MESSAGES
	 * @param label the label where the message is written
	 * @param message the message
	 */
	public static void showErrorMessageInStatus(JLabel label, String message) {
		label.setForeground(Color.RED);
		ViewHelper.writeInStatusLabel(label, message);
	}

	/**
	 * Writes a message in a label for SECONDS_SHOWING_MESSAGES
	 * @param label the label where the message is written
	 * @param message the message
	 */
	public static void showMessageInStatus(JLabel label, String message) {
		label.setForeground(Color.BLACK);
		ViewHelper.writeInStatusLabel(label, message);
	}

	/**
	 * In seconds, for how long messages are shown to the user.
	 */
	public static final int SECONDS_SHOWING_MESSAGES = 5;

	/**
	 * Writes message in a label for SECONDS_SHOWING_MESSAGES
	 * @param label the label where the message is written
	 * @param message the message
	 */
	private static void writeInStatusLabel(JLabel label, String message) {
		label.setText("  " + message);
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(SECONDS_SHOWING_MESSAGES);
			} catch (InterruptedException ignored) {
			} finally {
				label.setText("  ");
			}
		}).start();
	}

	/**
	 * Shows the dialog window to select a file, returning the name of the file selected.
	 * @param description description of the type of files to be opened
	 * @param extensions all the file extensions to be used as filter
	 * @return the absolute path of the selected file; null if no file was selected.
	 */
	public static Optional<String> selectFileFromDisk(JFrame parent, String description, String... extensions) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(description, extensions));
		int result = fileChooser.showSaveDialog(parent);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			return Optional.of(selectedFile.getAbsolutePath());
		}
		return Optional.empty();
	}

	/**
	 * Gets the file path for a resource
	 * @param resourceName the name of the resource
	 * @return the whole path where the resource is placed
	 */
	public static String getResourceNamePath(String resourceName) {
		URL resource  = ViewHelper.class.getClassLoader().getResource(resourceName);
		if(resource == null)
			return null;
		return new File(resource.getFile()).getAbsolutePath();
	}


}
