package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sokoban.Sokoban;

/**
 * StartMenuWindow is the home screen for the game where you can choose difficulty settings
 * @author Ashton
 *
 */
public class StartMenuWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6731047993571611092L;

	private Sokoban sokoban;
	private JButton MediumButton;
	private JButton EasyButton;
	private JButton HardButton;
	private JButton ExtremeButton;
	private ExitButton ExitButton;
	private InstructionButton InstructionButton;
	private JButton loadMapButton;
	private JLabel Logolabel;
	private ImageIcon LogoIcon;
	
	private Clip music;
		
	private JLabel Backgroundlabel;
	private ImageIcon BackgroundPicture;

	private ImageIcon TopRightIcon;

	/**
	 * constructor
	 */
	public StartMenuWindow(Sokoban sokoban) {
		/**
		 * create a JFrame with 450*228 fixed size title is "Sokoban Game" this
		 * window will be opened in the center of screen
		 */
		super("Sokoban Game");
		this.sokoban = sokoban;
		this.setSize(465, 350);
		this.setLocationByPlatform(true);
		this.setMinimumSize(new Dimension(465, 310));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		this.setLocation(screenWidth / 2 - 198, screenHeight / 2 - 114);
		
		/**
		 * set background image
		 */
		this.BackgroundPicture = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "BackgroundPicture.png");
		this.Backgroundlabel = new JLabel(BackgroundPicture);
		this.Backgroundlabel.setSize(465, 350);
		this.Backgroundlabel.setLocation(0,0);
		
		/**
		 * set the icon image
		 */
		this.TopRightIcon = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "toprighticon.png");
		this.setIconImage(TopRightIcon.getImage());

		/**
		 * set the logo icon with fixed size 396*104
		 */
		this.LogoIcon = new ImageIcon(System.getProperty("user.dir") + java.io.File.separator + "images"
				+ java.io.File.separator + "sokobanlogo.png");
		this.Logolabel = new JLabel(LogoIcon);
		this.Logolabel.setSize(396, 104);
		this.Logolabel.setLocation(27, 27);

		/**
		 * Set the load map button
		 */
		this.loadMapButton = new JButton("Load a pre-generated map");
		this.loadMapButton.setSize(357, 20);
		this.loadMapButton.setLocation(47, 151);
		
		/**
		 * set the four buttons at the fixed size and position add the
		 * components to the frame
		 */
		
		this.EasyButton = new JButton("Easy");
		this.EasyButton.setSize(85, 40);
		this.MediumButton = new JButton("Med");
		this.MediumButton.setSize(85, 40);
		this.HardButton = new JButton("Hard");
		this.HardButton.setSize(85, 40);
		this.ExtremeButton = new JButton("EX");
		this.ExtremeButton.setSize(85, 40);
		this.EasyButton.setLocation(47, 191);
		this.MediumButton.setLocation(137, 191);		
		this.HardButton.setLocation(227, 191);
		this.ExtremeButton.setLocation(317, 191);
		
		/**
		 * set the instruction button
		 */
		this.InstructionButton = new InstructionButton("Instruction");
		this.InstructionButton.setSize(165, 40);
		this.InstructionButton.setLocation(47, 251);

		// set the exit button
		this.ExitButton = new ExitButton("Exit");
		this.ExitButton.setSize(165, 40);
		this.ExitButton.setLocation(238, 251);

		// add buttons
		this.setLayout(null);
		this.add(MediumButton);
		this.add(EasyButton);
		this.add(HardButton);
		this.add(ExtremeButton);
		this.add(loadMapButton);
		this.add(Logolabel);
		this.add(ExitButton);
		this.add(InstructionButton);
		this.add(Backgroundlabel);
		this.addActionListenerToButtons(this);
		this.music = sokoban.getMusic();
		if (music != null) {
			music.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (sokoban.getMenuBop() != null) {
			sokoban.getMenuBop().stop();
			sokoban.getMenuBop().setFramePosition(0);
			sokoban.getMenuBop().start();
		}
		sokoban.menuAction(e);
	}

	/**
	 * adds listener to buttons
	 * @param listener - listens to user input
	 */
	private void addActionListenerToButtons(ActionListener listener) {
		this.InstructionButton.setActionCommand("instruction");
		this.ExitButton.setActionCommand("exit");
		this.ExtremeButton.setActionCommand("extreme");
		this.HardButton.setActionCommand("hard");
		this.MediumButton.setActionCommand("medium");
		this.EasyButton.setActionCommand("easy");
		this.loadMapButton.setActionCommand("loadMap");
		
		loadMapButton.addActionListener(listener);
		ExitButton.addActionListener(listener);
		MediumButton.addActionListener(listener);
		EasyButton.addActionListener(listener);
		HardButton.addActionListener(listener);
		ExtremeButton.addActionListener(listener);
		InstructionButton.addActionListener(listener);
	}

	/**
	 * display the StartMenu window
	 */
	public void display() {
		this.pack();
		this.setVisible(true);
	}
}
