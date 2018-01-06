package controller;

import model.Field;
import model.Position;

/**
 * Repeat an operation over a range of boxes
 * @author amin
 *
 */
public abstract class RangeTasker {
	
	public void doRangeOperation(Field field, Position start, Position end) {
		if (!field.inRange(start) || !field.inRange(end)) 
			throw new IndexOutOfBoundsException("Positions are out of range");
		
		if (!(start.getRow() <= end.getRow() && start.getColumn() <= end.getColumn()))
			throw new IllegalArgumentException("Start position must be lower or equals than end position");
		
		for (int row = start.getRow(); row <= end.getRow(); row++)
			for (int column = start.getColumn(); column <= end.getColumn(); column++)
				operation(field, new Position(row, column));
	}
	
	/**
	 * The operation to be done on the field
	 * @param field
	 * @param position
	 */
	public abstract void operation(Field field, Position position);
}
