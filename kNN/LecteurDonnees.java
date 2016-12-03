package kNN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LecteurDonnees {
	
	double x[][];
	String y[];
	int tailleBaseApp;
	int nbClasse;
	
	public LecteurDonnees(String fichier) throws IOException {
		String st;
        int nbLigne = 0;
        int ligne = 0;
        String[] separated;
        BufferedReader br1, br2;
        br1 = new BufferedReader(new FileReader(fichier));
 
        System.out.print("Lecture fichier... ");
        while ((st = br1.readLine()) != null) {
            nbLigne++;
        }
        br1.close();
        
        tailleBaseApp = nbLigne;
        x = new double[tailleBaseApp][4];
        y = new String[tailleBaseApp];
        
        br2 = new BufferedReader(new FileReader(fichier));
        while ((st = br2.readLine()) != null) {
            separated = st.split(",");
            
			for (int j = 0; j < separated.length-1; j++) {
				x[ligne][j] = Double.parseDouble(separated[j]);
			}
			y[ligne] = separated[separated.length-1];
			ligne++;
        }
        br2.close();
        nbClasse();
        System.out.println("OK\n");
	}
	
	public void nbClasse(){
		ArrayList<String> listClasse = new ArrayList<String>();
		for (int i = 0; i < tailleBaseApp; i++) {
			if(!listClasse.contains(this.y[i])){
				listClasse.add(this.y[i]);
			}
		}
		this.nbClasse = listClasse.size();
	}
	
}
