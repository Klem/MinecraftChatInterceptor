/**
 * Klem's Minecraft Chat Interceptor
 * 			Developed for
 * Maurin Entertainement Industries
 * v1.1.5
 */
package klem.minecraft.objects;

/**
 *  Représentation Java d'une chaine JSON correspondant à une mise a jour.
 *  La mise a jour est une "update"
 *  
 *  Il y a différent type d'updates : chat, tile, playerjoin, playerquit, daynight....
 *  Certain nous interressent (chat, playerjoin, playerquit...) et d'autres non (tile, daynight.....)
 *  On utilisera donc le type d'update lors de l'analyse.
 *  
 * Si'l ny a rien à mettre a jour, le fichier présent sur le serveur est tout de même généré mais "update" est vide.
 * C'est ce qui nous permet de fixer la condition d'execution. Si update est vide on ne fait rien, sinon, on analyse 
 *  
 *  ex update vide
 *  updates": []
 *  
 *  ex update plein
 *  "updates":[
      {
         "type":"playerquit",
         "playerName":"Kromsson",
         "account":"Kromsson",
         "timestamp":1346882912609
      }
   ],
 *  
 * @author e402685
 *
 */
public class Update {
	private String account;
	private String channel;
	private String message;
	private String playerName;
	private String source;
	private Long timestamp;
	private String type;

	public Update() {

	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Number getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Updates [account=");
		builder.append(account);
		builder.append(", channel=");
		builder.append(channel);
		builder.append(", message=");
		builder.append(message);
		builder.append(", playerName=");
		builder.append(playerName);
		builder.append(", source=");
		builder.append(source);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Update other = (Update) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
