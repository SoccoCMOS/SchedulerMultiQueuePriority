package Traitements;

import java.util.LinkedList;

import DonneesStatiques.DataDG;
import DonneesStatiques.Peripherique;
import DonneesStatiques.Processus;
import DonneesStatiques.State;
import DonneesStatiques.TypeEtat;
import DonneesStatiques.typeBehaviour;


public class ItHorloge extends Event{
	
	public ItHorloge(int numero, int q)
	{
		super(numero,q);
	}
	
	public Dispatch traiter(Processus tabProcess[],int nbProcess,int date, DataDG diagramme[],LinkedList<Integer> FilesPrets[], LinkedList<Integer> FileProcBlq,Peripherique periph[],int nbPeriph)
	{
		Dispatch e=new Dispatch(-1,-1);
		
		diagramme[this.numProcess].addState(new State(TypeEtat.Pret,-1,date,date));
		diagramme[this.numProcess].setValQuantum(this.quantum);
		
		tabProcess[numProcess].resetPriority();
		if (tabProcess[this.numProcess].noBehaviour()==false)
		{ /// Si sa fin d'exécution ne coincide pas avec cette fin de quantum
		if(tabProcess[this.numProcess].getNextBehaviour().getTypeComportement()==typeBehaviour.exec)
		{
			FilesPrets[0].addLast(this.numProcess);}
		}
		
		if (this.isListIsEmpty()) 
			{
				e=dispatcher(tabProcess,nbProcess,date,diagramme,FilesPrets,FileProcBlq,periph,nbPeriph);
			}
		else
		{
			e=new Dispatch(date,-1);
		}
		
		System.out.println("Traitement fin de quantum ");
		
		return e;
	}

}
