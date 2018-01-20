package sokoban;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * The GameEngine Holds the game Data
 * @author slamDunk
 *
 */
public class GameEngine {

	private int mapNum;
	private Board currentBoard;
	private Stack<Board> boardHistory;
	private Stack<Board> boardRedoHistory;
	private String gameDifficulty;
	private int movesTaken;
	
	/**
	 * Creates a new GameEngine that holds the game Data
	 * @param difficulty
	 * It is given a difficulty as either "easy", "medium, "hard" or "extreme"
	 * From that it starts the mapGeneration then runs the game.
	 */
	public GameEngine(String difficulty) {
		
		switch (difficulty) {
		case "easy":
		case "medium":
		case "hard":
		case "extreme":
			MapMaster map = new MapMaster(difficulty);
			this.gameDifficulty = difficulty;
			this.mapNum = map.getMapNum();
			break;
		default: // for map loading
			this.mapNum = Integer.parseInt(difficulty);
			break;
		}
		
		this.getMap(mapNum);
		this.boardHistory = new Stack<Board>();
		this.boardRedoHistory = new Stack<Board>();
		this.movesTaken = 0;
	}

	/**
	 * Returns the currentBoard
	 * @return
	 */
	public Board getCurrentBoard() {
		return this.currentBoard;
	}

	/**
	 * Returns how many moves are taken
	 * @return
	 */
	public int getMovesTaken() {
		return this.movesTaken;
	}
	
	
	/**
	 * Resets board for retryGame
	 */
	public void resetBoard() {
		this.movesTaken = 0;
		this.boardHistory.clear();
		this.boardRedoHistory.clear();
	}
	/**
	 * Reads the map from a text file, given a mapNum
	 * @param mapNum
	 * Reads from Maps/Map + mapNum
	 */
	private void getMap(int mapNum) {
		String dir = System.getProperty("user.dir") + java.io.File.separator + "Maps" + java.io.File.separator;
		String fname = dir + "Map" + mapNum;

		BufferedReader input = null;

		try {
			input = new BufferedReader(new FileReader(fname));

			int rows = Integer.valueOf(input.readLine().trim()).intValue();
			int cols = Integer.valueOf(input.readLine().trim()).intValue();
			int numBoxes = Integer.valueOf(input.readLine().trim()).intValue();
			int fogTemp = Integer.valueOf(input.readLine().trim()).intValue();
			boolean fog = false;
			if (fogTemp == 1){
				fog = true;
			}
			this.currentBoard = new Board(rows, cols, numBoxes, fog);
			switch (rows) {
			case 7:
				this.gameDifficulty = "easy";
				break;
			case 11:
				this.gameDifficulty = "medium";
				break;
			case 17:
				this.gameDifficulty = "hard";
				break;
			case 18:
				this.gameDifficulty = "extreme";
				break;
			}
		
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					this.currentBoard.setElement(i, j, (char) input.read());
				}

				// Skip over newline at end of row
				input.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("File " + fname + " not found");
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if the board is Complete and if so returns true
	 * @return
	 */
	public boolean isGameComplete() {
		return this.currentBoard.isSolved();
	}

	/**
	 * Attempts to move the player
	 * adds the old board into the board history and clones it to form a new
	 * @param moveDirection
	 * takes the direction that the player should move to
	 */
	public void newMove(String moveDirection) {

		boardHistory.push(this.currentBoard);
		boardRedoHistory.clear();

		Board newBoard = null;
		try {
			newBoard = (Board) currentBoard.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		newBoard.move(moveDirection);
		this.currentBoard = newBoard;
		this.movesTaken++;
	}

	/**
	 * Starts the next level, by generating a new map and changing to that map.
	 */
	public void nextLevel() {
		this.resetBoard();
		MapMaster map = new MapMaster(gameDifficulty);
		this.mapNum = map.getMapNum();
		this.getMap(mapNum);
		this.boardHistory.clear();
		this.boardRedoHistory.clear();
	}

	
	/**
	 * Tries to redo the move from the stacks of moves
	 */
	public void redoMove() {
		try {
			this.currentBoard = boardRedoHistory.pop();
			boardHistory.push(this.currentBoard);
			this.movesTaken++;
		} catch (Exception e) {
			;
		}
	}

	/**
	 * Reloads the map to reset the game
	 */
	public void resetGame() {
		this.getMap(mapNum);
	}

	/**
	 * Attempts to undo the move if there are moves on the stack
	 */
	public void undoMove() {
		try {
			boardRedoHistory.push(this.currentBoard);
			this.currentBoard = boardHistory.pop();
			this.movesTaken++;
		} catch (Exception e) {
			;
		}
	}
	
	/**
	 * @return
	 * returns the mapNum of the current Map
	 */
	public int getMapNum() {
		return this.mapNum;
	}
	
	/**
	 * @return
	 * Returns the current maps difficulty
	 */
	public String getMapDifficulty() {
		return this.gameDifficulty;
	}
}
