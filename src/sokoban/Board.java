package sokoban;

import javax.swing.ImageIcon;

import sokoban.boardItem.Box;
import sokoban.boardItem.BoxAtGoal;
import sokoban.boardItem.Goal;
import sokoban.boardItem.Player;
import sokoban.boardItem.Space;
import sokoban.boardItem.Square;
import sokoban.boardItem.Wall;


/**
 * Stores the game Board and needed data
 * @author slamDunk
 * 
 *
 */
public class Board implements Cloneable {

	// fill with Squares

	private Square[][] grid;

	Square space = new Space();
	Square wall = new Wall();
	Square goal = new Goal();
	Square box = new Box();
	Square boxAtGoal = new BoxAtGoal();
	Square player = new Player();
	
	private Coordinate man;
	private int boxCount;
	private boolean[][] originalGoalList;
	private boolean isFogActive;
	
	/**
	 * Class Board that stores the map
	 * @param row
	 * Number of Rows
	 * @param col
	 * Number of Columns
	 * @param numBoxes
	 * Number of Boxes 
	 * @param isFogActive
	 * Is the game mode fog
	 */
	public Board(int row, int col, int numBoxes, boolean isFogActive) {
		this.boxCount = numBoxes;
		this.isFogActive = isFogActive;
		grid = new Square[row][col];

		// As they don't need to be separate entities as they have no meaningful

		// difference from each other

		// Whereas boxes having separate states need to be different;

		originalGoalList = new boolean[row][col];

		for (int i = 0; i < row; i++) {

			for (int j = 0; j < col; j++) {

				originalGoalList[i][j] = false;

			}

		}

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		int rows = this.getNumRows();
		int cols = this.getNumCols();
		int boxes = this.getNumBox();
		char charRep = '#';
		Board clonedBoard = new Board(rows, cols, boxes, this.isFogActive);
		
		// deep copy on board
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {
				if (grid[i][j].equals(boxAtGoal)) {
					charRep = '*';
				} else if (grid[i][j].equals(space)) {
					charRep = ' ';
				} else if (grid[i][j].equals(wall)) {
					charRep = '#';
				} else if (grid[i][j].equals(goal)) {
					charRep = '.';
				} else if (grid[i][j].equals(box)) {
					charRep = '$';
				} else if (grid[i][j].equals(player)) {
					charRep = '@';
				}		
				clonedBoard.setElement(i, j, charRep);
				
				// copy the originalgoal list too
				if (this.wasGoalSquare(i, j)) clonedBoard.setWasGoal(i, j);
			}

		}
		
		return clonedBoard;

	}

	/**
	 * @return
	 * returns the man's Col coordinate
	 */
	public int getCol() {
		return man.getCol();
	}

	/**
	 * @return
	 * returns the man's Row coordinate
	 */
	public int getRow() {
		return man.getRow();
	}
	/**
	 * Gets the Element at Row/Col in the grid contained in board
	 * @param row
	 * @param col
	 * @return
	 */
	Square getElement(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Gets the image Icon at the grid Coordinates R, C 
	 * In the grid contained in the board
	 * @param R
	 * @param C
	 * @return
	 */
	public ImageIcon getImageIcon(int R, int C) {
		return grid[R][C].getImage();
	}
	
	/**
	 * @return
	 * Returns the FogImage for the fog mode of the game
	 */
	public ImageIcon getFogImage(){
		return new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
				+ "black.jpg");
	}

	/**
	 * @return
	 * Returns the number of Boxes that are in the map
	 */
	public int getNumBox() {
		return this.boxCount;
	}

	/**
	 * @return
	 * Returns the Maximum amount of Columns in the grid
	 */
	public int getNumCols() {
		return grid[0].length;
	}

	/**
	 * @return
	 * Returns the Maximum amount of Rows in the grid
	 */
	public int getNumRows() {
		return grid.length;
	}


	/**
	 * @return
	 * Checks whether the board is Solved and if so returns True
	 */
	public boolean isSolved() {
		int boxesAtGoal = 0;
		int rows = this.getNumRows();
		int cols = this.getNumCols();
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (grid[i][j].equals(boxAtGoal)) {
					boxesAtGoal++;
					if (boxesAtGoal == this.boxCount)
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Attempts to Move the player
	 * It works by checking if there is a free space or box next to the player, if there is a free space the player moves, if there is a box the program checks if the Square next to the box is a space and if so moves the box.
	 * @param direction
	 * Indicates the direction the user wishes to move the player
	 * @return
	 * Returns True if the player successfully moves and false if he does not
	 */
	boolean move(String direction) {
		int R = man.getRow();
		int C = man.getCol();
		boolean didMove = false;

		if (direction.equals("LEFT")) {
			if (grid[R][C - 1].isMoveable()) {
				grid[R][C - 1] = player;
				grid[R][C] = space;
				didMove = true;
				man.setCol(C - 1);
			} else if (grid[R][C - 1].isBox()) {
				if (grid[R][C - 2].isMoveable()) {
					shiftBoardUp(R, C);
					didMove = true;
					man.setCol(C - 1);
				}
			}
		} else if (direction.equals("RIGHT")) {
			if (grid[R][C + 1].isMoveable()) {
				grid[R][C + 1] = player;
				grid[R][C] = space;
				didMove = true;
				man.setCol(C + 1);
			} else if (grid[R][C + 1].isBox()) {
				if (grid[R][C + 2].isMoveable()) {
					shiftBoardDown(R, C);
					didMove = true;
					man.setCol(C + 1);
				}
			}
		} else if (direction.equals("UP")) {
			if (grid[R - 1][C].isMoveable()) {
				grid[R - 1][C] = player;
				grid[R][C] = space;
				didMove = true;
				man.setRow(R - 1);
			} else if (grid[R - 1][C].isBox()) {
				if (grid[R - 2][C].isMoveable()) {
					shiftBoardLeft(R, C);
					didMove = true;
					didMove = true;
					man.setRow(R - 1);
				}
			}
		} else if (direction.equals("DOWN")) {
			if (grid[R + 1][C].isMoveable()) {
				grid[R + 1][C] = player;
				grid[R][C] = space;
				didMove = true;
				man.setRow(R + 1);
			} else if (grid[R + 1][C].isBox()) {
				if (grid[R + 2][C].isMoveable()) {
					shiftBoardRight(R, C);
					didMove = true;
					man.setRow(R + 1);
				}
			}
		}
		
		if (didMove) {
			this.player.setFacing(direction);
			if (wasGoalSquare(R, C)) {
				grid[R][C] = goal;
			}
		}
		return didMove;
	}

	/**
	 * Sets the grid in the board to the map
	 * @param map
	 * The map is some Square[][] to set the board to.
	 */
	void setBoard(Square[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == wall) {
					this.grid[i][j] = wall;
				}
				this.grid[i][j] = map[i][j];
				if (this.grid[i][j].getClass().equals(player.getClass())) {
					man = new Coordinate(i, j);
				}
			}
		}
		this.grid = map;
	}

	/**
	 * Sets the Element in the grid at row, col to a certain element
	 * @param row
	 * The Row coordinate wanted
	 * @param col
	 * The Col coordinate wanted
	 * @param ch
	 * a char that the function converts to a Square and puts in the grid
	 */
	void setElement(int row, int col, char ch) {
		Square sq = null;
		switch (ch) {
		case '#':// wall
			sq = this.wall;
			break;
		case ' ':// space
			sq = this.space;
			break;
		case '$':// box
			sq = this.box;
			break;
		case '.':// goal
			sq = this.goal;
			originalGoalList[row][col] = true;
			break;
		case '@': // man
			sq = this.player;
			if (man == null) {
				this.man = new Coordinate(row, col);
			} else {
				man.setRow(row);
				man.setCol(col);
			}
			break;
		case '*': // box at goal
			sq = this.boxAtGoal;
			break;
		}
		grid[row][col] = sq;
	}

	/**
	 * Sets the number of Boxes
	 * @param numBoxes
	 */
	public void setNumBox(int numBoxes) {
		this.boxCount = numBoxes;
	}

	/**
	 * Sets that at the location row, col the location was originally a Goal
	 * @param row
	 * @param col
	 */
	public void setWasGoal(int row, int col) {
		originalGoalList[row][col] = true;
	}

	/**
	 * Sets that at the location row, col the location wasn't originally a Goal
	 * @param row
	 * @param col
	 */
	public void setWasntGoal(int row, int col) {
		originalGoalList[row][col] = false;
	}

	/**
	 * If the player can move it "shifts" the board by changing the next 2 grid coordinates.
	 * @param R
	 * @param C
	 */
	private void shiftBoardDown(int R, int C) {
		grid[R][C] = space;
		grid[R][C + 1] = player;
		if (grid[R][C + 2] == goal) {
			grid[R][C + 2] = boxAtGoal;
		} else {
			grid[R][C + 2] = box;
		}
	}

	/**
	 * If the player can move it "shifts" the board by changing the next 2 grid coordinates.
	 * @param R
	 * @param C
	 */
	private void shiftBoardLeft(int R, int C) {
		grid[R][C] = space;
		grid[R - 1][C] = player;
		if (grid[R - 2][C] == goal) {
			grid[R - 2][C] = boxAtGoal;
		} else {
			grid[R - 2][C] = box;
		}
	}

	/**
	 * If the player can move it "shifts" the board by changing the next 2 grid coordinates.
	 * @param R
	 * @param C
	 */
	private void shiftBoardRight(int R, int C) {
		grid[R][C] = space;
		grid[R + 1][C] = player;
		if (grid[R + 2][C] == goal) {
			grid[R + 2][C] = boxAtGoal;
		} else {
			grid[R + 2][C] = box;
		}
	}

	/**
	 * If the player can move it "shifts" the board by changing the next 2 grid coordinates.
	 * @param R
	 * @param C
	 */
	private void shiftBoardUp(int R, int C) {
		grid[R][C] = space;
		grid[R][C - 1] = player;
		if (grid[R][C - 2] == goal) {
			grid[R][C - 2] = boxAtGoal;
		} else {
			grid[R][C - 2] = box;
		}
	}

	/**
	 * Checks if at row,col the coordinate was originally a Goal
	 * If so returns True, if not returns false.
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean wasGoalSquare(int row, int col) {
		if (originalGoalList[row][col] == true) {
			return true;
		} else
			return false;
	}

	/**
	 * Returns the state of isFogActive
	 * @return
	 */
	public boolean isFogActive() {
		return isFogActive;
	}

	/**
	 * Sets the state of isFogActive
	 * @param isFogActive
	 */
	public void setFogActive(boolean isFogActive) {
		this.isFogActive = isFogActive;
	}

}
