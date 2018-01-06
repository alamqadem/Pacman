package model;

/**
 * A direction quantify a move on the field
 * @author amin
 *
 */
public final class Direction {
	private final int vertical;
	private final int horizontal;

	public Direction(int vertical, int horizontal) {
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	
	public int getVertical() {
		return vertical;
	}
	
	public int getHorizontal() {
		return horizontal;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof Direction))
			return false;
		
		Direction otherAsDirection = (Direction) other;
		
		return this.vertical == otherAsDirection.vertical && this.horizontal == otherAsDirection.horizontal;
	}
	
	private int getFinalRow(int startRow, int fieldRows) {
		return getFinal(startRow, vertical, fieldRows);
	}
	
	private int getFinalColumn(int startColumn, int fieldColumns) {
		return getFinal(startColumn, horizontal, fieldColumns);
	}
	
	public Position getFinalPosition(Position start, int fieldRows, int fieldColumns) {
		return new Position(getFinalRow(start.getRow(), fieldRows), getFinalColumn(start.getColumn(), fieldColumns));
	}
	
	/**
	 * Elaborate the move from the end to the start of a coordinate
	 * @param start
	 * @param displacement
	 * @param end
	 * @return
	 */
	public static int getFinal(int start, int displacement, int end) {
		if (end == 0)
			throw new IllegalArgumentException();
		
		int result = (start+displacement) % end;
		
		if (result < 0)
			result = result + end;
		
		return result;
	}
}
