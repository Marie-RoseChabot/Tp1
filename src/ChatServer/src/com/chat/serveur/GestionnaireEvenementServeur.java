package ChatServer.src.com.chat.serveur;

import ChatServer.src.com.chat.commun.evenement.Evenement;
import ChatServer.src.com.chat.commun.evenement.GestionnaireEvenement;
import ChatServer.src.com.chat.commun.net.Connexion;

/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String msg, typeEvenement, aliasExpediteur,aliasReceveur;
        ServeurChat serveur = (ServeurChat) this.serveur;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType().toUpperCase();

            switch (typeEvenement) {

                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveur.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des alias des personnes connectées :
                    cnx.envoyer("LIST " + serveur.list());
                    break;

                case "MSG":
                    msg= (evenement.getArgument());
                    serveur.envoyerATousSauf(msg,((Connexion) source).getAlias(),serveur.connectes);
                    break;

                case "JOIN":
                    aliasReceveur=(evenement.getArgument());
                    aliasExpediteur=((Connexion) source).getAlias();
                    serveur.gererInvitation(aliasReceveur,aliasExpediteur,serveur.connectes);
                    break;

                case "DECLINE":
                    aliasReceveur=(evenement.getArgument());
                    aliasExpediteur=((Connexion) source).getAlias();
                    serveur.gererRefus(aliasExpediteur,aliasReceveur,serveur.connectes);
                    break;

                case "INV":

                    aliasExpediteur=((Connexion) source).getAlias();

                  cnx.envoyer("INV"+serveur.envoyerHistorique(aliasExpediteur));
                    break;

                case "QUIT":
                    aliasReceveur=(evenement.getArgument());
                    aliasExpediteur=((Connexion) source).getAlias();
                    serveur.quitterChatPrive(aliasExpediteur,aliasReceveur,serveur.connectes);
                    break;

                case "PRV":
                    msg= (evenement.getArgument());
                    serveur.envoyerMessagePrive(((Connexion) source).getAlias(),msg,serveur.connectes);


                    break;
                default: //Renvoyer le texte recu convertit en majuscules
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}