package JeuEchecs.src.com.echecs.programme;

import JeuEchecs.src.com.echecs.PartieEchecs;
import JeuEchecs.src.com.echecs.Position;
import JeuEchecs.src.com.echecs.pieces.Piece;
import JeuEchecs.src.com.echecs.pieces.Pion;
import JeuEchecs.src.com.echecs.util.EchecsUtil;
/**
 * Programme pour tester les classes de jeu d'échecs.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class Main {
    public static void main(String[] args) {

        for (byte i=0;i<8;i++) {
            System.out.print(EchecsUtil.getLigne(i)+" ");
            for (int j=0;j<8;j++)
                System.out.print(". ");
            System.out.println();
        }
        System.out.print("  ");
        for (byte j=0;j<8;j++)
            System.out.print(EchecsUtil.getColonne(j)+" ");

        // A des fins de test!!!
        PartieEchecs partie = new PartieEchecs();

        Position ini = new Position('d',(byte)3);
        Position fin = new Position('e',(byte)4);

        System.out.println(partie.deplace(ini, fin));
        // pour faire que la piece se deplace pour vrai, il faut changer la position de l'objet a la position finale.
    }
}