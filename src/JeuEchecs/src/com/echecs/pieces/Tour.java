package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;
import JeuEchecs.src.com.echecs.util.EchecsUtil;

public class Tour extends Piece{
    Tour(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        // regarde si le deplacement est legal
            // deplacement vetical vers le bas
        if(pos2.estSurLaMemeLigneQue(pos1) && pos2.getLigne() - pos1.getLigne() < 0){
            for(int l = pos1.getLigne(); l < echiquier[EchecsUtil.indiceColonne(pos2.getColonne())].length; l ++){
                
            }

            // deplacement vertical vers le haut
        } else if(pos2.estSurLaMemeLigneQue(pos1) && pos2.getLigne() - pos1.getLigne() > 0){


            // deplacement horizontal vers la gauche
        } else if(pos2.estSurLaMemeColonneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) < 0){


            // deplacement horizontal vers la droite
        } else if(pos2.estSurLaMemeColonneQue(pos1) && EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne()) > 0){


        }
        return false;
    }
}
