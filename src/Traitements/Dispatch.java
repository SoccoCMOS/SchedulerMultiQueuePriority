package Traitements;

public class Dispatch {
	
	int date;
	int procElu;
	
	public Dispatch(int date, int proc)
	{
		this.date=date;
		this.procElu=proc;
	}
	
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getProcElu() {
		return procElu;
	}
	public void setProcElu(int procElu) {
		this.procElu = procElu;
	}
	
	
	public void afficherResTraitement()
	{
		System.out.println("Date :  " + this.date + "; Processus elu : " + this.procElu );
	}
	
	

}
