package ChatServer.src.com.chat.serveur;

public class SalonPrive {
    String aliasHote,aliasInvite;

    public SalonPrive(String aliasHote,String aliasInvite){
        this.aliasHote=aliasHote;
        this.aliasInvite=aliasInvite;
    }

    public String getAliasHote(){
        return aliasHote;
    }

    public String getAliasInvite(){
        return aliasInvite;
    }
}
