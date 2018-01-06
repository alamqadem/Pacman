package controller;

import model.Being;
import model.Direction;
import model.Field;
import model.Ghost;
import model.Pacman;
import model.Position;

public final class PacmanMove extends Move {
	
	private final Pacman pacman;

	public PacmanMove(Field field, Position start, Direction direction, Pacman pacman) {
		super(field, start, direction);
		this.pacman = pacman;
	}

	@Override
	public Position execute() {
		if (!isValid()) {
			if (pacman.haveAntiGhost()) 
				pacman.clockAntiGhost();
			return start;
		}
		
		if (!field.thereIsBeing(end)) {
			field.moveBeing(start, end);
		} else {
			Being enemy = field.getBeing(end);
			
			if (enemy instanceof Ghost)
				if (pacman.haveAntiGhost()) {
					field.moveBeing(start, end);
					pacman.kill(enemy);
					field.setZombie(end, enemy);
					pacman.getAntiGhostPoints();
				} else {
					enemy.kill(pacman);
					return start;
				}
		}
		
		if (field.thereIsItem(end)) {
			field.getItem(end).effect(pacman);
			if (field.getItem(end) instanceof Toffee)
				field.deleteToffee(end);
			else
				field.deleteItem(end);
		}
		
		if (pacman.haveAntiGhost()) 
			pacman.clockAntiGhost();
		
		return end;
	}

	@Override
	public String toString() {
		return "Pacman moves from " + start + " to " + end;
	}

}
