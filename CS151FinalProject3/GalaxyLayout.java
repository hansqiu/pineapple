/**
 * A concrete class implementing galaxy theme for BoardLayout interface 
 * @author Team Pineapple
 * CS 151 Spring 2018
 */

public class GalaxyLayout implements BoardLayout 
{
	/**
	 * To return a path of a stone image for Galaxy Theme
	 */
	public String getStonePic() {
		return "image/GalaxyStone.png";
	}

	/**
	 * To return a path of a pit image for Galaxy Theme
	 */
	public String getPitPic() 
	{
		return "image/GalaxyPit.png";
	}

	/**
	 * To return a path of a mancala image for Galaxy Theme
	 */
	public String getMancalaPic(Player p) 
	{
		return "image/GalaxyMancala.png";
	}

}