package controller;

import model.Pacman;

public abstract class Item {
	
	public abstract void effect(Pacman pacman);
	
	@Override
	public abstract String toString();
}
