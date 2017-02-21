package Traitements;

import java.util.LinkedList;

import DonneesStatiques.DataDG;
import DonneesStatiques.Peripherique;
import DonneesStatiques.Processus;
import DonneesStatiques.State;
import DonneesStatiques.TypeEtat;

public class NewProcess extends Event {
	
	int procActif;

	public int getProcActif() {
		return procActif;
	}

	public void setProcActif(int procActif) {
		this.procActif = procActif;
	}

	public NewProcess(int numero, int PrIt, int q)
	{
		super(numero,q);
		this.procActif=PrIt;
	}
	
	public Dispatch traiter(Processus tabProcess[],int nbProcess,int date, DataDG diagramme[],LinkedList<Integer> FilesPrets[], LinkedList<Integer> FileProcBlq,Peripherique periph[],int nbPeriph)
	{
		Dispatch e=new Dispatch(-1,-1);
		
		System.out.println("\n Traitement de l'IT d'arrivee de nouveau processus \n" + this.numProcess + " Valeur qtm  = " + this.quantum + "  process interrompu: " + this.procActif);
		
		diagramme[super.getNumProcess()].addState(new State(TypeEtat.Pret,-1,date,date));
		FilesPrets[0].addLast(super.getNumProcess());
		if (this.procActif!=-1) /// Interrompre le processus actif
		{
			diagramme[this.procActif].addState(new State(TypeEtat.Pret,-1,date,date));
			FilesPrets[tabProcess[this.procActif].getPriority()].addFirst(this.procActif);
			
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
