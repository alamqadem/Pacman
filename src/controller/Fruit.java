package controller;

import model.Pacman;

/**
 * Evry field have a fruit associated that appears and disappear 
 * @author amin
 *
 */

public abstract class Fruit extends TimerItem {
	
	private final int bonus;

	public Fruit(int countDown, int bonus) {
		super(countDown);
		this.bonus = bonus;
	}

	@Override
	public void startEffect() {
		// nothing
	}

	@Override
	public void endEffect() {
		// nothing
	}

	@Override
	public void effect(Pacman pacman) {
		pacman.increasePoints(bonus);
		stopTimer();
	}

	@Override
	public abstract String toString();
}
