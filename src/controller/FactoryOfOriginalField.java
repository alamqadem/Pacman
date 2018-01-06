package controller;

import controller.AntiGhost;
import model.Field;
import model.Position;

public class FactoryOfOriginalField implements FactoryOfFields {
	private static final int rows = 32;
	private static final int columns = 28;

	@Override
	public Field mkRandom() {
		Field field = new Field(rows, columns);
		
		RangeTasker blocker = new RangeTasker() {
			
			@Override
			public void operation(Field field, Position position) {
				field.setBlocked(position);
			}
		};
		
		blocker.doRangeOperation(field, new Position(0, 0), new Position(0, columns-1));
		
		blocker.doRangeOperation(field, new Position(rows-1, 0), new Position(rows-1, columns-1));
	
		blocker.doRangeOperation(field, new Position(1, 0), new Position(9, 0));
		
		blocker.doRangeOperation(field, new Position(2, 2), new Position(4, 5));
		
		blocker.doRangeOperation(field, new Position(2, 7), new Position(4, 11));
		
		blocker.doRangeOperation(field, new Position(1, 13), new Position(4, 14));
		
		blocker.doRangeOperation(field, new Position(2, 16), new Position(4, 20));
		
		blocker.doRangeOperation(field, new Position(2, 22), new Position(4, 25));
		
		blocker.doRangeOperation(field, new Position(6, 2), new Position(7, 5));
		
		blocker.doRangeOperation(field, new Position(6, 7), new Position(13, 8));
		
		blocker.doRangeOperation(field, new Position(6, 10), new Position(7, 17));
		
		blocker.doRangeOperation(field, new Position(6, 19), new Position(13, 20));
		
		blocker.doRangeOperation(field, new Position(6, 22), new Position(7, 25));
		
		blocker.doRangeOperation(field, new Position(0, 27), new Position(9, 27));
		
		blocker.doRangeOperation(field, new Position(9, 1), new Position(9, 5));
		
		blocker.doRangeOperation(field, new Position(9, 9), new Position(10, 11));
		
		blocker.doRangeOperation(field, new Position(9, 13), new Position(10, 14));
		
		blocker.doRangeOperation(field, new Position(9, 16), new Position(10, 18));
		
		blocker.doRangeOperation(field, new Position(9, 22), new Position(9, 26));
		
		blocker.doRangeOperation(field, new Position(10, 5), new Position(13, 5));
		
		blocker.doRangeOperation(field, new Position(10, 22), new Position(13, 22));
		
		blocker.doRangeOperation(field, new Position(13, 0), new Position(13, 5));
		
		blocker.doRangeOperation(field, new Position(13, 10), new Position(13, 11));
		
		blocker.doRangeOperation(field, new Position(13, 16), new Position(13, 17));
		
		blocker.doRangeOperation(field, new Position(13, 23), new Position(13, 27));
		
		blocker.doRangeOperation(field, new Position(14, 10), new Position(17, 10));
		
		blocker.doRangeOperation(field, new Position(16, 0), new Position(16, 5));
		
		blocker.doRangeOperation(field, new Position(16, 7), new Position(20, 8));
		
		blocker.doRangeOperation(field, new Position(17, 11), new Position(17, 17));
		
		blocker.doRangeOperation(field, new Position(14, 17), new Position(16, 17));
		
		blocker.doRangeOperation(field, new Position(16, 19), new Position(20, 20));
		
		blocker.doRangeOperation(field, new Position(16, 22), new Position(16, 27));
		
		blocker.doRangeOperation(field, new Position(17, 5), new Position(19, 5));
		
		blocker.doRangeOperation(field, new Position(17, 22), new Position(19, 22));
		
		blocker.doRangeOperation(field, new Position(20, 0), new Position(20, 5));
		
		blocker.doRangeOperation(field, new Position(19, 10), new Position(20, 17));
		
		blocker.doRangeOperation(field, new Position(20, 22), new Position(20, 27));
		
		blocker.doRangeOperation(field, new Position(21, 0), new Position(30, 0));
		
		blocker.doRangeOperation(field, new Position(22, 2), new Position(23, 5));
		
		blocker.doRangeOperation(field, new Position(22, 7), new Position(23, 11));
		
		blocker.doRangeOperation(field, new Position(21, 13), new Position(23, 14));
		
		blocker.doRangeOperation(field, new Position(22, 16), new Position(23, 20));
		
		blocker.doRangeOperation(field, new Position(22, 22), new Position(23, 25));
		
		blocker.doRangeOperation(field, new Position(25, 1), new Position(26, 2));
		
		blocker.doRangeOperation(field, new Position(24, 4), new Position(26, 5));
		
		blocker.doRangeOperation(field, new Position(25, 7), new Position(29, 8));
		
		blocker.doRangeOperation(field, new Position(25, 10), new Position(26, 17));
		
		blocker.doRangeOperation(field, new Position(25, 19), new Position(27, 20));
		
		blocker.doRangeOperation(field, new Position(24, 22), new Position(26, 23));
		
		blocker.doRangeOperation(field, new Position(21, 27), new Position(30, 27));
		
		blocker.doRangeOperation(field, new Position(25, 25), new Position(26, 26));
		
		blocker.doRangeOperation(field, new Position(28, 2), new Position(29, 11));
		
		blocker.doRangeOperation(field, new Position(27, 13), new Position(29, 14));
		
		blocker.doRangeOperation(field, new Position(28, 16), new Position(29, 25));
		
		for(int row = 0; row < field.getRows(); row++)
			for (int column = 0; column < field.getColumns(); column++)
				if (!field.isBlocked(new Position(row, column)))
					field.addToffee(new Position(row, column));							// add Toffees in all the boxes
		
		RangeTasker deleterOfToffees = new RangeTasker() {
			@Override
			public void operation(Field field, Position position) {
				field.deleteToffee(position);
			}
		};
		
		//delete toffees not required
		deleterOfToffees.doRangeOperation(field, new Position(14, 0), new Position(15, 5));
		
		deleterOfToffees.doRangeOperation(field, new Position(14, 7), new Position(15, 8));
		
		deleterOfToffees.doRangeOperation(field, new Position(11, 9), new Position(20, 9));
		
		deleterOfToffees.doRangeOperation(field, new Position(11, 10), new Position(12, 18));
		
		deleterOfToffees.doRangeOperation(field, new Position(9, 12), new Position(10, 12));
		
		deleterOfToffees.doRangeOperation(field, new Position(9, 15), new Position(10, 15));
		
		deleterOfToffees.doRangeOperation(field, new Position(13, 12), new Position(13, 15));
		
		deleterOfToffees.doRangeOperation(field, new Position(14, 11), new Position(16, 16));
		
		deleterOfToffees.doRangeOperation(field, new Position(18, 10), new Position(18, 17));
		
		deleterOfToffees.doRangeOperation(field, new Position(13, 18), new Position(20, 18));
		
		deleterOfToffees.doRangeOperation(field, new Position(14, 19), new Position(15, 20));
		
		deleterOfToffees.doRangeOperation(field, new Position(14, 22), new Position(15, 27));
		
		deleterOfToffees.doRangeOperation(field, new Position(10, 0), new Position(12, 4));
		
		deleterOfToffees.doRangeOperation(field, new Position(17, 0), new Position(19, 4));
		
		deleterOfToffees.doRangeOperation(field, new Position(10, 23), new Position(12, 27));
		
		deleterOfToffees.doRangeOperation(field, new Position(17, 23), new Position(19, 27));
		
		//delete Toffees and set the AntiGhost
		field.deleteToffee(new Position(3, 1));
		field.setItem(new Position(3, 1), new AntiGhost(30));
		
		field.deleteToffee(new Position(24, 1));
		field.setItem(new Position(24, 1), new AntiGhost(30));
		
		field.deleteToffee(new Position(3, 26));
		field.setItem(new Position(3, 26), new AntiGhost(30));
		
		field.deleteToffee(new Position(24, 26));
		field.setItem(new Position(24, 26), new AntiGhost(30));
		
		return field;
	}

}
