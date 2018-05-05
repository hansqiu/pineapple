import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.ImageIcon;


/** 
 * This class contains the methods pertaining to functionality for the game and 
 * the data for a given configuration and moment of gameplay.
 * 
 */

public class Mancala extends Pit {
    
	/**
     * Mancala class constructor - creates a mancala pit with the given parameters
     * @param n - the number of marbles to initialize the pit with
     * @param index - the location of the mancala
     * @param player - the Player this mancala belongs to
     * @param b - a concrete implementation of BoardStyle determining
     * the shape of the pits to be used in the game
     */
    public Mancala(int theNumOfStone, int theLocation, Player player, GameStyle theGameStyle) {
        super(theNumOfStone, theLocation, player, theGameStyle);
    }
    
    /**
	 *Get the player the pit belongs to
	 *@return the pit's player
	 */
    public Player getPlayer() {
        return super.getPlayer();
    }
    
    /**
	 *Change the number of marbles in the current pit
	 *@param n number of marbles to be set to
	 */
    public void setNumOfStones(int n) {
        super.setNumOfStones(n);
    }
    
    /**
	 *Get the number of marbles in the current pit
	 *@return the pit's marbles
	 */
    public int getNumOfStones() {
        return super.getNumOfStones();
    }
    
    /**
	 *Get the index or location of each pit
	 *@return the location of each pit
	 */
    public int getIndex() {
        return super.getIndex();
    }
    
    /**
	 *Check if the pit is empty or not
	 *@return true if the pit is empty and false otherwise
	 */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
	 *Get the shape of the pit to be drawn
	 *@param b - the board style determining the shape
	 *@return the Shape based on the board style
	 */
  
    public String getPitImagePath(GameStyle b)
	{
		return b.getMancalaPic(getPlayer());
	}
    /**
	 *Get the player the pit belongs to
	 *@param g graphics object used to draw shape
	 */
    public void paintComponent(Graphics g) 
    {   
		ImageIcon i = new ImageIcon(this.getPitImagePath(layout));
		i.paintIcon(this, g, 10, 80);
    }
}