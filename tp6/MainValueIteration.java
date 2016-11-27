package tp6;

import tp6.problemeDevant.ValueIteration;

public class MainValueIteration {

	public static void main(String[] args) {
		ValueIteration vi = new ValueIteration();
		vi.fairePlusieursIterations(100);
		System.out.println("Tableau des QValeurs :\n");
		System.out.println(vi.qVal.toString());
		System.out.println("------------------------------------------------------------------------------------------\n");
		System.out.println("Convergence algorithme : \n");
		for (int i = 0; i < vi.sommeQValeurs.length; i++) {
			System.out.println("t = "+i+" sommeValeurs = "+vi.sommeQValeurs[i]);
		}
		System.out.println("\n------------------------------------------------------------------------------------------\n");
		System.out.println("Politique optimale");
		System.out.println(vi.qVal.getPolitique().toString());
	}
}
