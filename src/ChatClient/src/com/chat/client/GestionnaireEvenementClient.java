package ChatClient.src.com.chat.client;

import ChatClient.src.com.chat.commun.evenement.Evenement;
import ChatClient.src.com.chat.commun.evenement.GestionnaireEvenement;
import ChatClient.src.com.chat.commun.net.Connexion;
import ChatServer.src.com.chat.serveur.ServeurChat;

/**
 * Cette classe représente un gestionnaire d'événement d'un client. Lorsqu'un client reçoit un texte d'un serveur,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementClient implements GestionnaireEvenement {
    private Client client;

    /**
     * Construit un gestionnaire d'événements pour un client.
     *
     * @param client Client Le client pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementClient(Client client) {
        this.client = client;
    }
    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un serveur.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String typeEvenement, arg;
        String[] membres, historique;
        ClientChat client = (ClientChat) this.client;
        
        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "END" : //Le serveur demande de fermer la connexion
                    client.deconnecter(); //On ferme la connexion
                    break;
                case "LIST" : //Le serveur a renvoyé la liste des connectés
                    arg = evenement.getArgument();
                    membres = arg.split(":");
                    System.out.println("\t\t"+membres.length+" personnes dans le salon :");
                    for (String s:membres)
                        System.out.println("\t\t\t- "+s);
                    break;
                case "MSG":
                    if(!(cnx.getAlias().equals(((Connexion) source).getAlias()))){

                        System.out.println("\t\t\t." + evenement.getType() + " " + evenement.getArgument());
                    }
                    break;
                case "HIST":
                    arg = evenement.getArgument();
                    historique = arg.split(":");
                    for (String s:historique)
                        System.out.println(s);
                	break;
                case "CHESSOK":// Iniatilise une partie d'echec
                	client.InitialiseChessGame();
                	System.out.print("\t\t\t." + evenement.getType() + " "+ evenement.getArgument() + "\n");
                	System.out.println(client.ChessGameMap());
                	break;
                case "MOVE"://Mise a jour de la partie d'echec
                	client.movePawn(evenement.getArgument());
                	client.ChessGameMap();
                	break;
                default: //Afficher le texte recu :
                    System.out.println("\t\t\t."+evenement.getType()+" "+evenement.getArgument());
            }
        }
    }
}
