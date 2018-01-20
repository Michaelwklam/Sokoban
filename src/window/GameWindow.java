package window;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import sokoban.Board;
import sokoban.GameEngine;
import sokoban.Sokoban;


/**
 * GameWindow displays the game when it is being played
 * @author SlamDunk
 *
 */
public class GameWindow extends JFrame implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6399530714228077057L;
	private Sokoban sokoban;
	private JPanel boardPanel;
    
	public GameWindow(GameEngine g, Sokoban sokoban) {
		super("ID: "+ g.getMapNum() + " ["+g.getMapDifficulty()+"]");
		this.sokoban = sokoban;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		boardPanel = new JPanel(new GridBagLayout());
		Board board = g.getCurrentBoard();
		int x = board.getRow();
		int y = board.getCol();
		//ImageIcon blackImage = 
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumCols(); j++) {
				GridBagConstraints constraint = new GridBagConstraints();
				constraint.gridx = j;
				constraint.gridy = i;
				Cell cell;
				if (board.isFogActive() && (x-i)*(x-i) + (y-j)*(y-j) >= 8){
					cell = new Cell(board.getFogImage() , i , j);
				}
				else {
					cell = new Cell(board.getImageIcon(i, j), i, j);		
				}
				boardPanel.add(cell, constraint);
			}
		}

		this.add(boardPanel);
		this.addKeyListener(this);
		this.pack();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (sokoban.getGameBop() != null) {
			sokoban.getGameBop().stop();
			sokoban.getGameBop().setFramePosition(0);
			sokoban.getGameBop().start();
		}
		sokoban.handleGameKeys(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * updateGrid refreshes the game everytime user input is detected
	 * @param board - the board stores the images and position of items
	 */
	public void updateGrid(Board board) {
		Component[] components = boardPanel.getComponents();
		int x = board.getRow();
		int y = board.getCol();	
		
		for (int i = 0; i < components.length; i++) {
			Cell myCell = (Cell) components[i];
			ImageIcon newImage;
			int manR = myCell.getGridPositionX();
			int manC = myCell.getGridPositionY();
			
			if (board.isFogActive() && (x-manR)*(x-manR) + (y-manC)*(y-manC) >= 8){
				newImage = board.getFogImage();
			}
			else {
				newImage = board.getImageIcon(manR,manC);		
			}
			//ImageIcon newImage = board.getImageIcon(myCell.getGridPositionX(), myCell.getGridPositionY());
			myCell.setIcon((Icon) newImage);
		}
			

	}
}
