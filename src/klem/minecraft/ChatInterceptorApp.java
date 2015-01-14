/**
 * Klem's Minecraft Chat Interceptor
 * 			Developed for
 * Maurin Entertainement Industries
 * v1.1.5
 */
package klem.minecraft;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import klem.minecraft.debug.MessageConsole;
import klem.minecraft.objects.Blacklist;
import klem.minecraft.utils.Config;
import klem.minecraft.utils.Constants;
import klem.minecraft.utils.Tools;

/**
 * Classe principale du pregramme. C'est elle qui gere l'appli
 * 
 * @author e402685
 * 
 */
public class ChatInterceptorApp extends javax.swing.JFrame {

	/**
	 * Variable de classes, accessible par toutes les méthodes (fonctions) Le
	 * private indique que seule les methodes ce cette classe peuvent y avoir
	 * accès
	 */
	private JMenuBar menuBar;
	private JMenu jMenu;
	private JMenu saveFrequencyMenu;
	private JMenuItem preferencesMenu;
	private JMenuItem exitMenuItem;
	private JMenuItem interceptMenuItem;
	private JMenuItem saveMenuItem;

	private JPanel panel;
	private static JTextArea textContainer;
	private static JTextArea console;
	ChatInterceptor interceptor = null;
	private boolean firstRun = true;
	private final InterceptorStart iStart = new InterceptorStart();
	private final InterceptorStop iStop = new InterceptorStop();
	private JMenu aboutMenu;
	private JMenuItem releaseNotesMenuItem;
	private JMenuItem licenseNotesMenuItem;
	public static Blacklist blacklistedUsers;
	public static Blacklist blacklistedWords;
	public static Config config;

	public static URL url;

	/**
	 * Point d'entrée du programme
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Appel au constructeur de lappli
				// on appelle le constructeur avec le mot clé new
				ChatInterceptorApp app = new ChatInterceptorApp();
				app.setLocationRelativeTo(null);
				app.setTitle("Klem's Minecraft Chat Interceptor v1.1.5");
				app.setVisible(true);
			}
		});
	}

	/**
	 * Constructeur
	 */
	public ChatInterceptorApp() {
		super();
		System.out.println("Launching " + this.getClass().getName() + "...");
		System.out.println("Loading configuration from " + ClassLoader.getSystemResource(Constants.FILENAME_CONFIG).getPath());
		initProperties();
		initGUI();

		interceptor = new ChatInterceptor(textContainer);

		System.out.println("Application initialized...");
	}

	/**
	 * Crré les différent élements de l'interface (taille de la zone, couleur de
	 * fond....)
	 */
	private void initGUI() {
		try {
			setSize(800, 600);
			setResizable(false);
			// creation des menus
			panel = new JPanel();
			panel.setLayout(new FlowLayout());
			panel.setBackground(Color.BLACK);

			createMenus();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// Panel, élément racine de l'interface graphique

			// zone de texte ou la sortie est écrite
			// hauteur en fonction de la présence ou non de la console
			int cols = 20;
			if (!config.isDebugEnabled()) {
				cols = 27;
			}
			textContainer = new JTextArea(cols, 95);
			textContainer.setForeground(Color.GREEN);
			textContainer.setBackground(Color.BLACK);
			textContainer.setLineWrap(true);
			textContainer.setWrapStyleWord(true);
			textContainer.setEditable(false);
			textContainer.setFont(new Font("Monospaced", Font.PLAIN, 14));
			textContainer.setSize(760, 560);

			// Ajout de la textarea au scrolling
			JScrollPane textScroll = new JScrollPane(textContainer);
			textScroll.setSize(780, 580);
			textScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			// ajout du scrolling a la racine
			panel.add(textScroll);

			// Textare de sortie console
			// mode debug uniquement
			if (config.isDebugEnabled()) {
				console = new JTextArea(5, 95);
				console.setForeground(Color.WHITE);
				console.setBackground(Color.BLACK);
				console.setLineWrap(true);
				console.setWrapStyleWord(true);
				console.setEditable(false);
				console.setFont(new Font("Monospaced", Font.PLAIN, 14));
				JScrollPane consoleScroll = new JScrollPane(console);
				MessageConsole mc = new MessageConsole(console);
				mc.redirectOut();
				mc.redirectErr(Color.RED, null);
				mc.setMessageLines(100);
				panel.add(consoleScroll);
			}

			SimpleAttributeSet attribs = new SimpleAttributeSet();
			StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_LEFT);

			setContentPane(panel);

			System.out.println("Initializing GUI components...");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initProperties() {
		config = new Config();
		config.printProperties();
		blacklistedUsers = new Blacklist(Constants.BLACKLIST_USERS_KEY);
		blacklistedUsers.print();
		blacklistedWords = new Blacklist(Constants.BLACKLIST_WORDS_KEY);
		blacklistedWords.print();

	}

	/**
	 * LE menu d'action
	 */
	private void createMenus() {
		// Barre de menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		{

			{
				// Element de menu
				interceptMenuItem = new JMenuItem("Intercepter");
				// Un "actionListener" est un composant qui assigne une classe à
				// un bouton.
				// Ici, on a créer un Listener qui s'appele "interceptor"
				interceptMenuItem.addActionListener(iStart);
				menuBar.add(interceptMenuItem);

			}
			{
				preferencesMenu = new JMenu("Préferences");
				JMenu blackListMenu = new JMenu("Blacklist");
				JMenuItem blacklistUser = new JMenuItem("Utilisateur");
				blacklistUser.addActionListener(new BlacklistEditor(Constants.BLACKLIST_USERS_KEY));
				JMenuItem blacklistWord = new JMenuItem("Mots");
				blacklistWord.addActionListener(new BlacklistEditor(Constants.BLACKLIST_WORDS_KEY));
				blackListMenu.add(blacklistUser);
				blackListMenu.add(blacklistWord);
				preferencesMenu.add(blackListMenu);
			}
			{
				// saveFrequencyMenu = new JMenu("AutoSave");
				// JCheckBoxMenuItem realtimeMenuItem = new JCheckBoxMenuItem(
				// "Temps réel");
				// JCheckBoxMenuItem messagesMenuItem50 = new JCheckBoxMenuItem(
				// "50");
				// JCheckBoxMenuItem messagesMenuItem100 = new
				// JCheckBoxMenuItem(
				// "100");
				// JCheckBoxMenuItem messagesMenuItem250 = new
				// JCheckBoxMenuItem(
				// "250");
				// JCheckBoxMenuItem messagesMenuItem500 = new
				// JCheckBoxMenuItem(
				// "500");
				// JCheckBoxMenuItem messagesMenuItem1000 = new
				// JCheckBoxMenuItem(
				// "1000");
				// JCheckBoxMenuItem messagesMenuItem2500 = new
				// JCheckBoxMenuItem(
				// "2500");
				// saveFrequencyMenu.add(realtimeMenuItem);
				// saveFrequencyMenu.add(messagesMenuItem50);
				// saveFrequencyMenu.add(messagesMenuItem100);
				// saveFrequencyMenu.add(messagesMenuItem250);
				// saveFrequencyMenu.add(messagesMenuItem500);
				// saveFrequencyMenu.add(messagesMenuItem1000);
				// saveFrequencyMenu.add(messagesMenuItem2500);
				//
				// preferencesMenu.add(saveFrequencyMenu);
				//
				// menuBar.add(preferencesMenu);

			}
			{
				saveMenuItem = new JMenuItem();
				saveMenuItem.addActionListener(new Save());
				saveMenuItem.setText("Enregister");
				menuBar.add(saveMenuItem);
			}

			{
				aboutMenu = new JMenu("A propos");
				licenseNotesMenuItem = new JMenuItem("License");
				licenseNotesMenuItem.addActionListener(new TextfilePopup("license.txt"));
				releaseNotesMenuItem = new JMenuItem("Release notes");
				releaseNotesMenuItem.addActionListener(new TextfilePopup("release-notes.txt"));
				aboutMenu.add(licenseNotesMenuItem);
				aboutMenu.add(releaseNotesMenuItem);
				menuBar.add(aboutMenu);
			}

			{
				exitMenuItem = new JMenuItem("Quitter");
				exitMenuItem.addActionListener(new ExitApp());
				menuBar.add(exitMenuItem);
			}
		}
	}

	/**
	 * Action invoqués lors du clic sur "intercepter"
	 * 
	 * @author e402685
	 * 
	 */
	class InterceptorStart implements ActionListener {
		/**
		 * Le actionEvent est le type d'évenement qui à été détécté, ii c'est un
		 * clic
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Classe contenant la logique d'interception

			// La classe d'interception a été créee en tant que Thread
			// (traitement parallèle)
			// Si il n'y avait pas de traitement en parallèle, le processus
			// d'interception bloquerait toute lappli.
			// Du coup on ne pourrai plus utiliser les menu ni faire quoi que ca
			// soit d'autre car le flux du programme
			// ne consisterai qu'en l'interception et non la gestion de
			// linterface et des évênements.
			// Quand on implémente une logique sous forme de thread, toute la
			// logique doit être codée dans la methode "run"
			// qui est appelé lorsque que l'on apelle "start" (pas top logique
			// mais bon ;))

			// distinction entre le lancement et la reprise apres pause
			if (firstRun) {
				System.out.println("Interception start.....");
				interceptor.start();
				firstRun = false;

			} else {
				interceptor.resume();
				System.out.println("Interception resumed");

			}

			// On demande a swing de mgérer la mise a jour de la GUI
			// dans son thread dédié
			interceptMenuItem.removeActionListener(iStart);
			interceptMenuItem.addActionListener(iStop);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					interceptMenuItem.setText("Pause");

				}
			});
		}
	}

	/**
	 * Action invoqués lors du clic sur "intercepter"
	 * 
	 * @author e402685
	 * 
	 */
	class InterceptorStop implements ActionListener {
		/**
		 * Le actionEvent est le type d'évenement qui à été détécté, ii c'est un
		 * clic
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			interceptor.suspend();
			System.out.println("Interception paused");
			interceptMenuItem.removeActionListener(iStop);
			interceptMenuItem.addActionListener(iStart);
			// On demande a swing de mgérer la mise a jour de la GUI
			// dans son thread dédié
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					interceptMenuItem.setText("Intercepter");

				}
			});
		}
	}

	/**
	 * Déclenche les éditeurs de blacklist
	 * 
	 * @author e402685
	 * 
	 */
	class BlacklistEditor implements ActionListener {

		private final String blacklisName;

		public BlacklistEditor(String blacklistName) {
			this.blacklisName = blacklistName;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Save to file
	 * 
	 * @author Big Enregistrement vers un fichier Action invoquée lors du clic
	 *         sur "enregistrer"
	 */
	class Save implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fileChooser = new JFileChooser();
			// Affiche la boite de séléction
			// et vérifie que c'est bien le bouton "ok" qui à été cliqué
			if (fileChooser.showSaveDialog(ChatInterceptorApp.this) == JFileChooser.APPROVE_OPTION) {
				// Thread pour écrire dans le fichier
				// Sers à ce que l'interface graphique ne soit pas figée
				// le temps que le fichier soit accédé + écrit durant des
				// opération longues
				// PAr ex enregistrer 2500 messages sur un lecteur résau
				Thread writeToFile = new Thread() {
					public void run() {
						try {
							// OUvre le fichier
							File file = fileChooser.getSelectedFile();
							FileWriter fstream = new FileWriter(file);
							BufferedWriter out = new BufferedWriter(fstream);
							// Ecrit ce qui a été intercepté
							// Pas de controle de validité. Si rien n'as été
							// intercepté
							// créera un fichier vide
							// TODO manage empty files
							out.write(textContainer.getText(0, textContainer.getDocument().getLength()));
							out.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (BadLocationException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				};
				// Lancement du Thread
				writeToFile.run();
			}
		}
	}

	/**
	 * Action invoqués lors du clic sur "a propos" Prend en paramètre le nom du
	 * fichier a afficher
	 * 
	 * @author e402685
	 * 
	 */
	class TextfilePopup implements ActionListener {

		private String fileToDisplay;

		public TextfilePopup(String fileToDisplay) {
			this.fileToDisplay = fileToDisplay;
		}

		private TextfilePopup() {
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			JOptionPane.showMessageDialog(null, Tools.getFileContentAsString(fileToDisplay), fileToDisplay,
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Action invoqués lors du clic sur "quitter"
	 * 
	 * @author e402685
	 * 
	 */
	class ExitApp implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// fait une sauvegarde auto avant de quitter
			// au cas ou
			if (!interceptor.getDisplayedUpdates().isEmpty()) {
				interceptor.saveAndPurge();
			} else {
				System.out.println("No data to save.");
			}
			System.out.println("Exiting.....");

			// c'est cracra de faire ca (ca équivaut a eteindre ton PC direct à
			// l'alim)
			// Il faudrai libérer les resources, fermer les connection etc etc.
			// ici on se l'autorise ca c'est une appli monoutilisateur mono
			// poste doc pas de risque
			// que des resources soient vérouillées par d'autres personnes
			// (fichier de sauvgarde)
			// ou que le nombre de connection monte en fleche,
			// saturant le serveur
			System.exit(0);
		}
	}

}