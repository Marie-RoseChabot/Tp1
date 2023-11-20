package ChatServer.src.com.JeuEchecs.src.com.echecs.pieces;

import ChatServer.src.com.JeuEchecs.src.com.echecs.Position;
import ChatServer.src.com.JeuEchecs.src.com.echecs.util.EchecsUtil;

public class Dame extends Piece{
    public Dame(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        // deplacement vetical vers le bas
        if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0){
            System.out.println("deplacement vetical vers le bas");
            for(int l = EchecsUtil.indiceLigne(pos2.getLigne()) - 1; l > EchecsUtil.indiceLigne(pos1.getLigne()); l --){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    return false;
                }
            }
            return true;

            // deplacement vertical vers le haut
        } else if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0){
            System.out.println("deplacement vetical vers le haut");
            for(int l = EchecsUtil.indiceLigne(pos1.getLigne()) - 1; l > EchecsUtil.indiceLigne(pos2.getLigne()); l --){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la gauche
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            System.out.println("deplacement horizontal vers la gauche");
            for(int c = EchecsUtil.indiceColonne(pos2.getColonne()) + 1; c < EchecsUtil.indiceColonne(pos1.getColonne()); c ++){
                if(echiquier[c][EchecsUtil.indiceLigne(pos2.getLigne())] != null){
                    System.out.println("FOUND: " + (c) + EchecsUtil.indiceLigne(pos2.getLigne()));
                    System.out.println(echiquier[c][EchecsUtil.indiceLigne(pos2.getLigne())].getClass());
                    System.out.println(echiquier[c][EchecsUtil.indiceLigne(pos2.getLigne())].getCouleur());
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la droite
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            System.out.println("deplacement horizontal vers la droite");
            for(int c = EchecsUtil.indiceColonne(pos1.getColonne()) + 1; c < EchecsUtil.indiceColonne(pos2.getColonne()); c ++){
                if(echiquier[c][EchecsUtil.indiceLigne(pos2.getLigne())] != null){
                    System.out.println("FOUND: " + (c) + EchecsUtil.indiceLigne(pos2.getLigne()));
                    System.out.println(echiquier[c][EchecsUtil.indiceLigne(pos2.getLigne())].getClass());
                    System.out.println(echiquier[c][EchecsUtil.indiceLigne(pos2.getLigne())].getCouleur());
                    return false;
                }
            }
            return true;

        }

        // deplacement diagonal vers le bas a gauche
        if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            System.out.println("diagonal vers le bas a gauche");
            int c = 1;
            for(int l = pos2.getLigne(); l < pos1.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) + c][l] != null){
                    System.out.println("FOUND: " + (EchecsUtil.indiceColonne(pos2.getColonne()) - c) + l);
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) - c][l].getClass());
                    return false;
                }
                c ++;
            }
            return true;

            // deplacement diagonal vers le bas a droite
        } else if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            System.out.println("diagonal vers le bas a droite");
            int c = 1;
            for(int l = pos2.getLigne(); l < pos1.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) - c][l] != null){
                    System.out.println("FOUND: " + (EchecsUtil.indiceColonne(pos2.getColonne()) - c) + l);
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) - c][l].getClass());
                    return false;
                }
                c ++;
            }
            return true;

            // deplacement deplacement diagonal vers le haut a gauche
        } else if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            System.out.println("diagonal vers le haut a gauche");
            int c = 1;
            for(int l = pos1.getLigne(); l < pos2.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos1.getColonne()) - c][l] != null){
                    System.out.println("FOUND: " + (EchecsUtil.indiceColonne(pos1.getColonne()) - c) + l);
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos1.getColonne()) - c][l].getClass());
                    return false;
                }
                c ++;
            }
            return true;

            // deplacement diagonal vers le haut a droite
        } else if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            System.out.println("diagonal vers le haut a droite");
            int c = 1;
            for(int l = pos1.getLigne(); l < pos2.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) + c][l] != null){
                    System.out.println("FOUND: " + (EchecsUtil.indiceColonne(pos2.getColonne()) - c) + l);
                    System.out.println(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) - c][l].getClass());
                    return false;
                }
                c ++;
            }
            return true;

        }
        return false;
    }
}
