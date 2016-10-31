package tp4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tp4.hmm.Distribution;
import tp4.hmm.Observation;
import tp4.hmm.State;
import tp4.hmm.Systeme;

/**
 * classe qui permet d'afficher l'observation courante
 * 
 * @author vthomas
 * 
 */
@SuppressWarnings("unused")
public class PanelObservation extends JPanel {

	/**
	 * taille pour affichage
	 */
	public int TAILLE = 100;

	/**
	 * Observation courante
	 */
	Systeme systeme;

	/**
	 * constructeur pour lier au modele
	 * 
	 * @param m
	 *            modele hmmlaby a utiliser
	 */
	public PanelObservation(Systeme s) {
		this.systeme = s;
		setPreferredSize(new Dimension(3 * TAILLE + 20, 3 * TAILLE + 20));
	}

	/**
	 * permet d'afficher la valeur des capteurs
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// on recupere la derniere observation
		Observation o = this.systeme.lastObservation;

		// affichage du labyrinthe
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				// contour
				g.setColor(Color.black);
				g.drawRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
			}
		if(o != null){
			dessinerCapteur(g, Observation.POSITION_CAPTEUR_1, o.obsCapteur1);
			dessinerCapteur(g, Observation.POSITION_CAPTEUR_2, o.obsCapteur2);
		}
	}

	private void dessinerCapteur(Graphics g, int num_capteur, boolean valeur) {
		// trouve position du cpateur
		int i, j;
		State s = new State(num_capteur);
		i = s.retournerCoord()[0];
		j = s.retournerCoord()[1];

		// si capteur actif
		if (valeur)
			g.setColor(Color.green);
		else
			g.setColor(Color.yellow);

		// dessine capteur
		g.fillRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
	}

}
