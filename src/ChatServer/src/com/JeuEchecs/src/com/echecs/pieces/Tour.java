package ChatServer.src.com.JeuEchecs.src.com.echecs.pieces;

import ChatServer.src.com.JeuEchecs.src.com.echecs.Position;
import ChatServer.src.com.JeuEchecs.src.com.echecs.util.EchecsUtil;

public class Tour extends Piece{
    public Tour(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        // regarde si le deplacement est legal
        // deplacement vetical vers le bas
        if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0){
            System.out.println("deplacement vetical vers le bas");
            for(int l = EchecsUtil.indiceLigne(pos2.getLigne()) -1; l > EchecsUtil.indiceLigne(pos1.getLigne()); l --){
                System.out.println("CHECKING: " + (pos2.getColonne()) + l);
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    System.out.println("FOUND: " + (EchecsUtil.indiceColonne(pos2.getColonne())) + l);
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l].getClass());
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l].getCouleur());
                    return false;
                }
            }
            return true;

            // deplacement vertical vers le haut
        } else if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0){
            System.out.println("deplacement vetical vers le haut");
            for(int l = EchecsUtil.indiceLigne(pos1.getLigne()) -1; l > EchecsUtil.indiceLigne(pos2.getLigne()); l --){
                System.out.println("CHECKING: " + (EchecsUtil.indiceColonne(pos2.getColonne())) + l);
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    System.out.println("FOUND: " + (EchecsUtil.indiceColonne(pos2.getColonne())) + l);
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l].getClass());
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l].getCouleur());
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la gauche
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            System.out.println("deplacement horizontal vers la gauche");
            for(int c = EchecsUtil.indiceColonne(pos2.getColonne()); c < EchecsUtil.indiceColonne(pos1.getColonne()); c ++){
                if(echiquier[c][pos2.getLigne()] != null){
                    System.out.println("FOUND: " + (c) + pos2.getLigne());
                    System.out.println(echiquier[c][pos2.getLigne()].getClass());
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la droite
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            System.out.println("deplacement horizontal vers la droite");
            for(int c = EchecsUtil.indiceColonne(pos1.getColonne()); c < EchecsUtil.indiceColonne(pos2.getColonne()); c ++){
                System.out.println("FOUND: " + (c) + pos2.getLigne());
                System.out.println(echiquier[c][pos2.getLigne()].getClass());
                if(echiquier[c][pos2.getLigne()] != null){
                    return false;
                }
            }
            return true;

        }
        return false;
    }
}