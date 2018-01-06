package view;

import javax.swing.ImageIcon;

import model.Ghost;

public class GhostTile extends Tile {
	private final static ImageIcon SCARED_GHOST = new ImageIcon("images/scaredGhost.jpg");
	private final static ImageIcon ZOMBIE_GHOST = new ImageIcon("images/zombieGhost.jpg");	
	private final static int TIME_OF_FLASHING = 6;
	
	private static ImageIcon currentImage = SCARED_GHOST;
	private static int checkedGhosts = 0;
	
		
	public GhostTile(Ghost ghost, int antiGhostRemainingTime, int numberOfGhosts) {
		super(ghost.isScared()? 
				currentImage = 
							(antiGhostRemainingTime <= TIME_OF_FLASHING ?
									(checkedGhosts++ == 0? 
											(currentImage==SCARED_GHOST?ZOMBIE_GHOST:SCARED_GHOST)
											:currentImage)
									:SCARED_GHOST) 
				:new ImageIcon("images/"+ghost.getName().toLowerCase()+".jpg"));
		
		if (checkedGhosts == numberOfGhosts)
			checkedGhosts = 0;
	}

	@Override
	public String toString() {
		return "ghost";
	}	
}
