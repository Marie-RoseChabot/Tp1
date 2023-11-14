package ChatServer.src.com.chat.serveur;

import ChatServer.src.com.chat.commun.net.Connexion;
import ChatServer.src.com.chat.serveur.Invitation;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Cette classe étend (hérite) la classe abstraite Serveur et y ajoute le nécessaire pour que le
 * serveur soit un serveur de chat.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-15
 */
public class ServeurChat extends Serveur {

    //attribut pour stocker tous les messages envoyes au salon de chat public
    public Vector<String> historique=new Vector<>();
    public Vector<Invitation> invitationPrivee= new Vector<>();

    public Vector<SalonPrive> salonPrives=new Vector<>();

    /**
     * Crée un serveur de chat qui va écouter sur le port spécifié.
     *
     * @param port int Port d'écoute du serveur
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
            connexion.envoyer("HIST " + hist);
        }
        return super.ajouter(connexion);
    }
    /**
     * Valide l'arrivée d'un nouveau client sur le serveur. Cette redéfinition
     * de la méthode héritée de Serveur vérifie si le nouveau client a envoyé
     * un alias composé uniquement des caractères a-z, A-Z, 0-9, - et _.
     *
     * @param connexion Connexion la connexion représentant le client
     * @return boolean true, si le client a validé correctement son arrivée, false, sinon
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
            if (texte.equalsIgnoreCase(cnx.getAlias())) { //alias déjà utilisé
                res = false;
                break;
            }
        }
        connexion.setAlias(texte);
        return true;
    }

    /**
     * Retourne la liste des alias des connectés au serveur dans une chaîne de caractères.
     *
     * @return String chaîne de caractères contenant la liste des alias des membres connectés sous la
     * forme alias1:alias2:alias3 ...
     */
    public String list() {
        String s = "";
        for (Connexion cnx:connectes)
            s+=cnx.getAlias()+":";
        return s;
    }
    /**
     * Retourne la liste des messages de l'historique de chat dans une chaîne
     * de caractères.
     *
     * @return String chaîne de caractères contenant la liste des alias des membres connectés sous la
     * forme message1\nmessage2\nmessage3 ...
     */
    public String historique() {
        String s="";

         if(historique != null){
            for (int i=0;i< historique.size();i++) {

                if (i != 0) {
                    s = (s + "\n" + "\t\t\t." + historique.get(i));
                }
                else
                    s=(s  + historique.get(i));
            }
        }

        return s;
    }


    /**
     * Envoie un message à tous les utilisateurs connectés sauf à celui qui l'écrit.
     *
     *
     */
    public void envoyerATousSauf(String str, String aliasExpediteur, Vector<Connexion> cnx) {
        ajouterHistorique(aliasExpediteur + ">>" + str);

        for (int i = 0; i < cnx.size(); i++) {
            if (!aliasExpediteur.equals(cnx.get(i).getAlias())) {
                cnx.get(i).envoyer(aliasExpediteur + ">>" + str);

            }

        }

    }

    //ajoute les messages à l'historique
    public void ajouterHistorique(String message){
        historique.addElement(message);

    }


    //Permet d'envoyer une demande d'invitation ou de répondre à une invitation

    public void gererInvitation(String alias2, String alias1, Vector<Connexion> cnx) {
        int j = 0;
        int l=0;
        Invitation invitation = new Invitation(alias1, alias2);
        boolean etat = false;
        int i = 0;

        //trouver la position de l'alias1 et alias2
        while (!((cnx.get(j).getAlias()).equals(alias2))) {
            j++;
        }

        while (!((cnx.get(l).getAlias()).equals(alias1))) {
            l++;
        }

        if(alias1.equals(alias2)){
            etat=true;
        }

        for(int k=0; k< salonPrives.size();k++){
            if(salonPrives.get(k).aliasInvite.equals(alias2)&&
                    salonPrives.get(k).aliasHote.equals(alias1)){
                etat=true;
            }
            else if(salonPrives.get(k).aliasInvite.equals(alias1)&&
                    salonPrives.get(k).aliasHote.equals(alias2)){
                etat=true;
            }
        }


        while (!etat) {

            if(invitationPrivee.isEmpty()){
                invitationPrivee.add(invitation);
                while (!((cnx.get(j).getAlias()).equals(alias2))) {
                    j++;
                }
                //Envoyer un texte pour informer alias2 que alias1 lui envoie une invitation de chat prive.
                cnx.get(j).envoyer(alias1 + " vous envoie une invitation de chat privé");

                etat=true;
            }

            else if ((invitationPrivee.get(i).aliasInvite.equals(invitation.getAliasHote()))&&
                    (invitationPrivee.get(i).aliasHote.equals(invitation.getAliasInvite()))) {

                //creer un salon prive
                SalonPrive salonPrive = new SalonPrive(alias1, alias2);
                salonPrives.add(salonPrive);
                cnx.get(j).envoyer("Salon privé créé");
                cnx.get(l).envoyer("Salon privé créé");

                invitationPrivee.remove(i);

                etat = true;
                }
            else {

                //parcourir toutes les connexions pour arriver au alias2=position j
                while (!((cnx.get(j).getAlias()).equals(alias2))) {
                    j++;

                }

                //Envoyer un texte pour informer alias2 que alias1 lui envoie une invitation de chat prive.
                cnx.get(j).envoyer(alias1 + " vous envoie une invitation de chat privé");
                //ajouter la nouvelle connexion au vecteur des invitations

                invitationPrivee.add(invitation);
                etat = true;
            }

            i++;


        }

        if(invitationPrivee.isEmpty()){
            invitationPrivee.add(invitation);
        }
    }

        public void gererRefus (String alias1, String alias2, Vector < Connexion > cnx){
            int j = 0;
            boolean etat=false;
            int i=0;
            int k=0;


            while((!etat)&& (i<salonPrives.size())) {


                if (!etat) {

                    while ((!etat) && (k < invitationPrivee.size())) {
                        System.out.println(invitationPrivee.get(k).aliasInvite);
                        System.out.println(invitationPrivee.get(k).aliasHote);


                        if (invitationPrivee.get(k).aliasInvite.equals(alias1)
                                && invitationPrivee.get(k).aliasHote.equals(alias2)) {
                            System.out.println("blabla");
                            etat = true;
                            invitationPrivee.remove(k);


                            while (!cnx.get(j).getAlias().equals(alias2)) {
                                j++;
                            }

                            //Envoyer un texte pour informer alias1 que alias1 refuse  l'invitation de chat prive.
                            cnx.get(j).envoyer(alias1 + " a refusé l'invitation de chat privé");
                        }
                        k++;
                    }

                }
            }

        }
        public Vector<String> envoyerHistorique (String aliasExpediteur){
            Vector<String> liste = new Vector<>();
            int j;
            for (int i = 0; i < invitationPrivee.size(); i++) {
                //si la liste d'invitation contient alias 1 faire une liste
                if (invitationPrivee.get(i).getAliasHote().equals(aliasExpediteur)) {
                    Invitation invitationAlias1 = new Invitation(aliasExpediteur, invitationPrivee.get(i).getAliasInvite());

                    liste.add(invitationAlias1.getAliasInvite());
                }
            }
            return liste;
        }



    public void quitterChatPrive(String alias1, String alias2, Vector < Connexion > cnx){
    int i=0;
    boolean etat=false;
    int j=0;

    while(!etat) {
        if ((salonPrives.get(i).aliasHote.equals(alias1))
                && (salonPrives.get(i).aliasInvite.equals(alias2))) {

            salonPrives.remove(i);
            System.out.println("Enlever");
            etat = true;


            while (!cnx.get(j).getAlias().equals(alias2)) {
                j++;
            }

            //Envoyer un texte pour informer alias1 que alias1 refuse  l'invitation de chat prive.
            cnx.get(j).envoyer(alias1 + " a quitté le chat privé");

        }
        i++;
    }
    }

    public void envoyerMessagePrive(String aliasExpediteur,String contenu,Vector < Connexion > cnx){
        String[] element=contenu.split(" ");
        int j=0;
        int i=0;
        boolean etat=false;


        while (!cnx.get(j).getAlias().equals(element[0])) {
            j++;
        }

        while(!etat) {
            if ((salonPrives.get(i).aliasHote.equals(aliasExpediteur)
                    && (salonPrives.get(i).aliasInvite.equals(element[0])))
            ||(salonPrives.get(i).aliasHote.equals(element[0])
                    && (salonPrives.get(i).aliasInvite.equals(aliasExpediteur)))){
                cnx.get(j).envoyer(aliasExpediteur + ">>" + element[1]);
                etat=true;
            }
            i++;
        }

    }
}

