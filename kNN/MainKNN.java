package kNN;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainKNN {
	
	LecteurDonnees ld;
	ClassifieurKNN classifieur;
	
	public MainKNN(){
	
		JFileChooser chooser = new JFileChooser(new File("."));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Database File", "txt", "md", "doc");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getPath());
		}
			try {
				ld = new LecteurDonnees(chooser.getSelectedFile().getPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		classifieur = new ClassifieurKNN(ld.x, ld.y, ld.tailleBaseApp, ld.nbClasse);
		classifieur.normaliser();
		classifieur.melangeBaseDonnees();
		
		try {
			classifieur.creationBlocs(8);
		} catch (ClassifieurKNN e) {
			e.printStackTrace();
		}
		
		classifieur.validationCroisee();
	}

	public static void main(String[] args) {
		new MainKNN();
	}

}
