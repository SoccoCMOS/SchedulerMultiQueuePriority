import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import org.jfree.chart.ChartUtilities;
import org.jfree.ui.RefineryUtilities;
import Traitements.PanelStatistics;
import Traitements.Resultat;
import Traitements.TableauInfos;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.SystemColor;




public class Accueil extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lblNewLabel = new JLabel("New label");
	JLabel lblQ = new JLabel("Quantum");
	JLabel lblNewLabel_1 = new JLabel("Dur\u00E9e E/S");
	JLabel lblNewLabel_2 = new JLabel("Nombre de Periph ");
	JSpinner qtm = new JSpinner();
	JSpinner dureeES = new JSpinner();
	JSpinner nb_periph = new JSpinner();
	@SuppressWarnings("rawtypes")
	JComboBox comboBox = new JComboBox();
	JLabel lblRecupperation = new JLabel(
			"R\u00E9cup\u00E9ration Infos procesuus ");
	JButton btnOk = new JButton(" OK");
	panneau p = new panneau();

	/************** variables traitements ****************/
	LancementSimul sl;
	GanttDemo ganttdemo;
	int quntam, nbPeriph, dES[];
	private final JButton btnNewButton = new JButton("Enregistrer");
     int indice=0;
	

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Accueil() {

		dES= new int[4];
		
		this.setResizable(false);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		p.setBackground(new Color(204, 204, 255));
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setLayout(null);
		setContentPane(p);

		p.add(lblNewLabel, BorderLayout.NORTH);
		lblQ.setForeground(SystemColor.window);

		lblQ.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblQ.setBounds(20, 30, 71, 23);
		p.add(lblQ);
		lblNewLabel_1.setForeground(SystemColor.window);

		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_1.setBounds(20, 127, 86, 20);
		p.add(lblNewLabel_1);
		lblNewLabel_2.setForeground(SystemColor.window);

		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewLabel_2.setBounds(123, 32, 127, 19);
		p.add(lblNewLabel_2);

		qtm.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		qtm.setBounds(20, 63, 71, 23);
		p.add(qtm);

		dureeES.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		dureeES.setBounds(136, 127, 63, 23);
		p.add(dureeES);
		nb_periph.setModel(new SpinnerNumberModel(0, 0, 4, 1));

		nb_periph.setBounds(136, 61, 66, 23);
		p.add(nb_periph);
		comboBox.setForeground(SystemColor.inactiveCaptionText);

		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Depuis un fichier ", "Saisie manuelle " }));
		comboBox.setBounds(245, 172, 132, 21);
		p.add(comboBox);
		lblRecupperation.setForeground(SystemColor.window);

		lblRecupperation.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblRecupperation.setBounds(10, 172, 240, 20);
		p.add(lblRecupperation);

		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnOk.setBounds(329, 228, 71, 23);
		p.add(btnOk);
		btnOk.setEnabled(false);
		
		btnNewButton.setForeground(SystemColor.inactiveCaptionText);
		btnNewButton.setEnabled(false);
		
		btnNewButton.setBounds(246, 127, 89, 23);
		
		p.add(btnNewButton);
		this.traitements();
	}

	public void traitements() {
		
		this.quntam = Integer.parseInt(this.qtm.getValue().toString());
		this.nbPeriph = Integer.parseInt(this.nb_periph.getValue().toString());		
		
		ChangeListener DUREE = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

			

					if (!dureeES.getValue().toString().equals("0")) {
						btnNewButton.setEnabled(true);

					}

					else {
						btnNewButton.setEnabled(false);
					}
				

			}
		};
		dureeES.addChangeListener(DUREE);
		btnNewButton.addMouseListener(new MouseAdapter() {
		
			public void mouseClicked(MouseEvent arg0) {
				
            if ( btnNewButton.isEnabled())
			 { if ( indice < Integer.parseInt(nb_periph.getValue()
									.toString())){
			System.out.println( Integer.parseInt(dureeES.getValue().toString()));
            	dES[indice]=Integer.parseInt(dureeES.getValue().toString());
            	indice++;
            	btnNewButton.setEnabled(false);;}
             }
			}
		});
		

		this.btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (btnOk.isEnabled()) {
       for ( int i=0; i<indice ; i++) System.out.println(dES[i]);
					
					
					if (comboBox.getSelectedIndex() == 0) {

						JFileChooser chooser = new JFileChooser();
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"TEXT FILES", "txt", "text");
						chooser.setFileFilter(filter);
						Component parent = null;
						int returnVal = chooser.showOpenDialog(parent);
						if (returnVal == JFileChooser.APPROVE_OPTION) {

							File selection = chooser.getSelectedFile();

							Init t = new Init(selection.getAbsolutePath());
							sl = new LancementSimul();
							//dES = Integer.parseInt(dureeES.getValue().toString());
							quntam = Integer
									.parseInt(qtm.getValue().toString());
							nbPeriph = Integer.parseInt(nb_periph.getValue()
									.toString());
							sl.lancerSimulateur(quntam, nbPeriph,t.nbProcessus,dES ,t.p);
							ganttdemo = new GanttDemo("Diagramme Gantt", sl.getSimul().getDonnees(), t.nbProcessus);
							ganttdemo.pack();
							// .setVisible(false);
							RefineryUtilities.centerFrameOnScreen(ganttdemo);
							TableauInfos tab = new TableauInfos(t.nbProcessus,
									t.p);
							PanelStatistics pan = new PanelStatistics(sl
									.getSimul().getSystemTimeStamp(), sl
									.getSimul().getTauxOccupCPU(),sl.getSimul().getQuantum(),sl.getSimul().getNbPeriph(),sl.getSimul().getDurES());
							Resultat res = new Resultat(ganttdemo
									.getContentPane(), tab, pan);
							res.setVisible(true);
							JButton j = new JButton("Enregistrer resultat");
							ganttdemo.getContentPane().setLayout(null);
							ganttdemo.getContentPane().add(j);
							j.setBounds(780, 30, 250, 30);

							j.addMouseListener(new MouseAdapter() {
								public void mouseClicked(MouseEvent arg0) {

									JFileChooser chooser = new JFileChooser();
									chooser.setDialogTitle("Enregistrer sous JPEG");
									chooser.showSaveDialog(ganttdemo
											.getContentPane());
									chooser.setApproveButtonText("Enregistrer");
									chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
									File selection = chooser.getSelectedFile();
									if (selection != null) {
										ganttdemo.getJfreechart()
												.createBufferedImage(500, 500);

										try {
											ChartUtilities.saveChartAsJPEG(
													selection,
													ganttdemo.jfreechart, 450,
													450);
										} catch (IOException e1) {
											
											 e1.printStackTrace();
										
										}
									}

								}

							});
						}

					} else {

						if (comboBox.getSelectedIndex() == 1) {

							//dES = Integer.parseInt(dureeES.getValue().toString());
							quntam = Integer
									.parseInt(qtm.getValue().toString());
							nbPeriph = Integer.parseInt(nb_periph.getValue()
									.toString());
							RecupInfo fr = new RecupInfo(quntam, dES, nbPeriph);
							fr.setVisible(true);
						}

					}
				}

			}
		});

		ChangeListener qtm1 = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				if (!btnOk.isEnabled()) {
					if (!qtm.getValue().toString().equals("0")) {
						btnOk.setEnabled(true);
					} else
						btnOk.setEnabled(false);
				}
			}
		};

		this.qtm.addChangeListener(qtm1);

	}
}