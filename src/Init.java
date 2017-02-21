import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

import DonneesStatiques.Behaviour;
import DonneesStatiques.Processus;
import DonneesStatiques.typeBehaviour;

public class Init {

	protected String source;
	public Processus[] p = new Processus[10];
	public int nbProcessus;

	public Init(String source) {

		this.source = source;
		lecture();
	}

	public void lecture() {
		try {
			String ligne;
			String str;
			int cpt = 0;
			BufferedReader fichier = new BufferedReader(new FileReader(source));

			while ((ligne = fichier.readLine()) != null) {
				p[cpt] = new Processus();
				p[cpt].setPID(cpt);
				p[cpt].setName(ligne.substring(0, ligne.indexOf(':')));
				str = ligne.substring(p[cpt].getName().length() + 1);
				p[cpt].setPriority(Integer.parseInt(str.substring(0,
						str.indexOf(':'))));
				str = str.substring(2);
				p[cpt].setDateEntree(Integer.parseInt(str.substring(0,
						str.indexOf(':'))));
				str = str
						.substring(str.substring(0, str.indexOf(':')).length() + 1);
				p[cpt].setTmpExecution(Integer.parseInt(str.substring(0,
						str.indexOf(':'))));
				str = str
						.substring(str.substring(0, str.indexOf(':')).length() + 1);
				this.initComportement(str, cpt);
				p[cpt].resetPriority();
				cpt++;
			}
			this.nbProcessus = cpt;
			fichier.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initComportement(String str, int nb) {

		LinkedList<Behaviour> comportement = new LinkedList<Behaviour>();
		Behaviour b;
		int test = 0;
		int fin = 0;
		if (str.indexOf('-') == -1) {
			b = new Behaviour();
			b.setTypeComportement(typeBehaviour.exec);
			b.setTmpOrNum(Integer.parseInt(str.substring(0, str.indexOf(';'))));
			comportement.add(b);
			fin = 1;
		}

		while (fin != 1) {

			if (test % 2 == 0) {
				b = new Behaviour();
				b.setTypeComportement(typeBehaviour.exec);

				b.setTmpOrNum(Integer.parseInt(str.substring(0,
						str.indexOf('-'))));
				comportement.add(b);
			} else {
				b = new Behaviour();
				b.setTypeComportement(typeBehaviour.ES);
				b.setTmpOrNum(Integer.parseInt(str.substring(1,
						str.indexOf('-'))));
				comportement.add(b);
			}

			str = str
					.substring(str.substring(0, str.indexOf('-')).length() + 1);
			test++;
			if (str.indexOf('-') == -1) {

				if (test % 2 == 0) {
					b = new Behaviour();
					b.setTypeComportement(typeBehaviour.exec);

					b.setTmpOrNum(Integer.parseInt(str.substring(0,
							str.indexOf(';'))));
					comportement.add(b);

				} else {
					b = new Behaviour();
					b.setTypeComportement(typeBehaviour.ES);
					b.setTmpOrNum(Integer.parseInt(str.substring(1,
							str.indexOf(';'))));
					comportement.add(b);
				}
				fin = 1;
			}

		}

		for (Behaviour elem : comportement) {
			elem.afficherBehaviour();
		}

		this.p[nb].setComportement(comportement);

	}

}
