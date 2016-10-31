package tp4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;

import tp4.hmm.State;
import tp4.hmm.Systeme;

/**
 * classe qui permet d'afficher un labyrinthe
 * 
 * @author vthomas
 * 
 */
public class PanelSimulateur extends JPanel {

	/**
	 * taille pour affichage
	 */
	public int TAILLE = 100;

	/**
	 * Image A charger
	 */
	public Image bf;

	/**
	 * le systeme à utiliser
	 */
	Systeme systeme;

	/**
	 * savoir si on afficheDoudou
	 */
	boolean afficheDoudou = true;

	/**
	 * constructeur pour lier au modele
	 * 
	 * @param m
	 *            modele hmmlaby a utiliser
	 */
	public PanelSimulateur(Systeme s) {
		URL URLresource = getClass().getResource("/tp4/image/hamster.jpg");
		this.bf = Toolkit.getDefaultToolkit().getImage(URLresource);
		this.systeme = s;
		setPreferredSize(new Dimension(3 * TAILLE + 20, 3 * TAILLE + 20));
	}

	/**
	 * permet d'afficher doudou
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// affichage colonne
		g.setColor(Color.gray);
		g.fillRect(1 * TAILLE, 1 * TAILLE, TAILLE - 1, TAILLE - 1);

		// affichage de doudou
		if (this.afficheDoudou) {

			State state = systeme.etatCourant;
			// on attend que l'image puisse s'afficher
			int[] retournerCoord = state.retournerCoord();
			int posX = retournerCoord[0] * TAILLE;
			int posY = retournerCoord[1] * TAILLE;
			while (!g.drawImage(bf, posX, posY, posX + TAILLE, posY + TAILLE, 0, 0, 200, 200, null)) {
			}

		}

		// affichage du labyrinthe
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				// contour
				g.setColor(Color.black);
				g.drawRect(i * TAILLE, j * TAILLE, TAILLE - 1, TAILLE - 1);
			}

	}
}
