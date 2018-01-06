package controller;

import java.util.Collections;

import model.Field;
import model.Position;

public class SortedDirections extends DirectionManagerForGhosts{

	public SortedDirections(Field field, Position ghostPosition, Position pacmanPosition) {
		super(field, ghostPosition);
		
		Collections.sort(directions, 
					new ComparatorOfDirections(ghostPosition, pacmanPosition, field.getRows(), field.getColumns()));	
	}

}
