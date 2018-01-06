package model;

public abstract class Ghost extends Being {

	private final String name;
	private final int aggressiveness;
	private final int intelligence;
	private final static int chillingParam = 2;        /// much a ghost get slower when pacman have the antighost

	public Ghost(String name, int speed, int aggressiveness, int intelligence) {
		super(speed);
		this.name = name;
		this.aggressiveness = aggressiveness;
		this.intelligence = intelligence;
	}
	
	/**
	 * Copy constructor: create a alive copy of the ghost copy. 
	 * @param copy: the Ghost to be copied.
	 */
	public Ghost(Ghost copy) {
		super(copy.getSpeed());
		this.name = copy.getName();
		this.aggressiveness = copy.getAggressiveness();
		this.intelligence = copy.getIntelligence();
	}

	public String getName() {
		return name;
	}
	
	public int getAggressiveness() {
		return aggressiveness;
	}
	
	public int getIntelligence() {
		return intelligence;
	}
	
	/**
	 * The antiGhost make the ghosts slower
	 * @param pacman
	 * @return
	 */
	protected int getSpeed(Pacman pacman) {
		return super.getSpeed() + (pacman.haveAntiGhost()? chillingParam : 0);
	}
	
	/**
	 * 
	 * @return true if pacman have an antiGhost setted in
	 */
	public abstract boolean isScared();
	
	/**
	 * Text change if pacman have an AntiGhost
	 * @param pacman
	 * @return
	 */
	@Override
	public String toString() {
		return (isDeath()? "=" : (isScared()? "€" : "£"));
	}
	
	/**
	 * This methods are blocked in this class
	 */
	
	@Override
	public int getSpeed() {
		return super.getSpeed() + (isScared()? 2 : 0);
	}

	
}
