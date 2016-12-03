package kNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ClassifieurKNN extends Exception{
	
	/**
	 * Vecteur d'enregistrement
	 */
	double x[][];
	
	/**
	 *  Vecteur des étiquettes des enregistrements
	 */
	String y[];
	
	/**
	 * Taille de la base d'apprentissage
	 */
	int m;
	
	
	/**
	 * Nombre de classe
	 */
	int nbClasses;
	
	/**
	 * Nombre de voisin à prendre en compte pour la prédiction
	 */
	int k = 3;
	
	/**
	 * Base d'apprentissage divisée en blocs
	 */
	double[] blocs[][];
	
	String blocsEtiquette[][];
	
	/**
	 * Nombre de blocs
	 */
	int nbBlocs;
	
	int nbElementParBloc;
	
	double blocApprentissage[][];
	
	String etiquetteApprentissage[];
	
	double blocValidation[][];
	
	double blocTest[][];
	
	/**
	 * Constructeur qui stock en memoire la base d'apprentissage
	 */
	public ClassifieurKNN(double xApp[][], String yApp[], int mApp, int Q) {
		this.x = xApp;
		this.y = yApp;
		this.m = mApp;
		this.nbClasses = Q;
	}
	
	public ClassifieurKNN(String s) {
		super(s);
	}

	/**
	 * Permet l'affichage de la base d'apprentissage
	 * @return String contenant la base d'apprentissage
	 */
	public String affichageBaseApprentissage(){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[i].length; j++) {
				sb.append(x[i][j]+" ");
			}
			sb.append(y[i]+"\n");
		}
		return sb.toString();
	}
	
	/**
	 * Fonction distance
	 * @param a premier vecteur d'enregistrement
	 * @param b deuxieme vecteur d'enregistrement
	 * @return distance entre les deux vecteurs
	 */
	double distance(double[] a, double[] b) {
		double dist = 0.;
		int dim = a.length;
		for(int i = 0; i < dim; i++){
			dist += (double)Math.abs(b[i]-a[i]);
			
		}
		return dist;
	}
	
	void normaliser(){
		System.out.print("Normalisation de la base d'apprentissage... ");
		double sommeLigne = 0;
		for (int i = 0; i < x.length; i++) {
			sommeLigne = 0;
			for (int j = 0; j < x[i].length; j++) {
				sommeLigne += x[i][j];
			}
			for (int j = 0; j < x[i].length; j++) {
				x[i][j] = x[i][j]/sommeLigne;
			}
		}
		System.out.println("OK\n");
	}
	
	String prediction(double[] xt, int K, int v) {
		double[] distance = new double[blocApprentissage.length];
		for (int i = 0; i < blocApprentissage.length; i++) {
			distance[i] = distance(blocApprentissage[i],xt);
		}
		
		int indice = 0;
		double[][] kMin = new double[K][2];
		for(int i = 0; i < K; i++){
			double min = Integer.MAX_VALUE;
			for(int j = 0; j < distance.length; j++){
				if(distance[j] < min){
					min = distance[j];
					kMin[i][0] = min;
					kMin[i][1] = j;
					indice = j;
				}
			}
			distance[indice] = Integer.MAX_VALUE;
		}
		
		HashMap<String,Integer> comptage = new HashMap<>();
		for (int i = 0; i < 90; i++) {
			if(!comptage.containsKey(etiquetteApprentissage[i])){
				comptage.put(etiquetteApprentissage[i], 0);
			}
		}
		
		int indiceK;
		for(int i = 0; i < kMin.length; i++){
			indiceK = (int)kMin[i][1];
			comptage.put(etiquetteApprentissage[indiceK], comptage.get(etiquetteApprentissage[indiceK])+1);
		}
		
		int maxCorrespondance = Integer.MIN_VALUE;
		String etiquette = null;
		for (String clef : comptage.keySet()) {
			if(comptage.get(clef) > maxCorrespondance){
				maxCorrespondance = comptage.get(clef);
				etiquette = clef;
			}
		}
		return etiquette;
	}
	
	void creationBlocs(int nbBlocs)throws ClassifieurKNN{
		int nbElementParBloc = (int)((double)x.length/(double)nbBlocs);
		if(nbElementParBloc != ((double)x.length/(double)nbBlocs))
			throw new ClassifieurKNN("Impossible de diviser en "+nbBlocs);
		this.nbBlocs = nbBlocs;
		this.nbElementParBloc = nbElementParBloc;
		blocs = new double[nbBlocs][nbElementParBloc][x[0].length];
		for (int i = 0; i < nbBlocs; i++) {
			for (int j = 0; j < nbElementParBloc; j++) {
				blocs[i][j] = x[j+(i*nbElementParBloc)];
			}
		}
		blocsEtiquette = new String[nbBlocs][nbElementParBloc];
		for (int i = 0; i < nbBlocs; i++) {
			for (int j = 0; j < nbElementParBloc; j++) {
				blocsEtiquette[i][j] = y[j+(i*nbElementParBloc)];
			}
		}
	}

	double[][] fusionTableauxApprentissage(double tab1[][], double tab2[][]){
		double[][] res = new double [tab1.length+tab2.length][tab1[0].length];
		for (int i = 0; i < tab1.length; i++) {
			 res[i] = tab1[i];
		}
		for (int j = 0; j < tab2.length; j++) {
			res[tab1.length+j] = tab2[j];
		}
		return res;
	}
	
	String[] fusionTableauxEtiqutte(String tab1[], String tab2[]){
		String[] res = new String [tab1.length+tab2.length];
		for (int i = 0; i < tab1.length; i++) {
			 res[i] = tab1[i];
		}
		for (int j = 0; j < tab2.length; j++) {
			res[tab1.length+j] = tab2[j];
		}
		return res;
	}
	
	void affichageBloc(double[][] bloc){
		for (int i = 0; i < bloc.length; i++) {
			for (int j = 0; j < bloc[i].length; j++) {
				System.out.print(bloc[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	void melangeBaseDonnees(){
		double[][] xMelange = new double[x.length][x[0].length];
		String[] yMelange = new String[y.length];
		ArrayList<Integer> aux = new ArrayList<Integer>();
		for (int i = 0; i < x.length; i++) {
			aux.add(i);
		}
		Collections.shuffle(aux);
		for (int i = 0; i < aux.size(); i++) {
			xMelange[i] = x[aux.get(i)];
			yMelange[i] = y[aux.get(i)];
		}
		x = xMelange;
		y = yMelange;
	}
	
	void validationCroisee(){
		HashMap<Integer, Double> res = new HashMap<Integer, Double>(); 
		int meilleurK = 0;
		double erreurSurBlocValidation;
		ArrayList<Integer> app;
		for (int t = 0; t < nbBlocs; t++) {
			this.blocTest = blocs[t];
			System.out.println("\nBaseTest : "+t+"\n");
			//affichageBloc(blocTest);
			for (int v = 0; v < nbBlocs; v++) {
				if(v != t){
					System.out.println("	BaseValidation : "+v+"\n");
					this.blocValidation = blocs[v];
					//affichageBloc(blocValidation);
					erreurSurBlocValidation = Double.MAX_VALUE;
					for (int k = 3; k <= 8; k++) {
						System.out.println("		Nombre de voisin k : "+k+"\n");
						//System.out.println("			BaseApprentissage : \n");
						app = new ArrayList<Integer>();
						for (int a = 0; a < nbBlocs; a++) {
							if(a != t && a != v){
								app.add(a);
							}
						}
						blocApprentissage = fusionTableauxApprentissage(blocs[app.get(0)], fusionTableauxApprentissage(blocs[app.get(1)], blocs[app.get(2)]));
						etiquetteApprentissage = fusionTableauxEtiqutte(blocsEtiquette[app.get(0)], fusionTableauxEtiqutte(blocsEtiquette[app.get(1)], blocsEtiquette[app.get(2)]));
						this.k = k;
						double erreur = erreurKNN(v);
						if(erreur < erreurSurBlocValidation){
							erreurSurBlocValidation = erreur;
							meilleurK = this.k;
						}
					}
					System.out.println("	Erreur sur blocValidation : "+erreurSurBlocValidation+"% Meilleur k : "+meilleurK+"\n");
				}
			}
		}
	}

	double erreurKNN(int v) {
		int erreur = 0;
		String etiquette, verifEtiquette;
		double[] vecteur = new double[x[0].length];
		for (int i = 0; i < blocValidation.length; i++) {
			vecteur = blocValidation[i];
			etiquette = prediction(vecteur, this.k, v);
			verifEtiquette = y[nbElementParBloc*v+i];
			if(!etiquette.equals(verifEtiquette))
				erreur ++;
		}
		return ((double)erreur/blocValidation.length)*100;
	}
	
	double perfKNN(int t, int k){
		int erreur = 0;
		String etiquette, verifEtiquette;
		double[] vecteur = new double[x[0].length];
		for (int i = 0; i < blocTest.length; i++) {
			vecteur = blocTest[i];
			etiquette = prediction(vecteur, k, t);
			verifEtiquette = y[nbElementParBloc*t+i];
			if(!etiquette.equals(verifEtiquette))
				erreur ++;
		}
	return ((double)erreur/blocTest.length)*100;
	}
}
