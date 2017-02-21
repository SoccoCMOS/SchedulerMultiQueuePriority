package Traitements;

import java.util.LinkedList;

import DonneesStatiques.DataDG;
import DonneesStatiques.Peripherique;
import DonneesStatiques.Processus;
import DonneesStatiques.State;
import DonneesStatiques.TypeEtat;

public class FinES extends Event {

	int numPeriph;
	int processIt;
	int positionFileBq;
	
	public FinES(int numero, int periph, int procIt, int pos, int q)
	{
		super(numero,q);
		this.numPeriph=periph;
		this.processIt=procIt;
		this.positionFileBq=pos;
	}
	
	public int getNumPeriph() {
		return numPeriph;
	}


	public void setNumPeriph(int numPeriph) {
		this.numPeriph = numPeriph;
	}


	public int getProcessIt() {
		return processIt;
	}


	public void setProcessIt(int processIt) {
		this.processIt = processIt;
	}


	public int getPositionFileBq() {
		return positionFileBq;
	}


	public void setPositionFileBq(int positionFileBq) {
		this.positionFileBq = positionFileBq;
	}


	public Dispatch traiter(Processus tabProcess[],int nbProcess,int date, DataDG diagramme[],LinkedList<Integer> FilesPrets[], LinkedList<Integer> FileProcBlq,Peripherique periph[],int nbPeriph)
	{
		Dispatch e=new Dispatch(-1,-1);
		
		System.out.println("\n Traitement de l'IT de fin d'E/S \n" + this.numProcess + " Processus interrompu " + this.processIt + "Num periph" + this.numPeriph + "Valeur qtm  = " + this.quantum );
		
		/// Mettre le processus interrompu à l'état pret
		
		if (this.processIt!=-1)
		{
			diagramme[this.processIt].addState(new State(TypeEtat.Pret,-1,date,date));
			/// Enfiler en tete de sa file le processus actif
			FilesPrets[tabProcess[this.processIt].getPriority()].addFirst(this.processIt);
		}
		
		/// On vérifie si le processus ayant fini l'ES a fini tous ses trt
		if (tabProcess[this.numProcess].noBehaviour()==true)
		{
			/// On le met directement à l'état terminé
			diagramme[this.numProcess].addState(new State(TypeEtat.Terminated,-1,date,date));
			diagramme[this.numProcess].setDateReveil(-1); // Tuer processus
			
			/// calcul des performances
			
			// 1. Temps de residence
			tabProcess[numProcess].setTmpResidence(date);
			
			// 2. Temps d'attente
			tabProcess[numProcess].calculTmpAttente();
		}
		else 
		{
			/// Enfiler le processus interrompant et mise à jour de son etat
			FilesPrets[tabProcess[this.numProcess].getPriority()].addLast(this.numProcess);
			diagramme[this.numProcess].addState(new State(TypeEtat.Pret,-1,date,date));
		}
		/// Supprimer le processus ayant fini l'E/S de la file bloquée
		if (FileProcBlq.indexOf(this.numProcess)==-1) System.out.println("Processus inexistant en file bloqué");
		else FileProcBlq.remove(FileProcBlq.indexOf(this.numProcess));
		
		/// Sauvegarder la date de sortie de la fille bloquée
		tabProcess[this.numProcess].incTmpBlq(date);
		
		/// MAJ de la file d'attente du peripherique
		periph[this.numPeriph].setProcEnCours(periph[this.numPeriph].getNextProc());
		if (periph[this.numPeriph].getProcEnCours()!=-1) /// Si file non vide
		{
			// On defile le prochain processus pour exécuter son ES
			periph[this.numPeriph].defilerprocessus();
			// On change son état dans le diagramme à l'état en ES et on met à jour sa date de réveil
			diagramme[periph[this.numPeriph].getProcEnCours()].addState(new State(TypeEtat.ES,this.numPeriph,date,date+periph[this.numPeriph].getTmpES()));
			diagramme[periph[this.numPeriph].getProcEnCours()].setDateReveil(date+periph[this.numPeriph].getTmpES());
		}
		
		if (this.isListIsEmpty()) 
			{
				e=dispatcher(tabProcess,nbProcess,date,diagramme,FilesPrets,FileProcBlq,periph,nbPeriph);
			}
		else
		{
			e=new Dispatch(date,-1);
		}
		return e;
	}

}
