package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;

public class Pion extends Piece{
    public Pion(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        // check if the pawn is moving only by one square, in front, not going back
        if(this.getCouleur() == 'b' && pos2.getLigne() - pos1.getLigne() == 1 && pos2.estSurLaMemeLigneQue(pos1) ||
                this.getCouleur() == 'n' && pos1.getLigne() - pos2.getLigne() == 1 && pos2.estSurLaMemeLigneQue(pos1) ||
                // check if the target position is occupied by a piece of another color, if so diagonal movement is possible
                this.getCouleur() == 'b' && pos2.getLigne() - pos1.getLigne() == 1 && pos2.estSurLaMemeDiagonaleQue(pos1) && echiquier[(byte)pos2.getColonne()][pos2.getLigne()].getCouleur() != this.getCouleur() ||
                this.getCouleur() == 'n' && pos1.getLigne() - pos2.getLigne() == 1 && pos2.estSurLaMemeDiagonaleQue(pos1) && echiquier[(byte)pos2.getColonne()][pos2.getLigne()].getCouleur() != this.getCouleur()){
            return true;
        }
        return false;
    }
}
