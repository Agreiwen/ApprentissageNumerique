package tp4.hmm;

import java.util.ArrayList;
import java.util.List;

/**
 * classe qui permet de gérer les états devra avoir une fonction hashcode et
 * equals pour gérer les hashmap
 * 
 * @author vthomas
 *
 */
public class State {

	/**
	 * le descriptif d'un etat
	 */
	int num_etat;

	/**
	 * l'ensemble des etats
	 */
	private static ArrayList<State> allS;

	/**
	 * constructeur
	 * 
	 * @param i
	 *            num de l'etat
	 */
	public State(int i) {
		this.num_etat = (i+8) % 8;
	}

	/**
	 * retourne touss les etats patron singleton.
	 */
	public static List<State> getAll() {

		// si pas cree, on cree
		if (allS == null) {
			// creation des etats
			allS = new ArrayList<>();
			for (int i = 0; i < 8; i++)
				allS.add(new State(i));
		}

		// retourne une copie
		return (List<State>) allS.clone();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num_etat;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (num_etat != other.num_etat)
			return false;
		return true;
	}

	/**
	 * permet de retourner les coordonnees de l'etat
	 * 
	 * @return coordo etat
	 */
	public int[] retournerCoord() {
		// les coordonnees des etats
		int[][] coord = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 2, 1 }, { 2, 2 }, { 1, 2 }, { 0, 2 }, { 0, 1 } };
		return (coord[this.num_etat]);
	}
	
	/**
	 * permet de retourner id a partir des coordonnees
	 * 
	 * @return coordo etat
	 */
	public static int retournerId(int x, int y) {
		int[][] ids= { {0,7,6},{1,-1,5},{2,3,4}};
		return (ids[x][y]);
	}
	
	/**
	 * permet d'afficher un etat
	 */
	public String toString()
	{
		return "S"+this.num_etat;
	}

}
