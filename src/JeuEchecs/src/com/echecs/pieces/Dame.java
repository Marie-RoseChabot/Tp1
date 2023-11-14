package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;
import JeuEchecs.src.com.echecs.util.EchecsUtil;

public class Dame extends Piece{
    public Dame(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
            // deplacement vertical vers le bas
        if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0){
            for(int l = pos2.getLigne() + 1; l < pos1.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    System.out.println("Object trouve, ligne: " + l);
                    return false;
                }
            }
            return true;

            // deplacement vertical vers le haut
        } else if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0){
            for(int l = pos1.getLigne() + 1; l < pos2.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    System.out.println("Object trouve, ligne: " + l);
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la gauche
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            for(int c = EchecsUtil.indiceColonne(pos2.getColonne()) + 1; c < EchecsUtil.indiceColonne(pos1.getColonne()); c ++){
                if(echiquier[c][pos2.getLigne()] != null){
                    System.out.println("Object trouve, colonne: " + c);
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la droite
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            for(int c = EchecsUtil.indiceColonne(pos1.getColonne()) + 1; c < EchecsUtil.indiceColonne(pos2.getColonne()); c ++){
                if(echiquier[c][pos2.getLigne()] != null){
                    System.out.println("Object trouve, colonne: " + c);
                    return false;
                }
            }
            return true;

            // deplacement diagonal vers le bas a gauche
        } else if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            int c = 1;
            for(int l = pos2.getLigne() + 1; l < pos1.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) + c][l] != null){
                    System.out.println("Object trouve, ligne: " + l + " colonne: " + (EchecsUtil.indiceColonne(pos2.getColonne()) + c));
                    return false;
                }
                c ++;
            }
            return true;

            // deplacement diagonal vers le bas a droite
        } else if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            int c = 1;
            for(int l = pos2.getLigne() + 1; l < pos1.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne()) - c][l] != null){
                    System.out.println("Object trouve, ligne: " + l + " colonne: " + (EchecsUtil.indiceColonne(pos2.getColonne()) - c));
                    return false;
                }
                c ++;
            }
            return true;

            // deplacement deplacement diagonal vers le haut a gauche
        } else if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            int c = 1;
            for(int l = pos1.getLigne() + 1; l < pos2.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos1.getColonne()) - c][l] != null){
                    System.out.println("Object trouve, ligne: " + l + " colonne: " + (EchecsUtil.indiceColonne(pos1.getColonne()) - c));
                    return false;
                }
                c ++;
            }
            return true;

            // deplacement diagonal vers le haut a droite
        } else if(pos2.estSurLaMemeDiagonaleQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0 && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            int c = 1;
            for(int l = pos1.getLigne() + 1; l < pos2.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos1.getColonne()) + c][l] != null){
                    System.out.println("Object trouve, ligne: " + l + " colonne: " + (EchecsUtil.indiceColonne(pos1.getColonne()) + c));
                    return false;
                }
                c ++;
            }
            return true;

        }
        return false;
    }
}
