package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sokoban.Sokoban;

/**
 * Finished Window is the window which displays when a player completes a game
 * @author SlamDunk
 *
 */
public class FinishedWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -3092908264664907382L;


	private Sokoban sokoban;
	private JPanel LabelPanel;
	private JLabel ScoreLabel;
	private JTextField ScoreTextField;
	private JButton retryButton;
	private JButton nextLevelButton;
	private JButton highScoreButton;
	private ExitButton exitButton;
	private ImageIcon TopRightIcon;
	
	private JLabel BackgroundLabel;
	private ImageIcon BackgroundPicture;

	private String ScoreNumber;
	// SET THE BASIC WINDOW AND COMPONENTS SIZE
	private final int WINDOWWIDTH = 400;
	private final int GAP = 20;
	private final int FRAMEWIDTH = 50;
	private final int SCORELABELWIDTH = WINDOWWIDTH - 2 * FRAMEWIDTH;
	private final int SCORELABELHEIGTH = (int) (0.6 * SCORELABELWIDTH);
	private final int BUTTONWIDTH = SCORELABELWIDTH;
	private final int BUTTONHEIGTH = BUTTONWIDTH / 5;

	private final int WINDOWHEIGTH = 2 * FRAMEWIDTH + 4 * GAP + SCORELABELHEIGTH + 4 * BUTTONHEIGTH + 30;

	public FinishedWindow(Sokoban sokoban) {
		// make new JFrame
		super("Sokoban Game");
		this.sokoban = sokoban;

		// set font1
		Font font1 = new Font("SansSerif", Font.BOLD, 20);

		//set background
		this.BackgroundPicture = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "GameMenuBackground.png");
		this.BackgroundLabel = new JLabel(BackgroundPicture);
		this.BackgroundLabel.setSize(400, 600);
		this.BackgroundLabel.setLocation(0,0);
		
		// set score label
		ScoreLabel = new JLabel("Turns Taken to Complete");
		ScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		ScoreLabel.setFont(font1);

		// set text field
		ScoreNumber = Integer.toString(sokoban.getGameScore());// this could be change to variable

		ScoreTextField = new JTextField();
		ScoreTextField.setText(ScoreNumber);
		ScoreTextField.setFont(font1);
		ScoreTextField.setHorizontalAlignment(JTextField.CENTER);
		ScoreTextField.setBorder(BorderFactory.createEmptyBorder());
		ScoreTextField.setEditable(false);
		
		// set label panel
		LabelPanel = new JPanel();
		LabelPanel.setSize(SCORELABELWIDTH, SCORELABELHEIGTH);
		LabelPanel.setLocation(FRAMEWIDTH, FRAMEWIDTH);
		LabelPanel.setBackground(Color.WHITE);
		LabelPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		LabelPanel.setLayout(new BoxLayout(LabelPanel, BoxLayout.PAGE_AXIS));
		LabelPanel.setOpaque(true);
		LabelPanel.add(ScoreLabel);
		LabelPanel.add(ScoreTextField);

		// set retry button
		
		highScoreButton = new JButton("Map Leaderboard");
		highScoreButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		highScoreButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + SCORELABELHEIGTH + GAP);

		// set highscore button
		retryButton = new JButton("Retry");
		retryButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		retryButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + SCORELABELHEIGTH + 2 * GAP + BUTTONHEIGTH);
				
		// set next level button
		nextLevelButton = new JButton("Next Level");
		nextLevelButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		nextLevelButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + SCORELABELHEIGTH + 3 * GAP + BUTTONHEIGTH + BUTTONHEIGTH);
			
		// set Exit button
		exitButton = new ExitButton("Exit");
		exitButton.setSize(BUTTONWIDTH, BUTTONHEIGTH);
		exitButton.setLocation(FRAMEWIDTH, FRAMEWIDTH + SCORELABELHEIGTH + 4 * GAP + 3 * BUTTONHEIGTH);

		// set Finished window attributes
		this.setSize(WINDOWWIDTH, WINDOWHEIGTH);
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(WINDOWWIDTH, WINDOWHEIGTH));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set frame location
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation((int) (screenWidth / 2 - WINDOWWIDTH / 2), (int) (screenHeight / 2 - WINDOWHEIGTH / 2));

		// set the window logo icon
		TopRightIcon = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "toprighticon.png");
		this.setIconImage(TopRightIcon.getImage());

		// add components to the window
		this.setLayout(null);
		this.add(LabelPanel);
		this.add(retryButton);
		this.add(highScoreButton);
		this.add(nextLevelButton);	
		this.add(exitButton);
		this.add(BackgroundLabel);
		this.addActionListenerToButtons(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sokoban.finishedAction(e);
	}

	/**
	 * addActionListenerToButton adds listeners and commands
	 * @param listener - listens to user input
	 */
	private void addActionListenerToButtons(ActionListener listener) {
		highScoreButton.setActionCommand("highscore");
		retryButton.setActionCommand("retry");
		nextLevelButton.setActionCommand("nextLevel");
		exitButton.setActionCommand("exit");

		highScoreButton.addActionListener(listener);
		retryButton.addActionListener(listener);
		nextLevelButton.addActionListener(listener);
		exitButton.addActionListener(listener);
	}

	// to display the finished window
	public void display() {
		this.pack();
		this.setVisible(true);
	}

}
