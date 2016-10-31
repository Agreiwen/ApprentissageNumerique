package tp4.hmm;

import java.util.ArrayList;
import java.util.List;

/**
 * classe qui permet de gerer les observations
 * 
 * devra implementer hahscode et equals pour les hashmap
 * 
 * @author vthomas
 *
 */
public class Observation {

	/**
	 * position des capteurs
	 */
	public final static int POSITION_CAPTEUR_1 = 1;
	public final static int POSITION_CAPTEUR_2 = 4;

	public boolean obsCapteur1, obsCapteur2;

	/**
	 * l'ensemble des observations
	 */
	private static ArrayList<Observation> allO;

	/**
	 * constructeur observation
	 */
	public Observation(boolean[] bs) {
		this.obsCapteur1 = bs[0];
		this.obsCapteur2 = bs[1];
	}

	/**
	 * constructeur observation
	 */
	public Observation(boolean b1, boolean b2) {
		this.obsCapteur1 = b1;
		this.obsCapteur2 = b2;
	}

	/**
	 * retourne toutes les observations patron singleton.
	 */
	public List<Observation> getAll() {
		// si pas cree, on cree
		if (allO == null) {
			// creation des etats
			allO = new ArrayList<>();
			allO.add(new Observation(true,true));
			allO.add(new Observation(false,false));
			allO.add(new Observation(true,false));
			allO.add(new Observation(false,true));
		}
		
		// retourne une copie
		return (List<Observation>) allO.clone();
	}
	
	@Override
	public int hashCode() {
		int premier = (obsCapteur1) ? 1 : 0;
		int deuxime = (obsCapteur2) ? 1 : 0;
		String res = ""+premier+deuxime;
		int result = Integer.valueOf(res);
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
		Observation other = (Observation) obj;
		if (obsCapteur1 != other.obsCapteur1 || obsCapteur2 != other.obsCapteur2)
			return false;
		return true;
	}

	public String toString()
	{
		return "O("+this.obsCapteur1+", "+this.obsCapteur2+")";
	}

}
