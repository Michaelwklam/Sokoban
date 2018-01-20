package sokoban;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

import sokoban.boardItem.Box;
import sokoban.boardItem.BoxAtGoal;
import sokoban.boardItem.Goal;
import sokoban.boardItem.Player;
import sokoban.boardItem.Space;
import sokoban.boardItem.Square;
import sokoban.boardItem.Wall;

/**
 * Generates the map
 * @author jfod803
 *
 */
public class MapGeneration {
	private Square[][] map;
	private Square[][] changedMap;
	private int numOfGoals;
	private int row;
	private int col;
	private int iterations;
	Square space = new Space();
	Square wall = new Wall();
	Square goal = new Goal();
	Square box = new Box();
	Square boxAtGoal = new BoxAtGoal();
	Square player = new Player();

	/**
	 * Takes a board and a difficulty to determine the size and the style
	 * @param board
	 * @param difficulty
	 */
	public MapGeneration(Board board, String difficulty) {
		this.row = board.getNumRows();
		this.col = board.getNumCols();
		iterations = 100;
		if (difficulty.equals("medium")) {
			iterations = 200;
		} else if (difficulty.equals("hard")) {
			iterations = 400;
		} else if (difficulty.equals("extreme")){
			iterations = 800;
		}

		map = new Square[row][col];
		changedMap = new Square[row][col];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (j == 0 || j == col - 1 || i == 0 || i == row - 1) {
					map[i][j] = wall;
					changedMap[i][j] = wall;
				} else {
					map[i][j] = space;
					changedMap[i][j] = wall;
				}
			}
		}
		Random rand = new Random();
		int R = rand.nextInt(row - 2) + 1;
		int C = rand.nextInt(col - 2) + 1;
		map[R][C] = player;
		changedMap[R][C] = player;

		int numOfGoals = rand.nextInt((row - 2) * (col - 2)) + 20;
		numOfGoals = (int) (numOfGoals * .1);
		// int numOfBox = numOfGoals;
		this.numOfGoals = numOfGoals;
		while (numOfGoals > 0) {
			addCrate();
			numOfGoals--;
		}
		board.setBoard(map);
	}

	/**
	 * Randomly adds Crates to the map, but does not break solvability
	 * 
	 */
	private void addCrate() {
		Random rand = new Random();
		while (true) {
			int R = rand.nextInt(row - 4) + 2;
			int C = rand.nextInt(col - 4) + 2;
			if (map[R][C] != player && map[R][C] != box) {
				if (map[R - 1][C] == box && map[R][C - 1] == box && map[R - 1][C - 1] == box) {

				} else if (map[R - 1][C] == box && map[R][C + 1] == box && map[R - 1][C + 1] == box) {

				} else if (map[R + 1][C] == box && map[R][C + 1] == box && map[R + 1][C + 1] == box) {

				} else if (map[R + 1][C] == box && map[R][C - 1] == box && map[R + 1][C - 1] == box) {

				} else {
					map[R][C] = box;
					changedMap[R][C] = box;
					break;
				}
			}
		}
	}

	/**
	 * @return
	 * Returns the Board
	 */
	public Square[][] getBoard() {
		return this.map;
	}

	/**
	 * @return
	 * Returns the num of Goals
	 */
	public int getNumGoals() {
		return this.numOfGoals;
	}

	/**
	 * Shuffles The board, by moving the player
	 * @param board
	 * the board to be shuffled
	 * @return
	 * Returns the number of Goals 
	 */
	public int shuffleBoard(Board board) {
		Random rand = new Random();
		int dir;
		int rows = board.getNumRows();
		int cols = board.getNumCols();

		for (int i = 0; i < this.iterations; i++) {
			dir = rand.nextInt(4);
			switch (dir) {
			case 0:
				board.move("LEFT");
				break;
			case 1:
				board.move("RIGHT");
				break;
			case 2:
				board.move("UP");
				break;
			case 3:
				board.move("DOWN");
				break;
			}
			int row = board.getRow();
			int col = board.getCol();
			if (changedMap[row][col].isWall()) {
				changedMap[board.getRow()][board.getCol()] = space;
			}
		}
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				board.setWasntGoal(row, col);
				if (changedMap[row][col].getClass().equals(box.getClass()) && map[row][col].isBox()) {
					changedMap[row][col] = space;
					surroundedByWall(row, col);
					this.numOfGoals--;
				} else if (map[row][col].isBox() && changedMap[row][col].getClass().equals(player.getClass())) {
				} else if (map[row][col].isBox()) {
					if (!boxNextToGoal(row, col)){
						changedMap[row][col] = goal;
						board.setWasGoal(row, col);
					}
				}
			}
		}
		if (this.numOfGoals == 0) {
			return this.numOfGoals;
		}
		board.setBoard(changedMap);
		board.setNumBox(this.numOfGoals);
		if (!finalCheck()){
			this.numOfGoals = 0;
		}
		return this.numOfGoals;
	}

	/**
	 * Writes the map to a file
	 * @param fog
	 * the check whether it is a "fog" map
	 * @return
	 * Returns the map Num
	 */
	public int writeToFile(boolean fog) {
		int mapNum = 1;
		try {
			Random rand = new Random();
			mapNum = 1;
			Writer writer = null;
			File file = new File(System.getProperty("user.dir") + java.io.File.separator + "Maps"
					+ java.io.File.separator + "Map" + mapNum);
			
			
			while (file.exists()){
				mapNum = rand.nextInt(100000000);
				file = new File(System.getProperty("user.dir") + java.io.File.separator + "Maps"
						+ java.io.File.separator + "Map" + mapNum);
			}
			
			
			writer = new BufferedWriter(new FileWriter(file));
			Integer rows = map.length;
			Integer cols = map[0].length;
			Integer fullNumOfGoals = this.numOfGoals;
			String stringRows = rows.toString();
			String stringCols = cols.toString();
			String stringNumGoals = fullNumOfGoals.toString();
			writer.append(stringRows);
			writer.append('\n');
			writer.append(stringCols);
			writer.append('\n');
			writer.append(stringNumGoals);
			writer.append('\n');
			if (fog){
				writer.append('1');
			}
			else{
				writer.append('0');
			}
			writer.append('\n');
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					if (map[row][col].equals(wall)) {
						writer.append('#');
					} else if (map[row][col].equals(space)) {
						writer.append(' ');
					} else if (map[row][col].equals(player)) {
						writer.append('@');
					} else if (map[row][col].equals(box)) {
						writer.append('$');
					} else if (map[row][col].equals(goal)) {
						writer.append('.');
					}
				}
				writer.append('\n');
			}
			writer.close();
		} catch (IOException e) {
			// do something
		}
		return mapNum;
	}
	
	/**
	 * Checks if a box is next to a goal and if so deletes both
	 * @param row
	 * the row coordinate of the box checked
	 * @param col
	 * the col coordinate of the box checked
	 * @return
	 * Returns true if something was deleted
	 */
	public boolean boxNextToGoal(int row, int col){
		boolean isTrue = false;
		if (changedMap[row][col+1].isBox()){
			changedMap[row][col+1] = space;
			isTrue = true;
		}
		else if (changedMap[row][col-1].isBox()){
			changedMap[row][col-1] = space;	
			isTrue = true;
		}
		else if (changedMap[row+1][col].isBox()){
			changedMap[row+1][col] = space;
			isTrue = true;
		}
		else if (changedMap[row-1][col].isBox()){
			changedMap[row-1][col] = space;
			isTrue = true;
		}
		if (isTrue){
			changedMap[row][col] = space;
			this.numOfGoals--;
		}
		return isTrue;
	}
	
	/**
	 * Checks the board if there is any issues with the amount of boxes and goals
	 * (if a box and player are on the same square is one known issue)
	 * @return
	 * Returns true if all is ok and false if not
	 */
	public boolean finalCheck(){
		boolean solveable = false;
		int boxCount = 0;
		int goalCount = 0;
		for (int row = 0; row < this.row; row++) {
			for (int col = 0; col < this.col; col++) {
				if (changedMap[row][col].isBox()){
					boxCount++;
				}
				else if (changedMap[row][col] == goal){
					goalCount++;
				}
			}
		}
		if (boxCount == goalCount){
			solveable = true;
		}
		return solveable;
	}
	/**
	 * Checks if a Spot is surrounded by walls and if so makes it a wall as well as there is no reason for it not to be
	 * @param row
	 * the row coordinate of what is being checked
	 * @param col
	 * the col coordinate of what is being checked
	 */
	public void surroundedByWall(int row, int col){
		if (changedMap[row][col+1].isWall() && changedMap[row][col-1].isWall() && changedMap[row+1][col].isWall() && changedMap[row-1][col].isWall()){
			changedMap[row][col] = wall;
		}
	}
}