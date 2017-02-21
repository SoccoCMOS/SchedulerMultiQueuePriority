package Traitements;
import java.awt.Component;


import javax.swing.JFrame;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class Resultat extends JFrame {

	private JTabbedPane Onglets;
	//private MENU;
	
	public Resultat(Component Gantt, TableauInfos tab, PanelStatistics stat) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 500);
		
		/// Ajout du menu
		
		/// Ajout des onglets
		Onglets=new JTabbedPane();
		
		Onglets.addTab("Diagramme de Gantt",Gantt);
		Onglets.addTab("Infos Processus",tab);
		Onglets.addTab("Statistiques et Performances",stat);
		
		this.getContentPane().add(Onglets);
		
	}

}
