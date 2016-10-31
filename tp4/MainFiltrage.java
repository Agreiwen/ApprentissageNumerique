package tp4;

import java.util.ArrayList;

import tp4.hmm.Distribution;
import tp4.hmm.HMM;
import tp4.hmm.Observation;
import tp4.hmm.State;

public class MainFiltrage {

	public static void main(String[] args) {
		HMM filtrage = new HMM();
		Distribution<State> distribInitiale = new Distribution<>();
		State s0 = new State(0);
		State s1 = new State(1);
		distribInitiale.setProba(s0, 1);
		//distribInitiale.setProba(s1, 0.5);
		
		Observation o1 = new Observation(true, false);
		Observation o2 = new Observation(false, true);
		Observation o3 = new Observation(true, false);
		ArrayList<Observation> l = new ArrayList<Observation>();
		l.add(o1);
		l.add(o2);
		l.add(o3);
		
		System.out.println("Agent en s0 à t=0 puis suite d\"observation suivante :");
		for (int i = 0; i < l.size(); i++) {
			System.out.print(l.get(i).toString()+"à t="+(i+1)+", ");
		}
		System.out.println("\n");
		System.out.println(filtrage.filtrage(distribInitiale, l));
		
		System.out.println(filtrage.observer(s0));
	}

}
