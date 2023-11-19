package ChatClient.src.com.chat.client;

import ChatServer.src.com.JeuEchecs.src.com.echecs.Position;

public class EtatPartieEchecs
{
	private char[][] etatEchiquier;
	
	public EtatPartieEchecs()
	{
        etatEchiquier = new char[][] {
            {'r', 'c', 'f', 'd', 'r', 'f', 'c', 'r'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'R', 'C', 'F', 'D', 'R', 'F', 'C', 'R'}
            };
	}
	
	public char[][] getEtatEchiquier() {
		return etatEchiquier;
	}
	
	public void setEtatEchiquier(int initX,int initY, int finX, int finY) {

		etatEchiquier[finY][finX]=etatEchiquier[initY][initX];
		etatEchiquier[initY][initX]=' ';

	}
	
	@Override
	public String toString()
	{
		String tableau = "";
		tableau += "\n\t\t\t";
		for(int i = 0; i < etatEchiquier.length; i++)
		{
			tableau += etatEchiquier.length-i + " ";
			
			for(int j = 0; j < etatEchiquier.length; j++)
			{
				tableau += etatEchiquier[i][j] + " ";
			}
			tableau += "\n\t\t\t.";

		}
		
		tableau += "  a b c d e f g h";
		
		return tableau;
	}
}
