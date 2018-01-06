package model;

import controller.Fruit;
import controller.Item;
import controller.Toffee;
import model.Box;

public final class Field {
	
	private final Box[][] boxes;
	private int numberOfToffees = 0;
	private final static int TOFFEE_BONUS = 20;
	
	/**
	 * a field is a matrix of boxes
	 * @param rows
	 * @param columns
	 */
	public Field(int rows, int columns) {
		boxes = new Box[rows][columns];
		
		for (int row = 0; row < rows; row++)
			for (int column = 0; column < columns; column++)
				setBox(new Position(row, column), new Box(false));
	}
	
	/**
	 * Copy constructor: the field must not contain any being or any zombie
	 * @param copy: the field to be copied
	 */
	public Field(Field copy) {
		int rows = copy.getRows();
		int columns = copy.getColumns();
		
		this.boxes = new Box[rows][columns];
		this.numberOfToffees = copy.numberOfToffees;
		
		for (int row = 0; row < rows; row++)
			for (int column = 0; column < columns; column++) {
				Position currentPosition = new Position(row, column);
				setBox(currentPosition, new Box(copy.getBox(currentPosition)));
			}
	}

	/**
	 * @return the rows of the matrix
	 */
	public int getRows() {
		if (boxes == null)
			return 0;
	
		return boxes.length;
	}
	
	/**
	 * @return the columns of the matrix
	 */
	public int getColumns() {
		if (boxes == null || boxes[0] == null)
			return 0;
		
		return boxes[0].length;	
	}
	
	/**
	 * 
	 * @param position
	 * @return true if it is a valid matrix row and column
	 */
	public boolean inRange(Position position) {
		return position.getRow() < getRows() && position.getColumn() < getColumns();
	}
	
	/**
	 * 
	 * @param position
	 * @return true if there is a being in the box
	 */
	public boolean thereIsBeing(Position position) {
		return getBeing(position) != null;
	}
	
	/**
	 * 
	 * @param position
	 * @return true if there is a death being (a zombie) in the box
	 */
	public boolean thereIsZombie(Position position) {
		return getZombie(position) != null;
	}
	
	/**
	 * 
	 * @param position
	 * @return true if there is an item in the box
	 */
	public boolean thereIsItem(Position position) {
		return getItem(position) != null;
	}
	
	/**
	 * 
	 * @param position
	 * @return the Item in the box
	 */
	public Item getItem(Position position) {
		return getBox(position).getItem();
	}
	
	/**
	 * 
	 * @param position
	 * @return the being in the box
	 */
	public Being getBeing(Position position) {
		return getBox(position).getBeing();
	}
	
	/**
	 * 
	 * @param position
	 * @return the death being(a.k.a. zombie) in the box
	 */
	public Being getZombie(Position position) {
		return getBox(position).getZombie();
	}
	
	/**
	 * modify a box item
	 * @param position
	 * @param item
	 */
	public void setItem(Position position, Item item) {
		getBox(position).setItem(item);
	}
	
	/**
	 * delete from the box the Item
	 * @param position
	 */
	public void deleteItem(Position position) {
		getBox(position).setItem(null);
	}
	
	/**
	 * modify a box being
	 * @param position
	 * @param being
	 */
	public void setBeing(Position position, Being being) {
		getBox(position).setBeing(being);
	}
	
	/**
	 * delete from the box the Being
	 * @param position
	 */
	public void deleteBeing(Position position) {
		getBox(position).setBeing(null);
	}
	
	/**
	 * modify a box zombie
	 * @param position
	 * @param zombie
	 */
	public void setZombie(Position position, Being zombie) {
		getBox(position).setZombie(zombie);
	}
	
	/**
	 * delete from the box the Zombie
	 * @param position
	 */
	public void deleteZombie(Position position) {
		getBox(position).setZombie(null);
	}
	
	/**
	 * change the box of a being
	 * @param start
	 * @param destination
	 */
	public void moveBeing(Position start, Position destination) {
		Being startBeing = getBeing(start); /* copy */
		
		deleteBeing(start);/* delete */
		
		setBeing(destination, startBeing); /* set */
	}
	
	/**
	 * change the box of a zombie
	 * @param start
	 * @param destination
	 */
	public void moveZombie(Position start, Position destination) {
		Being startZombie = getZombie(start); /* copy */
		
		deleteZombie(start);/* delete */
		
		setZombie(destination, startZombie); /* set */
	}
	
	/**
	 * add a Toffee in the box and update the counter of Toffees
	 * @param position
	 */
	public void addToffee(Position position) {
		setItem(position, new Toffee(TOFFEE_BONUS)); //add
		
		numberOfToffees++;               //update
	}
	
	/**
	 * eliminate a Toffee in the box and update the counter of Toffees
	 * @param position
	 */
	public void deleteToffee(Position position) {
		deleteItem(position);           //delete
		
		numberOfToffees--;              //update
	}
	
	/**
	 * 
	 * @return the number of toffees on the field
	 */
	public int getNumberOfToffees() {
		return numberOfToffees;
	}
	
	private void setBox(Position position, Box box) {
		boxes[position.getRow()][position.getColumn()] = box;
	}

	private Box getBox(Position position) {
		return boxes[position.getRow()][position.getColumn()];
	}
	
	/**
	 * 
	 * @param position
	 * @return true if the box is blocked
	 */
	public boolean isBlocked(Position position) {
		return getBox(position).isBlocked();
	}
	
	/**
	 * block a box
	 * @param position
	 */
	public void setBlocked(Position position) {
		setBox(position, new Box(true));
	}
	
	/**
	 * 
	 * @param row
	 * @return text that shows of a row of the matrix
	 */
	private String toStringRow(int row) {
		String result = "|";
		
		for (Box box : boxes[row])
			result += box + "|";
		
		result += "\n";
		
		int numberOfDashes = 4*getColumns() + 1;
		result += repeatString("_", numberOfDashes) + "\n";
		
		return result;
	}
	
	@Override
	public String toString() {
		int numberOfDashes = 4*getColumns() + 1;
		String result = repeatString("_", numberOfDashes) + "\n";
		
		int rows = getRows();
		
		for ( int row = 0; row < rows; row++ )
			result += toStringRow( row );
		
		return result;
	}
	
	/**
	 * 
	 * @param pacman
	 * @return text that show a state bar with the pacman points, pacman lives and fruit of the field
	 */
	public String toStringStateBar(Pacman pacman, Fruit fruit) {
		String result = "";	
		
		result = "SCORE " + pacman.getPoints();
		result += "   LIVES ";
			
		int pacmanLives = pacman.getLives();
			
		for (int index = 0; index < pacmanLives; index++)
			result += pacman + " ";
		
		if (fruit != null)
			result += "   " + fruit;
		
		result += "\n";
		
		return result;
	}
	
	private static String repeatString(String toRepeat, int times) {
		String result = "";
		
		for (int count = 0; count < times; count++)
			result += toRepeat;
		
		return result;
	}
}
