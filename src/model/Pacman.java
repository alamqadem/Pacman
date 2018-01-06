package model;

import controller.AntiGhost;

/**
 * The hero of the game
 * @author amin
 *
 */

public final class Pacman extends Being {

	private int points;
	private int lives;
	private AntiGhost antiGhost;

	public Pacman(int speed, int lives, int points) {
		super(speed);
		this.lives = lives;
		this.points = points;
	}
	
	public final void decreaseLives() {
		if (lives >= 0)
			lives--;
		else
			setDeath(true);
	}
	
	public final int getLives() {
		return lives;
	}
	
	public final void increasePoints(int increase) {
		points += increase;
	}
	
	public final int getPoints() {
		return points;
	}
	
	public final void setAntiGhost(AntiGhost antiGhost) {
		this.antiGhost = antiGhost;
	}
	
	public final void clockAntiGhost() {
		if (!haveAntiGhost())
			throw new UnsupportedOperationException();
		
		antiGhost.clockTimer();
	}
	
	public final void getAntiGhostPoints() {
		if (!haveAntiGhost())
			throw new UnsupportedOperationException();
		
		antiGhost.increaseKills();
	}
	
	public boolean haveAntiGhost() {
		return antiGhost != null;
	}
	
	public final int getAntiGhostRemainingTime() {
		if (!haveAntiGhost())
			throw new UnsupportedOperationException();
		
		return antiGhost.getRemainingTime();
	}

	@Override
	public String toString() {
		return "@";
	}
}
