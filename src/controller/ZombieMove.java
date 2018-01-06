package controller;

import model.Being;
import model.Direction;
import model.Field;
import model.Ghost;
import model.Position;

public class ZombieMove extends Move {
	
	private Ghost ghost;

	public ZombieMove(Field field, Position start, Direction direction, Ghost ghost) {
		super(field, start, direction);
		this.ghost = ghost;
	}

	/**
	 * A zombie cannot destroy another zombie
	 */
	@Override
	public Position execute() {
		if (!isValid())
			return start;
		
		if (!field.thereIsZombie(end)) {
			field.moveZombie(start, end);
			return end;
		}
			
		return start;
	}

	@Override
	public String toString() {
		return "Zombie " + ghost.getName() + " moves from " + getStart() + " to " + getEnd();
	}
	
	@Override
	protected boolean isValid() {
		Being being = field.getZombie(start);
		
		return  being != null &&  
				field.isBlocked(start) == field.isBlocked(end);
	}
}
