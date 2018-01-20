package sokoban.boardItem;

import javax.swing.ImageIcon;

public class Wall implements Square {
	public ImageIcon getImage() {
		return new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
				+ "Wall.png");
	}

	public boolean isBox() {
		return false;
	}

	public boolean isMoveable() {
		return false;
	}

	public boolean isWall() {
		return true;
	}


	public void setFacing(String Direction) {
		
	}
}
