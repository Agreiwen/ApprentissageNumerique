package tp6.problemeDevant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QLearning {
	
	public ProblemeDevant probleme;
	public StateXYO etatCourant;
	protected Map<StateXYO,Map<ActionOriente,Integer>> t;
	public QValeur qVal;
	protected final double gamma = 0.99;
	public double[] sommeQValeurs;
	
	public QLearning(){
		probleme = new ProblemeDevant();
		etatCourant = new StateXYO(0, 3, StateXYO.ORIENTE_EST);
		qVal = new QValeur(probleme);
		Map<ActionOriente,Integer> aux;
		t = new HashMap<StateXYO,Map<ActionOriente,Integer>>();
		ArrayList<StateXYO> etats = probleme.allState();
		ArrayList<ActionOriente> actions = (ArrayList<ActionOriente>) probleme.allAction();
		for (int i = 0; i < etats.size(); i++) {
			aux = new HashMap<ActionOriente, Integer>();
			for (int j = 0; j < actions.size(); j++) {
				aux.put(actions.get(j), 0);
			}
			t.put(etats.get(i), aux);
		}
	}
	
	public void updateExperience(StateXYO depart, ActionOriente a, StateXYO arrivee, double recompense){
		t.get(depart).put(a, t.get(depart).get(a)+1);
		double alpha = (double)1/t.get(depart).get(a);
		//System.out.println(alpha);
		double ancienneQValeur = qVal.getVal(depart, a);
		double nouvelleQValeur = (1-alpha)*ancienneQValeur+alpha*(probleme.recompense(depart, a, arrivee)+gamma*qVal.getValMax(arrivee));
		//TODO Erreur dans la formulette qui fausse tout
		//System.out.println(nouvelleQValeur);
		qVal.setVal(depart, a, nouvelleQValeur);
	}
	
	public ActionOriente choisirAction(){
		ActionOriente actionEpsilonGreedy;
		if(Math.random() > 0.2){
			actionEpsilonGreedy = qVal.getActionMax(etatCourant);
		}else{
			ArrayList<ActionOriente> listeAction = (ArrayList<ActionOriente>) probleme.allAction();
			actionEpsilonGreedy = listeAction.get((int)Math.random()*(listeAction.size()-1));
		}
		return actionEpsilonGreedy;
	}
	
	public void effectuerUneIteration(){
		ActionOriente actionEpsilonGreedy = choisirAction();
		StateXYO etatArrivee = probleme.transition(etatCourant, actionEpsilonGreedy).tirage();
		double recompense = probleme.recompense(etatCourant, actionEpsilonGreedy, etatArrivee);
		updateExperience(etatCourant, actionEpsilonGreedy, etatArrivee, recompense);
		//System.out.println(etatCourant+" "+actionEpsilonGreedy+" "+etatArrivee+" "+recompense);
		etatCourant = etatArrivee;
	}
	
	public void replacerAleatoirement(){
		ArrayList<StateXYO> listeEtat = probleme.allState();
		etatCourant = listeEtat.get((int)Math.random()*(listeEtat.size()-1));
	}
	
	public void apprendre(long n){
		sommeQValeurs = new double[(int) (n+1)];
		sommeQValeurs[0] = qVal.somme();
		for (int i = 0; i < n; i++) {
			if(etatCourant.x == -1 && etatCourant.y == -1){
				replacerAleatoirement();
			}
			effectuerUneIteration();
			sommeQValeurs[i+1] = qVal.somme();
		}
	}

}
