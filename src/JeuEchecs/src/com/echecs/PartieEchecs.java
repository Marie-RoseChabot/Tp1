package JeuEchecs.src.com.echecs;

import JeuEchecs.src.com.echecs.pieces.*;
import JeuEchecs.src.com.echecs.util.EchecsUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

/**
 * Représente une partie de jeu d'échecs. Orcheste le déroulement d'une partie :
 * déplacement des pièces, vérification d'échec, d'échec et mat,...
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class PartieEchecs {
    /**
     * Grille du jeu d'échecs. La ligne 0 de la grille correspond à la ligne
     * 8 de l'échiquier. La colonne 0 de la grille correspond à la colonne a
     * de l'échiquier.
     */
    private Piece[][] echiquier;
    private String aliasJoueur1, aliasJoueur2;
    private char couleurJoueur1, couleurJoueur2;
    private boolean broque = true;
    private boolean nroque = true;

    /**
     * La couleur de celui à qui c'est le tour de jouer (n ou b).
     */
    private char tour = 'b'; //Les blancs commencent toujours
    /**
     * Crée un échiquier de jeu d'échecs avec les pièces dans leurs positions
     * initiales de début de partie.
     * Répartit au hasard les couleurs n et b entre les 2 joueurs.
     */
    public PartieEchecs() {
        echiquier = new Piece[8][8];
        //Placement des pièces :

        // Pions
        for(int i = 0; i < 8; i ++){
            echiquier[i][6] = new Pion('n');
            echiquier[i][1] = new Pion('b');
        }
        // Tours
        echiquier[0][0] = new Tour('b');
        echiquier[7][0] = new Tour('b');
        echiquier[0][7] = new Tour('n');
        echiquier[7][7] = new Tour('n');
        // Cavaliers
        echiquier[1][0] = new Cavalier('b');
        echiquier[6][0] = new Cavalier('b');
        echiquier[1][7] = new Cavalier('n');
        echiquier[6][7] = new Cavalier('n');
        // Fous
        echiquier[2][0] = new Fou('b');
        echiquier[5][0] = new Fou('b');
        echiquier[2][7] = new Fou('n');
        echiquier[5][7] = new Fou('n');
        // Dame
        echiquier[3][0] = new Dame('b');
        echiquier[3][7] = new Dame('n');
        // Roi
        echiquier[4][0] = new Roi('b');
        echiquier[4][7] = new Roi('n');
    }

    /**
     * Change la main du jeu (de n à b ou de b à n).
     */
    public void changerTour() {
        if (tour == 'b')
            tour = 'n';
        else
            tour = 'b';
    }
    /**
     * Tente de déplacer une pièce d'une position à une autre sur l'échiquier.
     * Le déplacement peut échouer pour plusieurs raisons, selon les règles du
     * jeu d'échecs. Par exemple :
     *  Une des positions n'existe pas;
     *  Il n'y a pas de pièce à la position initiale;
     *  La pièce de la position initiale ne peut pas faire le mouvement;
     *  Le déplacement met en échec le roi de la même couleur que la pièce.
     *
     * @param initiale Position la position initiale
     * @param finale Position la position finale
     *
     * @return boolean true, si le déplacement a été effectué avec succès, false sinon
     */
    public boolean deplace(Position initiale, Position finale) {
        // la position initiale n'existe pas
        if(EchecsUtil.indiceColonne(initiale.getColonne()) < 0 || initiale.getLigne() < 0 || EchecsUtil.indiceColonne(initiale.getColonne()) > 7 || initiale.getLigne() > 7) {
            System.out.println("Position initiale n'existe pas");
            return false;
        }
        // la position finale n'existe pas
        if(EchecsUtil.indiceColonne(finale.getColonne()) < 0 || finale.getLigne() < 0 || EchecsUtil.indiceColonne(finale.getColonne()) > 7 || finale.getLigne() > 7) {
            System.out.println("Position finale n'existe pas");
            return false;
        }
        // la position initiale n'a pas de piece associee
        try{
            echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()].getClass();
        } catch (Exception e){
            System.out.println("La position initiale n'a pas de piece associee.");
            return false;
        }
        // Ce n'est pas une piece valide a bouger
        if(this.getTour() != echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()].getCouleur()){
            System.out.println("Cette piece n'est pas la votre ou ce n'est pas votre tour.");
            return false;
        }
        // voir si la position finale est associee a une piece. Le joueur tente de manger une piece qui l'appartient
        try {
            echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()].getClass();
            if(echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()].getCouleur() == echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()].getCouleur()){
                System.out.println("La destination est occupee par une piece qui vous appartient.");
                return false;
            }
        } catch (Exception ignored){}

        // When all checks are done, call peutSeDeplacer
        if(echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()].peutSeDeplacer(initiale, finale, echiquier)){
            // deplace la piece a la position finale
            Piece temp = echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()];
            char couleurTemp = echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()].getCouleur();
            echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] = echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()];
            echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()] = null;

            // determiner si le joueur est en echec apres le deplacement
            if(couleurTemp == estEnEchec()){
                System.out.println("Ce deplacement est invalide, car vous etes en echec.");
                // replace la piece a la position initiale
                echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()] = echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()];
                echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] = temp;
                return false;
            }

            // Check if a pawn got to the last line, if so create Dame
            if(echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] instanceof Pion && finale.getLigne() == 7 && echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()].getCouleur() == 'b'){
                echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] = new Dame('b');
            } else if(echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] instanceof Pion && finale.getLigne() == 0 && echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()].getCouleur() == 'n'){
                echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] = new Dame('n');
            }

            // Check if moved piece was Tour or Roi
            if(echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] instanceof Roi || echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] instanceof Tour){
                // si la piece deplacee est un roi noir
                if(echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()].getCouleur() == 'n' && echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] instanceof Roi){
                    // roque vers la gauche
                    if(EchecsUtil.indiceColonne(initiale.getColonne()) - EchecsUtil.indiceColonne(finale.getColonne()) == 2){
                        // Si la voie est libre, deplace la tour aussi
                        if(echiquier[0][7].peutSeDeplacer(new Position('a', (byte)7), new Position('b', (byte)7), echiquier)){
                            echiquier[3][7] = echiquier[0][7];
                            echiquier[0][7] = null;
                        } else {
                            // replace la piece a la position initiale
                            echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()] = echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()];
                            echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] = temp;
                            return false;
                        }
                    }
                    // roque vers la droite
                    if(EchecsUtil.indiceColonne(finale.getColonne()) - EchecsUtil.indiceColonne(initiale.getColonne()) == 2){
                        // Si la voie est libre, deplace la tour aussi
                        echiquier[5][7] = echiquier[7][7];
                        echiquier[7][7] = null;
                    }
                    broque = false;
                    // si la piece deplacee est un roi blanc
                } else if (echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()].getCouleur() == 'b' && echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] instanceof Roi) {
                    // roque vers la gauche
                    if(EchecsUtil.indiceColonne(initiale.getColonne()) - EchecsUtil.indiceColonne(finale.getColonne()) == 2){
                        // Si la voie est libre, deplace la tour aussi
                        if(echiquier[0][0].peutSeDeplacer(new Position('a', (byte)0), new Position('b', (byte)0), echiquier)){
                            echiquier[3][0] = echiquier[0][0];
                            echiquier[0][0] = null;
                        } else {
                            // replace la piece a la position initiale
                            echiquier[EchecsUtil.indiceColonne(initiale.getColonne())][initiale.getLigne()] = echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()];
                            echiquier[EchecsUtil.indiceColonne(finale.getColonne())][finale.getLigne()] = temp;
                            return false;
                        }
                    }
                    // roque vers la droite
                    if(EchecsUtil.indiceColonne(finale.getColonne()) - EchecsUtil.indiceColonne(initiale.getColonne()) == 2){
                        // Si la voie est libre, deplace la tour aussi
                        echiquier[5][0] = echiquier[7][0];
                        echiquier[7][0] = null;
                    }
                    nroque = false;
                }
            }

            return true;
        }
        return false;
    }

    /**
     * Vérifie si un roi est en échec et, si oui, retourne sa couleur sous forme
     * d'un caractère n ou b.
     * Si la couleur du roi en échec est la même que celle de la dernière pièce
     * déplacée, le dernier déplacement doit être annulé.
     * Les 2 rois peuvent être en échec en même temps. Dans ce cas, la méthode doit
     * retourner la couleur de la pièce qui a été déplacée en dernier car ce
     * déplacement doit être annulé.
     *
     * @return char Le caractère n, si le roi noir est en échec, le caractère b,
     * si le roi blanc est en échec, tout autre caractère, sinon.
     */
    public char estEnEchec() {
        // trouver la position des instances de roi
        int[][] roiIndexes = new int[2][2];
        int index = 0;

        for(int c = 0; c < echiquier.length; c ++) {
            for (int l = 0; l < echiquier[c].length; l++) {
                if (echiquier[c][l] instanceof Roi) {
                    roiIndexes[index][0] = c;
                    roiIndexes[index][1] = l;
                    index++;
                }
            }
        }

        // Voir si chaque piece peut se deplacer au roi de la couleur opposee
        for(int c = 0; c < echiquier.length; c ++){
            for(int l = 0; l < echiquier[c].length; l ++){
                Position ini = new Position(EchecsUtil.getColonne((byte)c),(byte)l);

                // if there is no piece, ignore this position
                if(echiquier[c][l] == null){
                    continue;
                }
                // regarde si la couleur de la piece est differente de celle du roi
                if(echiquier[c][l].getCouleur() == 'b' && echiquier[roiIndexes[0][0]][roiIndexes[0][1]].getCouleur() != 'b'){
                    Position fin = new Position(EchecsUtil.getColonne((byte)roiIndexes[0][0]),(byte)roiIndexes[0][1]);
                    if(echiquier[c][l].peutSeDeplacer(ini, fin, echiquier)){
                        return 'n';
                    }
                } else if(echiquier[c][l].getCouleur() == 'n' && echiquier[roiIndexes[0][0]][roiIndexes[0][1]].getCouleur() != 'n'){
                    Position fin = new Position(EchecsUtil.getColonne((byte)roiIndexes[0][0]),(byte)roiIndexes[0][1]);
                    if(echiquier[c][l].peutSeDeplacer(ini, fin, echiquier)){
                        return 'b';
                    }
                }
                if(echiquier[c][l].getCouleur() == 'b' && echiquier[roiIndexes[1][0]][roiIndexes[1][1]].getCouleur() != 'b'){
                    Position fin = new Position(EchecsUtil.getColonne((byte)roiIndexes[1][0]),(byte)roiIndexes[1][1]);
                    if(echiquier[c][l].peutSeDeplacer(ini, fin, echiquier)){
                        return 'n';
                    }
                } else if(echiquier[c][l].getCouleur() == 'n' && echiquier[roiIndexes[1][0]][roiIndexes[1][1]].getCouleur() != 'n'){
                    Position fin = new Position(EchecsUtil.getColonne((byte)roiIndexes[1][0]),(byte)roiIndexes[1][1]);
                    if(echiquier[c][l].peutSeDeplacer(ini, fin, echiquier)){
                        return 'b';
                    }
                }
            }
        }
        return 'p';
    }
    /**
     * Retourne la couleur n ou b du joueur qui a la main.
     *
     * @return char la couleur du joueur à qui c'est le tour de jouer.
     */
    public char getTour() {
        return tour;
    }
    /**
     * Retourne l'alias du premier joueur.
     * @return String alias du premier joueur.
     */
    public String getAliasJoueur1() {
        return aliasJoueur1;
    }
    /**
     * Modifie l'alias du premier joueur.
     * @param aliasJoueur1 String nouvel alias du premier joueur.
     */
    public void setAliasJoueur1(String aliasJoueur1) {
        this.aliasJoueur1 = aliasJoueur1;
    }
    /**
     * Retourne l'alias du deuxième joueur.
     * @return String alias du deuxième joueur.
     */
    public String getAliasJoueur2() {
        return aliasJoueur2;
    }
    /**
     * Modifie l'alias du deuxième joueur.
     * @param aliasJoueur2 String nouvel alias du deuxième joueur.
     */
    public void setAliasJoueur2(String aliasJoueur2) {
        this.aliasJoueur2 = aliasJoueur2;
    }
    /**
     * Retourne la couleur n ou b du premier joueur.
     * @return char couleur du premier joueur.
     */
    public char getCouleurJoueur1() {
        return couleurJoueur1;
    }
    /**
     * Retourne la couleur n ou b du deuxième joueur.
     * @return char couleur du deuxième joueur.
     */
    public char getCouleurJoueur2() {
        return couleurJoueur2;
    }
}