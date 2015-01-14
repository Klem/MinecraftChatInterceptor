/**
 * Klem's Minecraft Chat Interceptor
 * 			Developed for
 * Maurin Entertainement Industries
 * v1.1.5
 */
package klem.minecraft.objects;

import java.util.List;
/**
 * Objet étant la représentation java de ce qui est intercepté en JSON.
 * Pour chaque ligne de JSON analysée, une instance de cette classe est créee.
 * Une ligne de JSon contenant des objects enfants (player & update), ces classes on du être définies également.
 * Si player ou update n'est pas présent dans la ligne analysée, leur liste respective sont simplement vide
 * 
 * 
 * ex
 * {
   "timestamp":1346882264095,
   "hasStorm":false,
   "updates":[

   ],
   "isThundering":false,
   "servertime":7524,
   "players":[
      {
         "name":"MBelloc",
         "armor":0,
         "account":"MBelloc",
         "health":0,
         "type":"player",
         "z":-2606.0,
         "y":66.0,
         "world":"world",
         "x":272.0
      }
   ],
   "currentcount":4,
   "confighash":1601986070
}
 * 
 * 
 * 
 * @author e402685
 *
 */
public class Minecraft{
   	private Number confighash;
   	private Number currentcount;
   	private boolean hasStorm;
   	private boolean isThundering;
   	private List<Player> players;
   	private Number servertime;
   	private Number timestamp;
   	private List<Update> updates;
   	
   	public Minecraft() {
   		
   	}

 	public Number getConfighash(){
		return this.confighash;
	}
	public void setConfighash(Number confighash){
		this.confighash = confighash;
	}
 	public Number getCurrentcount(){
		return this.currentcount;
	}
	public void setCurrentcount(Number currentcount){
		this.currentcount = currentcount;
	}
 	public boolean getHasStorm(){
		return this.hasStorm;
	}
	public void setHasStorm(boolean hasStorm){
		this.hasStorm = hasStorm;
	}
 	public boolean getIsThundering(){
		return this.isThundering;
	}
	public void setIsThundering(boolean isThundering){
		this.isThundering = isThundering;
	}
 	public List getPlayers(){
		return this.players;
	}
	public void setPlayers(List players){
		this.players = players;
	}
 	public Number getServertime(){
		return this.servertime;
	}
	public void setServertime(Number servertime){
		this.servertime = servertime;
	}
 	public Number getTimestamp(){
		return this.timestamp;
	}
	public void setTimestamp(Number timestamp){
		this.timestamp = timestamp;
	}
 	public List getUpdates(){
		return this.updates;
	}
	public void setUpdates(List updates){
		this.updates = updates;
	}
}
