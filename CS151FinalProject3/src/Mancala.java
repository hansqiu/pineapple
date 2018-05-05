import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.ImageIcon;


public class Mancala extends Pit 
{
    public Mancala(int n, int index, Player currentPlayer, BoardLayout b) 
    {
        super(n, index, currentPlayer, b);
    }
    
    public Player getPlayer() 
    {
        return super.getPlayer();
    }
    
    public void setStones(int n) 
    {
        super.setStones(n);
    }
    
    public int getStones() 
    {
        return super.getStones();
    }
    
    public int getIndex() 
    {
        return super.getIndex();
    }
    
    public boolean isEmpty() 
    {
        return super.isEmpty();
    }

    public String getPitImagePath(BoardLayout b)
	{
		return b.getMancalaPic(getPlayer());
	}

    public void paintComponent(Graphics g) 
    {   
		ImageIcon i = new ImageIcon(this.getPitImagePath(layout));
		i.paintIcon(this, g, 10, 80);
    }
}