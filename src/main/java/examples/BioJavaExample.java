/**
 * Example use of Introspector with the BioJava library.
 * To compile the following file:
 * 1) Un comment the following class.
 * 2) Use the pom-biojava.xml file to compile the project with maven.
 */
/*
package examples;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.transcription.TranscriptionEngine;
import org.biojava.nbio.core.sequence.ProteinSequence;


public class BioJavaExample {
	public static void main(String[] args) throws Exception {
		// Create a DNA sequence
		//DNASequence dna = new DNASequence("ATGCCGTAG");
		String dnaString = "ATGGTGAGGAGGAGGA";
		DNASequence dna = new DNASequence(dnaString);
		// Translate the DNA sequence into a protein sequence
		ProteinSequence protein = dna.getRNASequence().getProteinSequence(TranscriptionEngine.getDefault());
		IntrospectorModel model = new IntrospectorModel("Protein sequence for \""+ dnaString + "\"", protein);
		new IntrospectorView("BioJava Introspection", model);
	}
}

*/