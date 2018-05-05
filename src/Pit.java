import java.awt.*;
import javax.swing.JComponent;

/** 
 * This class contains the methods pertaining to functionality for the game and 
 * the data for a given configuration and moment of gameplay.
 * 
 */

public class Pit extends JComponent{
	
	int stones;
	int index;
	Player player;
	BoardStyle style;
	
	/**
     * Pit class constructor - creates a pit with the given parameters
     * @param n - the number of stones to initialize the pit with
     * @param pit - the index of the mancala
     * @param player - the Player this mancala belongs to
     * @param style - a concrete implementation of BoardStyle determining
     * the shape of the pits to be used in the game
     */        
	public Pit (int n, int pit, Player play, BoardStyle aStyle)
	{
		stones = n;
		index = pit;
		player = play;
		style = aStyle;     
	}
	
	/**
	 *Get the player the pit belongs to
	 *@return the pit's player
	 */
	public Player getPlayer()
	{
		return player;
	}

	/**
	 *Change the number of stones in the current pit
	 *@param n number of stones to be set to
	 */
	public void setStones(int n)
	{
		stones = n;
	}
	
	/**
	 *Get the number of stones in the current pit
	 *@return the pit's stones
	 */
	public int getStones()
	{
		return stones;
	}
	
	/**
	 *Check if the pit is empty or not
	 *@return true if the pit is empty and false otherwise
	 */
	public boolean isEmpty()
	{
		if(getStones() == 0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 *Get the pit or index of each pit
	 *@return the index of each pit
	 */
	public int getIndex()
	{
		return index;
	}
	
	/**
	 *Get the style of the board, i.e. circles or rectangles
	 *@return the style of the board
	 */
	public BoardStyle getStyle()
	{
            return style;
    }
	
	/**
	 *Get the shape of the pit to be drawn
	 *@param b - the board style determining the shape
	 *@return the Shape based on the board style
	 */
	public Shape drawPit(BoardStyle b)
	{
		return b.getPit();
	}
	
	/**
	 *Get the player the pit belongs to
	 *@param g graphics object used to draw shape
	 */
	public void paintComponent(Graphics g)
	{
        super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.draw(this.drawPit(style));	
		int shapeHeight=this.drawPit(style).getBounds().height;
		int shapeWidth=this.drawPit(style).getBounds().width;
		int col = shapeWidth/2-5;
		int y = 0;
		int row = shapeHeight/2-5;
		int x = 0;
		for(int i = 0; i< getStones(); i++)
		{
			
			if( y< shapeHeight)
			{
				g2.drawOval(col,y, 10,10);
				y = y + 10;				
			}
			else{
			g2.drawOval(x,row, 10,10);
			x = x + 10;
			}
		}
	}
}
