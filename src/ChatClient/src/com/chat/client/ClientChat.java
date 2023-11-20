package ChatClient.src.com.chat.client;

/**
 * Cette classe étend la classe Client pour lui ajouter des fonctionnalités
 * spécifiques au chat et au jeu d'échecs en réseau.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class ClientChat extends Client 
{
	private boolean start = false;
	private EtatPartieEchecs etatPartieEchecs;
	
	public void nouvellePartie() {
		etatPartieEchecs = new EtatPartieEchecs();
	}
	
	public EtatPartieEchecs getEtatPartieEchecs() {
		return etatPartieEchecs;
	}

	public void setEtatPartieEchecs(EtatPartieEchecs etatPartieEchecs) {
		this.etatPartieEchecs = etatPartieEchecs;
	}
	
	/***
	 * Permet d'initialiser une partie d'echec.
	 */
	public void InitialiseChessGame()
	{
		nouvellePartie();
	}
	
	/***
	 * Affiche l'etat de la partie d'echec
	 * @return retourne une chaine de caractere de la map.
	 */
	public String ChessGameMap() 
	{
		if(start) {
			return getEtatPartieEchecs().toString();
		} else {
			return getEtatPartieEchecs().toStringStart();
		}
	}

	/***
	 * Permet de mettre a jour la position d'un pion.
	 */
	public void movePawn(String position) {
		//La valeur du pion en position inital
		char pawn = ' ';
		
		//Les position des pions initial et final.
		int pInitX, pFinalX, pInitY, pFinalY;
		
		//Permet d'extraire les coordonnes
		if(position.contains("-") || position.contains(" "))
		{ 
			//EXEMPLE C3  , ici on recupere le C
			pInitX = getPositionLigne(position.split("[\\s\\-]")[0].substring(0, 1));
			
			//EXEMPLE C3, ici on recupere le 3
			pInitY = Integer.parseInt(position.split("[\\s\\-]")[0].substring(1, 2));
			
			pFinalX = getPositionLigne(position.split("[\\s\\-]")[1].substring(0, 1));
			
			pFinalY = Integer.parseInt(position.split("[\\s\\-]")[1].substring(1, 2));
		}
		else
		{
			pInitX = getPositionLigne(position.substring(0, 1));
			pInitY = Integer.parseInt(position.substring(1, 2));
			
			pFinalX = getPositionLigne(position.substring(2, 3));
			pFinalY = Integer.parseInt(position.substring(3, 4));
		}
		
		char[][] echiquier = etatPartieEchecs.getEtatEchiquier();
				
		for(int i = 0; i < echiquier.length; i++)
		{
			for(int j = 0; j < echiquier.length; j++)
			{
				if(pInitX == i && pInitY == j)
				{
					pawn = echiquier[i][j];
					echiquier[i][j] = '.';// CASE VIDE
				}
				if(pFinalX == i && pFinalY == j)
				{
					echiquier[i][j] = pawn;
				}
			}
		}
		
		etatPartieEchecs.setEtatEchiquier(pInitX-1,pInitY-1,pFinalX-1,pFinalY-1);
	}

	private int getPositionLigne(String position)
	{
		int index = -1; 
		String[] ligne = {"a", "b", "c", "d", "e", "f", "g", "h"};
		
        for (int i = 0; i < ligne.length; i++) 
        {
            if (position.equals(ligne[i])) {
                index = i;
            }
        }
        
        return index;
	}
}
