import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartUtilities;
import org.jfree.ui.RefineryUtilities;
import Traitements.PanelStatistics;
import Traitements.Resultat;
import Traitements.TableauInfos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import DonneesStatiques.*;



public class RecupInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	public int tempsDexec = 0;
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu test2 = new JMenu("aide");
	

	
	private JMenuItem item3 = new JMenuItem("Afficher l'aide");
	JFileChooser file = new JFileChooser();
	JLabel lblNewLabel = new JLabel("DGantt");
	JLabel lblNewLabel_1 = new JLabel("N\u00B0processus ");
	JSpinner prio = new JSpinner();
	JSpinner Tdebut = new JSpinner();
	JLabel lblNewLabe = new JLabel("Priorit\u00E9 ");
	JLabel lblTemps = new JLabel("Arrive\u00E9 (ms)");
	JSpinner totalExec = new JSpinner();
	JLabel lblNewLabel_2 = new JLabel("Temps d'ex\u00E9cution");
	JButton ajouterTempsDexec = new JButton("Ajouter Ts CPU");
	JLabel lblNewLabel_3 = new JLabel("Temps EXEC");
	JButton ajouterNperiph = new JButton("Ajouter E/S");
	JLabel lblEsSurLe = new JLabel("E/S sur le p\u00E9riph");
	JSpinner tempsExec = new JSpinner();
	JSpinner numeroPeriph = new JSpinner();
	JButton btnNewButton_2 = new JButton("Diagramme de Gantt");
	panneau panel = new panneau();
	
	@SuppressWarnings("rawtypes")
	public JComboBox comboBox = new JComboBox();
	/****** infos traitemets ****************/
	public int nbPeriph;
	public int quntam;
	public int dureeES[];
	public Processus[] p = new Processus[10];
	LancementSimul sl;
	public int nbProcessus;
	GanttDemo ganttdemo;
	LinkedList<Behaviour> comportement = new LinkedList<Behaviour>();

	public Processus[] getP() {
		return p;
	}

	public void setP(Processus[] p) {
		this.p = p;
	}

	
	public void setQuntam(int quntam) {
		this.quntam = quntam;
	}

	private JSpinner Npocess = new JSpinner();
	private final JButton ajouterProcess = new JButton("AjouterProcessus");

	@SuppressWarnings("unchecked")
	public RecupInfo( int qtm, int dureeES[], int nbp) {
		nbProcessus = 0;
		nbPeriph = nbp;
		quntam = qtm;
		this.dureeES= dureeES;
		panel.setForeground(SystemColor.window);
		panel.setBackground(SystemColor.activeCaption);
	
		setBounds(new Rectangle(40, 50, 750, 850));
		panel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		// this.panel.setBackground(new Color(153, 180, 209));
		panel.setLayout(null);
		lblNewLabel.setForeground(SystemColor.window);

		lblNewLabel.setFont(new Font("Vrinda", Font.BOLD, 65));
		lblNewLabel.setBounds(10, 0, 228, 109);
		panel.add(lblNewLabel);
		lblNewLabel_1.setForeground(SystemColor.window);
		lblNewLabel_1.setBackground(new Color(0, 204, 153));

		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 114, 115, 38);
		panel.add(lblNewLabel_1);
		Npocess.setFont(new Font("Vrinda", Font.PLAIN, 15));
		Npocess.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		Npocess.setBounds(10, 181, 72, 38);
		panel.add(Npocess);
		lblNewLabe.setForeground(SystemColor.window);
		lblNewLabe.setBackground(new Color(0, 204, 153));
		lblNewLabe.setVerticalTextPosition(SwingConstants.TOP);
		lblNewLabe.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabe.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabe.setBounds(135, 123, 89, 38);
		panel.add(lblNewLabe);
		prio.setModel(new SpinnerNumberModel(0, 0, 0, 1));
		prio.setBounds(135, 182, 67, 38);
		panel.add(prio);
		lblTemps.setForeground(SystemColor.window);
		lblTemps.setBackground(new Color(0, 204, 153));
		lblTemps.setAlignmentX(0.5f);
		lblTemps.setFont(new Font("Dialog", Font.BOLD, 15));
		lblTemps.setVisible(true);
		lblTemps.setBounds(234, 117, 104, 33);
		panel.add(lblTemps);
		Tdebut.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0),
				null, new Integer(1)));
		Tdebut.setBounds(249, 182, 62, 38);
		panel.add(Tdebut);
		lblNewLabel_2.setForeground(SystemColor.window);
		lblNewLabel_2.setBackground(new Color(0, 204, 153));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_2.setBounds(348, 114, 141, 39);
		panel.add(lblNewLabel_2);
		totalExec.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		totalExec.setBounds(385, 182, 62, 38);
		panel.add(totalExec);
		ajouterTempsDexec.setEnabled(false);
		ajouterTempsDexec.setActionCommand("");
		ajouterTempsDexec.setBounds(299, 348, 122, 38);
		panel.add(ajouterTempsDexec);
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD, 15));
		lblNewLabel_3.setForeground(SystemColor.window);
		lblNewLabel_3.setBackground(new Color(0, 204, 153));
		lblNewLabel_3.setBounds(10, 305, 127, 22);
		panel.add(lblNewLabel_3);
		ajouterNperiph.setEnabled(false);
		ajouterNperiph.setBounds(299, 299, 122, 38);
		panel.add(ajouterNperiph);
		lblEsSurLe.setFont(new Font("Dialog", Font.BOLD, 15));
		lblEsSurLe.setForeground(SystemColor.window);
		lblEsSurLe.setBackground(new Color(0, 204, 153));
		lblEsSurLe.setBounds(10, 346, 127, 38);
		panel.add(lblEsSurLe);
		tempsExec.setEnabled(false);
		tempsExec.setModel(new SpinnerNumberModel(new Integer(0),
				new Integer(0), null, new Integer(1)));
		tempsExec.setBounds(170, 302, 67, 33);
		panel.add(tempsExec);
		numeroPeriph.setEnabled(false);
		numeroPeriph.setModel(new SpinnerNumberModel(-1, -1, 4, 1));
		numeroPeriph.setBounds(170, 353, 67, 29);
		panel.add(numeroPeriph);
		btnNewButton_2.setBounds(505, 397, 151, 33);
		panel.add(btnNewButton_2);
		this.setBounds(150, 100, 700, 500);
		/* ----------------------- */

		

		this.test2.add(item3);
	
		this.menuBar.add(test2);
		this.setJMenuBar(menuBar);
		getContentPane().add(panel);
		ajouterProcess.setBounds(359, 397, 122, 33);
		ajouterProcess.setEnabled(false);
		panel.add(ajouterProcess);

		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] {
				"Sans   E/S", "Avec   E/S" }));
		comboBox.setFont(new Font("Vrinda", Font.PLAIN, 15));
		comboBox.setToolTipText("Choisir d'executer avec ou sans E/S");
		comboBox.setBounds(10, 244, 171, 22);
		panel.add(comboBox);
		this.setVisible(true);

		init();

	}

	public void init() {
	this.item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Apropos aide =new Apropos();
			     aide.setVisible(true);

			}
		});

	
	/************ action sur le spinner1 **********/

		ChangeListener listener_totalExec = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				if (!totalExec.getValue().toString().equals("0"))
					ajouterProcess.setEnabled(true);

				else
					ajouterProcess.setEnabled(false);
			}
		};

		ChangeListener listener_numeroProcessus = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				if (Integer.parseInt(totalExec.getValue().toString()) < nbProcessus) {

				Erreur e1 = new Erreur();
                    e1.setVisible(true);
				}

			}
		};

		this.Npocess.addChangeListener(listener_numeroProcessus);
		this.totalExec.addChangeListener(listener_totalExec);

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				if (comboBox.getSelectedItem().toString().equals("Avec   E/S")) {

					// ajouterTempsDexec.setEnabled(true);
					// ajouterNperiph.setEnabled(true);
					// numeroPeriph.setEnabled(true);
					tempsExec.setEnabled(true);

				} else {

					ajouterTempsDexec.setEnabled(false);
					ajouterNperiph.setEnabled(false);
					numeroPeriph.setEnabled(false);
					tempsExec.setEnabled(false);

				}
			}
		});

		this.ajouterProcess.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent arg0) {

				if (ajouterProcess.isEnabled()) {
					if (tempsExec.isEnabled()) {
						Behaviour b = new Behaviour();

						b.setTmpOrNum(Integer.parseInt(tempsExec.getValue()
								.toString()));

						b.setTypeComportement(typeBehaviour.exec);

						comportement.add(b);

					} else {

						if (comboBox.getSelectedIndex() == 0) {
							comportement = new LinkedList<Behaviour>();
							Behaviour b = new Behaviour();
							b.setTmpOrNum(Integer.parseInt(totalExec.getValue()
									.toString()));

							b.setTypeComportement(typeBehaviour.exec);

							comportement.add(b);
							// b.afficherBehaviour();
						}

					}
					if (comboBox.getSelectedIndex() == 1)
						tempsDexec = tempsDexec
								+ Integer.parseInt(tempsExec.getValue()
										.toString());
					p[nbProcessus] = new Processus();
					/********* Verifier les champs des spinners *************/

					p[nbProcessus].setName(Npocess.getValue().toString());
					p[nbProcessus].setDateEntree(Integer.parseInt(Tdebut
							.getValue().toString()));
					p[nbProcessus].setPriority(Integer.parseInt(prio.getValue()
							.toString()));
					p[nbProcessus].setTmpExecution(Integer.parseInt(totalExec
							.getValue().toString()));
					p[nbProcessus].setPID(Integer.parseInt(Npocess.getValue()
							.toString()));
					p[nbProcessus].setComportement(comportement);
					// p[nbProcessus].afficherProcessusConsole();
					comportement = new LinkedList<Behaviour>();
					nbProcessus++;
					tempsDexec = 0;
					Npocess.setValue(nbProcessus);
					Tdebut.setValue(0);
					prio.setValue(0);
					totalExec.setValue(0);
					numeroPeriph.setValue(-1);
					tempsExec.setValue(0);
					comboBox.setSelectedIndex(0);

				}

			}

		});

		/***** action sur le bouton ajouter Temps Exec *******/
		ajouterTempsDexec.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Behaviour b = new Behaviour();
				tempsExec.setEnabled(true);
				if (ajouterTempsDexec.isEnabled()) {
					ajouterTempsDexec.setEnabled(false);

					// ajouterProcess.setEnabled(true);
					b.setTmpOrNum(Integer.parseInt(numeroPeriph.getValue()
							.toString()));
					b.setTypeComportement(typeBehaviour.ES);
					comportement.add(b);
					// b.afficherBehaviour();
					numeroPeriph.setValue(-1);
					numeroPeriph.setEnabled(false);

				}

			}
		});

		/***** action sur le bouton ajouter numero de périph *******/
		ajouterNperiph.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Behaviour b = new Behaviour();
				if (ajouterNperiph.isEnabled()) {

					tempsDexec = tempsDexec
							+ Integer.parseInt(tempsExec.getValue().toString());
					if (tempsDexec < Integer.parseInt(totalExec.getValue()
							.toString())) {
						ajouterNperiph.setEnabled(false);
						numeroPeriph.setEnabled(true);
						b.setTmpOrNum(Integer.parseInt(tempsExec.getValue()
								.toString()));
						b.setTypeComportement(typeBehaviour.exec);
						comportement.add(b);
						b.afficherBehaviour();
						tempsExec.setValue(0);
						tempsExec.setEnabled(false);
					} else {// tempsExec.setValue(0);
						if (tempsDexec == Integer.parseInt(totalExec.getValue()
								.toString())) {
							ajouterTempsDexec.setEnabled(false);
							ajouterNperiph.setEnabled(false);
							numeroPeriph.setEnabled(false);
							tempsExec.setEnabled(false);
							ajouterProcess.setEnabled(true);

						}

						else {
							tempsDexec = tempsDexec
									- Integer.parseInt(tempsExec.getValue()
											.toString());
							
						Erreur e = new Erreur();
						e.setVisible(true);
						}

					}
				}
			}
		});

		ChangeListener listener_Nperiph = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				if (numeroPeriph.isEnabled()) {
					if (!numeroPeriph.getValue().toString().equals("-1")) {
						ajouterTempsDexec.setEnabled(true);
						tempsExec.setEnabled(false);
						ajouterProcess.setEnabled(false);

					}

					else
						ajouterTempsDexec.setEnabled(false);
				}
			}
		};

		this.numeroPeriph.addChangeListener(listener_Nperiph);

		ChangeListener listener_tempsExec = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				if (tempsExec.isEnabled()) {

					if (!tempsExec.getValue().toString().equals("0")) {
						ajouterNperiph.setEnabled(true);
						ajouterProcess.setEnabled(true);

					}

					else {
						ajouterNperiph.setEnabled(false);
						ajouterProcess.setEnabled(false);
					}
				}

			}
		};

		this.tempsExec.addChangeListener(listener_tempsExec);

		this.btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				// Init t = new Init("source.txt");
				sl = new LancementSimul();
				sl.lancerSimulateur(quntam, nbPeriph,nbProcessus, dureeES, p);
				ganttdemo = new GanttDemo("Diagramme Gantt", sl.getSimul()
						.getDonnees(), nbProcessus);
				 
				 ganttdemo.pack();
				
				RefineryUtilities.centerFrameOnScreen(ganttdemo);
				TableauInfos tab = new TableauInfos(nbProcessus, p);
				PanelStatistics pan = new PanelStatistics(sl.getSimul()
						.getSystemTimeStamp(), sl.getSimul().getTauxOccupCPU(),sl.getSimul().getQuantum(),sl.getSimul().getNbPeriph(),sl.getSimul().getDurES());
				Resultat res = new Resultat(ganttdemo.getContentPane(), tab,
						pan);
				res.setVisible(true);
				JButton j = new JButton("Enregistrer resultat");
				ganttdemo.getContentPane().setLayout(null);
				ganttdemo.getContentPane().add(j);
				j.setBounds(780, 30, 250, 30);
			
				j.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {

						JFileChooser chooser = new JFileChooser();
						chooser.setDialogTitle("Enregistrer sous JPEG");
						chooser.showSaveDialog(ganttdemo.getContentPane());
						chooser.setApproveButtonText("Enregistrer");
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						File selection = chooser.getSelectedFile();
					if(selection!=null){
						ganttdemo.getJfreechart().createBufferedImage(500, 500);
				
						try {
							ChartUtilities.saveChartAsJPEG(selection,
									ganttdemo.jfreechart, 450, 450);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							System.out.println("catch");
						}
					}
				
					}

				});
				Programme.fr.setVisible(false);

			}
		});
	}

	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});

	}

}
