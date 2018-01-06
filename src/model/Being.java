package model;

public abstract class Being {
	
	private final int speed;
	private boolean alive = true;
	
	/**
	 * a being speed is how many ticks must pass for one move
	 * @param speed
	 */
	public Being(int speed) {
		this.speed = speed;
	}
	
	@Override
	public abstract String toString();
	
	public boolean isDeath() {
		return !alive;
	}
	
	protected void setDeath(boolean isDeath) {
		this.alive = !isDeath;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void kill(Being other) {
		other.setDeath(true);
	}
}
