package view;

import javax.swing.ImageIcon;

public class ZombieTile extends Tile {
	private final static ImageIcon IMAGE = new ImageIcon("images/zombieGhost.jpg");
	
	public ZombieTile() {
		super(IMAGE);
	}

	@Override
	public String toString() {
		return "zombie";
	}

}
