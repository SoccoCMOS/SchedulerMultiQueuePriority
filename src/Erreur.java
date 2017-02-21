import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;

import java.awt.Color;

public class Erreur extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Erreur dialog = new Erreur();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Erreur() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.inactiveCaption);
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JLabel lblLeDureDonne = new JLabel(
					"le dur\u00E9e donn\u00E9e depasse le temps d'execution total ");
			contentPanel.add(lblLeDureDonne);
		}
		{
			JLabel lblLaDureDonne = new JLabel(
					"la dur\u00E9e donn\u00E9e depasse le temps d'execution total");
			lblLaDureDonne.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblLaDureDonne.setForeground(SystemColor.textText);
			lblLaDureDonne.setToolTipText("");
			lblLaDureDonne.setBounds(24, 94, 388, 49);
			contentPanel.add(lblLaDureDonne);
		}

		JLabel lblErreur = new JLabel("Erreur");
		lblErreur.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblErreur.setForeground(new Color(255, 0, 0));
		lblErreur.setBounds(42, 43, 89, 40);
		contentPanel.add(lblErreur);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
