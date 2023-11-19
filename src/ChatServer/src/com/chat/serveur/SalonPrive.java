package ChatServer.src.com.chat.serveur;

import JeuEchecs.src.com.echecs.PartieEchecs;

public class SalonPrive 
{
	private String aliasHote;
	
	private String aliasInvite;

	private PartieEchecs partieEchecs;

	public SalonPrive(String aliasHote, String aliasInvite)
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
	
	public PartieEchecs getPartieEchecs() {
		return partieEchecs;
	}

	public void setPartieEchecs(PartieEchecs partieEchecs) {
		this.partieEchecs = partieEchecs;
	}
}
