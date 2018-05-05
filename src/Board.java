import java.util.ArrayList;
import javax.swing.event.*;

/** 
 * This class contains the methods pertaining to functionality for the game and 
 * the data for a given configuration and moment of gameplay.
 * 
 */
public class Board {

	private Player p;

	private boolean turn;
	private boolean freeTurn;
	private boolean changeBack;

	private CircularList<Pit> pits; 
	private ArrayList<ChangeListener> listeners;

	private int undoCounter;
	private int[] previousTurn;

	private final int MAX_UNDOS = 3;

	/**
	 * Board class constructor - creates a starting board initializing 
	 * empty "pits" or containers with no marbles.
	 * @param aBoardStyle - a concrete implementation of BoardStyle determining
	 * the shape of the pits to be used in the game
	 */
	public Board(BoardStyle aBoardStyle) {
		changeBack = false;
		turn = false;
		undoCounter=0;
		previousTurn = new int[14];
		p = Player.A;
		freeTurn = false;
		pits = new CircularList<Pit>();
		for (int i = 0; i < 6; i++) {
			pits.add(new Pit(0, i, Player.A, aBoardStyle));
		}
		pits.add(new Mancala(0, 6, Player.A, aBoardStyle));
		for (int i = 7; i < 13; i++) {
			pits.add(new Pit(0, i, Player.B, aBoardStyle));
		}
		pits.add(new Mancala(0, 13, Player.B, aBoardStyle));

		listeners = new ArrayList<ChangeListener>();
	}

	/**
	 * Populates board with given marbles for new game
	 * @param marbles - the number of marbles per pit.
	 */

	public void gameSetup(int marbles) {
		int counter = 0;
		for (Pit p : pits) {
			if (!(p instanceof Mancala)) {
				p.setStones(marbles);
				previousTurn[counter] = marbles;
			}  
			else {
				previousTurn[counter] = 0;
				counter++;
			}
		}
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * Attaches listeners to the collection of listeners for this model
	 * @param listener - ChangeListeners to be notified of changes to the model
	 */
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Returns the current player.
	 * @return - a player, Player.A or Player.B
	 */
	public Player getPlayer() {
		return p;
	}

	public boolean validateMove(int pitIndex){
		if(pitIndex < 6 && turn == false){
			return true;
		}

		if(pitIndex > 6 && turn == true){
			return true;
		}
		return false;
	}

	/**
	 * Distributes marbles of the pit chosen by the player around the pits 
	 * on the board beginning with the pit after the chosen pit.
	 * @param startIndex - the starting index to begin distributing marbles
	 * @param marbles - the number of marbles to distribute
	 */
	public void moveStones(int startIndex, int marbles) {
		int numStones = marbles;
		int currentIndex = startIndex;

		while (numStones > 0) {
			if(validateMove(startIndex)){
				if (currentIndex == 5 && p == Player.B) {
					currentIndex += 2;
				} else if (currentIndex == 12 && p == Player.A) {
					currentIndex = 0;
				} else {
					currentIndex++;
				}
				if (currentIndex == 14) {
					currentIndex = 0;
				}
				pits.get(currentIndex).setStones(pits.get(currentIndex).getStones() + 1);
				numStones--;
			}
		}
	}

	/**
	 * Determines the position of the pit of the last marble dropped on the 
	 * board for the selected pit.
	 * @param pit - a pit object containing the count of marbles to distribute
	 * @return the pit index in the board's array of pits that the last marble
	 * lands in
	 */
	public int getLastMarble(Pit pit) {
		int numStones = pit.getStones();
		int currentIndex = pit.getIndex();
		while (numStones > 0) {
			if (currentIndex == 5 && p == Player.B) {
				currentIndex += 2;
			} else if (currentIndex == 12 && p == Player.A) {
				currentIndex += 2;
			} else {
				currentIndex++;
			}
			if (currentIndex == 14) {
				currentIndex = 0;
			}
			numStones--;
		}
		return currentIndex;
	}

	/**
	 * Checks to see if conditions have been met where the player wins 
	 * marbles from the other player depending on the location 
	 * where the last pit dropped on the board. The last pit must be empty 
	 * and on the current player's side.
	 * @param lastStone - the index of the pit where the last marble
	 * dropped.
	 */
	private void takeOppositeStones(int lastStone) {
		int mancala=6;
		if(lastStone==6 || lastStone==13)return;
		else if (pits.get(lastStone).getStones() == 1 && pits.get(lastStone).getPlayer() == p) {
			if(pits.get(12-lastStone).getStones()==0)return;
			if(p==Player.A)mancala=6;
			else mancala=13;

			pits.get(mancala).setStones(pits.get(mancala).getStones() + pits.get(12 - lastStone).getStones());
			pits.get(mancala).setStones(pits.get(mancala).getStones() + pits.get(lastStone).getStones());
			pits.get(12 - lastStone).setStones(0);
			pits.get(lastStone).setStones(0);
		} 
	}

	/**
	 * Begins the sequence of actions after a player chooses a particular
	 * pit on the board.
	 * @param pit - the chosen pit by the player.
	 */
	public void choosePit(Pit pit) {
		if(!changeBack)
			undoCounter=0;
		if(pit.getPlayer()!=p)
			return;
		if(pit.getStones()==0)
			return;
		for (Pit pitt : pits) {
			previousTurn[pitt.getIndex()] = pitt.getStones();
		}

		freeTurn = getFreeTurn(pit);
		int lastStone = getLastMarble(pit);
		int numStones = pit.getStones();
		pit.setStones(0);

		moveStones(pit.getIndex(), numStones);
		takeOppositeStones(lastStone);
		if (endGame()) 

			if (!freeTurn) {
				switchPlayer();
			}
		changeBack=false;
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * Executes a check to see if the current player gets an extra turn
	 * depending on the condition where the last marble dropped from the 
	 * chosen pit by the player is the player's mancala.
	 * @param pit - the pit chosen by the player
	 * @return - a boolean determining whether or not the player receives
	 * an extra turn
	 */
	private boolean getFreeTurn(Pit pit) {
		int totalMoves = pit.getIndex() + pit.getStones();
		if ((totalMoves - 6) % 13 == 0 && p == Player.A) 
		{
			return true;
		}
		if ((totalMoves - 13) % 13 == 0 && p == Player.B) {
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if conditions have been met to end the game -- the game
	 * ends if any player's set of pits are empty.
	 * @return a boolean determining if game end conditions are satisfied
	 */
	public boolean endGame() {
		boolean emptyRow = true;
		for (int i = 0; i < 6; i++) {
			if (pits.get(i).getStones() != 0) {
				emptyRow = false;
			}
		}
		if (emptyRow) {
			return emptyRow;
		}

		emptyRow = true;

		for (int i = 7; i < 13; i++) {
			if (pits.get(i).getStones() != 0) {
				emptyRow = false;
			}
		}

		return emptyRow;
	}

	/**
	 * Checks if an undo action is valid and performs an undo function 
	 * where the previous state of the gameplay is restored.
	 */
	public void undo() {

		if (!validateUndo()) {
			System.out.println("You are not allowed to Undo");
			return;
		}

		changeBack = true;

		undoCounter++;
		for (Pit p : pits) 
			p.setStones(previousTurn[p.getIndex()]);

		if(!freeTurn)
			switchPlayer();


		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * Gets the ArrayList containing the pits.
	 * @return an arraylist of Pits.
	 */
	public CircularList<Pit> getData() {
		return pits;
	}

	/**
	 * Gets the mancala of a given player
	 * @param player - the player to whom the mancala belongs
	 * @return - the mancala object belonging to the player.
	 */
	public Mancala getMancala(Player player) {
		for (Pit p : pits) {
			if (p.getPlayer() == player && p instanceof Mancala) {
				return (Mancala) p;
			}
		}
		return null;
	}

	/**
	 * Switches the player status between Player.A and Player.B
	 */
	private void switchPlayer() {
		if (p == Player.A) {
			p = Player.B;
		} 
		else {
			p = Player.A;
		}
		freeTurn = false;
	}

	/**
	 * Checks to see if a previous state exists for the current snapshot
	 * of the distribution of marbles
	 * @return a boolean determining if the previous state exists.
	 */
	private boolean checkPreviousTurn(){
		for(Pit p: pits)
			if(p.getStones()!=previousTurn[p.getIndex()])
				return false;
		return true;
	}

	/**
	 * Checks the allowable conditions for a player to undo an action.
	 * @return a boolean, true if the player can undo, false if not.
	 */
	private boolean validateUndo(){

		if(checkPreviousTurn()){
			System.out.println("no previous state");
			return false;
		}

		if(undoCounter == MAX_UNDOS){
			System.out.println("you are not allowed any more attempts");
			return false;
		}

		return true;

	}

	/**
	 * Evaluates and returns the game status
	 * @return - a boolean to signal the end of the game.
	 */
	public boolean getGameStatus() {

		return endGame();
	}
}
