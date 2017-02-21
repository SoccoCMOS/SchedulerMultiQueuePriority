package Traitements;

import java.util.LinkedList;

import DonneesStatiques.DataDG;
import DonneesStatiques.Peripherique;
import DonneesStatiques.Processus;
import DonneesStatiques.State;
import DonneesStatiques.TypeEtat;

public class ItSVC extends Event {
	
	TypeSVC type;
	int numPeriph; // Si lancement d'ES
	
	public ItSVC(int numero, TypeSVC typ, int periph, int q)
	{
		super(numero,q);
		this.type=typ;
		this.numPeriph=periph;
	}
	
	
	public TypeSVC getType() {
		return type;
	}

	public void setType(TypeSVC type) {
		this.type = type;
	}


	public int getNumPeriph() {
		return numPeriph;
	}

	public void setNumPeriph(int numPeriph) {
		this.numPeriph = numPeriph;
	}

	public Dispatch traiter(Processus tabProcess[],int nbProcess,int date, DataDG diagramme[],LinkedList<Integer> FilesPrets[], LinkedList<Integer> FileProcBlq,Peripherique periph[],int nbPeriph)
	{
		Dispatch e=new Dispatch(-1,-1);
		
		if (this.getType()==TypeSVC.LancementES) /// S'il s'agit d'un SVC pour lancer une E/S
		{
			System.out.println("\n Traitement de l'IT de lancement d'E/S \n" + this.numProcess + " Valeur qtm  = " + this.quantum );
			State etat;
			/// Incrémenter la priorité du processus
			tabProcess[this.numProcess].incPriority();
			tabProcess[this.numProcess].getComportement().pollFirst(); /// Supprime le trt d'E/S
			
			/// Maj périphérique 
			if (periph[this.numPeriph].getProcEnCours()==-1) // Aucun processus sur le periph
			{
				periph[this.numPeriph].setProcEnCours(this.numProcess);
				etat=new State(TypeEtat.ES,this.numPeriph,date,date+periph[this.numPeriph].getTmpES());
			}
			else
			{
				/// Maj de l'etat du processus et calcul de la prochaine date de réveil
				etat=new State(TypeEtat.AttenteES,this.numPeriph,date,periph[this.numPeriph].getNbAtt()*periph[this.numPeriph].getTmpES()+diagramme[periph[this.numPeriph].getProcEnCours()].getDateReveil());
				periph[this.numPeriph].enfilerProcessus(tabProcess[this.numProcess]);
			}
			diagramme[this.numProcess].addState(etat);
			diagramme[this.numProcess].setDateReveil(etat.getdateReveil());
			//FilesPrets[tabProcess[this.numProcess].getPriority()].remove(this.numProcess);
			FileProcBlq.addLast(this.numProcess);
			/// Sauvegarder date d'entrée en file bloquée
			tabProcess[this.numProcess].incTmpBlq(-date);
		}
		
		else  /// Terminaison de programme
		{
			System.out.println("Traitement de fin d'execution ");
			diagramme[this.numProcess].addState(new State(TypeEtat.Terminated,-1,date,date));
			diagramme[this.numProcess].setDateReveil(-1); // Tuer processus
			
			/// calcul des performances
			
			// 1. Temps de residence
			tabProcess[numProcess].setTmpResidence(date);
			
			// 2. Temps d'attente
			tabProcess[numProcess].calculTmpAttente();
		}
		
		
		if (this.isListIsEmpty()==true) 
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
