package  tp6.modele;

/**
 * classe qui permet de définir un état abstrait
 */
public abstract class AsbtractState {

	/**
	 * force la redefinition de hashcode
	 */
	public abstract int hashCode();

	/**
	 * force la redefinition de equals
	 */
	public abstract boolean equals(Object obj);

}
