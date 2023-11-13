package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;

public class Pion extends Piece{
    String type = "pion";
    Pion(char couleur) {
        super(couleur);
        this.type = type;
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        // check if the pawn is moving only by one square, in front, not going back
        if(Math.abs(pos2.getLigne() - pos1.getLigne()) == 1 && pos2.estSurLaMemeLigneQue(pos1) ||
                // check if the target position is occupied by a piece of another color, if so diagonal movement is possible
                Math.abs(pos2.getLigne() - pos1.getLigne()) == 1 && pos2.estSurLaMemeDiagonaleQue(pos1) && echiquier[(byte)pos2.getColonne()][pos2.getLigne()].getCouleur() != echiquier[(byte)pos1.getColonne()][pos1.getLigne()].getCouleur()){
            return true;
        }
        return false;
    }
}
