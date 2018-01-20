package sokoban.boardItem;

import javax.swing.ImageIcon;

public interface Square {
	ImageIcon getImage();

	boolean isBox();

	boolean isMoveable();

	boolean isWall();
	
	void setFacing(String Direction);
}
