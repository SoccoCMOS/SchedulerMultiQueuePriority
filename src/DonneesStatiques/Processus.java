package DonneesStatiques;

import java.util.LinkedList;

public class Processus {
	String Name;
	int PID;
	int tmpExecution;
	int dateEntree;
	
	int priority=0;
	int tmpResidence=0;
	int tmpAttente=0;
	
	int tmpBlq=0;
	
	LinkedList<Behaviour> comportement;
	

	public Processus()
	{
		comportement=new LinkedList<Behaviour>();
	}
	public Processus(String name, int PID, int dateEntree, int tmpExec)
	{
		this.Name=name;
		this.PID=PID;
		this.dateEntree=dateEntree;
		this.tmpExecution=tmpExec;
		this.comportement=new LinkedList<Behaviour>();
	}

	
	public int getTmpBlq() {
		return tmpBlq;
	}
	public void setTmpBlq(int tmpBlq) {
		this.tmpBlq = tmpBlq;
	}
	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public int getPID() {
		return PID;
	}


	public void setPID(int pID) {
		PID = pID;
	}


	public int getTmpExecution() {
		return tmpExecution;
	}


	public void setTmpExecution(int tmpExecution) {
		this.tmpExecution = tmpExecution;
	}


	public int getDateEntree() {
		return dateEntree;
	}


	public void setDateEntree(int dateEntree) {
		this.dateEntree = dateEntree;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public int getTmpResidence() {
		return tmpResidence;
	}


	public void setTmpResidence(int dateSortie) {
		this.tmpResidence = dateSortie-this.dateEntree;
	}


	public int getTmpAttente() {
		return tmpAttente;
	}


	public void setTmpAttente(int tmpAttente) {
		this.tmpAttente = tmpAttente;
	}


	public LinkedList<Behaviour> getComportement() {
		return comportement;
	}


	public void setComportement(LinkedList<Behaviour> comportement) {
		this.comportement = comportement;
	}
	
   
 public void afficherProcessusConsole()
 {
	 System.out.println("\n Process Name: " + this.Name + "\n PID= " + this.PID + "\n Date d'entree: " + this.dateEntree + "\n Temps d'execution: " + this.tmpExecution + "\n Temps de residence: " + this.tmpResidence + "\n Temps d'attente: " + this.tmpAttente);
	 for (Behaviour b: this.comportement)
	 {
		 b.afficherBehaviour();
	 }
 }
 
 
 public void incPriority()
 {
	 this.priority++;
 }
 
 public void resetPriority()
 {
	 this.priority=0;
 }
 
 public Behaviour getNextBehaviour()
 {
	if (noBehaviour()==true) return new Behaviour(typeBehaviour.none,-1);
	return this.comportement.peekFirst();
 }
 
 public boolean noBehaviour()
 {
	 return this.comportement.isEmpty();
 }
 
 public void incTmpBlq(int v)
 {
	 this.tmpBlq+=v;
 }
 public void calculTmpAttente()
 {
	 this.tmpAttente=this.tmpResidence-this.tmpExecution-this.tmpBlq;
 }
 
	public int calculDateSortie()
	{
		return (this.tmpResidence + this.dateEntree);
	}

}
