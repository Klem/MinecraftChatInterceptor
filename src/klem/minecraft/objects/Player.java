/**
 * Klem's Minecraft Chat Interceptor
 * 			Developed for
 * Maurin Entertainement Industries
 * v1.1.5
 */
package klem.minecraft.objects;

import java.util.List;

/**
 * Représentation Java d'un chaine JSON représentant un joueur.
 * 
 * ex
 * players: [{
         "name":"Kromsson",
         "armor":0,
         "account":"Kromsson",
         "health":0,
         "type":"player",
         "z":-3578.0,
         "y":70.0,
         "world":"world",
         "x":-1073.0
      },
      {
         "name":"Totozzz",
         "armor":0,
         "account":"Totozzz",
         "health":0,
         "type":"player",
         "z":-4115.0,
         "y":10.0,
         "world":"world",
         "x":-2890.0
      }]
 * @author e402685
 *
 */
public class Player{
   	private String account;
   	private Number armor;
   	private Number health;
   	private String name;
   	private String type;
   	private String world;
   	private Number x;
   	private Number y;
   	private Number z;
   	
   	public Player() {
   		
   	}

 	public String getAccount(){
		return this.account;
	}
	public void setAccount(String account){
		this.account = account;
	}
 	public Number getArmor(){
		return this.armor;
	}
	public void setArmor(Number armor){
		this.armor = armor;
	}
 	public Number getHealth(){
		return this.health;
	}
	public void setHealth(Number health){
		this.health = health;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
 	public String getWorld(){
		return this.world;
	}
	public void setWorld(String world){
		this.world = world;
	}
 	public Number getX(){
		return this.x;
	}
	public void setX(Number x){
		this.x = x;
	}
 	public Number getY(){
		return this.y;
	}
	public void setY(Number y){
		this.y = y;
	}
 	public Number getZ(){
		return this.z;
	}
	public void setZ(Number z){
		this.z = z;
	}
}
