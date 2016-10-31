package tp4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import tp4.hmm.Distribution;
import tp4.hmm.State;
import tp4.hmm.Systeme;

/**
 * classe qui permet d'afficher une distribution
 * 
 * @author vthomas
 * 
 */
public class PanelDistribution extends JPanel {

	/**
	 * taille pour affichage
	 */
	public int TAILLE = 100;

	/**
	 * boolean pour savoir si on affiche proba
	 */
	boolean afficheProba = true;
	boolean affichePanel = true;

	/**
	 * le modele à utiliser
	 */
	Systeme systeme;

	/**
	 * constructeur pour lier au modele
	 * 
	 * @param m
	 *            modele hmmlaby a utiliser
	 */
	public PanelDistribution(Systeme s) {
		systeme = s;
		setPreferredSize(new Dimension(3 * TAILLE + 20, 3 * TAILLE + 20));
	}

	/**
	 * permet d'afficher une densité
	 */
	public void paint(Graphics g) {
		Distribution<State> densiteEnCours = this.systeme.distributionCourante;

		// si on affiche le panel
		if (this.affichePanel) {
			
			// affichage du labyrinthe
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					// modifie la couleur en fonction de la densité
					int retournerId = State.retournerId(i, j);

					// si c'est un etat qui existe
					if (retournerId != -1) {
						State etatCorrespondant = new State(retournerId);
						double densite = densiteEnCours.getProba(etatCorrespondant);
						Color c = new Color((int) (densite * 255), 0, 0);
						g.setColor(c);

						// dessine
						g.fillRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);

						// affiche proba
						g.setFont(new Font("Arial", Font.PLAIN, TAILLE / 8));
						if (this.afficheProba) {
							g.setColor(Color.white);
							int texteX = (int) ((i + 0.4) * TAILLE);
							int testeY = (int) ((j + 0.5) * TAILLE);
							double densite100 = (int) (densite * 100) / 100.;
							g.drawString("" + densite100, texteX, testeY);
						}
					}
					// contour
					g.setColor(Color.white);
					g.drawRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);

				}
			
		} else {
			// affiche pas, cases grises
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					g.setColor(Color.GRAY);
					g.fillRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
					g.setColor(Color.white);
					g.drawRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
				}
		}
	}

}
