package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;
import JeuEchecs.src.com.echecs.util.EchecsUtil;

public class Tour extends Piece{
    public Tour(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        // regarde si le deplacement est legal
            // deplacement vetical vers le bas
        if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0){
            for(int l = pos2.getLigne() + 1; l < pos1.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    return false;
                }
            }
            return true;

            // deplacement vertical vers le haut
        } else if(pos2.estSurLaMemeColonneQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0){
            for(int l = pos1.getLigne() + 1; l < pos2.getLigne(); l ++){
                if(echiquier[EchecsUtil.indiceColonne(pos2.getColonne())][l] != null){
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la gauche
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){
            for(int c = EchecsUtil.indiceColonne(pos2.getColonne()) + 1; c < EchecsUtil.indiceColonne(pos1.getColonne()); c ++){
                if(echiquier[c][pos2.getLigne()] != null){
                    return false;
                }
            }
            return true;

            // deplacement horizontal vers la droite
        } else if(pos2.estSurLaMemeLigneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){
            for(int c = EchecsUtil.indiceColonne(pos1.getColonne()) + 1; c < EchecsUtil.indiceColonne(pos2.getColonne()); c ++){
                if(echiquier[c][pos2.getLigne()] != null){
                    return false;
                }
            }
            return true;

        }
        return false;
    }
}
