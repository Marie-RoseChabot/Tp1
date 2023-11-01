package JeuEchecs.src.com.echecs.programme;

import JeuEchecs.src.com.echecs.Position;
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
        Position a = new Position('d',(byte)1);
        Position b = new Position('d',(byte)2);
        System.out.println(a.estVoisineDe(b));
    }
}