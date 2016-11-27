package  tp6.modele;

import java.util.ArrayList;

/**
 * represente une distribution sur des objets d'un ensemble E
 * 
 * 
 * @author vthomas
 *
 */
public class Distribution<E> {

	/**
	 * la représentation de la distribution
	 * <li>elements représente les valeurs de la distribution
	 * <li>probas les probabilités associées
	 * 
	 * <p>
	 * les probas sont associées à l'element de meme indice
	 */
	public ArrayList<E> elements = new ArrayList<E>();
	public ArrayList<Double> probas = new ArrayList<Double>();

	/**
	 * ajoute un element sur la distribution
	 * 
	 * @param elem
	 *            element à ajouter
	 * @param proba
	 *            probabilité correspondante
	 */
	public void ajouter(E elem, double proba) {
		elements.add(elem);
		probas.add(proba);
	}

	/**
	 * echantillonner ==> effectuer un tirage sur la distribution
	 * 
	 * @return la valeur qui a été échantillonée selon la distribution
	 */
	public E tirage() {
		double somme = 0;
		double rd = Math.random();

		for (int i = 0; i < this.elements.size(); i++) {
			// on ajoute la valeur à la somme
			somme = somme + this.probas.get(i);

			// si rd est plus petit
			if (rd < somme) {
				return (this.elements.get(i));
			}
		}

		throw new Error("probleme echantillonage");
	}

}
