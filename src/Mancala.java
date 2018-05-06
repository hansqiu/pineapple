import java.awt.Graphics;

import javax.swing.ImageIcon;


public class Mancala extends Pit 
{
    public Mancala(int theNumOfStone, int theLocation, Player currentPlayer, GameStyle theGameStyle) 
    {
        super(theNumOfStone, theLocation, currentPlayer, theGameStyle);
    }
    
    public void setNumOfStones(int n) 
    {
        super.setNumOfStones(n);
    }
    
    public int getNumOfStones() 
    {
        return super.getNumOfStones();
    }
    
    public Player getPlayer() 
    {
        return super.getPlayer();
    }
    
    public int getIndex() 
    {
        return super.getIndex();
    }
    
    public boolean isEmpty() 
    {
        return super.isEmpty();
    }
  
    public String getPitImagePath(GameStyle b)
	{
		return b.getMancalaPic(getPlayer());
	}
   
    public void paintComponent(Graphics g) 
    {   
		ImageIcon i = new ImageIcon(this.getPitImagePath(layout));
		i.paintIcon(this, g, 10, 80);
    }
}