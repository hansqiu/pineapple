import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameModel 
{

	private Player currentPlayer;
	private ArrayList<Pit> pitList; 		
	private boolean extraTurn;	
	private boolean canUndo;
	private int numOfUndo;
	private int[] previousNumOfStone;
	private ArrayList<ChangeListener> listeners;
	
    public GameModel(GameStyle theGameStyle) 
    {
    	currentPlayer = Player.A;
    	pitList = new ArrayList<Pit>();
		for (int i = 0; i < 6; i++) {
			pitList.add(new Pit(0, i, Player.A, theGameStyle));
		}
		pitList.add(new Mancala(0, 6, Player.A, theGameStyle));
		for (int i = 7; i < 13; i++) {
			pitList.add(new Pit(0, i, Player.B, theGameStyle));
		}
		pitList.add(new Mancala(0, 13, Player.B, theGameStyle));

		listeners = new ArrayList<ChangeListener>();		
		extraTurn = false;		
		canUndo = false;
		numOfUndo = 0;
		previousNumOfStone = new int[14];
	}
        
	public void initialize(int stones) 
	{
		int counter = 0;
		for (Pit p : pitList) 
		{
			if (!(p instanceof Mancala)) 
			{
				p.setNumOfStones(stones);
				previousNumOfStone[counter] = stones;
			}  
			else 
			{
				previousNumOfStone[counter] = 0;
				counter++;
			}
		}
		notifyChangeListeners();
	}
	
	public void choosePit(Pit pit) 
	{
		if(!canUndo)
			numOfUndo=0;
		if(pit.getPlayer() != currentPlayer)
			return;
		if(pit.getNumOfStones() == 0)
			return;
		for (Pit pitt : pitList) {
			previousNumOfStone[pitt.getIndex()] = pitt.getNumOfStones();
		}

		extraTurn = getExtraTurn(pit);
		int lastStoneDropped = getLastStone(pit);
		int numberOfStones = pit.getNumOfStones();
		pit.setNumOfStones(0);

		distributeStones(pit.getIndex(), numberOfStones);
		wonOpponentStones(lastStoneDropped);
		if (gameIsOver()) 
			clearBoard();
		if (!extraTurn) {
			switchPlayer();
		}
		canUndo = false;
		notifyChangeListeners();
	}
	
	public void distributeStones(int startIndex, int stones) 
	{
		int numberOfStones = stones;
		int currentIndex = startIndex;

		while (numberOfStones > 0) {
			if (currentIndex == 5 && currentPlayer == Player.B) 
			{
				currentIndex += 2;
			} else if (currentIndex == 12 && currentPlayer == Player.A) 
			{
				currentIndex = 0;
			} else {
				currentIndex++;
			}
			if (currentIndex == 14) 
			{
				currentIndex = 0;
			}
			pitList.get(currentIndex).setNumOfStones(pitList.get(currentIndex).getNumOfStones() + 1);
			numberOfStones--;
		}
	}
        
	public int getLastStone(Pit pit) 
	{
		int numberOfStones = pit.getNumOfStones();
		int currentIndex = pit.getIndex();
		while (numberOfStones > 0) 
		{
			if (currentIndex == 5 && currentPlayer == Player.B) 
			{
				currentIndex += 2;
			} else if (currentIndex == 12 && currentPlayer == Player.A) 
			{
				currentIndex += 2;
			} else 
			{
				currentIndex++;
			}
			
			if (currentIndex == 14) 
			{
				currentIndex = 0;
			}
			numberOfStones--;
		}
		return currentIndex;
	}
    
	private boolean getExtraTurn(Pit pit) {
		int totalMoves = pit.getIndex() + pit.getNumOfStones();
		if ((totalMoves - 6) % 13 == 0 && currentPlayer == Player.A) 
		{
			return true;
		}
		if ((totalMoves - 13) % 13 == 0 && currentPlayer == Player.B) {
			return true;
		}
		return false;
	}
        
	private void wonOpponentStones(int lastStoneDropped) 
	{
		int mancala = 6;
		if(lastStoneDropped == 6 || lastStoneDropped == 13) return;
		else if (pitList.get(lastStoneDropped).getNumOfStones() == 1 && pitList.get(lastStoneDropped).getPlayer() == currentPlayer) 
		{
			if(pitList.get(12-lastStoneDropped).getNumOfStones()==0)return;
			if(currentPlayer==Player.A)mancala=6;
			else mancala=13;

			pitList.get(mancala).setNumOfStones(pitList.get(mancala).getNumOfStones() + pitList.get(12 - lastStoneDropped).getNumOfStones());
			pitList.get(mancala).setNumOfStones(pitList.get(mancala).getNumOfStones() + pitList.get(lastStoneDropped).getNumOfStones());
			pitList.get(12 - lastStoneDropped).setNumOfStones(0);
			pitList.get(lastStoneDropped).setNumOfStones(0);
		} 
	}
	
	public boolean gameIsOver() 
	{
		boolean emptyRow = true;
		for (int i = 0; i < 6; i++) 
		{
			if (pitList.get(i).getNumOfStones() != 0) {
				emptyRow = false;
			}
		}
		if (emptyRow) {
			return emptyRow;
		}

		emptyRow = true;

		for (int i = 7; i < 13; i++) {
			if (pitList.get(i).getNumOfStones() != 0) {
				emptyRow = false;
			}
		}

		return emptyRow;
	}
	

	public ArrayList<Pit> getPitList() 
	{
		return pitList;
	}

	public Mancala getMancala(Player player) 
	{
		for (Pit p : pitList) 
		{
			if (p.getPlayer() == player && p instanceof Mancala) 
			{
				return (Mancala) p;
			}
		}
		return null;
	}
	
	private void switchPlayer() 
	{
		if (currentPlayer == Player.A) 
		{
			currentPlayer = Player.B;
		} else 
		{
			currentPlayer = Player.A;
		}
		extraTurn = false;
	}

	private boolean noPreviousState()
	{
		for(Pit p: pitList)
			if(p.getNumOfStones() != previousNumOfStone[p.getIndex()])
				return false;
		return true;
	}
       
	public void undo() 
	{

		if (!canUndo()) 
		{
			System.out.println("You can't undo this turn!");
			return;
		}

		canUndo = true;
		numOfUndo++;
		
		for (Pit pat : pitList) 
			pat.setNumOfStones(previousNumOfStone[pat.getIndex()]);

		if(!extraTurn)
			switchPlayer();

		notifyChangeListeners();
	}

	 
	private boolean canUndo()
	{
		if(noPreviousState())
		{
			System.out.println("You can't undo since this is your first turn!");
			return false;
		} else if(numOfUndo == 3)
		{
			System.out.println("You have used all 3 attempts of Undo!");
			return false;
		} else if(gameIsOver())
		{
			return false;
		} else
		{
			return true;
		}
	}
	
	public void notifyChangeListeners()
	{
		for (int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).stateChanged(new ChangeEvent(this));
		}
	}
	
	public void attach(ChangeListener listener) 
	{
		listeners.add(listener);
	}
        
	public Player getPlayer() 
	{
		return currentPlayer;
	}
     
	public boolean getGameStatus() 
	{

		return gameIsOver();
	}

	private void clearBoard()
	{
		for (int i = 0; i < 6; i++) 
		{
			pitList.get(6).setNumOfStones(pitList.get(6).getNumOfStones() + pitList.get(i).getNumOfStones());
			pitList.get(i).setNumOfStones(0);
		}
		for (int i = 7; i < 13; i++) 
		{
			pitList.get(13).setNumOfStones(pitList.get(13).getNumOfStones() + pitList.get(i).getNumOfStones());
			pitList.get(i).setNumOfStones(0);
		}
	}
}