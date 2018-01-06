package controller;

import java.util.Comparator;

import model.Direction;
import model.Position;

/**
 * Use the Position.distance function to compare a direction, the source is in position1 and we want to go to position2
 * @author amin
 *
 */
public class ComparatorOfDirections implements Comparator<Direction> {
	
	private final Position position1;
	private final Position position2;
	private final int fieldRows;
	private final int fieldColumns;

	public ComparatorOfDirections(Position position1, Position position2, int fieldRows, int fieldColumns) {
		this.position1 = position1;
		this.position2 = position2;
		this.fieldRows = fieldRows;
		this.fieldColumns = fieldColumns;
	}
	
	private int distance(Direction direction) {
		Position end = direction.getFinalPosition(position1, fieldRows, fieldColumns);
		return end.getDistance(position2);
	}
	
	@Override
	public int compare(Direction direction1, Direction direction2) {
		return distance(direction1) -  distance(direction2);
	}

}
