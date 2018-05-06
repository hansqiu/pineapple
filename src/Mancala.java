/**
 * A class regarding mancala of Mancala Board Game
 * @author Team Pineapple
 * CS 151 Spring 2018
 */

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Mancala extends Pit 
{
    /**
    * Constructor that initializes Mancala object with the number of stones,
    * the index of the pit, the player whose turn it is, and the style of the boardgame
    * @param theNumOfStone is the number of stones in each pit
    * @param theLocation is the index
    * @param currentPlayer is the player whose turn it is
    * @param theGameStyle is the style of the game
    */
    public Mancala(int theNumOfStone, int theLocation, Player currentPlayer, GameStyle theGameStyle) 
    {
        super(theNumOfStone, theLocation, currentPlayer, theGameStyle);
    }
    
    /**
    * Sets the number of stones in each pit
    * @param n is the number of stones to set to
    */
    public void setNumOfStones(int n) 
    {
        super.setNumOfStones(n);
    }
    
    /**
    * Gets the number of stones 
    * @return the number of stones
    */
    public int getNumOfStones() 
    {
        return super.getNumOfStones();
    }
    
    /**
    * gets the current player
    * @return the player whose turn it is
    */
    public Player getPlayer() 
    {
        return super.getPlayer();
    }
    
    /**
    * Gets the index of the pits
    * @return the index of the pits from the Pit class
    */
    public int getIndex() 
    {
        return super.getIndex();
    }
    
    /**
    * Checks if the pit is empty
    * Returns the isEmpty from the Pit class
    */
    public boolean isEmpty() 
    {
        return super.isEmpty();
    }
  
    /** 
    * Gets the image path of the pit
    * @param b is gamestyle that has been chosen
    * @return the gamestyle image path
    */
    public String getPitImagePath(GameStyle b)
	{
		return b.getMancalaPic(getPlayer());
	}
   
   /**
   * Paints the images for the game style
   * @param g is the object getting drawn
   */
    public void paintComponent(Graphics g) 
    {   
		ImageIcon i = new ImageIcon(this.getPitImagePath(layout));
		i.paintIcon(this, g, 10, 60);
		ImageIcon j = new ImageIcon(this.getStoneImagePath(layout));
		int height = 60;
		for(int k = 0; k < numOfStone; k++)
		{
			j.paintIcon(this, g, 10, height);
			height = height + 15;
		}
    }
}
