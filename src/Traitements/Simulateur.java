package Traitements;
import DonneesStatiques.DataDG;
import DonneesStatiques.Processus;

public class Simulateur {

	/// Données
	int quantum;
	int nbPeriph;
	int[] durES;
	Processus tabProcess[];
	int nbProcess;
	
	/// résultats des divers traitements
	DataDG donnees[];
	int systemTimeStamp=0;
	float tauxOccupCPU=0;
	
	/// Constructeur
	
	public Simulateur(int quantum,int nbPeriph,int durEs[],Processus tab[],int nbProcess)
	{
		int i;
		this.quantum=quantum;
		this.nbPeriph=nbPeriph;
		this.durES=new int[nbPeriph];
	
		this.durES=durEs;
		this.tabProcess=new Processus[nbProcess];
		this.tabProcess=tab;
		this.nbProcess=nbProcess;
		
		donnees=new DataDG[nbProcess];
		for(i=0; i<nbProcess; i++)
		{
			donnees[i]=new DataDG();
		
		}
	}
	
	/// Setters and Getters
	public int getQuantum() {
		return quantum;
	}
	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}
	public int getNbPeriph() {
		return nbPeriph;
	}
	public void setNbPeriph(int nbPeriph) {
		this.nbPeriph = nbPeriph;
	}
	public int[] getDurES() {
		return durES;
	}
	public void setDurES(int[] durES) {
		this.durES = durES;
	}
	public Processus[] getTabProcess() {
		return tabProcess;
	}
	public void setTabProcess(Processus[] tabProcess) {
		this.tabProcess = tabProcess;
	}
	public int getNbProcess() {
		return nbProcess;
	}
	public void setNbProcess(int nbProcess) {
		this.nbProcess = nbProcess;
	}
	public  DataDG[] getDonnees() {
		return this.donnees;
	}
	public void setDonnees(DataDG[] donnees) {
		this.donnees = donnees;
	}
	public int getSystemTimeStamp() {
		return systemTimeStamp;
	}
	public void setSystemTimeStamp(int systemTimeStamp) {
		this.systemTimeStamp = systemTimeStamp;
	}
	
	/// Génération des données du diagramme de Gantt
	
	public void recupererData()
	{
		Ordonnanceur ordo=new Ordonnanceur(this.durES,nbPeriph,quantum);
		this.donnees=ordo.genererData(tabProcess, nbProcess);
	}
	
	/// Affichage du diagramme de Gantt
	
	public float getTauxOccupCPU() {
		return tauxOccupCPU;
	}

	public void setTauxOccupCPU(float tauxOccupCPU) {
		this.tauxOccupCPU = tauxOccupCPU;
	}
	
	public void afficherDGantt()
	{
		
		int i;
		
		for(i=0; i<this.nbProcess; i++)
		{  
			
			if (this.donnees[i]==null) 
				{
				  System.out.println("\n Pas de progression a afficher ");
				  continue;
				}
			this.donnees[i].afficherDonneesGantt();
		}
	}
	
	public void afficherSimulateur()
	{
		int i;
		System.out.println(" \n Quantum : " + this.quantum + "  --- Nombre de periph : " + this.nbPeriph + " --- Nombre de process : " + this.nbProcess + "\n");
		System.out.println("\n Affichage des peripheriques \n ");
		for(i=0;i<this.nbPeriph;i++)
		{
			System.out.println(this.durES);
		}
		
		System.out.println("\n Affichage des processus \n ");
		for(i=0;i<this.nbProcess;i++)
		{
		  this.tabProcess[i].afficherProcessusConsole();
		}
		
		System.out.println("\n Affichage des données de Gantt \n ");
		this.afficherDGantt();
		
		System.out.println("\n System Time Stamp =  " + this.systemTimeStamp);
		System.out.println("\n Taux d'occupation CPU =  " + this.tauxOccupCPU + " %.");	
	}
	
	public void calculSystemTimeStamp()
	{
		int i;
		this.systemTimeStamp=0;
		for (i=0; i<this.nbProcess; i++)
		{
			if (tabProcess[i].calculDateSortie()>this.systemTimeStamp)
			{
				this.systemTimeStamp=tabProcess[i].calculDateSortie();
			}
		}
	}
	
	
	public void calculTauxOccup()
	{
		int tempsActif=0,i;
		
		for (i=0; i<this.nbProcess; i++)
		{
			tempsActif+=tabProcess[i].getTmpExecution();
		}
		try
		{
			this.tauxOccupCPU=tempsActif*100/this.systemTimeStamp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
