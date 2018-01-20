package window;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


import sokoban.Score;

/**
 * HighScoreWindow displays the highscore of the map
 * @author SlamDUnk
 *
 */
public class HighScoreWindow extends JFrame {

	private static final long serialVersionUID = -4889844495065330079L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public HighScoreWindow(int mapNum) {
		
		setTitle("Leaderboard");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(36, 166, 96));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JFormattedTextField frmtdtxtfldHighscoresForMap = new JFormattedTextField();
		frmtdtxtfldHighscoresForMap.setFont(new Font("Tahoma", Font.PLAIN, 20));
		frmtdtxtfldHighscoresForMap.setText("Highscores for Map #"+mapNum);
		frmtdtxtfldHighscoresForMap.setEditable(false);
		panel.add(frmtdtxtfldHighscoresForMap);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(50, 196, 126));
		contentPane.add(panel_1, BorderLayout.CENTER);
		
		DefaultTableModel model = new DefaultTableModel(); 
		JTable table = new JTable(model); 

		// Create a couple of columns 
		model.addColumn("Rank");
		model.addColumn("UserName"); 
		model.addColumn("UserScore"); 
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(175);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(175);
		
		// populate table
		try {
			int rank = 1;
			for (Score s: this.getHighscores(mapNum)) {
				model.addRow(new Object[]{rank,s.getName(), s.getScore()});
				rank++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		panel_1.add(table);
	}

	public ArrayList<Score> getHighscores(int mapNum) throws FileNotFoundException {
		ArrayList<Score> hiscores = new ArrayList<Score>();
		String dir = System.getProperty("user.dir") + java.io.File.separator + "Scores" + java.io.File.separator;
		String fname = dir + "Map" + mapNum;
		
		Scanner scan = null;
		try {
			scan = new Scanner(new File(fname));
			while (scan.hasNextLine()) {
				String next = scan.nextLine();
				String[] temp = next.split(" ");
				String userName = temp[0];
				int userScore = Integer.parseInt(temp[1]);
				Score newScore = new Score(userName, userScore);
				hiscores.add(newScore);
			}
		}
		finally {
			if (scan != null) scan.close();
		}
		
		// sort the hiscores
		Collections.sort(hiscores, scoreComparator);
		
		return hiscores;
	}
	
	/**
	 * Comparator compares the scores for the high score
	 */
	private final Comparator<Score> scoreComparator = new Comparator<Score>() {
		public int compare(Score a, Score b) {
		    return Integer.compare(a.getScore(), b.getScore()); //ascending
		}
	};
}

