package DonneesStatiques;

public class Behaviour {
	
	typeBehaviour typeComportement;
	int tmpOrNum;
	
	public Behaviour()
	{
		
	}
	
	public Behaviour(typeBehaviour type, int tmp)
	{
		this.typeComportement=type; 
		this.tmpOrNum=tmp;
	}
	
	
	public typeBehaviour getTypeComportement() {
		return typeComportement;
	}
	public void setTypeComportement(typeBehaviour typeComportement) {
		this.typeComportement = typeComportement;
	}
	public int getTmpOrNum() {
		return tmpOrNum;
	}
	public void setTmpOrNum(int tmpOrNum) {
		this.tmpOrNum = tmpOrNum;
	}
	
	
	public void decTmps(int d)
	{
		this.tmpOrNum-=d;
	}
	
	public void afficherBehaviour()
	{
		System.out.println("\n Type Comportement : "+this.typeComportement);
		if (this.typeComportement==typeBehaviour.exec) System.out.println(" ** Temps d'execution : " + this.tmpOrNum);
		if (this.typeComportement==typeBehaviour.ES) System.out.println(" ** Numero de peripherique : " + this.tmpOrNum);	
	}

}
