package ChatServer.src.com.JeuEchecs.src.com.echecs.pieces;

import ChatServer.src.com.JeuEchecs.src.com.echecs.Position;
import ChatServer.src.com.JeuEchecs.src.com.echecs.util.EchecsUtil;

public class Roi extends Piece{
    public Roi(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        if(pos2.estVoisineDe(pos1)){
            return true;
            // roque
        } else return Math.abs(EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne())) == 2 && pos2.estSurLaMemeLigneQue(pos1);
    }
}
