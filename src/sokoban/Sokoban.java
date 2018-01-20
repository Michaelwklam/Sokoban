package sokoban;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

import window.FinishedWindow;
import window.GameMenuWindow;
import window.GameWindow;
import window.HighScoreWindow;
import window.InstructionWindow;
import window.StartMenuWindow;


/**
 * @author slamDunk
 * Sokoban runs the game merges the front end and backend, by giving the user input to the backend
 * 
 */

public class Sokoban {

	private GameEngine gameEngine;
	private SoundEngine soundEngine;
	private StartMenuWindow menuDisplay;
	private GameWindow gameDisplay;
	private GameMenuWindow pauseDisplay;
	private InstructionWindow instructionDisplay;
	private FinishedWindow finishedDisplay;
	private HighScoreWindow highScoreDisplay;

	
	/**
	 * Starts a new Sokoban class
	 */
	public Sokoban() {
		this.soundEngine = new SoundEngine();
		this.menuDisplay = new StartMenuWindow(this);
		this.menuDisplay.setVisible(true);
	}

	/**
	 * @return
	 * Returns the gameScore ie the moves taken 
	 */
	public int getGameScore() {
		return gameEngine.getMovesTaken();
	}
	
	/**
	 * Checks if the game is complete
	 */
	private void checkGameComplete() {
		if (gameEngine.isGameComplete()) {
			gameDisplay.setVisible(false);
			finishedDisplay = new FinishedWindow(this);
			finishedDisplay.display();
			// store his score to scores
			String userName= JOptionPane.showInputDialog(finishedDisplay, "Save score as", "Enter your name", JOptionPane.INFORMATION_MESSAGE);
			if(userName != null && !userName.isEmpty())
				this.saveScore(userName);
		}
	}

	/**
	 * Saves the score to a file
	 * @param userName
	 * Takes the user name to write to the file with the score
	 */
	private void saveScore(String userName) {
		String dir = System.getProperty("user.dir") + java.io.File.separator + "Scores" + java.io.File.separator;
		String fname = dir + "Map" + gameEngine.getMapNum();
		try
		{
		    FileWriter fw = new FileWriter(fname,true); //the true will append the new data
		    fw.write(userName+ " " + this.getGameScore()+"\n");//appends the string to the file
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	/**
	 * Exits the game 
	 */
	private void exitGame() {
		this.menuDisplay.dispatchEvent(new WindowEvent(menuDisplay, WindowEvent.WINDOW_CLOSING));
		this.gameDisplay.dispatchEvent(new WindowEvent(gameDisplay, WindowEvent.WINDOW_CLOSING));
		return;
	}

	/**
	 * Takes input for the postGame Menu
	 * @param e
	 * e is the input from ActionEvent
	 */
	public void finishedAction(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "highscore":
			this.showHiScores();
			break;
		case "retry":
			this.retryGame();
			break;
		case "nextLevel":
			this.nextLevelGame();
			break;
		case "exit":
			this.exitGame();
		}
	}

	/**
	 * Creates the highscore window
	 */
	public void showHiScores() {
		highScoreDisplay = new HighScoreWindow(gameEngine.getMapNum());
		highScoreDisplay.setVisible(true);
	}
	
	/**
	 * closes windows and goes to the main menu
	 */
	private void goToMenu() {
		pauseDisplay.dispose();
		gameDisplay.dispose();
		gameDisplay = null;
		pauseDisplay = null;

		menuDisplay.setVisible(true);
		menuDisplay.requestFocus();
	}

	/**
	 * Key Handler that takes the user input
	 * @param e
	 * e is the KeyEvent 
	 */
	public void handleGameKeys(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			gameEngine.newMove("UP");
			break;

		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			gameEngine.newMove("DOWN");
			break;

		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			gameEngine.newMove("LEFT");
			break;

		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			gameEngine.newMove("RIGHT");
			break;

		case KeyEvent.VK_U:
			gameEngine.undoMove();
			break;
		
		case KeyEvent.VK_R:
			gameEngine.redoMove();
			break;
			
		case KeyEvent.VK_P:
			this.pauseGame();
			break;

		case KeyEvent.VK_Q:
			this.exitGame();
			break;
		}

		gameDisplay.updateGrid(gameEngine.getCurrentBoard());
		this.checkGameComplete();
	}

	/**
	 * closes the instructions
	 */
	private void hideInstructions() {
		instructionDisplay.dispose();
		instructionDisplay = null;
		//gameDisplay.requestFocus();
	}

	/**
	 * loads the map from a file
	 */
	private void loadMap() {
		String map= JOptionPane.showInputDialog(menuDisplay, "Map ID:", "Load pre-generated map", JOptionPane.INFORMATION_MESSAGE);

		try { 
			
			int mapID = Integer.parseInt(map); 
			
			File mapFile = new File(System.getProperty("user.dir") + java.io.File.separator + "Maps"
					+ java.io.File.separator + "Map" + mapID);
			if (mapFile.exists()) {	
				gameEngine = new GameEngine(Integer.toString(mapID));
				gameDisplay = new GameWindow(gameEngine, this);
				menuDisplay.setVisible(false);
				gameDisplay.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(menuDisplay, "This map does not exist", "Map not found", JOptionPane.ERROR_MESSAGE);
			}
		} catch(NumberFormatException e) { 
			JOptionPane.showMessageDialog(menuDisplay, "Invalid Map ID", "Error", JOptionPane.ERROR_MESSAGE);
		} catch(NullPointerException e) {
			JOptionPane.showMessageDialog(menuDisplay, "Invalid Map ID", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	
	/**
	 * Handles the button in the Instruction Window
	 * @param e
	 */
	public void instructionAction(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "return":
			this.hideInstructions();
		}
	}

	/**
	 * Handles the buttons in the menu
	 * @param e
	 */
	public void menuAction(ActionEvent e) {
		switch (e.getActionCommand()) {

		case "instruction":
			this.showInstructions();
			break;
		case "exit":
			this.exitGame();
			break;
		case "loadMap":
			this.loadMap();
			break;
		default:
			gameEngine = new GameEngine(e.getActionCommand());
			gameDisplay = new GameWindow(gameEngine, this);
			menuDisplay.setVisible(false);
			gameDisplay.setVisible(true);
		}
	}

	/**
	 * Runs the next level in gameEngine
	 * then refreshes the display
	 */
	private void nextLevelGame() {
		gameEngine.nextLevel();
		gameDisplay.updateGrid(gameEngine.getCurrentBoard());
		gameDisplay.setVisible(true);
		this.resumeGame();
	}

	/**
	 * Pauses the game and creates a new pause window
	 */
	private void pauseGame() {

		if (pauseDisplay == null) {
			pauseDisplay = new GameMenuWindow(this);
			pauseDisplay.display();
		}
	}

	/**
	 * Handles the buttons in the pause menu
	 * @param e
	 */
	public void pauseMenuAction(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "resume":
			this.resumeGame();
			break;

		case "mainMenu":
			this.goToMenu();
			break;
		case "instruction":
			this.showInstructions();
			break;
		case "retry":
			this.retryGame();
			break;

		case "exit":
			this.exitGame();
			break;
		}
	}

	/**
	 * Closes the pause window and runs the main windows
	 */
	private void resumeGame() {
		if (pauseDisplay != null) {
			pauseDisplay.dispose();
		}

		if (finishedDisplay != null) {
			finishedDisplay.dispose();
		}
		pauseDisplay = null;
		finishedDisplay = null;

		gameDisplay.requestFocus();
	}

	/**
	 * Reloads the map and refreshes the display
	 */
	private void retryGame() {
		gameEngine.resetGame();
		gameEngine.resetBoard();
		gameDisplay.updateGrid(gameEngine.getCurrentBoard());
		gameDisplay.setVisible(true);
		this.resumeGame();
	}

	/**
	 * Brings up a window to show instructions
	 */
	private void showInstructions() {
		if (instructionDisplay == null) {
			instructionDisplay = new InstructionWindow(this);
			instructionDisplay.display();
		}
	}
	
	/**
	 * @return return a clip that represents the game wide music
	 */
	public Clip getMusic() {
		return soundEngine.getMusic();
	}
	
	
	/**
	 * @return return a clip that represents a menu sound effect
	 */
	public Clip getMenuBop() {
		return soundEngine.getMenuBop();
	}
	
	/**
	 * @return return a clip that represents a game sound effect
	 */
	public Clip getGameBop() {
		return soundEngine.getGameBop();
	}
}
