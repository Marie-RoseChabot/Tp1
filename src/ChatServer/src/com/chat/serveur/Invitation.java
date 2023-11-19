package ChatServer.src.com.chat.serveur;

public class Invitation 
{
	private String aliasHote;
	
	private String aliasInvite;

	public Invitation(String aliasHote, String aliasInvite)
	{
		this.aliasHote = aliasHote;
		this.aliasInvite = aliasInvite;
	}
	
	public String getAliasHote() {
		return aliasHote;
	}

	public String getAliasInvite() {
		return aliasInvite;
	}
}
