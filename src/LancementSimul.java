import Traitements.Simulateur;
import DonneesStatiques.Processus;

public class LancementSimul {

	Simulateur simul;
	
	public Simulateur getSimul() {
		return simul;
	}

	public void setSimul(Simulateur simul) {
		this.simul = simul;
	}

	public void lancerSimulateur(int quantum, int nbPeriph, int nbProcess,int durES[] ,Processus tabProcess[]) {
		System.out.println("DEBUT DE LA SIMULATION");
		simul = new Simulateur(quantum, nbPeriph, durES, tabProcess, nbProcess);
		simul.recupererData();
		simul.calculSystemTimeStamp();
		simul.calculTauxOccup();
		simul.afficherSimulateur();

	}

	

}
