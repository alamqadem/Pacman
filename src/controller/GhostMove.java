package controller;

import model.Being;
import model.Direction;
import model.Field;
import model.Ghost;
import model.Pacman;
import model.Position;

public final class GhostMove extends Move {
	
	private final Ghost ghost;

	public GhostMove(Field field, Position start, Direction direction, Ghost ghost) {
		super(field, start, direction);
		this.ghost = ghost;
	}

	/**
	 * A ghost cannot destroy another ghost
	 */
	@Override
	public Position execute() {
		if (!isValid())
			return start;
		
		if (!field.thereIsBeing(end)) {
			field.moveBeing(start, end);
			return end;
		}
		
		Being being = field.getBeing(end);
		
		if (being instanceof Pacman) {
			Pacman pacman = (Pacman) being;
			
			if (pacman.haveAntiGhost())
				pacman.kill(ghost);       // the ghost is death (kamikaze style)              
			else
				ghost.kill(pacman);       // pacman is death
		}
		
		return start;
	}

	@Override
	public String toString() {
		return "Ghost " + ghost.getName() + " moves from " + start + " to " + end;
	}
	
	@Override
	protected boolean isValid() {
		Being being = field.getBeing(start);
		
		return super.isValid() && being == ghost;
	}

}
