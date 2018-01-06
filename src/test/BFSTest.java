package test;

import model.Field;
import model.Ghost;
import model.Position;

import org.junit.Test;

import controller.DistanceCalculator;
import controller.FactoryOfFields;
import controller.FactoryOfOriginalField;

public class BFSTest {
	private static Field field;
	
	private static Ghost marker = new Ghost("marker", 1, 0, 0){

		@Override
		public String toString() {
			return "X";
		}

		@Override
		public int getSpeed() {
			return 0;
		}

		@Override
		public boolean isScared() {
			// TODO Auto-generated method stub
			return false;
		}
		
	};
	
	public void test(Position position1, Position position2){
		FactoryOfFields factory = new FactoryOfOriginalField();
		
		field = factory.mkRandom();
		
		System.out.println(field);
		
		markBestPath(position1, position2);
		
		System.out.println(field);
	}
	
	@Test
	public void tunnelTest() {
		test(new Position(14, 21), new Position(14, 2));
	}
	
	@Test
	public void complexTest() {
		test(new Position(1,1), new Position(30, 26));
	}
	
	/**
	 * mark the best path on the field from position1 to position2
	 * @param position1
	 * @param position2
	 */
	private void markBestPath(Position position1, Position position2) {
		DistanceCalculator distanceCalculator;
		
		Position currentPosition = position1;
		mark(currentPosition);
		while (!currentPosition.equals(position2)) {
			distanceCalculator = new DistanceCalculator(field, currentPosition, position2, 0);
			currentPosition = distanceCalculator.getNextDirection().getFinalPosition(currentPosition, field.getRows(), field.getColumns());
			mark(currentPosition);
		}
	}
	
	private void mark(Position position) {
		System.out.println("debug: mark " +  position);
		field.setBeing(position, marker);
	}
	
	

}
