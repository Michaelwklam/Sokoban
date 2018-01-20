package sokoban.boardItem;

import javax.swing.ImageIcon;

public class Space implements Square {
	public ImageIcon getImage() {
		return new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
				+ "Space.png");
	}

	public boolean isBox() {
		return false;
	}

	public boolean isMoveable() {
		return true;
	}

	public boolean isWall() {
		return false;
	}
	
	public void setFacing(String Direction) {
		
	};
}
