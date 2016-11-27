package tp6.problemeDevant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QValeur {
	
	protected Map<StateXYO, Map<ActionOriente, Double>> qValeur;
	protected Map<ActionOriente, Double> distributionAction;
	ArrayList<StateXYO> etats;
	ArrayList<ActionOriente> actions;
	
	public QValeur(ProblemeDevant prob){
		etats = prob.allState();
		actions = (ArrayList<ActionOriente>) prob.allAction();
		qValeur = new HashMap<StateXYO, Map<ActionOriente,Double>>();
		for (int i = 0; i < etats.size(); i++) {
			distributionAction = new HashMap<ActionOriente, Double>();
			for (int j = 0; j < actions.size(); j++) {
				distributionAction.put(actions.get(j), 0.0);
			}
			qValeur.put(etats.get(i), distributionAction);
		}
	}
	
	public double getVal(StateXYO s, ActionOriente a){
		return qValeur.get(s).get(a);
	}
	
	public void setVal(StateXYO s, ActionOriente a, double valeur){
		qValeur.get(s).put(a, valeur);
	}

	public ActionOriente getActionMax(StateXYO s){
		ActionOriente res = null;
		double probaMax = Integer.MIN_VALUE;
		for (ActionOriente clef : qValeur.get(s).keySet()) {
			if(probaMax <= qValeur.get(s).get(clef)){
				probaMax = qValeur.get(s).get(clef);
				res = clef;
			}
		}
		return res;
	}
	
	public double getValMax(StateXYO s){
		double probaMax = Integer.MIN_VALUE;
		for (ActionOriente clef : qValeur.get(s).keySet()) {
			if(probaMax < qValeur.get(s).get(clef)){
				probaMax = qValeur.get(s).get(clef);
			}
		}
		return probaMax;
	}
	
	@Override
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append("State | Devant | Droite | Gauche | Best action | \n");
		for(int i = 0; i < etats.size(); i++){
			s.append(etats.get(i).toString()+ " | ");
			for (int j = 0; j < actions.size(); j++) {
				s.append(qValeur.get(etats.get(i)).get(actions.get(j)) + " | ");
			}
			s.append(getActionMax(etats.get(i)).toString() + " |");
			s.append("\n");
		}
		return s.toString();
	}
	
	public double somme(){
		double somme = 0;
		for (StateXYO clefState : qValeur.keySet()) {
			for (ActionOriente clefAction : qValeur.get(clefState).keySet()) {
				somme += qValeur.get(clefState).get(clefAction);
			}
		}
		return somme;
	}
	
	public Map<StateXYO,ActionOriente> getPolitique(){
		HashMap<StateXYO,ActionOriente> res = new HashMap<StateXYO,ActionOriente>();
		for (StateXYO clefState : qValeur.keySet()) {
			res.put(clefState, getActionMax(clefState));
		}
		return res;
	}
}
