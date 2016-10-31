package tp4.hmm;

import java.util.HashMap;
import java.util.Map;

public class ProcessusObservation {

	/**
	 * les donnees du probleme
	 */
	static double VOIR_SI_PRESENT = 0.9;
	static double VOIR_SI_ABSENT = 0.2;

	/**
	 * matrice d'observation qui associe à un état et une distribution sur les
	 * observations obtenues dans cet état
	 * <p>
	 * <ul>
	 * <li>clef: etat s_t
	 * <li>valeur : distribution sur O_t => P(O_t/S_t)
	 * </ul>
	 * <p>
	 */
	public Map<State, Distribution<Observation>> observation;

	/**
	 * construction de la matrice d'observation
	 */
	public ProcessusObservation() {
		this.observation = new HashMap<>();
		State s0 = new State(0);
		State s1 = new State(1);
		State s2 = new State(2);
		State s3 = new State(3);
		State s4 = new State(4);
		State s5 = new State(5);
		State s6 = new State(6);
		State s7 = new State(7);
		
		Distribution<Observation> d0 = new Distribution<Observation>();
		Distribution<Observation> d1 = new Distribution<Observation>();
		Distribution<Observation> d2 = new Distribution<Observation>();
		Distribution<Observation> d3 = new Distribution<Observation>();
		Distribution<Observation> d4 = new Distribution<Observation>();
		Distribution<Observation> d5 = new Distribution<Observation>();
		Distribution<Observation> d6 = new Distribution<Observation>();
		Distribution<Observation> d7 = new Distribution<Observation>();
		
		Observation o1 = new Observation(true, true);
		Observation o2 = new Observation(false, false);
		Observation o3 = new Observation(true, false);
		Observation o4 = new Observation(false, true);
		
		d0.setProba(o1,VOIR_SI_ABSENT*VOIR_SI_ABSENT);
		d0.setProba(o2, (1-VOIR_SI_ABSENT)*(1-VOIR_SI_ABSENT));
		d0.setProba(o3, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		d0.setProba(o4, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		observation.put(s0, d0);
		
		d1.setProba(o1,VOIR_SI_PRESENT*VOIR_SI_ABSENT);
		d1.setProba(o2, (1-VOIR_SI_PRESENT)*(1-VOIR_SI_ABSENT));
		d1.setProba(o3, VOIR_SI_PRESENT*(1-VOIR_SI_ABSENT));
		d1.setProba(o4, (1-VOIR_SI_PRESENT)*VOIR_SI_ABSENT);
		observation.put(s1, d1);
		
		d2.setProba(o1,VOIR_SI_ABSENT*VOIR_SI_ABSENT);
		d2.setProba(o2, (1-VOIR_SI_ABSENT)*(1-VOIR_SI_ABSENT));
		d2.setProba(o3, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		d2.setProba(o4, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		observation.put(s2, d2);
		
		d3.setProba(o1,VOIR_SI_ABSENT*VOIR_SI_ABSENT);
		d3.setProba(o2, (1-VOIR_SI_ABSENT)*(1-VOIR_SI_ABSENT));
		d3.setProba(o3, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		d3.setProba(o4, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		observation.put(s3, d3);
		
		d4.setProba(o1,VOIR_SI_ABSENT*VOIR_SI_PRESENT);
		d4.setProba(o2, (1-VOIR_SI_ABSENT)*(1-VOIR_SI_PRESENT));
		d4.setProba(o3, VOIR_SI_ABSENT*(1-VOIR_SI_PRESENT));
		d4.setProba(o4, (1-VOIR_SI_ABSENT)*VOIR_SI_PRESENT);
		observation.put(s4, d4);
		
		d5.setProba(o1,VOIR_SI_ABSENT*VOIR_SI_ABSENT);
		d5.setProba(o2, (1-VOIR_SI_ABSENT)*(1-VOIR_SI_ABSENT));
		d5.setProba(o3, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		d5.setProba(o4, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		observation.put(s5, d5);
		
		d6.setProba(o1,VOIR_SI_ABSENT*VOIR_SI_ABSENT);
		d6.setProba(o2, (1-VOIR_SI_ABSENT)*(1-VOIR_SI_ABSENT));
		d6.setProba(o3, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		d6.setProba(o4, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		observation.put(s6, d6);
		
		d7.setProba(o1,VOIR_SI_ABSENT*VOIR_SI_ABSENT);
		d7.setProba(o2, (1-VOIR_SI_ABSENT)*(1-VOIR_SI_ABSENT));
		d7.setProba(o3, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		d7.setProba(o4, VOIR_SI_ABSENT*(1-VOIR_SI_ABSENT));
		observation.put(s7, d7);
	}

	/**
	 * 
	 * matrice d'observation retourne la probabilité d'observer o dans l'état s
	 * 
	 * @param o
	 *            observation possible
	 * @param s
	 *            état reel
	 * @return probabilité d'observer o sachant s
	 */
	public double probaObservation(Observation o, State s) {
		double proba;
		try {
			proba = this.observation.get(s).getProba(o);
		} catch (Exception e) {
			System.out.println("catch");
			proba = 0;
		}
		return proba;
	}

	/**
	 * permet d'observer a partir d'un etat
	 * 
	 * @param depart
	 *            etat a partir duquel on observer
	 * @return observation faite
	 */
	public Observation observer(State depart) {
		Observation res = null;
		try {
			res = this.observation.get(depart).tirage();
		} catch (Exception e) {
			// Peut etre un truc a faire
			e.printStackTrace();
		}
		return res;
	}

}
