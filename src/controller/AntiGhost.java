package controller;

import model.Pacman;

/**
 * with this pacman can eat ghosts
 * @author amin
 *
 */
public final class AntiGhost extends TimerItem {
	
	/**
	 * how many ghost killed by pacman
	 */
	private int kills = 0;
	
	private Pacman pacman;

	public AntiGhost(int countDown) {
		super(countDown);
	}
	
	/**
	 * pacman now can kill the enemies
	 */
	@Override
	public void effect(Pacman pacman) {
		this.pacman = pacman;
		pacman.setAntiGhost(this);
	}
	
	/**
	 * Evry ghost killed call this method to have your increase of the points
	 */
	public void increaseKills() {
		kills++;
		pacman.increasePoints(200*kills);
	}
	
	@Override
	public void startEffect() { 
		// nothing
	}

	/**
	 * remove the power to pacman
	 */
	@Override
	public void endEffect() {
		pacman.setAntiGhost(null);
	}

	@Override
	public String toString() {
		return "!";
	}

}
