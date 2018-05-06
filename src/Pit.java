/**
 * A class regarding pit of Mancala Board Game
 * @author Team Pineapple
 * CS 151 Spring 2018
 */

import java.awt.Graphics;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.ImageIcon;

public class Pit extends JComponent
{
	int numOfStone;
	int location;
	Player player;
	GameStyle layout;
	     
	/**
	* constructor that initializes instance variables 
	* @param theNumbOfStones is the number of stones in the pit
	* @param theLocation is the index of the the current pit
	* @param currentPlayer is the player whose turn it is
	* @param theGameStyle the style of the game
	*/
	public Pit (int theNumOfStone, int theLocation, Player currentPlayer, GameStyle theGameStyle)
	{
		numOfStone = theNumOfStone;
		location = theLocation;
		player = currentPlayer;
		layout = theGameStyle;     
	}
	
	/**
	* Sets the number of stones in each pit
	* @param n is the number of stones that will be in each pit
	*/
	public void setNumOfStones(int n)
	{
		numOfStone = n;
	}
	
	/**
	* Gets the number of stones in the pit
	* @return numOfStone the number of stones in the pit
	*/
	public int getNumOfStones()
	{
		return numOfStone;
	}
	
	/**
	* Gets the player currently taking a turn
	* @return player currently taking turn
	*/
	public Player getPlayer()
	{
		return player;
	}

	/**
	* Gets the index of the current pit
	* @return location is the index of the current pit
	*/
	public int getIndex()
	{
		return location;
	}
	
	/**
	* Checks if the pit is empty
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
	* Gets the path of the pit image for the style
	* @param s is the style of the pits on the gameboard
	*/
	public String getPitImagePath(GameStyle s)
	{
		return s.getPitPic();
	}
	
	/**
	* Gets the path of the stone image for the style
	* @param s is the style of the stones on the gameboard
	*/
	public String getStoneImagePath(GameStyle s)
	{
		return s.getStonePic();
	}
	
	/**
	* Paints the stones and pits onto the gameboard
	* @param g is the object getting drawn
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
