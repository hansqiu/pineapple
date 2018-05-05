import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.*;

public class MancalaTester
{
	static JPanel mainPanel;
	static JButton threeWood;
	static JButton fourWood;
	static JButton threeGalaxy;
	static JButton fourGalaxy;
	static JFrame mainFrame;;

	public static void main(String[] args)
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
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					Board b = new Board(wl);
					BoardView bV=new BoardView(b);
					b.addChangeListener(bV);
					b.fillBoard(3);
				};
			});
			fourWood.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					Board b = new Board(wl);
					BoardView bV=new BoardView(b);
					b.addChangeListener(bV);
					b.fillBoard(4);
				};
			});
			threeGalaxy.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					Board b = new Board(gl);
					BoardView bV=new BoardView(b);
					b.addChangeListener(bV);
					b.fillBoard(3);
				};
			});
			fourGalaxy.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
					Board b = new Board(gl);
					BoardView bV=new BoardView(b);
					b.addChangeListener(bV);
					b.fillBoard(4);
				};
			});
		}
	}
}
