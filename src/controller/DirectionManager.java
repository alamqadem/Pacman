package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import model.Direction;
import model.Field;
import model.Position;

/**
 * A DirectionManager allow to iterate on the possible directions and to have one randomly
 * @author amin
 *
 */

public class DirectionManager implements Iterable<Direction> {
	
	protected final ArrayList<Direction> directions = new ArrayList<Direction>(Arrays.asList(new Direction[]{
			new Direction( 0, -1),new Direction(-1,  0), new Direction( 0,  0), 
			new Direction( 1,  0),	new Direction( 0,  1)
	}));
	
	private final static Random random = new Random();
	
	public DirectionManager(Field field, Position position) {
		
		for (int index = 0; index < directions.size(); index++) {
			Position end = directions.get(index).getFinalPosition(position, field.getRows(), field.getColumns());
			
			if (field.isBlocked(end))
				directions.remove(index--);
		}
	}

	@Override
	public Iterator<Direction> iterator() {
		return directions.iterator();
	}
	
	
	public Direction getRandom() {
		if (directions.isEmpty())
			return null;
		
		return directions.get(random.nextInt(directions.size()));
	}
	
	public Direction getTheLast() {
		return directions.get(directions.size()-1);
	}
	
	
}
