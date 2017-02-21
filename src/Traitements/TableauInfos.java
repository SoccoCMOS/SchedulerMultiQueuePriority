package Traitements;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




import DonneesStatiques.Processus;


/// JExcel API
import java.io.File;
import java.io.IOException; 

import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

@SuppressWarnings("serial")
public class TableauInfos extends JPanel implements ActionListener {

	int i;
	Processus tabProcess[];
	int nbProcess;
	JScrollPane zoneResult;
	JTable tabResu;
	PanelStatistics pan;
	JButton saveToExcel;
	
	@SuppressWarnings("unchecked")
	public TableauInfos(int nb, Processus tab[])
	{
		this.nbProcess=nb;
		tabProcess=new Processus[nb];
		this.tabProcess=tab;
		
		this.setSize(new Dimension(1050,this.nbProcess*40));
		this.setLocation(200, 300);
		this.setLayout(null);
		
		/// Graphisme
		
		/// Création du tableau
		@SuppressWarnings("rawtypes")
		Vector columnNames=new Vector();
		columnNames.add("Nom Processus");
		columnNames.add("PID");
		columnNames.add("Priorité");
		columnNames.add("Date d'entrée");
		columnNames.add("Temps d'exécution");
		columnNames.add("Temps de résidence");
		columnNames.add("Temps d'Attente");
		columnNames.add("Temps bloqué");
		
		DefaultTableModel model = new DefaultTableModel(columnNames, 8);
 
		tabResu=new JTable(this.nbProcess,8);
		
		tabResu.setModel(model);
		
		/// Modification du header du tableau
		
		/// Remplissage
		for(i=0; i<this.nbProcess; i++)
		{
			tabResu.setValueAt(this.tabProcess[i].getName(),i,0);
			tabResu.setValueAt(this.tabProcess[i].getPID(),i,1);
			tabResu.setValueAt(this.tabProcess[i].getPriority(),i,2);
			tabResu.setValueAt(this.tabProcess[i].getDateEntree(),i,3);
			tabResu.setValueAt(this.tabProcess[i].getTmpExecution(),i,4);
			tabResu.setValueAt(this.tabProcess[i].getTmpResidence(),i,5);
			tabResu.setValueAt(this.tabProcess[i].getTmpAttente(),i,6);
			tabResu.setValueAt(this.tabProcess[i].getTmpBlq(),i,7);
		}
		
		/// Création de la zone Scroll
		
		zoneResult=new JScrollPane(tabResu);
		saveToExcel=new JButton("Save Results");
		saveToExcel.setCursor(new Cursor(12));
		
		/// Gestion du bouton
		this.saveToExcel.addActionListener(this);
		
		zoneResult.setBounds(10, 10,1000,this.nbProcess*40);
		this.add(zoneResult);
		saveToExcel.setBounds(350,this.nbProcess*40 + 10,180, 30);
		this.add(saveToExcel);
		
	}

	public Processus[] getTabProcess() {
		return tabProcess;
	}

	public void setTabProcess(Processus[] tabProcess) {
		this.tabProcess = tabProcess;
	}

	public int getNbProcess() {
		return nbProcess;
	}

	public void setNbProcess(int nbProcess) {
		this.nbProcess = nbProcess;
	}
	
	
	public void saveToExcel(String path)
	{
		int i;
		try {
			/// Ouverture du fichier
			File output = new File(path);
			/// Instanciation d'un classeur
            WritableWorkbook writableWorkbook = Workbook.createWorkbook(output);
            /// Instanciation d'une feuille de calcul
            WritableSheet writableSheet = writableWorkbook.createSheet("Infos Processus", 0);
 
            //Spécification des labels des différentes colonnes
            
            Label labelNom = new Label(0, 0, "Nom");
            Label labelPID = new Label(1, 0, "PID");
            Label labelPriority = new Label(2, 0, "Priorité");
            Label labelEntry = new Label(3, 0, "Entry Date");
            Label labelExecTime = new Label(4, 0, "Execution Time");
            Label labelResidTime = new Label(5, 0, "Residence Time");
            Label labelWait = new Label(6, 0, "Waiting Time");
            Label labelBlq = new Label(7, 0, "Blocked Time");
 
            //Ajout des labels
            writableSheet.addCell(labelNom);
            writableSheet.addCell(labelPID);
            writableSheet.addCell(labelPriority);
            writableSheet.addCell(labelEntry);
            writableSheet.addCell(labelExecTime);
            writableSheet.addCell(labelResidTime);
            writableSheet.addCell(labelWait);
            writableSheet.addCell(labelBlq);
            
            
            // Création et ajout des donnees
            Label nomProcessus;
            Number PID,priority,entry,exec,resid,wait,blq;
            
            for (i=0; i<this.nbProcess; i++)
            {
            	nomProcessus=new Label(0,i+1,this.tabProcess[i].getName());
            	PID=new Number(1,i+1,this.tabProcess[i].getPID());
            	priority=new Number(2,i+1,this.tabProcess[i].getPriority());
            	entry=new Number(3,i+1,this.tabProcess[i].getDateEntree());
            	exec=new Number(4,i+1,this.tabProcess[i].getTmpExecution());
            	resid=new Number(5,i+1,this.tabProcess[i].getTmpResidence());
            	wait=new Number(6,i+1,this.tabProcess[i].getTmpAttente());
            	blq=new Number(7,i+1,this.tabProcess[i].getTmpBlq());
            	
            	/// Ajout
                writableSheet.addCell(nomProcessus);
                writableSheet.addCell(PID);
                writableSheet.addCell(priority);
                writableSheet.addCell(entry);
                writableSheet.addCell(exec);
                writableSheet.addCell(resid);
                writableSheet.addCell(wait);
                writableSheet.addCell(blq);
            }
 
            //Write and close the workbook
            writableWorkbook.write();
            writableWorkbook.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }}
        



	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser explorer=new JFileChooser();
		explorer.setDialogTitle("Enregistrer sous fichier Excel");
		explorer.showSaveDialog(this);
		explorer.setApproveButtonText("Enregistrer");
		explorer.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); /// Choix du répertoire de sauvegarde
		
		try
		{
			if (explorer.getSelectedFile()!=null) 
				{
					this.saveToExcel(explorer.getSelectedFile().getAbsolutePath()+".xls");
				}
		}
		catch(Exception except)
		{
			except.printStackTrace();
		}
	}

}
