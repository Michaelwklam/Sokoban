package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sokoban.Sokoban;

/**
 * InstructionWindow displays the instructions for the game
 * @author SlamDunk
 *
 */
public class InstructionWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4735057412270482556L;

	private Sokoban sokoban;
	// set the frame size
	private final int WINDOWWIDTH = 600;
	private final int GAP = (int) (0.05 * WINDOWWIDTH);
	private final int FRAMEWIDTH = 2 * GAP;
	private final int TEXTWIDTH = WINDOWWIDTH - 2 * FRAMEWIDTH;
	private final int TEXTHEIGTH = (int) (0.6 * TEXTWIDTH);
	private final int BUTTONWIDTH = (int) (0.15 * TEXTWIDTH);
	private final int BUTTONHEIGTH = (int) (0.6 * BUTTONWIDTH);

	private final int WINDOWHEIGTH = 2 * FRAMEWIDTH + 2 * GAP + TEXTHEIGTH + BUTTONHEIGTH;
	private JButton returnButton;	
	private JLabel BackgroundLabel;//new
	private ImageIcon BackgroundIcon;//new	
	private JLabel InstructionLabel;
	private ImageIcon InstructionPicture;

	/**
	 * InstructionWindow displays the instructions
	 * @param sokoban
	 */
	public InstructionWindow(Sokoban sokoban) {
		super("Sokoban Game");
		this.sokoban = sokoban;

		returnButton = new JButton("Return");
		returnButton.setActionCommand("return");
		returnButton.setSize(BUTTONWIDTH + 30, BUTTONHEIGTH);
		returnButton.setLocation((int) (WINDOWWIDTH / 2 - BUTTONWIDTH / 2), FRAMEWIDTH + GAP + TEXTHEIGTH);

		// set text field
		this.InstructionPicture = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "InstructionsPicture.png");
		this.InstructionLabel = new JLabel(InstructionPicture);
		this.InstructionLabel.setSize(500, 400);
		this.InstructionLabel.setLocation(FRAMEWIDTH,FRAMEWIDTH);
		//this.add(InstructionLabel);
//		instructionLabel = new JLabel;
//		instructionTextField.setSize(TEXTWIDTH, TEXTHEIGTH);
//		instructionTextField.setLocation(FRAMEWIDTH, FRAMEWIDTH);

		// set frame
		this.setSize(WINDOWWIDTH, WINDOWHEIGTH);
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(WINDOWWIDTH, WINDOWHEIGTH));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set the window logo icon
		ImageIcon TopRightIcon = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "toprighticon.png");
		this.setIconImage(TopRightIcon.getImage());

		// set frame location
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation((int) (screenWidth / 2 - WINDOWWIDTH / 2), (int) (screenHeight / 2 - WINDOWHEIGTH / 2));
		
		//set the back ground label with the size as the same as window size
		this.BackgroundIcon = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
			+ java.io.File.separator + "InstructionsBackgroundPicture.png");
		this.BackgroundLabel = new JLabel(BackgroundIcon);
		this.BackgroundLabel.setSize(WINDOWWIDTH, WINDOWHEIGTH);
		this.BackgroundLabel.setLocation(0,0);

		// add components to the window
		this.setLayout(null);
		this.add(returnButton);
		this.addActionListenerToButtons(this);
		
		this.add(BackgroundLabel);//new
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (sokoban.getGameBop() != null) {
			sokoban.getGameBop().stop();
			sokoban.getGameBop().setFramePosition(0);
			sokoban.getGameBop().start();
		}

		sokoban.instructionAction(e);
	}

	/**
	 * Adds listeners to buttons
	 * @param listener - listens to user input
	 */
	private void addActionListenerToButtons(ActionListener listener) {
		returnButton.setActionCommand("return");
		returnButton.addActionListener(listener);
	}

	// to display the finished window
	public void display() {

		this.pack();
		this.setVisible(true);
	}

}
