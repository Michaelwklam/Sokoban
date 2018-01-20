package sokoban.boardItem;

import javax.swing.ImageIcon;

public class Player implements Square {
	
	private String direction;
	
	public Player() {
		// default direction
		this.direction = "DOWN";
	}
	
	public ImageIcon getImage() {
		ImageIcon i = null;
		
		switch(this.direction) {
		case "UP":
			i = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
					+ "Player_up.png");
			break;
		case "LEFT":
			i = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
					+ "Player_left.png");
			break;
		case "RIGHT":
			i = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
					+ "Player_right.png");
			break;
		default:
			i = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images" + java.io.File.separator
					+ "Player.png");
			break;
		}
		
		return i; 
	}

	public String facingDirection() {
		return this.direction;
	}
	
	public void setFacing(String dir) {
		this.direction = dir;
	}
	
	public boolean isBox() {
		return false;
	}

	public boolean isMoveable() {
		return false;
	}

	public boolean isWall() {
		return false;
	};
}
