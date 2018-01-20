package sokoban.boardItem;

import javax.swing.ImageIcon;

public class BoxAtGoal implements Square {
	public ImageIcon getImage() {
		return new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
				+ "Box2.png");
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