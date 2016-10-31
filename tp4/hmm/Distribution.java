package tp4.hmm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * permet de g�rer une densit� de probabilit� sur des Objets E quelconques
 * 
 * @author vthomas
 * 
 */
public class Distribution<E> {

	/**
	 * liste de probabilit� discretes Associe aux elements une densit�
	 */
	private Map<E, Double> prob;

	/**
	 * retourne la proba associee a l'element passe en parametre
	 */
	public double getProba(E element) {
		// si l'element est dans la densite, on retourne la proba
		if (this.prob.containsKey(element))
			return (this.prob.get(element));

		// element n'exite pas ==> proba nulle (matrice creuses)
		return (0);
	}

	/**
	 * modifie la proba associee a l'element passe en parametre
	 */
	public void setProba(E element, double p) {
		this.prob.put(element, p);
	}

	/**
	 * Constructeur genere une densit� vide
	 * 
	 * attention, ce n'est pas une vraie densit� (somme des proba != 1)
	 * 
	 * @liste liste des �tats de la densit�
	 */
	public Distribution() {
		prob = new HashMap<E, Double>();
	}

	/**
	 * Constructeur genere une densit� avec les memes �tats mais probabilit�s
	 * nulles
	 * 
	 * @param liste
	 *            des �tats de la densit�
	 */
	public Distribution(Distribution<E> d) {
		prob = new HashMap<E, Double>();

		// creer les probabilit�s constantes � chaque �tat
		for (E s : d.prob.keySet()) {
			prob.put(s, 0.);
		}

	}

	/**
	 * permet de normaliser une densit� de probabilite (somme des proba=1)
	 */
	public void normalise() {
		double norme = calculNorme();
		try {
			for (E clef : prob.keySet()) {
				prob.put(clef, (double)getProba(clef)/(double)norme);
			}
		} catch (Exception e) {
			// Peut etre un truc a faire
			e.printStackTrace();
		}
	}

	/**
	 * permet de calculer la norme d'une densite en thoerie retourne 1 mais peut
	 * servir pour normaliser
	 * 
	 * @return somme des probas de la densit�
	 */
	public double calculNorme() {
		double norme = 0;
		try {
			for (E clef : prob.keySet()){
			  norme += getProba(clef);
			}
		} catch (Exception e) {
			// Peut etre un truc a faire
			e.printStackTrace();
		}
		return norme;
	}

	/**
	 * fait un tirage aleatoire selon cette densit�
	 * 
	 * @return l'indice qui aura �t� selectionn�
	 */
	public E tirage() {
		double random = (Math.random()*10)/10;
		double borneInf = 0;
		double borneSup = 0;
		E element = null;
		for (E clef : prob.keySet()) {
			if(getProba(clef) != 0){
				borneInf = borneSup;
				borneSup = borneSup + getProba(clef);
				if(random >= borneInf && random <= borneSup){
					element = clef;
				}
			}
		}
		return element;
	}

	/**
	 * methode toString
	 */
	public String toString() {
		String res = "";
		for (E clef : this.prob.keySet()) {
			res += clef + "->" + (((int) (this.prob.get(clef) * 1000)) * 1. / 1000);
			res += ", ";
		}
		return (res);
	}

}
