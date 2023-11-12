package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;

public class Roi extends Piece{
    String type = "roi";
    Roi(char couleur) {
        super(couleur); // Calls the constructor of the superclass Monarch
        this.type = type;
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        return false;
    }
}
