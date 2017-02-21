

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class Apropos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Apropos() 
	{
		this.setModal(true);
		this.setSize(new Dimension(760,500));
		this.setResizable(false);
		this.setTitle("A Propos");
		this.setLocation(400,200);
		this.getContentPane().setBackground(Color.white);		
		
		initComponent();
	}
	
	
	public void initComponent()
	{
	
		regles=new JPanel();
		regles.setBorder(BorderFactory.createTitledBorder(new TitledBorder(""),"Infos sur le TP",0,0,new Font("Times New Roman",1,28),Color.orange));
		regles.setPreferredSize(new Dimension(400,400));
		regles.setBackground(Color.white);
		
		// Créer le JScrollPane qui va contenir le panel précédent
		
		body=new JScrollPane(regles);
		body.setPreferredSize(new Dimension(500,300));
		body.setMinimumSize(new Dimension(500,300));
		body.setMaximumSize(new Dimension(500,300));
		body.setBorder(null);

		
		// Ajout du corps à la fenetre
		
		this.getContentPane().add(body,BorderLayout.CENTER);
        body.setViewportView(aide);
	}


// Attributes
private JScrollPane body;
private JPanel regles;
private JLabel aide=new JLabel(new ImageIcon("img.png"));

}
