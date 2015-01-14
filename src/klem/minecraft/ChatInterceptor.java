/**
 * Klem's Minecraft Chat Interceptor
 * 			Developed for
 * Maurin Entertainement Industries
 * v1.1.5
 */
package klem.minecraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;

import klem.minecraft.objects.Blacklist;
import klem.minecraft.objects.Minecraft;
import klem.minecraft.objects.Update;
import klem.minecraft.utils.Config;
import klem.minecraft.utils.Constants;
import klem.minecraft.utils.Tools;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Logique d'interception. Etant un Thread, elle �tant la classe Thread et
 * d�fini la m�thode run de celuici
 * 
 * @author e402685
 * 
 */
public class ChatInterceptor extends Thread {
	private static final Blacklist BLACKLISTED_USERS = ChatInterceptorApp.blacklistedUsers;
	private static final Blacklist BLACKLISTED_WORDS = ChatInterceptorApp.blacklistedWords;
	private static final Config CONFIG = ChatInterceptorApp.config;

	private final JTextArea textContainer;
	private final Gson gson = new Gson();
	private Minecraft minecraft = null;
	private List<Update> updates = null;
	private final List<Update> displayedUpdates = new ArrayList<Update>();
	private final List<Update> ignoredUpdates = new ArrayList<Update>();
	public StringBuilder builder = new StringBuilder();
	private URL url;
	private String content;
	private Date firstMessageDate;
	private Date lastMessageDate;

	// public afin qu'il soient accessible depuis l'extterieur
	// notemment pour recharger la liste apres edition
	// sans devoir interrompre l'interception en cours;

	public ChatInterceptor(JTextArea textContainer) {
		this.textContainer = textContainer;
		System.out.println("Loading engine....");
	}

	@Override
	public void run() {
		System.out.println("Starting chat interception from " + CONFIG.getUrl());



		// nb de messages affich�s
		int displayedMessages = 0;
		try {

			// Boucle d'interception.
			// A chaque passage, on r�cup�re la dernire version du fichier,
			// on extrait le contenu, on l'analyse afin de filtrer ce que l'on
			// veut afficher
			// On � d�fini une condition de sortie "maxMessage" ou
			// l'interception s'arrete

			// TODO define exit condition if needed, wait for customer feedback
			// ;)

			while (displayedMessages < CONFIG.getMaxDisplayedMessages()) {
				// while ((content = br.readLine()) != null) {
				try {
					// R�cup�ration de la derni�re version du fichier.
					// Il semble que le serveur de la dynmap r�g�n�re ce fichier
					// toutes les 3 secondes environ
					url = new URL(CONFIG.getUrl());
					// r�cup�ration du contenu du fichier
					// en tant que ch�ine analysable
					content = Tools.getContentAsString(url);

					if (CONFIG.isDebugEnabled()) {
						System.out.println(content);
					}

				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
				// C'est la que �a devient tricky (va lire la doc de Minecraft,
				// Update et Player)
				// Cela transform le contenu Json en grappe d'objets java,
				// manipulable ais�ment
				// gson.fromJson(texte, type de destination)
				minecraft = gson.fromJson(content, Minecraft.class);

				// r�cup�ration des updates
				updates = minecraft.getUpdates();

				// Un traitement par update trouv�e
				for (Update update : updates) {

					// Si l'update est d'un type contenu dans la liste de ceux �
					// traiter ,
					// A savoir "playerjoin", "playerquit" et "chat"
					if (Arrays.asList(Constants.PROCESS_UPDATES).contains(update.getType())) {

						// je me suis rendu compte qu'un message de chat �tait
						// pr�sent � l�cran 25s
						// Le fichier est reg�n�r� toutes les 3 secondes.
						// En cons�quence, le message est pr�sent dans chaque
						// fichier g�n�r� durant ces 25s,
						// ce qui resulte en un meme message affich� 6 a 10
						// fois.
						// Je stocke donc l'update dans une liste une fois
						// qu'elle � �t� trait�e,
						// Ce qui me permet de savoir si ce que je re�oi est un
						// doublon ou non

						// Si l'update recue n'est pas dans la liste de celle
						// d�ja trait�e
						if (!displayedUpdates.contains(update)) {

							// v�rification si le player emmeteur du message
							// n'est pas blacklist�
							// et que il n'as pas d�ja �t� trait�
							// en tant que tel
							if (BLACKLISTED_USERS.contains(update.getPlayerName()) && !ignoredUpdates.contains(update)) {
								System.out.println("Message " + update.getMessage() + " ignor�, player "
										+ update.getPlayerName() + " est blacklist� : ");
								ignoredUpdates.add(update);
							}

							// v�rification si le message recu n'est pas un
							// texte blacklist�
							// et que il n'as pas d�ja �t� trait�.
							// Si il a d�ja �t� blacklist� par son player, il
							// est ignor�
							// G�rer un blacklist pour plusieurs raisons est
							// inutile
							if (BLACKLISTED_WORDS.contains(update.getMessage()) && !ignoredUpdates.contains(update)) {
								System.out.println("Message " + update.getMessage() + " ignor�, mot blacklist� : ");
								// ajout � la liste des ignor�s
								ignoredUpdates.add(update);
							}

							// le player n'est pas blacklist�, on traite
							else {

								// creation de la ligne
								String line = createLine(update);
								// affichage de la ligne
								textContainer.append(line);
								// curseur � la fin du contenu pour assurer le
								// scrolling auto
								textContainer.setCaretPosition(textContainer.getDocument().getLength());

								// rafraichissement de la zone de texte
								textContainer.repaint();

								// Premier message intercept� pour cette s�rie
								// (entre 2 autosave), utilisation
								// de sa date pour la g�n�ration du
								// nom de fichier
								if (displayedUpdates.size() == 0) {
									firstMessageDate = new Date((Long) update.getTimestamp());
								} else {
									// / Dernier message intercept�, utilisation
									// de sa date pour la g�n�ration du
									// nom de fichier
									lastMessageDate = new Date((Long) update.getTimestamp());
								}
								// incr�mentation du nombre de messages
								displayedMessages++;
								// ajout � la liste des d�ja trait�
								displayedUpdates.add(update);
								System.out.println("DisplayedMessages : " + displayedMessages + ", Ignored messages : "
										+ ignoredUpdates.size());
							}

						} else {
							// System.out.println("Already displayed update : "
							// +
							// update.toString());
						}

					} else {
						// System.out.println("Ignored update:"+update.toString());
					}

				}
				// traitement des update termin�
				// vidage de la liste
				updates.clear();
				// on vide la liste des update trait�es
				// afin d'�viter de saturer la memoire
				// quand le nb d�l�ments dans la liste
				// atteint un certain seuil
				if (displayedMessages != 0 && 0 == displayedMessages % CONFIG.getPurgHistoryThreshold()) {
					// si le AUTOSAVE est activ�, on ecrit dans un fichier avant
					// de purger
					if (CONFIG.isAutoSavePurge()) {
						saveAndPurge();
					} else {
						// on n'ecrit pas dans le fichier
						displayedUpdates.clear();
					}

					// on en profite �galement pour vider la liste des update
					// ignor�es
					ignoredUpdates.clear();
					System.out.println("Ignored messages history purged");
				}
				try {
					// on interrompt le thread pendant un laps de temps.
					// Le serveur �tant fix� sur environ 3 seconde, 5s
					// d'interruption semble acceptable
					// <1s : trop de requetes dans le vide
					// >10s risque de rater certains messages
					Thread.sleep(CONFIG.getRefreshTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} // et c'est reparti pour un tour....
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode qui cr�e la ligne a afficher a partir des infos contenues dans
	 * l'update
	 * 
	 * @param update
	 * @return
	 */
	private String createLine(Update update) {

		String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(
				new Date((Long) update.getTimestamp()));

		if (Constants.UPDATE_PLAYERJOIN.equals(update.getType())) {
			return "<" + date + ">  " + update.getPlayerName() + " vient de se connecter\n";
		}

		if (Constants.UPDATE_PLAYERQUIT.equals(update.getType())) {
			return "<" + date + ">  " + update.getPlayerName() + " vient de se d�connecter\n";
		}

		return "<" + date + ">  " + update.getPlayerName() + " : " + update.getMessage() + "\n";
	}

	/**
	 * Ecrit les messages sauvgard� dans un fichier et purge le cache
	 */
	public void saveAndPurge() {
		File file = null;

		SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy.HHmmss");

		String firstDate = sdf.format(firstMessageDate);
		String lastDate = sdf.format(lastMessageDate);

		try {
			// OUvre le fichier en le cr�ant le cas �ch�ant
			String path = ChatInterceptorApp.class.getProtectionDomain().getCodeSource().getLocation().getPath()
					+ "chat/";
			System.out.println(path);
			file = new File(path + firstDate + "_" + lastDate + ".txt");

			if (!file.exists()) {
				file.createNewFile();
			}
			// Ecrit le contenu de la zone de texte dans le fichier
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			for (Update update : displayedUpdates) {
				out.write(createLine(update));
			}
			out.close();
			// Purge de la liste des update
			displayedUpdates.clear();
			System.out.println("Messages history saved to " + file.getAbsolutePath());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public List<Update> getDisplayedUpdates() {
		return displayedUpdates;
	}

	public List<Update> getIgnoredUpdates() {
		return ignoredUpdates;
	}

	public Date getFirstMessageDate() {
		return firstMessageDate;
	}

	public Date getLastMessageDate() {
		return lastMessageDate;
	}

}
