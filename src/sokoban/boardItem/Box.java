package sokoban.boardItem;

import javax.swing.ImageIcon;

public class Box implements Square {

	public ImageIcon getImage() {
		return new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
				+ "Box.png");
	}

	public boolean isBox() {
		return true;
	}

	public boolean isMoveable() {
		return false;
	}

	public boolean isWall() {
		return false;
	}
	
	public void setFacing(String Direction) {
		
	};
}
