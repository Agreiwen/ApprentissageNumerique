package tp4;

import tp4.hmm.Distribution;
import tp4.hmm.HMM;
import tp4.hmm.Observation;
import tp4.hmm.State;

public class MainCorrection {

	public static void main(String[] args) {
		HMM prediction = new HMM();
		Distribution<State> distribInitiale = new Distribution<>();
		State s0 = new State(0);
		State s1 = new State(1);
		distribInitiale.setProba(s0, 0.5);
		distribInitiale.setProba(s1, 0.5);
		
		System.out.println("Distribution S_t=0 :\n");
		System.out.println(distribInitiale.toString());
		
		Distribution<State> distribApres;
		distribApres = prediction.maJTransition(distribInitiale);
		System.out.println("\nDistribution S_t=1 :\n");
		System.out.println(distribApres.toString());
		
		Observation o = new Observation(true, false);
		distribApres = prediction.MaJObservation(distribApres, o);
		System.out.println("\nDistribution S_t=1 avec O_t=1 = (vrai,faux) :\n");
		System.out.println(distribApres.toString());
	}

}
