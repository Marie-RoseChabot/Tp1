package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;

public class Tour extends Piece{
    Tour(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        return false;
    }
}
