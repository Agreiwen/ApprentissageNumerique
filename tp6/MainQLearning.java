package tp6;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import tp6.problemeDevant.QLearning;

public class MainQLearning {
	
	protected String destination;
	protected BufferedWriter fichier;
	protected QLearning ql;
	
	public MainQLearning(String destination) {
		System.out.println("Apprentissage...\n");
		ql = new QLearning();
		ql.apprendre(100000000);
		System.out.println("Ecriture fichier...\n");
		/*this.destination = destination;
		try {
			fichier = new BufferedWriter(new FileWriter(destination));
			for (int i = 0; i < ql.sommeQValeurs.length; i++) {
				if(i%100 == 0 || i == 0){
					fichier.write(Integer.toString(i)+" "+(int)ql.sommeQValeurs[i]);
					fichier.newLine();
				}
			}
			fichier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println(ql.qVal.toString());
		System.out.println(ql.qVal.somme());
	}

	public static void main(String[] args) {
		new MainQLearning("convergenceQLearning.txt");
	}

}
