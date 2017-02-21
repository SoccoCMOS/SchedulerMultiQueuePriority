package DonneesStatiques;

import java.util.LinkedList;

public class Peripherique {
	String Name;
	int TmpES;
	int ProcEnCours=-1; // Au depart aucun processus en attente
	LinkedList<Processus> fileAttente;
	
	public Peripherique(String Name, int tempsES)
	{
		this.Name=Name;
		this.TmpES=tempsES;
		fileAttente=new LinkedList<Processus>();
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getTmpES() {
		return TmpES;
	}
	public void setTmpES(int tmpES) {
		TmpES = tmpES;
	}
	public LinkedList<Processus> getFileAttente() {
		return fileAttente;
	}
	public void setFileAttente(LinkedList<Processus> fileAttente) {
		this.fileAttente = fileAttente;
	}
	
	public int getProcEnCours() {
		return ProcEnCours;
	}

	public void setProcEnCours(int procEnCours) {
		ProcEnCours = procEnCours;
	}

	public void enfilerProcessus(Processus p)
	{
		this.fileAttente.addLast(p);	
	}
	
	public Processus defilerprocessus() //Executer ES
	{
		return this.fileAttente.pollFirst();
	}

	
	public void afficherPeripherique()
	{
		int i=0;
		System.out.println("\n Periph Name: " + this.Name + "\n Temps d'entree sortie= " + this.TmpES + "\n Processus en cours " + this.ProcEnCours);
		System.out.println("\n Processus en attente ");
		
		for (Processus p : this.fileAttente)
		{
			System.out.println("\n Processus en position " + i + " " + p.getName() );
			i++;
		}
	}
	
	public int getPositionFilePeriph(Processus p)  // Retourne la position du processus dans la file d'attente
	{
		return this.fileAttente.indexOf(p);
	}
	
	public int getNextProc()  // retourne le prochain processus à exécuter une ES sur ce periph en le supprimant de la file d'attente
	{
		if (this.fileAttente.isEmpty()==true)
		{
			return -1; // aucun processus en attente
		}
		else 
		{
			return this.fileAttente.pollFirst().getPID();
		}
	}
	
	public int getNbAtt()
	{
		return this.fileAttente.size();
	}
	
}
