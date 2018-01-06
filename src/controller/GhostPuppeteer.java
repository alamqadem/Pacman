package controller;

import model.Direction;
import model.Field;
import model.Ghost;
import model.Pacman;
import model.Position;

/**
 * Choose the best move for the ghost evaluating his parameters
 * @author amin
 *
 */

public final class GhostPuppeteer {
	
	private final Move move;
	private final Position end;
	
	public GhostPuppeteer(Ghost ghost, Position ghostPosition, Position ghostStartPosition, Pacman pacman, 
							Position pacmanPosition, Field field) {
		move = getNextMove(ghost, ghostPosition, ghostStartPosition, pacman, pacmanPosition, field);
		end = move.execute();
	}
	
	public final Move getMove() {
		return move;
	}

	private final Move getNextMove(Ghost ghost, Position ghostPosition, Position ghostStartPosition, Pacman pacman, Position pacmanPosition, Field field) {
		if (ghost.isDeath())
			return goToHome(ghost, ghostPosition, ghostStartPosition, field);
		
		DistanceCalculator distanceCalculator = new DistanceCalculator(field,ghostPosition, pacmanPosition, ghost.getIntelligence());
		
		if (distanceCalculator.getDistance() <= ghost.getAggressiveness())
			if (pacman.haveAntiGhost())
				return escapeFromPacman(ghost, ghostPosition, pacmanPosition, field);
			else
				return attackPacman(ghost, ghostPosition, distanceCalculator.getNextDirection(), field);
		else
			return randomDirection(ghost, ghostPosition, field);
	}
	
	private final Move attackPacman(Ghost ghost, Position ghostPosition, Direction attackDirection, Field field) {
		return new GhostMove(field, ghostPosition, attackDirection, ghost);	
	}
	
	private final Move escapeFromPacman(Ghost ghost, Position ghostPosition, Position pacmanPosition, Field field) {
		DirectionManager sortedDirections = new SortedDirections(field, ghostPosition, pacmanPosition);
		
		Direction escapeDirection = sortedDirections.getTheLast();
		
		return new GhostMove(field, ghostPosition, escapeDirection, ghost);
	}
	
	private final Move randomDirection(Ghost ghost, Position ghostPosition, Field field) {
		DirectionManager randomDirections = new DirectionManagerForGhosts(field, ghostPosition); 
		
		Direction randomDirection = randomDirections.getRandom();
		
		return new GhostMove(field, ghostPosition, randomDirection, ghost);
	}
	
	private final Move goToHome(Ghost ghost, Position ghostPosition, Position startGhostPosition, Field field) {
		Direction nextDirection;
		
		if (ghostPosition.equals(startGhostPosition))
			nextDirection = new Direction(0, 0);
		else {
			DistanceCalculator distanceCalculator = new DistanceCalculator(field, ghostPosition, startGhostPosition, 0);
			nextDirection = distanceCalculator.getNextDirection();
		}
		
		return new ZombieMove(field, ghostPosition, nextDirection, ghost);
	}
	
	public final Position getEndPosition() {
		return end;
	}
}
