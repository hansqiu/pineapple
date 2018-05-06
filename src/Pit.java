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
		int width, height;
		Random random = new Random();
		for(int k = 0; k < numOfStone; k++)
		{
			width =  random.nextInt(i.getIconWidth()/3);
			height =  random.nextInt(i.getIconHeight()/3);
			j.paintIcon(this, g, width, height);
		}
	
	}
}