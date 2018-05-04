import java.awt.GridLayout;
import javax.swing.*;

public class MainMenu 
{
	public MainMenu()
	{
		JFrame mainFrame = new JFrame("CS 151 Team Pineapple Spring 2018");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,2,2,2));
		
		JButton threeWood = new JButton("Wood Theme with 3 stones");
		JButton fourWood = new JButton("Wood Theme with 4 stones");
		
		
		JButton threeGalaxy = new JButton("Galaxy Theme with 3 stones");
		JButton fourGalaxy = new JButton("Galaxy Theme with 4 stones");
		
		mainPanel.add(threeWood);
		mainPanel.add(fourWood);
		mainPanel.add(threeGalaxy);
		mainPanel.add(fourGalaxy);
		
		mainFrame.add(mainPanel);
		mainFrame.setSize(400, 400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		
	}

}
