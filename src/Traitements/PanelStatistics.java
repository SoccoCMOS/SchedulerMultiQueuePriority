package Traitements;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;

import javax.swing.JTextField;

import java.awt.Font;

@SuppressWarnings("serial")
public class PanelStatistics extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField quantum;
	private JTextField nbperiph;
	private JTextField durES;

	public PanelStatistics()
	{
		
	}
	
	public PanelStatistics(int sysTime, float taux, int quantum, int nbrePeriph, int durES[]) {
		
		String s;
		int i;
		
		setBackground(new Color(205, 92, 92));
		setForeground(new Color(255, 250, 205));
		
		JLabel lblTauxOccupationCpu = new JLabel("Taux Occupation CPU  = ");
		lblTauxOccupationCpu.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblTauxOccupationCpu.setForeground(new Color(255, 255, 255));
		lblTauxOccupationCpu.setBackground(Color.PINK);
		add(lblTauxOccupationCpu);
		
		textField = new JTextField(new Float(taux).toString());
		textField.setEditable(false);
		add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("%");
		label.setFont(new Font("Tahoma", Font.BOLD, 18));
		label.setForeground(new Color(255, 255, 255));
		add(label);
		
		JLabel lblSystemTimeStamp = new JLabel(" System Time Stamp   = ");
		lblSystemTimeStamp.setForeground(new Color(255, 255, 255));
		lblSystemTimeStamp.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblSystemTimeStamp.setBackground(Color.PINK);
		add(lblSystemTimeStamp);
		
		textField_1 = new JTextField(new Integer(sysTime).toString());
		textField_1.setEditable(false);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblMs = new JLabel("ms");
		lblMs.setForeground(new Color(255, 255, 255));
		lblMs.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblMs);
		
		JLabel lblQuantum = new JLabel(" Quantum   = ");
		lblQuantum.setForeground(new Color(255, 255, 255));
		lblQuantum.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblQuantum.setBackground(Color.PINK);
		add(lblQuantum);
		
		this.quantum = new JTextField(new Integer(quantum).toString());
		this.quantum.setEditable(false);
		add(this.quantum);
		this.quantum.setColumns(10);
		
		JLabel lblQt = new JLabel("ms");
		lblQt.setForeground(new Color(255, 255, 255));
		lblQt.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblQt);
		
		
		JLabel nbPeriph = new JLabel(" Nombre de peripheriques  = ");
		nbPeriph.setForeground(new Color(255, 255, 255));
		nbPeriph.setFont(new Font("Times New Roman", Font.BOLD, 18));
		nbPeriph.setBackground(Color.PINK);
		add(nbPeriph);
		
		this.nbperiph = new JTextField(new Integer(nbrePeriph).toString());
		this.nbperiph.setEditable(false);
		add(this.nbperiph);
		this.quantum.setColumns(10);
		add(this.nbperiph);
		
		s=new String("Duree ES: ");
		for (i=0; i<nbrePeriph;i++)
		{
			s=s+ " ** " + durES[i];
		}
		this.durES=new JTextField(s);
		add(this.durES);
	}

}
