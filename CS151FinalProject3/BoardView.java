import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class BoardView implements ChangeListener 
{
	private final Board board;
	private ArrayList<Pit> pits;
	final JTextField playerTurn;
	
	public BoardView(Board b) 
	{
		board = b;
		pits = b.getData();
		JFrame frame = new JFrame("Mancala");
		frame.setSize(1200, 600);
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				board.undo();
			}
		});
		final  JPanel grid= new JPanel(new GridLayout(0,8));
		grid.add(b.getMancala(Player.TWO));
		for(int i=0;i<6;i++)
		{
			JPanel ingrid = new JPanel(new GridLayout(2,0));
			final Pit toppit = pits.get(12-i);
			final Pit botpit = pits.get(i);
			ingrid.add(toppit);
			ingrid.add(botpit);
			toppit.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					board.choosePit(toppit);
				}
			});
			botpit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					board.choosePit(botpit);
				}
			});
			grid.add(ingrid);
		}
		grid.add(b.getMancala(Player.ONE));
		frame.add(undoButton, BorderLayout.NORTH);
		frame.add(grid, BorderLayout.CENTER);
		playerTurn=new JTextField("Now is player " + board.getPlayer().toString() + "'s turn");
		playerTurn.setHorizontalAlignment(JTextField.CENTER);
		frame.add(playerTurn,BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void stateChanged(ChangeEvent e) 
	{
		pits = board.getData();
		for (Pit p:pits)
		{
			p.repaint();
		}
		
		playerTurn.setText("Now is player " + board.getPlayer().toString() + "'s turn");
		
		if(board.gameOver())
		{
			String score = "Final score: Player One "+pits.get(6).getStones();
			score += ", Player Two "+pits.get(13).getStones()+ ". ";
			if(pits.get(6).getStones()>pits.get(13).getStones())
				playerTurn.setText(score + "Player One Wins!");
			else if(pits.get(6).getStones()<pits.get(13).getStones())
				playerTurn.setText(score + "Player Two Wins!");
			else
				playerTurn.setText(score + "Draw!");
		}
	}
}