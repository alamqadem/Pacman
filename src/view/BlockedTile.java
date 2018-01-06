package view;

import javax.swing.ImageIcon;

public class BlockedTile extends Tile {
	private final static ImageIcon image = new ImageIcon("images/stoneSquareWall.jpg");

	public BlockedTile() {
		super(image);
	}

	@Override
	public String toString() {
		return "blocked";
	}

}
