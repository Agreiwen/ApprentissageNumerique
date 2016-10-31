package tp4.hmm;

import java.util.HashMap;
import java.util.Map;

public class ProcessusTransition {

	/**
	 * matrice de transition qui associe à un état de départ la densite
	 * d'arriver sur l'etat d'arrivee
	 * 
	 * <p>
	 * clef :etat de depart s_t<br>
	 * valeur : densite sur s_t+1 = P(S_t+1/S_t) <br>
	 * 
	 */
	public Map<State, Distribution<State>> transition;

	/**
	 * creation des transitions correspondant au hamster
	 */
	public ProcessusTransition() {
		this.transition = new HashMap<>();
		State s0 = new State(0);
		State s1 = new State(1);
		State s2 = new State(2);
		State s3 = new State(3);
		State s4 = new State(4);
		State s5 = new State(5);
		State s6 = new State(6);
		State s7 = new State(7);
		
		Distribution<State> d0 = new Distribution<State>();
		Distribution<State> d1 = new Distribution<State>();
		Distribution<State> d2 = new Distribution<State>();
		Distribution<State> d3 = new Distribution<State>();
		Distribution<State> d4 = new Distribution<State>();
		Distribution<State> d5 = new Distribution<State>();
		Distribution<State> d6 = new Distribution<State>();
		Distribution<State> d7 = new Distribution<State>();
		
		d0.setProba(s7, 0.1);
		d0.setProba(s0, 0.2);
		d0.setProba(s1, 0.7);
		transition.put(s0, d0);
		
		d1.setProba(s0, 0.1);
		d1.setProba(s1, 0.2);
		d1.setProba(s2, 0.7);
		transition.put(s1, d1);
		
		d2.setProba(s1, 0.1);
		d2.setProba(s2, 0.2);
		d2.setProba(s3, 0.7);
		transition.put(s2, d2);
		
		d3.setProba(s2, 0.1);
		d3.setProba(s3, 0.2);
		d3.setProba(s4, 0.7);
		transition.put(s3, d3);
		
		d4.setProba(s3, 0.1);
		d4.setProba(s4, 0.2);
		d4.setProba(s5, 0.7);
		transition.put(s4, d4);
		
		d5.setProba(s4, 0.1);
		d5.setProba(s5, 0.2);
		d5.setProba(s6, 0.7);
		transition.put(s5, d5);
		
		d6.setProba(s5, 0.1);
		d6.setProba(s6, 0.2);
		d6.setProba(s7, 0.7);
		transition.put(s6, d6);
		
		d7.setProba(s6, 0.1);
		d7.setProba(s7, 0.2);
		d7.setProba(s0, 0.7);
		transition.put(s7, d7);
	}

	/**
	 * matrice de transition P(arrivee / depart)
	 * 
	 * @param sArrivee
	 *            etat d'arrivée
	 * @param sDepart
	 *            etat de depart
	 * @return probabilité d'arrivée en sArrivée etant donné qu'on se trouve en
	 *         sDepart
	 */
	public double probaTransition(State sDepart, State sArrivee) {
		double proba;
		try {
			proba = this.transition.get(sDepart).getProba(sArrivee);
		} catch (Exception e) {
			proba = 0;
		}
		return proba;
	}

	/**
	 * retourne un etat d'arrivee a partir d'un etat de depart en
	 * echantillonnant le modele de transition
	 * 
	 * @param s
	 *            etat de depart
	 * @return etat d'arrivee
	 */
	public State evoluerEtat(State depart) {
		State res =  null;
		try {
			res = this.transition.get(depart).tirage();
		} catch (Exception e) {
			// Peut etre un truc a faire
			e.printStackTrace();
		}
		return res;
	}

}
