
package DonneesStatiques;

import java.util.LinkedList;

public class DataDG { /// Données du Diagramme de Gantt
	
	LinkedList<State> progress;
	int dateReveil;
	int valQuantum;
	
	public DataDG()
	{
		
	}
	
	public DataDG(int dateRev, int valQt)
	{
		this.dateReveil=dateRev;
		this.valQuantum=valQt;
		this.progress=new LinkedList<State>();
	}


	public LinkedList<State> getProgress() {
		return progress;
	}


	public void setProgress(LinkedList<State> progress) {
		this.progress = progress;
	}


	public int getDateReveil() {
		return dateReveil;
	}


	public void setDateReveil(int dateReveil) {
		this.dateReveil = dateReveil;
	}


	public int getValQuantum() {
		return valQuantum;
	}


	public void setValQuantum(int valQuantum) {
		this.valQuantum = valQuantum;
	}
	
	
	public void afficherDonneesGantt()
	{
		System.out.println("\n Date de Reveil : " + this.dateReveil + "\n Valeur du quantum : " + this.valQuantum + "\n Progression depuis T0 : \n ");

		for (State s : this.progress)
		{
			s.afficherEtat();
		}
	
	}
	
	public State getState()  // retourne l'etat actuel du processus
	{
		if (this.progress.isEmpty()) return (new State(TypeEtat.Inexistant,-1,-1,-1));
		return this.progress.peekLast(); // Il s'agit du dernier etat enregistré
	}
	
	public void addState(State s)
	{
		this.progress.addLast(s);
	}
	
	public void decQtm(int v)
	{
		this.valQuantum-=v;
	}

}
