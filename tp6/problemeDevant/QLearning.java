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
	double iteration = 1;
	double nbIteration = 0;
	
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
		double qValeurActuelle = qVal.getVal(depart, a);
		double vitesseApprentissage = (double)1.0/t.get(depart).get(a);
		double facteurActualisation = gamma;
		double valeurOptimaleEstimee = qVal.getValMax(arrivee);
		double valeurApprise = recompense + (facteurActualisation * valeurOptimaleEstimee);
		double nouvelleQValeur = qValeurActuelle + vitesseApprentissage * (valeurApprise - qValeurActuelle);
		qVal.setVal(depart, a, nouvelleQValeur);
	}
	
	public ActionOriente choisirAction(){
		ActionOriente actionEpsilonGreedy;
		//epsilonGreedy va partir de 1 jusque 0 dans le but de faire une exploration totale au debut et
		//en faire de moins en moins par la suite
		double epsilonGreedy = (double) 1.0 - iteration / nbIteration;
		//double epsilonGreedy = 0.8;
		if(Math.random() >= 1-epsilonGreedy){
			actionEpsilonGreedy = qVal.getActionMax(etatCourant);
		}else{
			ArrayList<ActionOriente> listeAction = (ArrayList<ActionOriente>) probleme.allAction();
			actionEpsilonGreedy = listeAction.get((int)(Math.random()*listeAction.size()));
		}
		return actionEpsilonGreedy;
	}
	
	public void effectuerUneIteration(){
		ActionOriente actionEpsilonGreedy = choisirAction();
		StateXYO etatArrivee = probleme.transition(etatCourant, actionEpsilonGreedy).tirage();
		double recompense = probleme.recompense(etatCourant, actionEpsilonGreedy, etatArrivee);
		updateExperience(etatCourant, actionEpsilonGreedy, etatArrivee, recompense);
		etatCourant = etatArrivee;
	}
	
	public void replacerAleatoirement(){
		ArrayList<StateXYO> listeEtat = probleme.allState();
		etatCourant = listeEtat.get((int)(Math.random()*listeEtat.size()));
	}
	
	public void apprendre(long n){
		nbIteration = (double)n;
		sommeQValeurs = new double[(int) (n+1)];
		sommeQValeurs[0] = qVal.somme();
		for (int i = 0; i < n; i++) {
			if(etatCourant.x == -1 && etatCourant.y == -1){
				replacerAleatoirement();
			}
			effectuerUneIteration();
			sommeQValeurs[i+1] = qVal.somme();
			iteration++;
		}
	}

}
