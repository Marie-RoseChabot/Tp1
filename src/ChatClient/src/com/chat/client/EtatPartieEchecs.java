package ChatClient.src.com.chat.client;

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
	
	public void setEtatEchiquier(char[][] etatEchiquier) {
		this.etatEchiquier = etatEchiquier;
	}
	
	@Override
	public String toString()
	{
		String tableau = "";
		for(int i = 0; i < etatEchiquier.length; i++)
		{
			tableau += i + 1 + " ";
			
			for(int j = 0; j < etatEchiquier.length; j++)
			{
				tableau += etatEchiquier[i][j] + " ";
			}
			
			tableau += "\n";
		}
		
		tableau += "  a b c d e f g h";
		
		return tableau;
	}
}
