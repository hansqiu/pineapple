import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JComponent;
import javax.swing.ImageIcon;


public class Pit extends JComponent
{
	int stones;
	int location;
	Player player;
	BoardLayout layout;
	      
	public Pit (int n, int index, Player currentPlayer, BoardLayout b)
	{
		stones = n;
		location = index;
		player = currentPlayer;
		layout = b;     
	}
	
	public void setStones(int n)
	{
		stones = n;
	}
	
	public int getStones()
	{
		return stones;
	}
	
	public int getIndex()
	{
		return location;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public boolean isEmpty()
	{
		if(stones == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String getPitImagePath(BoardLayout b)
	{
		return b.getPitPic();
	}
	
	public String getStoneImagePath(BoardLayout b)
	{
		return b.getStonePic();
	}
	
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
		ImageIcon i = new ImageIcon(this.getPitImagePath(layout));
		i.paintIcon(this, g, 10, 10);
		ImageIcon j = new ImageIcon(this.getStoneImagePath(layout));
		int height = -30;
		for(int k = 0; k < stones; k++)
		{
			j.paintIcon(this, g, 10, height);
			height = height + 15;
		}
	
	}
}