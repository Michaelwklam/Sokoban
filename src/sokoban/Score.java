package sokoban;

/**
 * @author slamDunk
 * Stores the score of the user on the map
 */
public class Score {
	private int turnsTaken;
	private String userName;
	
	/**
	 * @param user
	 * String name of user from input box
	 * @param turnsTaken
	 * int value of the turns taken by the user for the map
	 */
	public Score(String user, int turnsTaken) {
		this.userName = user;
		this.turnsTaken = turnsTaken;
	}
	
	/**
	 * @return
	 * Returns the score
	 */
	public int getScore() {
		return this.turnsTaken;
	}
	
	/**
	 * @return
	 * Returns the username
	 */
	public String getName() {
		return this.userName;
	}
}
