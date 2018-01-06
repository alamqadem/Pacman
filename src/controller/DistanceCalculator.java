package controller;

import java.util.LinkedList;
import java.util.Queue;

import model.Direction;
import model.Field;
import model.Position;

/**
 * Evaluate the distance of the minimum path on the field from the position1 to the position2 (use BFS)
 * @author amin
 *
 */
public class DistanceCalculator {
	
	private final int distance;
	private final Direction next;
	
	
	public DistanceCalculator(Field field, Position position1, Position position2, int obstacleIncrement) {
		MatrixOfCells data = new MatrixOfCells(field.getRows(), field.getColumns());
		Queue<Position> fifoQueue = new LinkedList<Position>();
		
		/** 
		 * BFS 
		 * */
		data.getCell(position1).color = 'g';
		data.getCell(position1).distance = 0;
		
		fifoQueue.offer(position1);
		
		while (!fifoQueue.isEmpty()) {	
			Position currentPosition = fifoQueue.element();
			
			DirectionManager possibleDirections = new DirectionManager(field, currentPosition);
			
			for (Direction direction : possibleDirections) {
				Position adjacentPosition = direction.getFinalPosition(currentPosition, field.getRows(), field.getColumns());
				
				Cell adjacentCell = data.getCell(adjacentPosition);
				if (adjacentCell.color == 'w') {
					adjacentCell.color = 'g';
					adjacentCell.distance = data.getCell(currentPosition).distance + 1 + 
											(field.thereIsBeing(adjacentPosition)? obstacleIncrement : 0);
					adjacentCell.previous = currentPosition;
					fifoQueue.offer(adjacentPosition);
				}
			}
			
			fifoQueue.remove();
			data.getCell(currentPosition).color = 'b';
		}
		
		distance = data.getCell(position2).distance;
		
		/**
		 * Find the next direction from position1 travel through again the path from position2 to position1
		 */
		Position currentPosition = position2;
		Position previousPosition = currentPosition;
		while (currentPosition != position1) {
			previousPosition = currentPosition;
			currentPosition = data.getCell(previousPosition).previous;
		}
		
		next = position1.getDirection(previousPosition);
	}
	
	public final int getDistance() {
		return distance;
	}
	
	public Direction getNextDirection() {
		return next;
	}
	
	/**
	 * Private class to store the data used in the bfs search
	 * @author amin
	 *
	 */
	
	private final class MatrixOfCells {
		private final Cell[][] cells;
		
		public MatrixOfCells(int rows, int columns) {
			cells = new Cell[rows][columns];
			
			for (int row = 0; row < rows; row++)
				for (int column = 0; column < columns; column++)
					cells[row][column] = new Cell();
		}
		
		public Cell getCell(Position position) {
			return cells[position.getRow()][position.getColumn()];
		}
		
	}
	
	private final class Cell {
		public char color = 'w';
		public Position previous;
		public int distance;
	}
}


