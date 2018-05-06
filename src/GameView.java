/**
 * View part of MVC architecture for Mancala Project 
 * @author Team Pineapple
 * CS 151 Spring 2018
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GameView implements ChangeListener 
{
	private final GameModel board;
	private ArrayList<Pit> pits;
	final JTextField playerTurn;
	
	/**
	* Draws the frame of the mancala game, with undo button, colors, pits
	* and adds the actionlistener to the board
	* @param b is the gameboard that holds the game info
	*/
	public GameView(GameModel b) 
	{
		board = b;
		pits = b.getPitList();
		JFrame frame = new JFrame("CS 151 Team Pineapple Spring 2018 Mancala Game");
		frame.setSize(1000, 500);
		JButton undoButton = new JButton("Undo");
		undoButton.setPreferredSize(new Dimension(30,40));
		undoButton.setBorder(new LineBorder(Color.red, 4));
		undoButton.addActionListener(new ActionListener() 
		{
			/**
			* @param e is the event being performed
			* performs the undo when the button is clicked
			*/
			public void actionPerformed(ActionEvent e) 
			{
				board.undo();
			}
		});
		
		final  JPanel panel = new JPanel(new GridLayout(0,8));
		panel.setBackground(Color.GREEN);
		panel.add(b.getMancala(Player.B));
		for(int i=0; i<6; i++)
		{
			JPanel pitGrid = new JPanel(new GridLayout(2,0));
			pitGrid.setBackground(Color.PINK);
			final Pit upperPit = pits.get(12-i);
			final Pit lowerPit = pits.get(i);
			pitGrid.add(upperPit);
			pitGrid.add(lowerPit);
			upperPit.addMouseListener(new MouseAdapter() 
			{
				/**
				* @param e is the mouse event
				* this checks if the mouse clicks on the pits of player B
				*/
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); 
					board.choosePit(upperPit);
				}
			});
			lowerPit.addMouseListener(new MouseAdapter() 
			{
				/**
				* @param e is sthe mouse event
				* checks if mouse clicks on pits of player A
				*/
				public void mouseClicked(MouseEvent e) 
				{
					super.mouseClicked(e); 
					board.choosePit(lowerPit);
				}
			});
			panel.add(pitGrid);
		}
		panel.add(b.getMancala(Player.A));
		
		playerTurn=new JTextField("Now is player " + board.getPlayer().toString() + "'s turn");
		playerTurn.setHorizontalAlignment(JTextField.CENTER);
		frame.add(playerTurn,BorderLayout.NORTH);
		
		frame.add(undoButton, BorderLayout.SOUTH);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	* @param e is the change event
	* shows the changed state in the player turn, the number of stones in each pit, and the end result of the game
	*/
	public void stateChanged(ChangeEvent e) 
	{
		pits=board.getPitList();
		
		for (Pit p:pits){
			p.repaint();
		}
		
		playerTurn.setText("Now is player " + board.getPlayer().toString() + "'s turn");
		
		if(board.gameIsOver())
		{
			String score = "Final score: Player A "+pits.get(6).getNumOfStones();
			score += ", Player B " + pits.get(13).getNumOfStones() + ". ";
			if(pits.get(6).getNumOfStones() > pits.get(13).getNumOfStones())
				playerTurn.setText(score+"Player A Wins!");
			else if(pits.get(6).getNumOfStones() < pits.get(13).getNumOfStones())
				playerTurn.setText(score+"Player B Wins!");
			else
				playerTurn.setText(score+"It's a tie!");
		}
	}
}
