package controller;

/**
 * These are Items with a countDown like a bomb, implement startEffect and endEffect as you want
 * @author amin
 *
 */
public abstract class TimerItem extends Item {
	
	private final int countDown;
	private int elapsedTime = 0;

	public TimerItem(int countDown) {
		this.countDown = countDown;
	}

	public final void clockTimer() {
		if (elapsedTime == 0)
			startEffect();
		
		if (isExpired())
			endEffect();
		else
			elapsedTime++;
	}
	
	public final void stopTimer() {
		elapsedTime = countDown;
	}
	
	public final boolean isExpired() {
		return elapsedTime == countDown;
	}
	
	public final int getRemainingTime() {
		return countDown - elapsedTime;
	}
	
	public final int getExpiration() {
		return countDown;
	}
	
	public abstract void startEffect();
	
	public abstract void endEffect();
}
