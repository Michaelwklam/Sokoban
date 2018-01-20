package sokoban;

/**
 * Runs the MapGeneration algorithm and logic
 * @author slamDunk
 *
 */
public class MapMaster {

	private int mapNum;

	/**
	 * @param difficulty
	 * Usage difficulty = "easy", "medium, "hard" or "extreme"
	 * given a difficulty to determine what size map to generate
	 */
	public MapMaster(String difficulty) {
		int minBoxes = 3;
		int rows = 7;
		int cols = 7;
		boolean fog = false;
		if (difficulty.equals("medium")) {
			rows = 11;
			cols = 11;
			minBoxes = 7;
		} else if (difficulty.equals("hard")) {
			rows = 17;
			cols = 17;
			minBoxes = 9;
		} else if (difficulty.equals("extreme")){
			rows = 18;
			cols = 27;
			minBoxes = 14;
			fog = true;
		}

		Board currentBoard = new Board(rows, cols, 0, fog);

		MapGeneration map = new MapGeneration(currentBoard, difficulty);

		// boardHistory.push(this.currentBoard);

		while (map.shuffleBoard(currentBoard) < minBoxes) {

			map = new MapGeneration(currentBoard, difficulty);

		}

		// this.currentBoard.setBoard(map.getBoard());
		this.mapNum = map.writeToFile(fog);
	}

	/**
	 * Returns the Map Number of the map generated 
	 * @return
	 */
	public int getMapNum() {
		return this.mapNum;
	}
}
