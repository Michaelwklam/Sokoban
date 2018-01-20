package window;

import javax.swing.JButton;

/**
 * Exit button leaves the game 
 * @author SlamDunk
 *
 */
public class ExitButton extends JButton {

	/**
	 * this is a functional exit button which will be extended by other class
	 * with different looks and feels
	 */
	private static final long serialVersionUID = -8440131566063819605L;

	public ExitButton(String str) {
		super(str);
		// add some listeners
	}
}
