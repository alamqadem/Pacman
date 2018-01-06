package view;

import javax.swing.ImageIcon;

public class ToffeeTile extends Tile {
	private final static ImageIcon image = new ImageIcon("images/toffee.jpg");
	
	public ToffeeTile() {
		super(image);
	}

	@Override
	public String toString() {
		return "toffee";
	}

}
