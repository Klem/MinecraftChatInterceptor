package klem.minecraft.objects;

import java.util.Arrays;
import java.util.Properties;

import klem.minecraft.utils.PropertiesManager;

public class Blacklist {

	private String name;
	private Properties blacklist;
	private String[] items;

	private Blacklist() {
	}

	public Blacklist(String name) {

		this.name = name;
		this.blacklist = PropertiesManager.getBlacklist();
		// récupère la liste de valeur correspondant à la clé 'name'
		// et la renvoie sous forme de tableau de valeur
		this.items = blacklist.getProperty(name).split(",");
	}

	public boolean contains(String value) {
		return Arrays.asList(items).contains(value);
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public String[] getItems() {
		return items;
	}

	public String getName() {
		return name;
	}

	public void print() {
		System.out.println("Blacklisted : " + Arrays.toString(items));

	}

}
