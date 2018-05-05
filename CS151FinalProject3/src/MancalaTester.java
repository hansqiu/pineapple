import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;

public class MancalaTester
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
		mainPanel.setLayout(new GridLayout(2,2,10,10));

		Border raisedBevel = BorderFactory.createRaisedBevelBorder();
		Border loweredBevel = BorderFactory.createLoweredBevelBorder();
		Border compoundOne = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
		
		threeWood = new JButton("Wood Theme with 3 stones");
		threeWood.setBorder(compoundOne);
		threeWood.setBackground(Color.yellow);
		fourWood = new JButton("Wood Theme with 4 stones");
		fourWood.setBorder(compoundOne);
		fourWood.setBackground(Color.yellow);
		

		Border compoundTwo = BorderFactory.createCompoundBorder(loweredBevel, raisedBevel);
		threeGalaxy = new JButton("Galaxy Theme with 3 stones");
		threeGalaxy.setBorder(compoundTwo);
		threeGalaxy.setBackground(Color.DARK_GRAY);
		fourGalaxy = new JButton("Galaxy Theme with 4 stones");
		fourGalaxy.setBorder(compoundTwo);
		fourGalaxy.setBackground(Color.DARK_GRAY);

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
			WoodLayout wl = new WoodLayout();
			GalaxyLayout gl = new GalaxyLayout();
			threeWood.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); 
					Board b = new Board(wl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.setBoard(3);
				};
			});
			fourWood.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); 
					Board b = new Board(wl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.setBoard(4);
				};
			});
			threeGalaxy.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e);
					Board b = new Board(gl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.setBoard(3);
				};
			});
			fourGalaxy.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); 
					Board b = new Board(gl);
					BoardView bV=new BoardView(b);
					b.attach(bV);
					b.setBoard(4);
				};
			});
		}
	}
}
