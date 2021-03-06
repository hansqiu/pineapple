/**
 * Model part of MVC architecture for Mancala Project 
 * @author Team Pineapple
 * CS 151 Spring 2018
 */

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameModel 
{
	private Player currentPlayer;
	private ArrayList<Pit> pitList; 
	private ArrayList<ChangeListener> listeners;
	private boolean extraTurn;	
	private boolean canUndo;
	private int numOfUndo;
	private int[] previousNumOfStone;

	/*
	 * Constructor of GameModel Object that initializes the instance variables that will be used throughout the program
	 * GameModel object will use a GameStyle object which will have the info about the style users have chosen as an input.
	 * @param theGameStyle the style of the game that will be used throughout the mancala game
	 */
    public GameModel(GameStyle theGameStyle) 
    {
    	currentPlayer = Player.A;
    	pitList = new ArrayList<Pit>();
		
    	for (int i = 0; i < 6; i++) 
		{
			pitList.add(new Pit(0, i, Player.A, theGameStyle));
		}
		pitList.add(new Mancala(0, 6, Player.A, theGameStyle));
		
		for (int i = 7; i < 13; i++) 
		{
			pitList.add(new Pit(0, i, Player.B, theGameStyle));
		}
		pitList.add(new Mancala(0, 13, Player.B, theGameStyle));

		listeners = new ArrayList<ChangeListener>();		
		extraTurn = false;		
		canUndo = false;
		numOfUndo = 0;
		previousNumOfStone = new int[14];
	}
        
    /*
     * To initialize pits with the chosen number of stones
     * @param stones is the number of stones that will first occupy each pit
     */
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
	
	/**
	* Finds the pit that the player chooese for their turn 
	* @param pit where the players turn will start
	*/
	public void choosePit(Pit pit) 
	{
		if(!canUndo)
			numOfUndo=0;
		
		if(pit.getPlayer() != currentPlayer)
			return;
		
		if(pit.getNumOfStones() == 0)
			return;
		
		for (Pit p : pitList) 
		{
			previousNumOfStone[p.getIndex()] = p.getNumOfStones();
		}

		extraTurn = getExtraTurn(pit);
		int lastStoneDropped = getLastStone(pit);
		int numberOfStones = pit.getNumOfStones();
		pit.setNumOfStones(0);

		distributeStones(pit.getIndex(), numberOfStones);
		wonOpponentStones(lastStoneDropped);
		
		if (!extraTurn) 
			switchPlayer();
		
		if (gameIsOver()) 
			clearBoard();
	
		canUndo = false;
		notifyChangeListeners();
	}
	
	/**
	* Moves the stones into each next pit, decrementing by one with each step,skipping the opponents mancala
	* @param startIndex is the pit where the players turn starts
	* @param stones is the number of stones in the pit
	*/
	public void distributeStones(int startIndex, int stones) 
	{
		int numberOfStones = stones;
		int currentIndex = startIndex;

		while (numberOfStones > 0) 
		{
			if (currentIndex == 5 && currentPlayer == Player.B) 
			{
				currentIndex += 2;
			} else if (currentIndex == 12 && currentPlayer == Player.A) 
			{
				currentIndex = 0;
			} else 
			{
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
        /**
	* Gets the last stone from the turn
	* @param pit is the pit where the last stone is placed
	* @return currentIndex is the index where the last stone is put
	*/
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
    
	/**
	* Checks the pit where the last stone is placed in a turn, if it's in the own mancala, get an extra turn
	* @param pit is the ending pit of a turn
	*/
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
        
	/**
	* Checks if the last stone dropped is in an empty pit on players own side, with stones in the opposing pit
	* @param lastStoneDropped is the index of the last stone dropped in a turn
	*/
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
	
	/**
	* Checks if the game is over by checking if there are any empty rows on the board
	* @return emptyRow is a boolean that returns false if there are no empty rows
	*/
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
	
	/**
	* Gets the arraylist with the pits
	* @return pitList is the list of pits in the game
	*/
	public ArrayList<Pit> getPitList() 
	{
		return pitList;
	}

	/**
	* Gets the mancala of the player whose turn it is
	* @param player is the player whose turn it is
	*/
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
	
	/**
	* Switches the players turn every time there is no extra turn
	*/
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

	/**
	* checks if it's the first turn to check if there is an undo available
	*/
	private boolean noPreviousState()
	{
		for(Pit p: pitList)
			if(p.getNumOfStones() != previousNumOfStone[p.getIndex()])
				return false;
		return true;
	}
       
	/**
	* Restores the previous state if undo is allowed
	*/
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

	 /**
	 * Checks if player is able to undo by checking if it's the first turn, if they've used all of their undos for the game, or 
	 * if the game is over -- let's player know if they can't
	 */
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
	
	/**
	* Used to notify the change listeners
	*/
	public void notifyChangeListeners()
	{
		for (int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	* attaches change listener
	* @param is the listener is attached to the arraylist of changelisteners
	*/
	public void attach(ChangeListener listener) 
	{
		listeners.add(listener);
	}
        
	/**
	* Gets the current player
	* @return currentPlayer is the current turn
	*/
	public Player getPlayer() 
	{
		return currentPlayer;
	}
     
	/**
	* Checks if the game is over
	* @return the result from the gameIsOver method
	*/
	public boolean getGameStatus() 
	{

		return gameIsOver();
	}

	/**
	* Clears the board when the game is over
	*/
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
