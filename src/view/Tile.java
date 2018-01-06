package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Tile extends JLabel {
	
	public Tile(ImageIcon image) {
		super(image);
		setOpaque(true);
		setBackground(Color.BLACK);
	}
	
	@Override
	public abstract String toString();
	
}
