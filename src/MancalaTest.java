import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.*;

public class MancalaTest
{
	static JPanel mainPanel;
	static JButton threeWood;
	static JButton fourWood;
	static JButton threeGalaxy;
	static JButton fourGalaxy;
	static JFrame mainFrame;

	public static void main(String[] args)
	{

		mainFrame = new JFrame("CS 151 Team Pineapple Spring 2018");

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,2,2,2));

		threeWood = new JButton("Wood Theme with 3 stones");
		fourWood = new JButton("Wood Theme with 4 stones");


		threeGalaxy = new JButton("Galaxy Theme with 3 stones");
		fourGalaxy = new JButton("Galaxy Theme with 4 stones");

		mainPanel.add(threeWood);
		mainPanel.add(fourWood);
		mainPanel.add(threeGalaxy);
		mainPanel.add(fourGalaxy);

		mainFrame.add(mainPanel);
		mainFrame.setSize(400, 400);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

		threeWood.addActionListener(new chooseOne());
		fourWood.addActionListener(new chooseOne());
		threeGalaxy.addActionListener(new chooseOne());
		fourGalaxy.addActionListener(new chooseOne());
	}
	
	static class chooseOne implements ActionListener
	{

		public void actionPerformed (ActionEvent e)
		{
			mainFrame.setVisible(false);
			ForestStyle wl = new ForestStyle();
			GalaxyStyle gl = new GalaxyStyle();
			threeWood.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					GameModel b = new GameModel(wl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.initialize(3);
				};
			});
			fourWood.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					GameModel b = new GameModel(wl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.initialize(4);
				};
			});
			threeGalaxy.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					GameModel b = new GameModel(gl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.initialize(3);
				};
			});
			fourGalaxy.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					GameModel b = new GameModel(gl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.initialize(4);
				};
			});
		}
	}
}
