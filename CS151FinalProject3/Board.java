import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Board 
{

	private Player currentPlayer;
	private ArrayList<Pit> pitList; 
	
	private ArrayList<ChangeListener> listeners;
	
	private boolean extraTurn;
	
	private boolean canUndo;
	private int countUndo;
	private int[] previousState;
	
    public Board(BoardLayout aBoardStyle) 
    {
    	currentPlayer = Player.ONE;
    	pitList = new ArrayList<Pit>();
		for (int i = 0; i < 6; i++) {
			pitList.add(new Pit(0, i, Player.ONE, aBoardStyle));
		}
		pitList.add(new Mancala(0, 6, Player.ONE, aBoardStyle));
		for (int i = 7; i < 13; i++) {
			pitList.add(new Pit(0, i, Player.TWO, aBoardStyle));
		}
		pitList.add(new Mancala(0, 13, Player.TWO, aBoardStyle));

		listeners = new ArrayList<ChangeListener>();
		
		extraTurn = false;
		
		canUndo = false;
		countUndo = 0;
		previousState = new int[14];
	}
        
	public void setBoard(int stones) 
	{
		int counter = 0;
		for (Pit p : pitList) 
		{
			if (!(p instanceof Mancala)) 
			{
				p.setStones(stones);
				previousState[counter] = stones;
			}  
			else 
			{
				previousState[counter] = 0;
				counter++;
			}
		}
		notifyChangeListeners();
	}
        
	
        
	public int getLastMarble(Pit pit) 
	{
		int numberOfMarbles = pit.getStones();
		int currentIndex = pit.getIndex();
		while (numberOfMarbles > 0) {
			if (currentIndex == 5 && currentPlayer == Player.TWO) {
				currentIndex += 2;
			} else if (currentIndex == 12 && currentPlayer == Player.ONE) {
				currentIndex += 2;
			} else {
				currentIndex++;
			}
			if (currentIndex == 14) {
				currentIndex = 0;
			}
			numberOfMarbles--;
		}
		return currentIndex;
	}

	public void distributeMarbles(int startIndex, int marbles) {
		int numberOfMarbles = marbles;
		int currentIndex = startIndex;

		while (numberOfMarbles > 0) {
			if (currentIndex == 5 && currentPlayer == Player.TWO) {
				currentIndex += 2;
			} else if (currentIndex == 12 && currentPlayer == Player.ONE) {
				currentIndex = 0;
			} else {
				currentIndex++;
			}
			if (currentIndex == 14) {
				currentIndex = 0;
			}
			pitList.get(currentIndex).setStones(pitList.get(currentIndex).getStones() + 1);
			numberOfMarbles--;
		}
	}

	private void wonOpponentMarbles(int lastMarbleDropped) {
		int mancala = 6;
		if(lastMarbleDropped == 6 || lastMarbleDropped == 13) return;
		else if (pitList.get(lastMarbleDropped).getStones() == 1 && pitList.get(lastMarbleDropped).getPlayer() == currentPlayer) 
		{
			if(pitList.get(12-lastMarbleDropped).getStones()==0)return;
			if(currentPlayer==Player.ONE)mancala=6;
			else mancala=13;

			pitList.get(mancala).setStones(pitList.get(mancala).getStones() + pitList.get(12 - lastMarbleDropped).getStones());
			pitList.get(mancala).setStones(pitList.get(mancala).getStones() + pitList.get(lastMarbleDropped).getStones());
			pitList.get(12 - lastMarbleDropped).setStones(0);
			pitList.get(lastMarbleDropped).setStones(0);
		} 
	}

	public void choosePit(Pit pit) {
		if(!canUndo)
			countUndo=0;
		if(pit.getPlayer()!=currentPlayer)
			return;
		if(pit.getStones()==0)
			return;
		for (Pit pitt : pitList) {
			previousState[pitt.getIndex()] = pitt.getStones();
		}

		extraTurn = getExtraTurn(pit);
		int lastMarbleDropped = getLastMarble(pit);
		int numberOfMarbles = pit.getStones();
		pit.setStones(0);

		distributeMarbles(pit.getIndex(), numberOfMarbles);
		wonOpponentMarbles(lastMarbleDropped);
		if (gameOver()) 
			clearBoard();
		if (!extraTurn) {
			switchPlayer();
		}
		canUndo=false;
		notifyChangeListeners();
	}
        
	private boolean getExtraTurn(Pit pit) {
		int totalMoves = pit.getIndex() + pit.getStones();
		if ((totalMoves - 6) % 13 == 0 && currentPlayer == Player.ONE) 
		{
			return true;
		}
		if ((totalMoves - 13) % 13 == 0 && currentPlayer == Player.TWO) {
			return true;
		}
		return false;
	}
        
	public boolean gameOver() 
	{
		boolean emptyRow = true;
		for (int i = 0; i < 6; i++) 
		{
			if (pitList.get(i).getStones() != 0) {
				emptyRow = false;
			}
		}
		if (emptyRow) {
			return emptyRow;
		}

		emptyRow = true;

		for (int i = 7; i < 13; i++) {
			if (pitList.get(i).getStones() != 0) {
				emptyRow = false;
			}
		}

		return emptyRow;
	}

	public void undo() 
	{

		if (!canUndo()) 
		{
			System.out.println("You can't undo this turn!");
			return;
		}

		canUndo = true;
		countUndo++;
		
		for (Pit pat : pitList) 
			pat.setStones(previousState[pat.getIndex()]);

		if(!extraTurn)
			switchPlayer();

		notifyChangeListeners();
	}

	public ArrayList<Pit> getData() 
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
		if (currentPlayer == Player.ONE) 
		{
			currentPlayer = Player.TWO;
		} else 
		{
			currentPlayer = Player.ONE;
		}
		extraTurn = false;
	}

	private boolean noPreviousState()
	{
		for(Pit p: pitList)
			if(p.getStones() != previousState[p.getIndex()])
				return false;
		return true;
	}
        
	private boolean canUndo()
	{
		if(noPreviousState())
		{
			System.out.println("You can't undo since this is your first turn!");
			return false;
		} else if(countUndo == 3)
		{
			System.out.println("You have used all 3 attempts of Undo!");
			return false;
		} else if(gameOver())
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

		return gameOver();
	}

	private void clearBoard()
	{
		for (int i = 0; i < 6; i++) 
		{
			pitList.get(6).setStones(pitList.get(6).getStones() + pitList.get(i).getStones());
			pitList.get(i).setStones(0);
		}
		for (int i = 7; i < 13; i++) 
		{
			pitList.get(13).setStones(pitList.get(13).getStones() + pitList.get(i).getStones());
			pitList.get(i).setStones(0);
		}
	}
}