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
 * GameMenuWindow displays the Menu when the game is paused
 * @author SlamDunk
 *
 */
public class GameMenuWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7965907968475573017L;

	// SET THE BASIC WINDOW AND COMPONENTS SIZE
	private final int WINDOWWIDTH = 400;
	private final int GAP = 20;
	private final int FRAMEWIDTH = 50;
	private final int BUTTONWIDTH = WINDOWWIDTH - 2 * FRAMEWIDTH;
	private final int BUTTONHEIGTH = BUTTONWIDTH / 5;

	private final int WINDOWHEIGTH = 2 * FRAMEWIDTH + 5 * BUTTONHEIGTH + 4 * GAP + 30;

	private Sokoban sokoban;
	private JButton ResumeButton;
	private JButton GoToMenuButton;
	private InstructionButton InstructionButton;
	private JButton RetryButton;
	
	private JLabel BackgroundLabel;
	private ImageIcon BackgroundPicture;

	private ExitButton ExitButton;

	public GameMenuWindow(Sokoban sokoban) {
		super("Pause");
		this.sokoban = sokoban;
		
		//set Background
		this.BackgroundPicture = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "GameMenuBackground.png");
		this.BackgroundLabel = new JLabel(BackgroundPicture);
		this.BackgroundLabel.setSize(400, 600);
		this.BackgroundLabel.setLocation(0,0);
		
		// set Resume button
		ResumeButton = new JButton("Resume");
		ResumeButton.setActionCommand("resume");
		ResumeButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		ResumeButton.setLocation(FRAMEWIDTH, FRAMEWIDTH);

		// set Go to menu button
		GoToMenuButton = new JButton("Main Menu");
		GoToMenuButton.setActionCommand("mainMenu");
		GoToMenuButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		GoToMenuButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + BUTTONHEIGTH + GAP);

		// set instruction button
		InstructionButton = new InstructionButton("Instruction");
		InstructionButton.setActionCommand("instruction");
		InstructionButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		InstructionButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + 2 * BUTTONHEIGTH + 2 * GAP);

		// set retry button
		RetryButton = new JButton("Retry");
		RetryButton.setActionCommand("retry");
		RetryButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		RetryButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + 3 * BUTTONHEIGTH + 3 * GAP);

		// set exit button
		ExitButton = new ExitButton("Exit");
		ExitButton.setActionCommand("exit");
		ExitButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		ExitButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + 4 * BUTTONHEIGTH + 4 * GAP);

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

		// add components to the window
		this.setLayout(null);
		this.add(ResumeButton);
		this.add(GoToMenuButton);
		this.add(InstructionButton);
		this.add(RetryButton);
		this.add(ExitButton);
		this.add(BackgroundLabel);

		this.addActionListenerToButtons(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (sokoban.getMenuBop() != null) {
			sokoban.getMenuBop().stop();
			sokoban.getMenuBop().setFramePosition(0);
			sokoban.getMenuBop().start();
		}
		sokoban.pauseMenuAction(e);
	}

	/**
	 * addActionListenerToButtons adds listeners onto buttons
	 * @param l - listener 
	 */
	private void addActionListenerToButtons(ActionListener l) {
		ResumeButton.addActionListener(l);
		GoToMenuButton.addActionListener(l);
		InstructionButton.addActionListener(l);
		RetryButton.addActionListener(l);
		ExitButton.addActionListener(l);
	}

	// to display the finished window
	public void display() {
		this.pack();
		this.setVisible(true);
	}

}
