package tp6;

import java.util.HashMap;

import tp6.problemeDevant.ActionOriente;
import tp6.problemeDevant.ProblemeDevant;
import tp6.problemeDevant.StateXYO;
import tp6.problemeDevant.ValueIteration;

public class MainPolitique {

	public static void main(String[] args) {
		// Recherche de la politique optimale
		ValueIteration vi = new ValueIteration();
		vi.fairePlusieursIterations(100);
		HashMap<StateXYO, ActionOriente> politiqueOptimale = (HashMap<StateXYO, ActionOriente>) vi.qVal.getPolitique();
		
		// Etat courant
		StateXYO etatCourant = new StateXYO(0, 3, StateXYO.ORIENTE_SUD);
		System.out.print("Etat courant : "+etatCourant.toString());
		
		// Initialisation
		ProblemeDevant prob = new ProblemeDevant();
		ActionOriente a;
		StateXYO nouvelEtat;
		for (int i = 0; i < 20; i++) {
			// Recherche de la meilleure action a effectuer
			a = politiqueOptimale.get(etatCourant);
			System.out.print(", Meilleure action : "+a.toString());
			// Appel a transition pour ne pas effectuer l'action de facon deterministe
			nouvelEtat = prob.transition(etatCourant, a).tirage();
			System.out.print(", Nouvel etat : "+nouvelEtat.toString());
			// Ce nouvel etat devient etat courant
			etatCourant = nouvelEtat;
			System.out.print("\nEtat courant : "+etatCourant.toString());
		}
	}

}
