package tp6;

import tp6.problemeDevant.QLearning;

public class MainQLearning {

	public static void main(String[] args) {
		QLearning ql = new QLearning();
		ql.apprendre(500000);
		System.out.println(ql.qVal.toString());
		System.out.println(ql.qVal.somme());
	}

}
