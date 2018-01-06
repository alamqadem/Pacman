package view;

import javax.swing.ImageIcon;

public class AntiGhostTile extends Tile {
	private final static ImageIcon image = new ImageIcon("images/antiGhost.jpg");
	
	public AntiGhostTile() {
		super(image);
	}
	@Override
	public String toString() {
		return "antighost";
	}

}
