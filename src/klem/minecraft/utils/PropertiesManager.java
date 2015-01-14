package klem.minecraft.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author e402685 Cette classe renvoie des singleton
 * 
 */
public class PropertiesManager {

	public static Properties blacklist = null;
	public static Properties config = null;

	/**
	 * Renvoi le fichier properties des blacklist. L'aspect "singleton" est
	 * défini par le fait que l'apelle à cette méthode renvoie toujours la meme
	 * instance. Le fichier de données ne changeant presque jamais, il est
	 * inutile d'aller le relire a chaque fois que l'on a besoin des blacklist.
	 * Par conséquent, si la liste nn'est pas encore chargée, on lit le fichier
	 * et on renevoie la liste. Si elle à déja été chargée une fois, on renvoice
	 * qui est déja présent en mémoir
	 * 
	 * @return
	 */
	public static Properties getBlacklist() {
		// la liste n'est pas chargée
		if (blacklist == null) {
			// on le le fichier
			blacklist = new Properties();
			File propFile = null;
			try {
				URL in = ClassLoader.getSystemResource(Constants.FILENAME_BLACKLIST);
				System.out.println("Loading blacklists from " + in.toExternalForm());
				blacklist.load(in.openStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// sinon on renvoie l'existant
		return blacklist;
	}

	/**
	 * Renvoi le fichier de configuration. L'aspect "singleton" est défini par
	 * le fait que l'apelle à cette méthode renvoie toujours la meme instance.
	 * Le fichier de données ne changeant presque jamais, il est inutile d'aller
	 * le relire a chaque fois que l'on a besoin des blacklist. Par conséquent,
	 * si la liste nn'est pas encore chargée, on lit le fichier et on renevoie
	 * la liste. Si elle à déja été chargée une fois, on renvoice qui est déja
	 * présent en mémoir
	 * 
	 * @return
	 */
	public static Properties getConfig() {

		// la liste n'est pas chargée
		if (config == null) {
			// on le le fichier
			config = new Properties();
			File propFile = null;
			try {

				URL in = ClassLoader.getSystemResource(Constants.FILENAME_CONFIG);
				System.out.println("Loading configuration from " + in.toExternalForm());
				config.load(in.openStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// sinon on renvoie l'existant
		return config;
	}

	// constructeur privé,
	// pour interdire l'appel de 'new'
	private PropertiesManager() {
	}

}
