package DonneesStatiques;

public class State {
	
	TypeEtat etat;
	int numPeriph;
	int dateDebut;
	int dateReveil;
	
	public State(TypeEtat etat, int numES, int dateDebut, int dateReveil)
	{
		this.etat=etat;
		this.numPeriph=numES;
		this.dateDebut=dateDebut;
		this.dateReveil=dateReveil;
	}
	
	

	public TypeEtat getEtat() {
		return etat;
	}

	public void setEtat(TypeEtat etat) {
		this.etat = etat;
	}

	public int getNumPeriph() {
		return numPeriph;
	}

	public void setNumPeriph(int numPeriph) {
		this.numPeriph = numPeriph;
	}

	public int getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(int dateDebut) {
		this.dateDebut = dateDebut;
	}

	public int getdateReveil() {
		return dateReveil;
	}

	public void setdateReveil(int dateReveil) {
		this.dateReveil = dateReveil;
	}
	
	
	public void afficherEtat()
	{
		System.out.println("\n Etat : " + this.etat );
		if (this.etat==TypeEtat.ES) System.out.println("\n Peripherique numero: " + this.numPeriph);
		System.out.println("\n Date de debut " + this.dateDebut + "\n Date de reveil " + this.dateReveil );
	}

}
