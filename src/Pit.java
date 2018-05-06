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
	       
	public Pit (int theNumOfStone, int theLocation, Player currentPlayer, GameStyle theGameStyle)
	{
		numOfStone = theNumOfStone;
		location = theLocation;
		player = currentPlayer;
		layout = theGameStyle;     
	}
	
	public void setNumOfStones(int n)
	{
		numOfStone = n;
	}
	
	public int getNumOfStones()
	{
		return numOfStone;
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public int getIndex()
	{
		return location;
	}
	
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
	
	public String getPitImagePath(GameStyle s)
	{
		return s.getPitPic();
	}
	
	public String getStoneImagePath(GameStyle s)
	{
		return s.getStonePic();
	}
	
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