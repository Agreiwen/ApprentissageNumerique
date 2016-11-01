package tp4;

import java.lang.reflect.Array;
import java.util.ArrayList;

import tp4.hmm.Distribution;
import tp4.hmm.HMM;
import tp4.hmm.Observation;
import tp4.hmm.State;

public class MainViterbi {

	public static void main(String[] args) {
		HMM filtrage = new HMM();
		Distribution<State> distribInitiale = new Distribution<>();
		State s0 = new State(0);
		State s1 = new State(1);
		distribInitiale.setProba(s0, 0.5);
		distribInitiale.setProba(s1, 0.5);
		
		Observation o0 = new Observation(false,false);
		Observation o1 = new Observation(true, false);
		Observation o2 = new Observation(false, true);
		Observation o3 = new Observation(true, false);
		Observation o4 = new Observation(false, false);
		ArrayList<Observation> obs = new ArrayList<Observation>();
		obs.add(obs.size(),o0);
		obs.add(obs.size(),o1);
		obs.add(obs.size(),o2);
		obs.add(obs.size(),o3);
		obs.add(obs.size(),o4);
		
		ArrayList<State> viterbi = new ArrayList<>();
		viterbi = filtrage.viterbi(obs, distribInitiale);
		
		System.out.print("[");
		for (int i = viterbi.size()-1; i >=0; i--) {
			String s = (i==0) ? viterbi.get(i).toString() : viterbi.get(i).toString()+", ";
			System.out.print(s);
		}
		System.out.println("]");
	}

}
