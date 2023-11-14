package JeuEchecs.src.com.echecs.pieces;

import JeuEchecs.src.com.echecs.Position;
import JeuEchecs.src.com.echecs.util.EchecsUtil;

public class Cavalier extends Piece{
    public Cavalier(char couleur) {
        super(couleur);
    }
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        // si la forme est un L (de 3x2) de n'importe quel cote, le deplacement est valide
        return (Math.abs(pos2.getLigne() - pos1.getLigne()) == 2 && Math.abs(EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne())) == 1) ||
                (Math.abs(pos2.getLigne() - pos1.getLigne()) == 1 && Math.abs(EchecsUtil.indiceColonne(pos2.getColonne()) - EchecsUtil.indiceColonne(pos1.getColonne())) == 2);
    }
}
