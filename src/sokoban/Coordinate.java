package sokoban;

/**
 * @author slamDunk
 *
 */
public class Coordinate {
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public int row;
	public int col;

	/**
	 * A Coordinate class that stores the Row and Col.
	 * @param row
	 * the row coordinate
	 * @param col
	 * the col coordinate
	 */
	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	//Note the javadoc was being weird, probz because it's a standard java function
	public boolean equals(Object o) {
		if (o instanceof Coordinate) {
			if (((Coordinate) o).getRow() == this.getRow() && ((Coordinate) o).getCol() == this.getCol()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the Col
	 * @return
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * Returns the Row
	 * @return
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Sets the Column as c
	 * @param c
	 */
	public void setCol(int c) {
		this.col = c;
	}

	/**
	 * Sets the Row as R
	 * @param r
	 */
	public void setRow(int r) {
		this.row = r;
	}
}
