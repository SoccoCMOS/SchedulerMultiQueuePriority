package Traitements;

import java.util.LinkedList;

import DonneesStatiques.DataDG;
import DonneesStatiques.Peripherique;
import DonneesStatiques.Processus;
import DonneesStatiques.State;
import DonneesStatiques.TypeEtat;
import DonneesStatiques.typeBehaviour;

public abstract class Event {
	
	int  numProcess; // PID du processus ayant lancé cet event
	int quantum;
	boolean listIsEmpty;
	
	public Event(int numero, int qtm)
	{
		this.numProcess=numero;
		this.quantum=qtm;
	}
	
	public int getNumProcess() {
		return numProcess;
	}

	public void setNumProcess(int numProcess) {
		this.numProcess = numProcess;
	}
	

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}
	
	
	public boolean isListIsEmpty() {
		return listIsEmpty;
	}

	public void setListIsEmpty(boolean listIsEmpty) {
		this.listIsEmpty = listIsEmpty;
	}

	public void afficherEvent()
	{
		System.out.println("\n Evenement de type " + this.getClass().getName() + "  Process Numero : " + this.numProcess + " Valeur actuelle restante du Quantum : " + this.quantum);
	}

	public abstract Dispatch traiter(Processus tabProcess[],int nbProcess,int date, DataDG diagramme[],LinkedList<Integer> FilesPrets[], LinkedList<Integer> FileProcBlq,Peripherique periph[],int nbPeriph);
	
	public Dispatch dispatcher(Processus tabProcess[],int nbProcess,int date, DataDG diagramme[],LinkedList<Integer> FilesPrets[], LinkedList<Integer> FileProcBlq,Peripherique periph[],int nbPeriph)
	{
		// Déclaration des variables
		Dispatch d=new Dispatch(-1,-1);
		int i, valqtm,priority,saveDate;
		Processus p;
		State etat;
		
		/// Trouver le processus pret le plus prioritaire
		
		priority=-1;
		for (i=9; i>=0;i--)
		{
			if(FilesPrets[i].isEmpty()==false)
			{
				priority=i;
				break;
			}
		}
		
		if (priority==-1) /// Aucun processus à l'état pret 
		{
			/// 1er scénario possible : tous les processus sont terminés
			if(this.allTerminated(diagramme, nbProcess))
			{
				return d;
			}
			else /// 2e scénario possible : au moins un processus est bloqué => pret et non terminé
			{
				date=this.rechercheMinDateReveil(diagramme, nbProcess, date);
				d.setDate(date);
				return d;
			}
		}
		
		else // Au moins une file pret n'est pas vide
		{
			// On recupere le processus le plus prioritaire
			p=tabProcess[FilesPrets[priority].pollFirst()];
			etat=new State(TypeEtat.Actif,-1,date,date);
			
			// C'est le processus élu par le dispatcher
			d.setProcElu(p.getPID());
			
			// On calcule la date de réveil de ce processus qui sera également la date du prochain réveil système
			if (p.getNextBehaviour().getTypeComportement()==typeBehaviour.exec) // Test de sécurité
			{
				/// On récupère la valeur restante du quantum alloué au processus
				valqtm=diagramme[p.getPID()].getValQuantum();
	
				if(valqtm>=p.getNextBehaviour().getTmpOrNum())
				{    /// Il lui reste assez du quantum pour effectuer toute l'exécution
					
					saveDate=date;
					date=this.minimum(this.rechercheMinDateReveil(diagramme, nbProcess, date),date+p.getNextBehaviour().getTmpOrNum());
					
					p.getNextBehaviour().decTmps(date-saveDate);
					diagramme[p.getPID()].decQtm(date-saveDate);
					
					if(p.getNextBehaviour().getTmpOrNum()==0)
					{
						p.getComportement().pollFirst(); /// Supprimer le traitement 
					}
				}
				
				else
				{
					/// Reste du quantum insuffisant
					saveDate=date;
					date=this.minimum(this.rechercheMinDateReveil(diagramme, nbProcess, date),date+valqtm);
					p.getNextBehaviour().decTmps(date-saveDate);
					diagramme[p.getPID()].decQtm(date-saveDate);
				}
				
			/// MAJ des dates de réveil des autres processus prêts
				d.setDate(date);
				etat.setdateReveil(date);
				diagramme[p.getPID()].addState(etat);
				diagramme[p.getPID()].setDateReveil(date);
				
				for (i=0; i<nbProcess; i++)
				{
					if (diagramme[i].getState().getEtat()==TypeEtat.Pret)
					{
						diagramme[i].setDateReveil(date);
						diagramme[i].getState().setdateReveil(date);
					}
				}	
			}
			else
			{
				System.out.println("\n Erreur ! le processus doit effectuer une exécution ! \n ");
			}
			
			
		}
		
		return d;
	}
	
	public boolean allTerminated(DataDG diagramme[],int nbProcess)
	{
		int i;
		for (i=0; i<nbProcess; i++)
		{
			if (diagramme[i].getState().getEtat()!=TypeEtat.Terminated) // Il existe au moins ce processus qui n'est pas terminé
			{
				return false;
			}
		}
		
		return true;
	}
	
	public int rechercheMinDateReveil(DataDG diagramme[], int nbProcess, int date)
	{
		int i,j;
		int min=-1; // Par defaut
		
		for(i=0; i<nbProcess; i++)
		{
			if (diagramme[i].getDateReveil()==-1) continue; /// On ne considère pas les processus terminés
			else
			{
				if (diagramme[i].getDateReveil()==date) continue; /// On ne considère pas les processus sensés se réveiller à la date actuelle "date"
				else
				{
					min=diagramme[i].getDateReveil();
					break; // On a trouvé l'indice du premier processus non terminé et ne se réveillant pas à date
				}
			}
		}
		
		for(j=i; j<nbProcess; j++)
		{
			if (diagramme[j].getDateReveil()==-1) continue; /// On ne considère pas les processus terminés
			else
			{
				if (diagramme[j].getDateReveil()==date) continue; /// On ne considère pas les processus sensés se réveiller à la date actuelle "date"
				else
				{
					if(diagramme[j].getDateReveil()<min)
					{
						min=diagramme[j].getDateReveil();
					}
				}
			}
			
		}
		
		return min;
	}
	
	public int minimum (int a, int b)
	{
		if (a<0) return b;
		if (a<=b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}
}
