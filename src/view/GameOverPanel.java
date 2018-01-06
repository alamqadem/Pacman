package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GameOverPanel extends JPanel {
	private final BestScoresPanel bestScorePanel;
	private final static String BEST_SCORE_FILE_NAME = "files/bestScore.txt";
	
	public GameOverPanel(boolean win, int userScore) {
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setLayout(new GridLayout(3, 1));
		
		JLabel title = new JLabel(new ImageIcon("images/" + (win? "you_win": "gameOver") + ".jpg"));
		InputPanel inputPanel = new InputPanel(userScore);
		bestScorePanel = new BestScoresPanel();
		
		
		add(title);
		add(inputPanel);
		add(bestScorePanel);
	}
	
	private class InputPanel extends JPanel {
		
		public InputPanel(final int score) {
			setBackground(GameOverPanel.this.getBackground());
			setLayout(new GridLayout(2, 1));
			
			JLabel scoreLabel = new JLabel(String.valueOf(score), JLabel.CENTER);
			scoreLabel.setForeground(GameOverPanel.this.getForeground());
			Font labelFont = scoreLabel.getFont();
			scoreLabel.setFont(new Font(labelFont.getName(), Font.BOLD, labelFont.getSize()*8));
			
			JPanel inputPanel = new JPanel();
			
			inputPanel.setBackground(this.getBackground());
			inputPanel.setLayout(new GridLayout(1, 3));
			
			final JTextField inputBox = new JTextField("Enter your name here");	
			
			inputBox.setBackground(GameOverPanel.this.getBackground());
			inputBox.setForeground(GameOverPanel.this.getForeground());	
			inputBox.requestFocusInWindow();
			
			JButton submitButton = new JButton("submit!");
			
			submitButton.setBackground(Color.RED);
			submitButton.setForeground(GameOverPanel.this.getForeground());
			
			submitButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String playerName = inputBox.getText().toUpperCase();
					Object[] playerData = new Object[]{playerName, new Integer(score)};
					
					inputBox.setText("");	
					inputBox.setEditable(false);
					
					DefaultTableModel model = (DefaultTableModel) GameOverPanel.this.bestScorePanel.bestScoresTable.getModel();
					model.addRow(playerData);
					
					try {
						writeInFile(playerData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			inputPanel.add(new JLabel());
			inputPanel.add(inputBox);
			inputPanel.add(submitButton);
			
			add(scoreLabel);
			add(inputPanel);
		}
		
		private void writeInFile(Object[] playerInfo) throws IOException {
			FileWriter file = null;
			try {
				file = new FileWriter(BEST_SCORE_FILE_NAME, true);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BufferedWriter writer = null;
			
			writer = new BufferedWriter(file);
			
			writer.write(playerInfo[0] + "|" + playerInfo[1]);
			writer.newLine();
			
			writer.close();
		}
	}
	
	private class BestScoresPanel extends JPanel{
		private final JTable bestScoresTable;
		
		public BestScoresPanel() {
			setBackground(GameOverPanel.this.getBackground());
			setLayout(new BorderLayout());
			
			JLabel bestScoresTitle = new JLabel("Best Scores");
			bestScoresTitle.setForeground(GameOverPanel.this.getForeground());
			
			String[] columnNames = {"Name", "Score"};
			
			Object[][] data = null;
			try {
				data = readFromFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// get this data in input from a file
			
			bestScoresTable = new JTable(new DefaultTableModel(data, columnNames));
			
			bestScoresTable.setBackground(GameOverPanel.this.getBackground());
			bestScoresTable.setForeground(GameOverPanel.this.getForeground());
			bestScoresTable.getTableHeader().setBackground(bestScoresTable.getBackground());
			bestScoresTable.getTableHeader().setForeground(bestScoresTable.getForeground());
			
			JScrollPane tableScroll = new JScrollPane(bestScoresTable);
			tableScroll.getViewport().setBackground(bestScoresTable.getBackground());
			tableScroll.getViewport().setForeground(bestScoresTable.getForeground());
			
			add(bestScoresTitle, BorderLayout.NORTH);
			add(tableScroll, BorderLayout.CENTER);
		}
		
		private Object[][] readFromFile() throws IOException {
			FileReader fileReader = null;
			try {
				fileReader = new FileReader(BEST_SCORE_FILE_NAME);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			BufferedReader bufferedReader = null;
			
			bufferedReader = new BufferedReader(fileReader);
			
			List<Object[]> resultAsList = new ArrayList<Object[]>();
			
			for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
				String name = line.substring(0, line.indexOf('|'));
				Integer points = new Integer(Integer.valueOf(line.substring(line.indexOf('|')+1)));
				resultAsList.add(new Object[]{name, points});
			}
			
			bufferedReader.close();
			
			return resultAsList.toArray(new Object[resultAsList.size()][2]);
		}
	}
}
