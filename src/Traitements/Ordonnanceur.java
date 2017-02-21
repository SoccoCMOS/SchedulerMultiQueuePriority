package Traitements;

import java.util.ArrayList;

import java.util.LinkedList;

import DonneesStatiques.DataDG;
import DonneesStatiques.Peripherique;
import DonneesStatiques.Processus;
import DonneesStatiques.State;
import DonneesStatiques.TypeEtat;
import DonneesStatiques.typeBehaviour;

//import DonneesStatiques.Processus;

public class Ordonnanceur {

	// LinkedList<Processus> FilesPrets[];
	// LinkedList<Processus> FileProcBlq[];

	LinkedList<Integer> FilesPrets[];
	LinkedList<Integer> FileProcBlq;
	Peripherique periph[];
	int nbPeriph;
	int procActif = -1;
	int quantum;
	ArrayList<Event> listEvent;

	public LinkedList<Integer>[] getFilesPrets() {
		return FilesPrets;
	}

	public void setFilesPrets(LinkedList<Integer>[] filesPrets) {
		FilesPrets = filesPrets;
	}

	public LinkedList<Integer> getFileProcBlq() {
		return FileProcBlq;
	}

	public void setFileProcBlq(LinkedList<Integer> fileProcBlq) {
		FileProcBlq = fileProcBlq;
	}

	public Peripherique[] getPeriph() {
		return periph;
	}

	public void setPeriph(Peripherique[] periph) {
		this.periph = periph;
	}

	public int getNbPeriph() {
		return nbPeriph;
	}

	public void setNbPeriph(int nbPeriph) {
		this.nbPeriph = nbPeriph;
	}

	public int getProcActif() {
		return procActif;
	}

	public void setProcActif(int procActif) {
		this.procActif = procActif;
	}

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public ArrayList<Event> getListEvent() {
		return listEvent;
	}

	public void setListEvent(ArrayList<Event> listEvent) {
		this.listEvent = listEvent;
	}

	@SuppressWarnings("unchecked")
	public Ordonnanceur(int durES[], int nbPeriph, int qtm) {
		int i;
	  
		this.nbPeriph = nbPeriph;
		this.periph = new Peripherique[nbPeriph];

		for (i = 0; i < nbPeriph; i++) {
			periph[i] = new Peripherique("F" + i, durES[i]);
		}

		this.procActif = -1;
		// this.FileProcBlq=new LinkedList<Processus>();
		this.FileProcBlq = new LinkedList<Integer>();

		this.FilesPrets = new LinkedList[10];

		for (i = 0; i < 10; i++) {
			FilesPrets[i] = new LinkedList<Integer>();
			// FilesPrets[i]=new LinkedList<Processus>();
		}

		this.quantum = qtm;

		this.listEvent = new ArrayList<Event>();
	}

	public void addEventAtDate(int date, DataDG diagramme[],
			Processus tabProcess[], int nbProcess) {
		// Declaration des variables
		Event e;
		int i;

		// Ajout des events

		for (i = 0; i < nbProcess; i++) {
			if (diagramme[i].getDateReveil() == date) // On considère les
														// processus dont la
														// date de réveil = date
			{
				// On traite les evenements sur processus actif
				if (diagramme[i].getState().getEtat() == TypeEtat.Actif) {
					if (diagramme[i].getValQuantum() == 0) // / IT_HORLOGE
					{
						e = new ItHorloge(i, this.quantum);
						//e.setListIsEmpty(listEvent.isEmpty());
						this.listEvent.add(e);
					}

					if (tabProcess[i].getNextBehaviour().getTypeComportement() == typeBehaviour.ES) // Va
																									// lancer
																									// une
																									// ES
					{
						e = new ItSVC(i, TypeSVC.LancementES, tabProcess[i]
								.getNextBehaviour().getTmpOrNum(), this.quantum);
						//e.setListIsEmpty(listEvent.isEmpty());
						this.listEvent.add(e);
					}

					if (tabProcess[i].noBehaviour() == true) // Fin d'execution
					{
						e = new ItSVC(i, TypeSVC.FinPrgm, -1, this.quantum);
						//e.setListIsEmpty(listEvent.isEmpty());
						this.listEvent.add(e);
					}
				}

				if (diagramme[i].getState().getEtat() == TypeEtat.ES)
				// On traite les evenements sur processus en ES, les processus
				// en attente d'ES sont traités lors de la fin d'ES
				{
					System.out.println(this.FileProcBlq.indexOf(tabProcess[i]
							.getPID()));
					e = new FinES(i, diagramme[i].getState().getNumPeriph(),
							this.procActif,
							this.FileProcBlq.indexOf(tabProcess[i].getPID()),
							this.quantum);
					
					this.listEvent.add(e);
				}

				if (diagramme[i].getState().getEtat() == TypeEtat.Inexistant) {
					// arrivée de processus
					e = new NewProcess(i, this.procActif, this.quantum);
					//e.setListIsEmpty(listEvent.isEmpty());
					this.listEvent.add(e);
				}
			}

			continue;
		}

	}

	public int getPriorEvent() {
		// Test de la liste
		if (this.listEvent.isEmpty() == true)
			return -1;

		// Declaration des variables
		int i = -1;

		// 1. On cherche les fin de quantum
		i = searchItHorloge();
		if (i != -1)
			return i;

		// 2. On cherche les appels au superviseur
		i = searchItSVC();
		if (i != -1)
			return i;

		// 3. On cherche les fins d'E/S
		i = searchFinES();
		if (i != -1)
			return i;

		// 4. On cherche les arrivées de nouveau processus
		i = searchNewProcess();
		if (i != -1)
			return i;

		return -1;

	}

	// / Recherche des types d'evenements

	public int searchItHorloge() {
		for (Event e : this.listEvent) {
			if (e instanceof ItHorloge) {
				return listEvent.indexOf(e);
			}
		}
		return -1;
	}

	public int searchItSVC() {
		for (Event e : this.listEvent) {
			if (e instanceof ItSVC) {
				return listEvent.indexOf(e);
			}
		}
		return -1;
	}
	
	public int searchFinES() {
		for (Event e : this.listEvent) {
			if (e instanceof FinES) {
				return listEvent.indexOf(e);
			}
		}
		return -1;
	}

	public int searchNewProcess() {
		for (Event e : this.listEvent) {
			if (e instanceof NewProcess) {
				return listEvent.indexOf(e);
			}
		}
		return -1;
	}

	// /// Générer les données du DG

	public DataDG[] genererData(Processus tabProcess[], int nbProcess) {
		int i;
		int date;
		
		Event toTreat;
		Dispatch d = new Dispatch(-1, 1);
		DataDG diagramme[] = new DataDG[nbProcess];

		if (this.noProcess(tabProcess, nbProcess) == true)
			return null;
		// Initialisation
		
		/// Initialisation de la date de début de la simulation à la date de début du premier processus
		date=tabProcess[0].getDateEntree();
		for (i=1; i<nbProcess; i++)
		{
			if (tabProcess[i].getDateEntree()<date) date=tabProcess[i].getDateEntree();
		}
		/// Initialisation de la table des données du diagramme

		for (i = 0; i < nbProcess; i++) {
			diagramme[i] = new DataDG(tabProcess[i].getDateEntree(), this.quantum);
			diagramme[i].addState(new State(TypeEtat.Inexistant, -1, 0,
					tabProcess[i].getDateEntree()));
		}

		// Exécution

		this.addEventAtDate(date, diagramme, tabProcess, nbProcess);

		while ((this.noProcess(tabProcess, nbProcess) == false)
				|| (this.listEvent.isEmpty() == false)) {
			
			while (this.listEvent.isEmpty() == false) {

				//toTreat = this.listEvent.get(this.getPriorEvent());
				if (listEvent.size() > 1) // / Pour traiter le cas d'evenements
											// simultanés
				{
					for (Event ev : listEvent) {
						ev.setListIsEmpty(false);
					}
				}

				else {
					for (Event ev : listEvent) {
						ev.setListIsEmpty(true);
					}
				}
				System.out.println("Event a traiter " + this.getPriorEvent());
				toTreat = this.listEvent.get(this.getPriorEvent());
				this.listEvent.remove(toTreat);
				d = toTreat.traiter(tabProcess, nbProcess, date, diagramme,
						FilesPrets, FileProcBlq, periph, nbPeriph);
				// / MAJ
				this.procActif = d.getProcElu();
				date = d.getDate();
				d.afficherResTraitement();
				if (listEvent.isEmpty() == false) // / encore des evenements à
													// traiter
				{
					// / mettre à jour leurs processus actifs
					for (Event e : this.listEvent) {
						if (e instanceof FinES) {
							((FinES) e).setProcessIt(d.getProcElu());
						}
						if (e instanceof NewProcess) {
							((NewProcess) e).setProcActif(d.getProcElu());
						}
					}
				}
			}

			this.addEventAtDate(date, diagramme, tabProcess, nbProcess);
		}

		return diagramme;
	}

	public boolean noProcess(Processus tab[], int taille) {
		int i;
		for (i = 0; i < taille; i++) {
			if (tab[i].noBehaviour() == false)
				return false;
		}
		return true;
	}

	public void afficherProcesseurState() {
		int i;
		System.out.println("\n Processus Actif " + this.procActif);
		System.out.println("\n Affichage de la liste des evenements \n");
		for (i = 0; i < this.listEvent.size(); i++) {
			listEvent.get(i).afficherEvent();
		}
		System.out.println("\n Affichage de la file bloq \n");
		System.out.println(this.FileProcBlq.toString());

		System.out.println("\n Affichage des files prets \n");
		for (i = 0; i < 10; i++) {
			System.out.println(this.FilesPrets[i].toString());
		}

		System.out.println("\n Affichage des peripheriques \n");
		for (i = 0; i < this.nbPeriph; i++) {
			this.periph[i].afficherPeripherique();
		}
	}
}
