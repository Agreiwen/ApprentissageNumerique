package tp2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ModeleDeLangage {
	//Map pour le trigramme
	Map<String, Map<String, Map<String, Integer>>> mapDebut;
	Map<String, Map<String, Integer>> mapIntermediaireTrigramme;
	Map<String, Integer> distributionTrigramme;
	//Map pour le bigramme
	Map<String, Map<String, Integer>> mapBigramme;
	Map<String, Integer> distributionBigramme;
	String fichier;
	int tailleFichier = 0;
	String[] musique;
	
	public ModeleDeLangage(String fichier) {
		mapDebut = new HashMap<>();
		mapIntermediaireTrigramme = new HashMap<>();
		distributionTrigramme = new HashMap<>();
		mapBigramme = new HashMap<>();
		distributionBigramme = new HashMap<>();
		this.fichier = fichier;
	}
	
	public void lectureFichier() throws IOException {
        String st;
        int nbLigne = 0;
        BufferedReader br1, br2;
        br1 = new BufferedReader(new FileReader(fichier));
 
        System.out.print("Lecture fichier... ");
        while ((st = br1.readLine()) != null) {
            nbLigne++;    
        }
        br1.close();
        musique = new String[nbLigne];
        br2 = new BufferedReader(new FileReader(fichier));
        
        int compt = 0;
        while ((st = br2.readLine()) != null) {
            musique[compt] = st.toString();
            compt++;
        }
        br2.close();
        System.out.println("OK\n");
    }
	
	public void remplissageMapTrigramme(){
		String mot1, mot2, mot3;
		for (int i = 0; i < musique.length - 2; i++) {
			mot1 = musique[i];
			mot2 = musique[i+1];
			mot3 = musique[i+2];
			mapIntermediaireTrigramme = new HashMap<>();
			distributionTrigramme = new HashMap<>();
			if(mapDebut.containsKey(mot1)){
				if(mapDebut.get(mot1).containsKey(mot2)){
					if(mapDebut.get(mot1).get(mot2).containsKey(mot3)){
						mapDebut.get(mot1).get(mot2).put(mot3, mapDebut.get(mot1).get(mot2).get(mot3)+1);
					}else{
						mapDebut.get(mot1).get(mot2).put(mot3, 1);
					}
				}else{
					distributionTrigramme.put(mot3, 1);
					mapDebut.get(mot1).put(mot2, distributionTrigramme);
				}
			}else{
				distributionTrigramme.put(mot3, 1);
				mapIntermediaireTrigramme.put(mot2, distributionTrigramme);
				mapDebut.put(mot1, mapIntermediaireTrigramme);
			}
		}
	}
	
	public void remplissageMapBigramme(){
		String mot1, mot2;
		for (int i = 0; i < musique.length - 1; i++) {
			mot1 = musique[i];
			mot2 = musique[i+1];
			distributionBigramme = new HashMap<>();
			if(mapBigramme.containsKey(mot1)){
				if(mapBigramme.get(mot1).containsKey(mot2)){
					mapBigramme.get(mot1).put(mot2, mapBigramme.get(mot1).get(mot2)+1);
				}else{
					mapBigramme.get(mot1).put(mot2, 1);
				}
			}else{
				distributionBigramme.put(mot2, 1);
				mapBigramme.put(mot1, distributionBigramme);
			}
		}
	}
	
	public void afficheMatriceTransitionTrigramme() {
		for (Entry<String, Map<String, Map<String, Integer>>> entree : mapDebut.entrySet())
		{
		  System.out.println(entree.getKey() + " : "+entree.getValue());
		}
    }
	
	public void afficheMatriceTransitionBigramme() {
		for (Entry<String, Map<String, Integer>> entree : mapBigramme.entrySet())
		{
		  System.out.println(entree.getKey() + " : "+entree.getValue());
		}
    }
	
	public double proba3Mots(String mot3, String mot1, String mot2){
		//mot3 sachant mot1 et mot2
		int numerateur, denominateur = 0;
		double proba;
		try{
			numerateur = mapDebut.get(mot1).get(mot2).get(mot3);
		}
		catch (Exception e){
			numerateur = 0;
		}
		try{
			for (String clef : mapDebut.get(mot1).get(mot2).keySet()) {
				denominateur += mapDebut.get(mot1).get(mot2).get(clef);
			}
		}
		catch (Exception e){
			numerateur = 0;
			denominateur = 1;
		}
		proba = (double)numerateur/(double)denominateur;
		System.out.println("P("+mot3+"|"+mot1+","+mot2+") = "+proba);
		//System.out.println("Numerateur : "+numerateur+" / Denominateur : "+denominateur);
		return proba;
	}
	
	public double proba2Mots(String mot2, String mot1){
		//mot2 sachant mot1
		int numerateur, denominateur = 0;
		double proba;
		try{
			numerateur = mapBigramme.get(mot1).get(mot2);
		}
		catch (Exception e){
			numerateur = 0;
		}
		try{
			for (String clef : mapBigramme.get(mot1).keySet()) {
				denominateur += mapBigramme.get(mot1).get(clef);
			}
		}
		catch (Exception e){
			numerateur = 0;
			denominateur = 1;
		}
		proba = (double)numerateur/(double)denominateur;
		System.out.println("P("+mot2+"|"+mot1+") = "+proba);
		//System.out.println("Numerateur : "+numerateur+" / Denominateur : "+denominateur);
		return proba;
	}
	
	public double proba1Mot(String mot){
		int numerateur = 0, denominateur = musique.length;
		double proba;
		for (int i = 0; i < musique.length; i++) {
			if(musique[i].equals(mot)){
				numerateur++;
			}
		}
		proba = (double)numerateur/(double)denominateur;
		System.out.println("P("+mot+") = "+proba);
		//System.out.println("Numerateur : "+numerateur+" / Denominateur : "+denominateur);
		return proba;
	}
	
	public double probaSuiteDeMot(String[] tab){
		double proba = 0;
		proba += proba1Mot(tab[0]);
		proba *= proba2Mots(tab[1], tab[0]);
		for (int i = 0; i < tab.length - 2; i++) {
			proba *= proba3Mots(tab[i+2], tab[i], tab[i+1]);
		}
		return proba;
	}
	
	public String prochainSachant2Mots(String mot1, String mot2){
		String mot = "";
		int proba = 0;
		try{
			for (String clef : mapDebut.get(mot1).get(mot2).keySet()) {
				if(mapDebut.get(mot1).get(mot2).get(clef) > proba){
					mot = clef;
					proba = mapDebut.get(mot1).get(mot2).get(clef);
				}else if(mapDebut.get(mot1).get(mot2).get(clef) == proba){
					if(Math.round(Math.random()) == 1){
						mot = clef;
						proba = mapDebut.get(mot1).get(mot2).get(clef);
					}
				}
			}
		}
		catch(Exception e){
			mot = "Il n'y a pas \""+mot1+"\" puis \""+mot2+"\" dans le fichier texte.";
			System.out.println(mot);
			System.exit(0);
		}
		return mot;
	}
	
	public void chantomatic(String mot1, String mot2){
		String mot3 = "";
		System.out.println(mot1);
		System.out.println(mot2);
		for (int i = 0; i < 100; i++) {
			mot3 = prochainSachant2Mots(mot1, mot2);
			System.out.println(mot3);
			mot1 = mot2;
			mot2 = mot3;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Analyse Numerique - TP2\n");
		ModeleDeLangage m = new ModeleDeLangage("Beyonce.txt.traite.txt");
		try {
			m.lectureFichier();
		} catch (IOException e) {
			e.printStackTrace();
		}
		m.remplissageMapTrigramme();
		m.remplissageMapBigramme();
		
		//m.afficheMatriceTransitionBigramme();
		//m.afficheMatriceTransition();
		//System.out.println("\n"+m.proba3Mots("bien", "ca", "va"));
		//System.out.println(m.prochainSachant2Mots("va", "bien"));
		//System.out.println();
		//m.chantomatic("i", "found");
		String[] tab = new String[5];
		tab[0] = "i";
		tab[1] = "found";
		tab[2] = "a";
		tab[3] = "way";
		tab[4] = "to";
		System.out.println("P("+tab[0]+","+tab[1]+","+tab[2]+","+tab[3]+","+tab[4]+") = "+m.probaSuiteDeMot(tab));
	}

}
