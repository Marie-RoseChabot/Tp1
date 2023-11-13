package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;

public class Cavalier extends Piece{
    String type = "cavalier";
    Cavalier(char couleur) {
        super(couleur);
        this.type = type;
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        return false;
    }
}
