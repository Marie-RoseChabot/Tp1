package ChatClient.src.com.chat.client;

import ChatServer.src.com.JeuEchecs.src.com.echecs.Position;

public class EtatPartieEchecs
{
	private char[][] etatEchiquier;
	
	public EtatPartieEchecs()
	{
        etatEchiquier = new char[][] {
            {'t', 'c', 'f', 'd', 'r', 'f', 'c', 't'},
            {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
            {'T', 'C', 'F', 'D', 'R', 'F', 'C', 'T'}
            };
	}
	
	public char[][] getEtatEchiquier() {
		return etatEchiquier;
	}
	
	public void setEtatEchiquier(int initX,int initY, int finX, int finY) {

		etatEchiquier[finY][finX]=etatEchiquier[initY][initX];
		etatEchiquier[initY][initX]=' ';

	}

	public String toStringStart()
	{
		String tableau = "";
		tableau += "\n\t\t\t.";
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
		tableau += "\n\n";

		return tableau;
	}
}
