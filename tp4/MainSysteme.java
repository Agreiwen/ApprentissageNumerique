package tp4;

import tp4.hmm.Distribution;
import tp4.hmm.HMM;
import tp4.hmm.State;
import tp4.hmm.Systeme;

public class MainSysteme {

	public static void main(String[] args) {
		HMM hmm = new HMM();
		Distribution<State> distribInitiale = new Distribution<>();
		State s0 = new State(0);
		//State s1 = new State(1);
		distribInitiale.setProba(s0, 1);
		//distribInitiale.setProba(s1, 0.5);
		Systeme s = new Systeme(hmm, distribInitiale);
		
		System.out.println("Distribution initiale : "+s.distributionCourante.toString()+"\n");
		System.out.println("Etat courant : "+s.etatCourant.toString()+"\n");
		
		for (int i = 0; i < 10; i++) {
			System.out.println("------------------------------------------------------------------------------------\n");
			s.evoluerSysteme();
			System.out.println("Etat courant : "+s.etatCourant.toString()+"\n");
			System.out.println("Observation : "+s.lastObservation.toString()+"\n");
			System.out.println("Nouvelle distribution : "+s.distributionCourante.toString()+"\n");
		}
	}

}
