package ChatServer.src.com.chat.serveur;

import ChatServer.src.com.chat.commun.net.Connexion;
import JeuEchecs.src.com.echecs.PartieEchecs;
import JeuEchecs.src.com.echecs.Position;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Cette classe �tend (h�rite) la classe abstraite Serveur et y ajoute le n�cessaire pour que le
 * serveur soit un serveur de chat.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-15
 */
public class ServeurChat extends Serveur {

    //Liste de l'historique du chat.
    private Vector<String> historique = new Vector<String>();

    //Liste des invitations pour rejondre un chat priver.
    private Vector<Invitation> invitationPrivateChat = new Vector<Invitation>();
    
    //Liste de tous les salon prives du serveur
    private Vector<SalonPrive> salonPrive = new Vector<SalonPrive>();
    
    //Liste de tous les partie d'echecs du serveur.
    private Vector<SalonPrive> gameChess = new Vector<SalonPrive>();
    
    /**
     * Cr�e un serveur de chat qui va �couter sur le port sp�cifi�.
     *
     * @param port int Port d'�coute du serveur
     */
    public ServeurChat(int port) {
        super(port);
    }

    @Override
    public synchronized boolean ajouter(Connexion connexion) {
        String hist = this.historique();
        if ("".equals(hist)) {
            connexion.envoyer("OK");
        }
        else {
            connexion.envoyer("HIST \n" + hist);
        }
        return super.ajouter(connexion);
    }
    /**
     * Valide l'arriv�e d'un nouveau client sur le serveur. Cette red�finition
     * de la m�thode h�rit�e de Serveur v�rifie si le nouveau client a envoy�
     * un alias compos� uniquement des caract�res a-z, A-Z, 0-9, - et _.
     *
     * @param connexion Connexion la connexion repr�sentant le client
     * @return boolean true, si le client a valid� correctement son arriv�e, false, sinon
     */
    @Override
    protected boolean validerConnexion(Connexion connexion) {

        String texte = connexion.getAvailableText().trim();
        char c;
        int taille;
        boolean res = true;
        if ("".equals(texte)) {
            return false;
        }
        taille = texte.length();
        for (int i=0;i<taille;i++) {
            c = texte.charAt(i);
            if ((c<'a' || c>'z') && (c<'A' || c>'Z') && (c<'0' || c>'9')
                    && c!='_' && c!='-') {
                res = false;
                break;
            }
        }
        if (!res)
            return false;
        for (Connexion cnx:connectes) {
            if (texte.equalsIgnoreCase(cnx.getAlias())) { //alias d�j� utilis�
                res = false;
                break;
            }
        }
        connexion.setAlias(texte);
        return true;
    }

    /**
     * Retourne la liste des alias des connect�s au serveur dans une cha�ne de caract�res.
     *
     * @return String cha�ne de caract�res contenant la liste des alias des membres connect�s sous la
     * forme alias1:alias2:alias3 ...
     */
    public String list() {
        String s = "";
        for (Connexion cnx:connectes)
            s+=cnx.getAlias()+":";
        return s;
    }
    
    /**
     * Retourne la liste des messages de l'historique de chat dans une cha�ne
     * de caract�res.
     *
     * @return String cha�ne de caract�res contenant la liste des alias des membres connect�s sous la
     * forme message1\nmessage2\nmessage3 ...
     */
    public String historique() 
    {
        String s = "";     
        for(String msg:historique)
        	s+=msg+"\n";    
        return s;
    }
    
    /**
     * Permet d'envoyer un message a tous les utilisateurs
     * @param msg le message a envoyer
     * @param aliasExpediteur l'utilisateur qui a ecris le message.
     */
    public void envoyerATousSauf(String msg, String aliasExpediteur) 
    {
    	for (Connexion cnx: connectes)
    	{
    		if(!cnx.getAlias().equals(aliasExpediteur))
    		{
    			cnx.envoyer(aliasExpediteur + ">>" + msg);
    		}
    	}
    }

    /**
     * Permet de sauvegarder les conversations du chat
     * @param msg le message a enregistrer
     */
	public void ajouterHistorique(String msg) 
	{
		historique.add(msg);	
	}
	
	/**
	 * Permet d'envoyer une invitation a un utilisateur.
	 * @param aliasHote l'utilisateur qui envois la demande d'invitation.
	 * @param aliasInvite l'utilisateur qui recois la demande d'invitation.
	 */
	public void sendInvitation(String aliasHote, String aliasInvite, boolean isChessInvitation)
	{
		//Permet de valider la creation d'une invitation
		Boolean canCreateInvitation = true;
		
		//Permet de valider la creation d'une invitation de jeu d'echec
		Boolean canCreateInvitationChess = false;
		
		//Permet de supprimer une invitation, si l'inviter accepter l'invitation de l'hote.
		Invitation invitationToDelete = null;
		
		//L'hote ne peut pas s'inviter lui-meme.
		if(aliasHote.equals(aliasInvite))
			canCreateInvitation = false;
		
		//Permet de verifier si aliasHote est dans un salon prive avec aliasInivite
		for(SalonPrive salon : salonPrive)
		{
			//Verifie si il existe un salon priver hoster par l'hote.
			if(salon.getAliasHote().equals(aliasHote) && salon.getAliasInvite().equals(aliasInvite))
			{
				canCreateInvitation = false;
				canCreateInvitationChess = true;
			}
			
			//Verifie si il existe un salon priver hoster par l'inviter
			if(salon.getAliasHote().equals(aliasInvite) && salon.getAliasInvite().equals(aliasHote))
			{
				canCreateInvitation = false;
				canCreateInvitationChess = true;
			}
		}
		
		//Permet de verifier si l'un des alias est dans une partie d'echec.
		if(isChessInvitation)
		{
			for(SalonPrive game : gameChess)
			{
				//Verifie si l'hote est dans une partie.
				if(game.getAliasHote().equals(aliasHote) || game.getAliasInvite().equals(aliasHote))
					canCreateInvitationChess = false;
				
				//Verifie si l'inviter est dans une partie.
				if(game.getAliasHote().equals(aliasInvite) || game.getAliasInvite().equals(aliasInvite))
					canCreateInvitationChess = false;
			}	
		}
		
		//Permet de faire une recherche dans la liste d'invitations.
        for (Invitation invitation : invitationPrivateChat) 
        {
        	//Verifie si aliasHote a envoye une invitation a aliasInvite
            if (invitation.getAliasHote().equals(aliasHote) && invitation.getAliasInvite().equals(aliasInvite)) 
            {
            	canCreateInvitation = false;
            	
    	    	for (Connexion cnx: connectes) {
    	    		if(cnx.getAlias().toLowerCase().equals(aliasHote))
    	    			cnx.envoyer("Tu as deja envoyer une demande a "+ aliasInvite);
    	    	}
            }
            
        	//Verifie si aliasInvite a envoye une invitation a aliasHote.
            if (invitation.getAliasHote().equals(aliasInvite) && invitation.getAliasInvite().equals(aliasHote)) 
            {    			
    			//Permet de creer une salon priver.
    			if(!isChessInvitation && canCreateInvitation)
    			{
        			invitationToDelete = invitation;
        			canCreateInvitation = false;
        			
        			SalonPrive salonp = new SalonPrive(aliasInvite, aliasHote);
        			salonPrive.add(salonp);
        			
        	    	for (Connexion cnx: connectes) {
        	    		if(cnx.getAlias().toLowerCase().equals(aliasInvite))
        	    			cnx.envoyer(aliasHote + " a accepter ton invitation !");
        	    	}
    			}
    			
    			//Permet de creer une partie d'echec.
    			if(isChessInvitation && canCreateInvitationChess)
    			{
        			invitationToDelete = invitation;
        			
        			canCreateInvitation = false;
    				canCreateInvitationChess = false;
    				
        			SalonPrive chessSession = new SalonPrive(aliasInvite, aliasHote);
        			gameChess.add(chessSession);
        			
        			//En Initilise la partie d'echec
        			PartieEchecs partieEchec = new PartieEchecs();
        			chessSession.setPartieEchecs(partieEchec);
        			
        			//Attribue le nom des joueurs.
        			partieEchec.setAliasJoueur1(aliasInvite);
        			partieEchec.setAliasJoueur2(aliasHote);
        			
        	    	for (Connexion cnx: connectes) {
        	    		if(cnx.getAlias().toLowerCase().equals(aliasInvite))
        	    			cnx.envoyer("CHESSOK "+ aliasInvite +" "+ partieEchec.getCouleurJoueur1());
        	    		
        	    		if(cnx.getAlias().toLowerCase().equals(aliasHote))
        	    			cnx.envoyer("CHESSOK "+ aliasHote +" "+ partieEchec.getCouleurJoueur2());
        	    	}
    			}
            }
        }
                
        //Si l'invitation a ete accepter, alors on supprime.
        if(invitationToDelete != null)
        	invitationPrivateChat.remove(invitationToDelete);
        
        /*Permet de creer une invitation pour un salon priver si ce n'est pas une demande pour jouer
          au echec. Dans le cas contraire on verifie si la demande d'invitation est de type jouer au echec
          et qu'il respecte les conditions pour creer l'invitation d'une partie d'echec.*/
        if(!isChessInvitation && canCreateInvitation || isChessInvitation && canCreateInvitationChess)
        {
        	Invitation invitationToCreate = new Invitation(aliasHote, aliasInvite);
        	
        	invitationPrivateChat.add(invitationToCreate);
        	
	    	for (Connexion cnx: connectes)
	    	{
	    		if(cnx.getAlias().toLowerCase().equals(aliasInvite))
	    		{
	    			if(!isChessInvitation)
	    				cnx.envoyer(aliasHote + " souhaite te parler en priver. JOIN (accepter) ou DECLINE (refuser)");
	    			else
	    				cnx.envoyer(aliasHote + " souhaite jouer au echec avec toi. CHESS (accepter) ou DECLINE (refuser)");
	    		}
    		}
        }
	}
	
	 /**
	  * Permet de refuser une demande d'invitation pour un chat priver.
	  * @param aliasWhoWasInvited l'utilisateur qui recoit l'invitation.
	  * @param aliasWhoInvited l'utilisateur qui envoie l'invitation.
	  */
	public void declineInvitation(String aliasWhoWasInvited, String aliasWhoInvited)
	{
		//l'invitation qui doit etre supprimer.
		Invitation invitationToDelete = null;
		
        for (Invitation invitation : invitationPrivateChat) 
        {
        	//Permet a l'utilisateur qui a recu l'invitation de refuser
            if (invitation.getAliasHote().equals(aliasWhoInvited) && invitation.getAliasInvite().equals(aliasWhoWasInvited)) 
            {
            	invitationToDelete = invitation;
    			
    	    	for (Connexion cnx: connectes) {
    	    		if(cnx.getAlias().toLowerCase().equals(aliasWhoInvited))	    			
    	    			cnx.envoyer(aliasWhoWasInvited + " a refuser ton invitation !");
    	    	}
            }
            
        	//Permet a l'utilisateur qui a envoyer une invitation de la supprimer
            if (invitation.getAliasHote().equals(aliasWhoWasInvited) && invitation.getAliasInvite().equals(aliasWhoInvited)) 
            	invitationToDelete = invitation;
        }
        
        //On supprime l'invitation
        if(invitationToDelete != null)
        	invitationPrivateChat.remove(invitationToDelete);        
	}
	
	
	/***
	 * Permet de voir qui nous a envoye une invitation
	 * @param alias 
	 * @return
	 */
    public String historiqueInvitation(String alias) 
    {
        String s = "";
        
        for (Invitation invitation : invitationPrivateChat) 
        {
        	if(invitation.getAliasInvite().equals(alias))
        		s += invitation.getAliasHote() + "\n";
        }
        
        return s;
    }
    
    /***
     * Permet d'envoyer des messages priver dand un salon priver.
     * @param msg
     * @param aliasExpediteur
     * @param aliasToSendMsg
     */
    public void envoyerMsgPrive(String msg, String aliasExpediteur, String aliasToSendMsg) 
    {
        for (SalonPrive salon : salonPrive) 
        {
        	//Permet a l'utilisateur qui a cree l'invitation d'envoyer un message a l'autre utilisateur
            if (salon.getAliasHote().equals(aliasExpediteur) && salon.getAliasInvite().equals(aliasToSendMsg)) 
            {
    	    	for (Connexion cnx: connectes) {
    	    		if(cnx.getAlias().toLowerCase().equals(aliasToSendMsg))	    			
    	    			cnx.envoyer(aliasExpediteur + ">>" + msg);
    	    	}
            }
            //Permet a recu l'invitation d'envoyer un message.
            if (salon.getAliasHote().equals(aliasToSendMsg) && salon.getAliasInvite().equals(aliasExpediteur)) 
            {
    	    	for (Connexion cnx: connectes) {
    	    		if(cnx.getAlias().toLowerCase().equals(aliasToSendMsg))	    			
    	    			cnx.envoyer(aliasExpediteur + ">>" + msg);
    	    	}
            }
        }
    }
    
   
    /***
     * Permet de quitter un Salon priver.
     * @param aliasExpediteur
     */
    public void leaveSalonPriver(String aliasExpediteur) 
    {
    	//Permet d'enregistrer le salon a supprimer.
		SalonPrive salonToDelete = null;
		
        for (SalonPrive salon : salonPrive) 
        {
            //Permet a l'utilisateur qui a recu l'invitation de quitter le salon.
            if (salon.getAliasHote().equals(aliasExpediteur)) 
            {
            	salonToDelete = salon;
            	
    	    	for (Connexion cnx: connectes) {
    	    		if(cnx.getAlias().toLowerCase().equals(salon.getAliasHote()))	    			
    	    			cnx.envoyer(salon.getAliasInvite() + " a quitter le salon.");
    	    	}
            }
            
            //Permet a l'utilisateur qui a cree l'invitation de quitter le salon priver.
            if (salon.getAliasInvite().equals(aliasExpediteur)) 
            {
            	salonToDelete = salon;
            	
    	    	for (Connexion cnx: connectes) {
    	    		if(cnx.getAlias().toLowerCase().equals(salon.getAliasInvite()))	    			
    	    			cnx.envoyer(salon.getAliasHote() + " a quitter le salon.");
    	    	}
            }
        }
        
        if(salonToDelete != null)
        	salonPrive.remove(salonToDelete);
    }

    /***
     * Permet de faire bouger un pion
     * @param position la position voulu.
     */
    public void movePawn(String alias, String position)
    {
    	//Permet de savoir la couleur du joueur et de son adversaire
    	char aliasPawnColor, opponentPawnColor;
    	
    	//Le salon ou se deruole la partie d'echec
    	SalonPrive salonEchec = null;
    	
    	//La partie d'echec que le joueur joue
    	PartieEchecs partieEchec = null;
    	
    	//La position initial et final d'un pion
    	Position posInit, posFinal = null;
    	
    	//Si le joueur est dans une partie, on recupere sa partie
		for(SalonPrive game : gameChess) {
			if(game.getAliasHote().equals(alias) || game.getAliasInvite().equals(alias)) {
				partieEchec = game.getPartieEchecs();
				salonEchec = game;
			}
		}
		
		//Permet de voir si finalement le joueur est dans une partie d'echec
		if(partieEchec != null)
		{
			//On recupere la couleur de notre joueur
			aliasPawnColor = (alias == partieEchec.getAliasJoueur1() ? partieEchec.getCouleurJoueur1() : partieEchec.getCouleurJoueur2());
			
			//On recupere la couleur de notre adversaire
			opponentPawnColor = (aliasPawnColor == 'b' ? 'n' : 'b');
			
			//Verifie si c'est notre tour
			if(partieEchec.getTour() == aliasPawnColor)
			{
				//Permet de d'extraire les coordonnes
				if(position.contains("-") || position.contains(" "))
				{ 
					//J'aurais pu faire String[] , ... mais la flemme a cette heure si je suis brain dead
					String  pinit = position.split("[\\s\\-]")[0];
					String pfinal = position.split("[\\s\\-]")[1];
		
					posInit = new Position(pinit.charAt(0), (byte)pinit.charAt(1));
					posFinal = new Position(pfinal.charAt(0), (byte)pfinal.charAt(1));
				}
				else
				{
					String  pinit = position.substring(0, 2);
					String pfinal = position.substring(2, 4);
					
					posInit = new Position(pinit.charAt(0), (byte)pinit.charAt(1));
					posFinal = new Position(pfinal.charAt(0), (byte)pfinal.charAt(1));
				}
				
				//Verifie si le deplacement a ete reussi
				if(partieEchec.deplace(posInit, posFinal))
				{
					//update de la partie dans le salon.
					salonEchec.setPartieEchecs(partieEchec);
					
			    	for (Connexion cnx: connectes)
			    	{	
			    		if(cnx.getAlias().equals(partieEchec.getAliasJoueur1()) || cnx.getAlias().equals(partieEchec.getAliasJoueur2()))
			    		{
			    			cnx.envoyer("MOVE " + position);
			    		}
			    		
			    		//Verifie si notre joueur est en echec.
						if(partieEchec.estEnEchec() == aliasPawnColor ) {
						}
			    	}
			    	
			    	//On fait changer de tour
			    	partieEchec.changerTour();
				}
				else
				{
					//Envoie a notre joueur que son deplacement est invalide
			    	for (Connexion cnx: connectes) {
			    		if(cnx.getAlias().equals(alias)) {
			    			cnx.envoyer("INVALID");
			    		}
			    	}
				}
			}
		}
    }
    
    /***
     * Permet d'abondonner une partie d'echec.
     */
	public void giveUp(String aliasWhoGiveUp) 
	{
		//Permet de recuperer le salon du jeu d'echec.
		SalonPrive salonChess = null;
		
		//Permet de recuperer l'alias du second joueur.
		String aliasOpponent;
		
		//On recherche le salon ou se trouver aliasWhoGiveUp
		for(SalonPrive game : gameChess) {
			if(game.getAliasHote().equals(aliasWhoGiveUp) || game.getAliasInvite().equals(aliasWhoGiveUp))
			{
				salonChess = game;
				aliasOpponent = (aliasWhoGiveUp == game.getAliasHote() ? game.getAliasInvite() : game.getAliasHote());
				
		    	for (Connexion cnx: connectes) {
		    		if(cnx.getAlias().equals(aliasOpponent)) {
		    			cnx.envoyer(aliasWhoGiveUp + " a abondonner la partie.");
		    		}
		    	}
			}
		}
		
		if(salonChess != null) {
			gameChess.remove(salonChess);
		}
	}
}
