package model;

/**
 * Identify a position on the field
 * @author amin
 *
 */
public final class Position {
	
	private int row;
	private int column;

	public Position(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Position))
			return false;
		
		Position otherAsPosition = (Position) other;
		
		return otherAsPosition.row == this.row &&
				otherAsPosition.column == this.column;
	}
	
	@Override
	public String toString() {
		return "( " + row + ", " + column + " )";
	}

	public Direction getDirection(Position other) {
		return new Direction(other.row-this.row, other.column-this.column);
	}
	
	/**
	 * Pitagorian distance
	 * @param other
	 * @return
	 */
	public int getDistance(Position other) {
		int rowGap = this.row - other.row;
		int columnGap = this.column - other.column;
		
		return (int) Math.sqrt(rowGap*rowGap + columnGap*columnGap);
	}
}
