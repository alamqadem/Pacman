package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Pacman;


public class StateBar extends JPanel {
	private JLabel points;
	private JPanel lives;
	private JLabel fruitLabel;
	
	private final static ImageIcon PACMAN_IMAGE = new ImageIcon("images/pacman.jpg");

	public StateBar() {
		setBackground(Color.BLACK);
		setLayout(new GridLayout(1, 3));
	}
	
	public void updateState(Pacman pacman, ImageIcon fruitImage) {
		removeAll();
		
		points = new JLabel(String.valueOf(pacman.getPoints()));
		points.setForeground(Color.WHITE);
		
		lives = new JPanel();
		lives.setBackground(Color.BLACK);
		lives.setLayout(new GridLayout(1, 3));
		
		
		for (int index = 0; index < pacman.getLives(); index++)
			lives.add(new JLabel(PACMAN_IMAGE));
		
		fruitLabel = new JLabel(fruitImage);

		add(points);
		add(lives);
		add(fruitLabel);		
	}
	

}
