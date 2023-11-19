package ChatServer.src.com.chat.serveur;

import ChatServer.src.com.chat.commun.evenement.Evenement;
import ChatServer.src.com.chat.commun.evenement.GestionnaireEvenement;
import ChatServer.src.com.chat.commun.net.Connexion;

/**
 * Cette classe repr�sente un gestionnaire d'�v�nement d'un serveur. Lorsqu'un serveur re�oit un texte d'un client,
 * il cr�e un �v�nement � partir du texte re�u et alerte ce gestionnaire qui r�agit en g�rant l'�v�nement.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'�v�nements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire g�re des �v�nements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * M�thode de gestion d'�v�nements. Cette m�thode contiendra le code qui g�re les r�ponses obtenues d'un client.
     *
     * @param evenement L'�v�nement � g�rer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String msg, typeEvenement, aliasExpediteur, aliasInviter, aliasToSendMsg, chessPosition;
        ServeurChat serveur = (ServeurChat) this.serveur;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            switch (typeEvenement) 
            {
            case "EXIT": //Ferme la connexion avec le client qui a envoy� "EXIT":
                cnx.envoyer("END");
                serveur.enlever(cnx);
                cnx.close();
                break;
            case "LIST": //Envoie la liste des alias des personnes connect�es :
                cnx.envoyer("LIST " + serveur.list());
                break;      
            case "HIST":
            	cnx.envoyer("HIST\n"+ serveur.historique());
            	break;        
            case "MSG":
                msg = evenement.getArgument();
                aliasExpediteur = cnx.getAlias();
                serveur.ajouterHistorique(aliasExpediteur + ">>" + msg);
                serveur.envoyerATousSauf(msg, aliasExpediteur);
                break;
            case "JOIN": // Envoie une invitation pour un chat priver
            	aliasInviter = evenement.getArgument().toLowerCase();
            	aliasExpediteur = cnx.getAlias().toLowerCase();
            	serveur.sendInvitation(aliasExpediteur, aliasInviter, false);
            	break;
            case "DECLINE": // Refuse une invitation pour le chat priver
            	aliasInviter = evenement.getArgument().toLowerCase();
            	aliasExpediteur = cnx.getAlias().toLowerCase();
            	serveur.declineInvitation(aliasExpediteur, aliasInviter);
            	break;
            case "INV": // Envoie toutes les utilisateurs qui on envoye une invitation (moi)
            	aliasExpediteur = cnx.getAlias().toLowerCase();
            	cnx.envoyer("Les utilisateurs qui t'ont envoye une invitation :\n"+ serveur.historiqueInvitation(aliasExpediteur));
            	break;
            case "PRV":// Envoie un message priver a un utilisateur
                msg = evenement.getArgument().split(" ")[1].toString();
                aliasExpediteur = cnx.getAlias();
            	aliasToSendMsg = evenement.getArgument().split(" ")[0].toString().toLowerCase();
                serveur.envoyerMsgPrive(msg, aliasExpediteur, aliasToSendMsg);
                break;
            case "QUIT":// Permet de quitter un salon prive
            	aliasInviter = evenement.getArgument().toLowerCase();
                serveur.leaveSalonPriver(aliasInviter);
                break;
            case "CHESS":// Permet d'envoyer une demande d'une partie d'echec
            	aliasInviter = evenement.getArgument().toLowerCase();
            	aliasExpediteur = cnx.getAlias().toLowerCase();
            	serveur.sendInvitation(aliasExpediteur, aliasInviter, true);
                break;
            case "MOVE":// Permet d'envoyer une demande d'une partie d'echec
            	chessPosition = evenement.getArgument().toLowerCase();
            	aliasExpediteur = cnx.getAlias().toLowerCase();
            	serveur.movePawn(aliasExpediteur, chessPosition);
                break;
            case "ABANDON":
            	aliasExpediteur = cnx.getAlias().toLowerCase();
            	serveur.giveUp(aliasExpediteur);
            	break;
            default: //Renvoyer le texte recu convertit en majuscules :
                msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                cnx.envoyer(msg);
            }
        }
    }
}