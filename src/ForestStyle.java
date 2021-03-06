/**
 * A concrete class implementing wood theme for BoardLayout interface 
 * @author Team Pineapple
 * CS 151 Spring 2018
 */

public class ForestStyle implements GameStyle 
{
	/**
	 * To return a path of a stone image for Forest Theme
	 */
	public String getStonePic() 
	{
		return "image/WoodStone.png";
	}

	/**
	 * To return a path of a pit image for Forest Theme
	 */
	public String getPitPic() 
	{
		return "image/WoodPit.png";
	}

	/**
	 * To return a path of a mancala image for Forest Theme
	 */
	public String getMancalaPic(Player p) 
	{
		return "image/WoodMancala.png";
	}

}
