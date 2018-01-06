package controller;

import model.Being;
import model.Field;
import model.Pacman;
import model.Position;

public class DirectionManagerForGhosts extends DirectionManager {
	
	public DirectionManagerForGhosts(Field field, Position ghostPosition) {
		super(field, ghostPosition);
		
		for (int index = 0; index < directions.size(); index++) {
			Position end = directions.get(index).getFinalPosition(ghostPosition, field.getRows(), field.getColumns());
			
			if (field.thereIsBeing(end)) {
				Being being = field.getBeing(end);
				
				if (being instanceof Pacman) {
					Pacman pacman = (Pacman) being;
					
					if (pacman.haveAntiGhost())
						directions.remove(index--);
				}
			}
		}
	}
}
