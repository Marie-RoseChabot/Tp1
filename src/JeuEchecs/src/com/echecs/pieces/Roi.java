package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;

public class Roi extends Piece{
    public Roi(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        return pos2.estVoisineDe(pos1);
    }
}
