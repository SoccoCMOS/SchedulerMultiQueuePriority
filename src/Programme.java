
import java.io.IOException;

import javax.swing.UIManager;

public class Programme {
	
	 static Accueil fr;

	public static void main(String args[]) throws IOException {

		try {
		     UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e){
		     System.out.println("Erreur de chargement de Nimbus !");
		}
		
		fr= new Accueil();
		fr.setVisible(true);
	}
	
}
