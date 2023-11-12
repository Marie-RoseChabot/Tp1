package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;

public class Fou extends Piece{
    String type = "fou";
    Fou(char couleur) {
        super(couleur); // Calls the constructor of the superclass Monarch
        this.type = type;
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        return false;
    }
}