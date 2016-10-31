package tp4.hmm;

import java.util.Observable;

/**
 * un systeme represente une instance d'un systeme modelise par un HMM.
 * <p>
 * un systeme est défini par
 * <ul>
 * <li>le modele sous-jacent (HMM)
 * <li>l'etat reel courant du systeme
 * <li>la connaissance qu'on a sur l'etat du systeme
 * </ul>
 *
 */
public class Systeme extends Observable {

	/**
	 * attribut modele hmm
	 */
	private HMM modele;

	/**
	 * la distrubution courante
	 */
	public Distribution<State> distributionCourante;

	/**
	 * la derniere observation vue
	 */
	public Observation lastObservation;

	/**
	 * etat actuel
	 */
	public State etatCourant;

	/**
	 * construit un systeme à partir d'un modele de HMM
	 * 
	 * @param hmm
	 *            le modele a partir duquel construire un systeme
	 * @param initiale
	 *            la distribution initiale sur l'etat
	 */
	public Systeme(HMM hmm, Distribution<State> initiale) {
		this.modele = hmm;
		this.distributionCourante = initiale;
		this.etatCourant = initiale.tirage();
	}

	/**
	 * permet de faire evoluer l'etat courant du systeme d'un pas de temps et
	 * recupere l'observation
	 * <ul>
	 * <li>effectue une transition
	 * <li>effectue une observation
	 * </ul>
	 * 
	 * @return couple etat t+1 , observation a l'instant t+1
	 */
	public Observation evoluerEtatReel() {
		Observation res = null;
		this.etatCourant = modele.evoluerEtat(etatCourant);
		res = this.modele.observer(etatCourant);
		return res;
	}

	/**
	 * met a jour le belief apres avoir reçu une observation
	 * 
	 * @param o
	 *            observation reçue
	 */
	public void mettreAjourBelieef(Observation o) {
		this.distributionCourante = this.modele.maJTransition(distributionCourante);
		this.distributionCourante = this.modele.MaJObservation(distributionCourante, o);
	}

	/**
	 * permet de faire evoluer l'etat reel et la connaissance sur l'etat
	 */
	public void evoluerSysteme() {
		this.lastObservation = evoluerEtatReel();
		mettreAjourBelieef(this.lastObservation);
		setChanged();
		notifyObservers();
	}

}
