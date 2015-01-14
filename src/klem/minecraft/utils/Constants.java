/**
 * Klem's Minecraft Chat Interceptor
 * 			Developed for
 * Maurin Entertainement Industries
 * v1.1.5
 */
package klem.minecraft.utils;

/**
 * Classe conteant les constantes Permet de centraliser les valeurs dans le cas
 * ou elles changent Ce sont tout les paramètres qui influent sur le comportment
 * du pregramme Il peuvent éventuellement être définis via un fichier de config
 * 
 * @author e402685
 * 
 */
public class Constants {
	// Les type d'update a afficher
	public static final String[] PROCESS_UPDATES = { "chat", "playerjoin", "playerquit" };
	// les typez d'updates a ignorer
	public static final String[] IGNORE_UPDATES = { "tile" };
	// update pour connection joueur
	public static final String UPDATE_PLAYERJOIN = "playerjoin";
	// iupdate pour déconnection joueur
	public static final String UPDATE_PLAYERQUIT = "playerquit";

	/**
	 * Ces variables sont maintenant définies dans le config.properties
	 * 
	 * mode debug. Si activé, une zone de texte apparait sous celle des messages
	 * et intercepte nombre d'update a mettre en memeoire avant de purger la
	 * sortie console public static final boolean DEBUG_ENABLED = false; URL du
	 * fichier public static final String URL =
	 * "http://hfrcraft.deepdark.fr/dynmap/standalone/dynmap_world.json"; nombre
	 * de messages avant arret public static final int MAX_DISPLAYED_MESSAGES =
	 * 2500; ms de latence entre 2 rafraichissement public static final long
	 * REFRESH_TIME = 50; sauvegarde automatique avant purge public static final
	 * boolean AUTOSAVE_ON_PURGE = true; public static final int
	 * PURGE_HISTORY_TRESHOLD = 500; emplacement dur repertoir de sauvegarde
	 */
	public static final String AUTOSAVE_DIR = "C:\\minecraft\\";
	// fichier des releas notes
	public static final String FILENAME_RELEASE_NOTES = "release-notes.txt";
	// fichier license
	public static final String FILENAME_LICENSE = "license.txt";
	// fichier des blacklists
	public static final String FILENAME_BLACKLIST = "properties\\blacklists.properties";
	// fichier de config
	public static final String FILENAME_CONFIG = "properties\\config.properties";
	// Clé pour récupérer la liste des users blacklisté depuis le fichier
	public static final String BLACKLIST_USERS_KEY = "users";
	// Clé pour récupérer la liste des users blacklisté depuis le fichier
	public static final String BLACKLIST_WORDS_KEY = "words";
	// Caractère séparateur entre 2 valeurs de la blacklist;
	public static final String BLACKLIST_VALUES_SEPARATOR = ",";


}
