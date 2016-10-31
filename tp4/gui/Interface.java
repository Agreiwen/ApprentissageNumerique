package tp4.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tp4.hmm.*;

/**
 * se charge de gérer l'ensemble de l'interface construit la JFrame et ajoute le
 * contenu --> panel --> boutons en passant le bon modele
 * 
 * @author vthomas
 * 
 */
public class Interface implements Observer {

	/**
	 * le systeme execute
	 */
	Systeme systeme;

	// les differents panels

	/**
	 * pannel qui affiche la densite
	 */
	PanelDistribution panelDensite;

	/**
	 * pannel qui affiche l'observation
	 */
	PanelObservation panelObservation;

	/**
	 * panel qui affiche l'etat courant
	 */
	PanelSimulateur panelSimulateur;

	/**
	 * la frame qui contient tout
	 */
	JFrame f;

	/**
	 * creation de l'interface
	 * 
	 * @param m
	 *            modele à passer
	 */
	public Interface(Systeme s) {
		this.systeme = s;
		// s'ajouter en observer
		this.systeme.addObserver(this);

		// initialisation de la JFrame
		f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// redimensionnement
		f.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				int nb_Cases = 3 * 3 + 1;
				panelDensite.TAILLE = f.getWidth() / nb_Cases;
				panelObservation.TAILLE = f.getWidth() / nb_Cases;
				panelSimulateur.TAILLE = f.getWidth() / nb_Cases;
			}
		});

		// Panel global
		JPanel contenu = new JPanel();
		contenu.setLayout(new BorderLayout());
		f.setContentPane(contenu);

		// panel des options
		JPanel options = creationPanelOptions();
		contenu.add(options, BorderLayout.NORTH);
		// Panel Images
		JPanel contenuImages = creationPanelImages(s);
		contenu.add(contenuImages, BorderLayout.CENTER);
		// bouton evoluer
		JButton Bouton = creationBoutonEvoluer(f);
		contenu.add(Bouton, BorderLayout.SOUTH);

		
		// affichage de la JFrame
		f.pack();
		f.setVisible(true);

	}


	/**
	 * creation du panel des options de l'affichage
	 * 
	 * @return Panel avec les checkbox utiles
	 */
	private JPanel creationPanelOptions() {
		// Panel Options
		JPanel options = new JPanel();

		// affichage doudou
		JCheckBox afficheDoudou = new JCheckBox("Cache Hamster");
		afficheDoudou.addActionListener(new ActionListener() {
			// si on clique on affiche ou pas la densite
			public void actionPerformed(ActionEvent event) {

				// modifie l'attribut dans panelSimulateur
				panelSimulateur.afficheDoudou = !panelSimulateur.afficheDoudou;
				panelSimulateur.repaint();
			}
		});
		options.add(afficheDoudou);

		// affichage densite
		JCheckBox afficheProbas = new JCheckBox("Cache belief");
		afficheProbas.addActionListener(new ActionListener() {
			// si on clique on affiche ou pas la densite
			public void actionPerformed(ActionEvent event) {

				// on modifie l'attribut dans affichepanel
				panelDensite.affichePanel = !panelDensite.affichePanel;
				panelDensite.repaint();
			}
		});
		options.add(afficheProbas);
		return options;
	}

	/**
	 * bouton pour faire evoluer le systeme
	 * 
	 * @param f
	 * @return
	 */
	private JButton creationBoutonEvoluer(final JFrame f) {
		// on construit un bouton qui fait tout
		JButton Bouton = new JButton("executer");
		Bouton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				systeme.evoluerSysteme();
			}
		});
		return Bouton;
	}

	private JPanel creationPanelImages(Systeme s) {
		// les images
		JPanel contenuImages;
		contenuImages = new JPanel();
		contenuImages.setLayout(new GridLayout(0, 3));

		// ajout de l'affichage du vrai simulateur
		this.panelSimulateur = new PanelSimulateur(s);
		contenuImages.add(this.panelSimulateur);

		// ajout de l'affichage de l'observation
		this.panelObservation = new PanelObservation(s);
		contenuImages.add(this.panelObservation);

		// ajout de l'affichage de la densité
		this.panelDensite = new PanelDistribution(s);
		contenuImages.add(this.panelDensite);
		return contenuImages;
	}

	@Override
	public void update(Observable o, Object arg) {
		f.repaint();
	}

}
