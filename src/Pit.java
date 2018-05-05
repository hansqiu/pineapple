import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JComponent;
import javax.swing.ImageIcon;

/** 
 * This class contains the methods pertaining to functionality for the game and 
 * the data for a given configuration and moment of gameplay.
 * 
 */

public class Pit extends JComponent{
	
	int numOfStone;
	int location;
	Player playar;
	GameStyle layout;
	
	/**
     * Pit class constructor - creates a pit with the given parameters
     * @param n - the number of marbles to initialize the pit with
     * @param index - the location of the mancala
     * @param player - the Player this mancala belongs to
     * @param sty - a concrete implementation of BoardStyle determining
     * the shape of the pits to be used in the game
     */        
	public Pit (int theNumOfStone, int theLocation, Player thePlayer, GameStyle theGameStyle)
	{
		numOfStone = theNumOfStone;
		location = theLocation;
		playar = thePlayer;
		layout = theGameStyle;     
	}
	
	/**
	 *Get the player the pit belongs to
	 *@return the pit's player
	 */
	public Player getPlayer()
	{
		return playar;
	}

	/**
	 *Change the number of marbles in the current pit
	 *@param n number of marbles to be set to
	 */
	public void setNumOfStones(int n)
	{
		numOfStone = n;
	}
	
	/**
	 *Get the number of marbles in the current pit
	 *@return the pit's marbles
	 */
	public int getNumOfStones()
	{
		return numOfStone;
	}
	
	/**
	 *Get the index or location of each pit
	 *@return the location of each pit
	 */
	public int getIndex()
	{
		return location;
	}
	
	/**
	 *Check if the pit is empty or not
	 *@return true if the pit is empty and false otherwise
	 */
	public boolean isEmpty()
	{
		if(numOfStone == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	

	
	/**
	 *Get the shape of the pit to be drawn
	 *@param b - the board style determining the shape
	 *@return the Shape based on the board style
	 */
	public String getPitImagePath(GameStyle s)
	{
		return s.getPitPic();
	}
	
	public String getStoneImagePath(GameStyle s)
	{
		return s.getStonePic();
	}
	
	/**
	 *Get the player the pit belongs to
	 *@param g graphics object used to draw shape
	 */
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
		ImageIcon i = new ImageIcon(this.getPitImagePath(layout));
		i.paintIcon(this, g, 10, 10);
		ImageIcon j = new ImageIcon(this.getStoneImagePath(layout));
		int height = -30;
		for(int k = 0; k < numOfStone; k++)
		{
			j.paintIcon(this, g, 10, height);
			height = height + 15;
		}
	
	}
}