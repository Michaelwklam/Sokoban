package window;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Cell allows the implementation of items in the map grid
 * @author SlamDunk
 *
 */
class Cell extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 58554757456938984L;
	private final int gridPositionX;
	private final int gridPositionY;

	/**
	 * 
	 * @param image - the image to display 
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	Cell(ImageIcon image, int x, int y) {
		super(image);
		this.gridPositionX = x;
		this.gridPositionY = y;
	}

	/**
	 * finds the x-coordinate of an item
	 * @return the x coordinate of an item
	 */
	int getGridPositionX() {
		return this.gridPositionX;
	}

	/**
	 * finds the y-coordinate of an item
	 * @return the y coordinate of an item
	 */
	int getGridPositionY() {
		return this.gridPositionY;
	}
}
