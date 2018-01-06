package controller;

import model.Being;
import model.Direction;
import model.Field;
import model.Position;

/**
 * Move a being into the field
 * @author amin
 *
 */
public abstract class Move {
	
	protected final Field field;
	protected final Position start;
	protected final Direction direction;
	protected final Position end;

	public Move(Field field, Position start, Direction direction) {
		this.field = field;
		this.start = start;
		this.direction = direction;
		this.end = direction.getFinalPosition(start, field.getRows(), field.getColumns());
	}
	
	public Field getField() {
		return field;
	}
	
	public Position getStart() {
		return start;
	}
	
	public Position getEnd() {
		return end;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	protected boolean isValid() {
		Being being = field.getBeing(start);
		
		return  being != null &&  
				field.isBlocked(start) == field.isBlocked(end);
	}
	
	/**
	 * Modify the field 
	 * @return the end position of pacman
	 */
	public abstract Position execute();
	
	@Override
	public abstract String toString();
}
