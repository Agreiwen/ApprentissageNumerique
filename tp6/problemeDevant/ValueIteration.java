package tp6.problemeDevant;

import java.util.ArrayList;

import tp6.modele.Distribution;

public class ValueIteration {
	
	protected ProblemeDevant probleme;
	public QValeur qVal;
	protected final double gamma = 0.99;
	public double[] sommeQValeurs;
	
	public ValueIteration(){
		probleme = new ProblemeDevant();
		qVal = new QValeur(probleme);
	}
	
	public QValeur appliquerBellman(QValeur oldValue){
		QValeur res = new QValeur(probleme);
		double ancienneQValeurDOuJeVais = 0; //use
		double probaTransition = 0; //use
		double recompense = 0; //use
		double nouvelleProba = 0;
		ArrayList<StateXYO> etats = probleme.allState();
		ArrayList<ActionOriente> actions = (ArrayList<ActionOriente>) probleme.allAction();
		for (int i = 0; i < etats.size(); i++) {
			for (int j = 0; j < actions.size(); j++) {
				nouvelleProba = 0;
				Distribution<StateXYO> dis = probleme.transition(etats.get(i), actions.get(j));
				for (int k = 0; k < dis.elements.size(); k++) {
					probaTransition = dis.probas.get(k);
					recompense = probleme.recompense(etats.get(i), actions.get(j), dis.elements.get(k));
					ancienneQValeurDOuJeVais = oldValue.getValMax(dis.elements.get(k));
					nouvelleProba += (double)(probaTransition*(recompense+(double)((gamma*ancienneQValeurDOuJeVais))));
					//nouvelleProba = (double)Math.round(nouvelleProba * 100) / 100;
				}
				res.setVal(etats.get(i), actions.get(j), nouvelleProba);
			}
		}
		return res;
	}
	
	public void faireUneIteration(){
		sommeQValeurs = new double[2];
		sommeQValeurs[0] = qVal.somme();
		qVal = appliquerBellman(qVal);
		sommeQValeurs[1] = qVal.somme();
	}
	
	public void fairePlusieursIterations(int n){
		sommeQValeurs = new double[n+1];
		sommeQValeurs[0] = qVal.somme();
		for (int i = 0; i < n; i++) {
			qVal = appliquerBellman(qVal);
			sommeQValeurs[i+1] = qVal.somme();
		}
	}
}
