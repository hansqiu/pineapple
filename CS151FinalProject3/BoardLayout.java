/**
 * The Strategy interface to change the appearance of stones, pits and mancala 
 * @author Team Pineapple
 * CS 151 Spring 2018
 */

public interface BoardLayout 
{
	public String getStonePic();
	public String getPitPic();
	public String getMancalaPic(Player p);
}