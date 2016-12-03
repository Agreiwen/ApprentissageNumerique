package kNN;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class MainKNN {
	
	LecteurDonnees ld;
	ClassifieurKNN classifieur;
	
	MainKNN(){
		try {
			System.setOut(new PrintStream(new File("test.txt")));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ld = new LecteurDonnees("iris.data.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		classifieur = new ClassifieurKNN(ld.x, ld.y, ld.tailleBaseApp, ld.nbClasse);
		/*System.out.println("Basse d'apprentissage :\n");
		System.out.println(classifieur.affichageBaseApprentissage()+"\n");
		System.out.println("Taille de la base : "+classifieur.m+"\nNombre de classe : "+classifieur.nbClasses+"\n");*/
		classifieur.normaliser();
		/*System.out.println(classifieur.affichageBaseApprentissage()+"\n");
		classifieur.tauxErreur();*/
		classifieur.melangeBaseDonnees();
		try {
			classifieur.creationBlocs(5);
		} catch (ClassifieurKNN e) {
			e.printStackTrace();
		}
		
		classifieur.validationCroisee();
	}

	public static void main(String[] args) {
		new MainKNN();
	}

}
