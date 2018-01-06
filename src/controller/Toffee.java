package controller;

import model.Pacman;

/**
 * a toffee increase the points of pacman
 * @author amin
 */
public class Toffee extends Item {
	
	private final int bonus;

	public Toffee(int bonus) {
		this.bonus = bonus;
	}

	@Override
	public void effect(Pacman pacman) {
		pacman.increasePoints(bonus);
	}

	@Override
	public String toString() {
		return ".";
	}

}
