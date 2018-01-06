package model;

import model.Being;
import controller.AntiGhost;
import controller.Item;
import controller.Toffee;

public final class Box {

	private final boolean blocked;
	private Item item;
	private Being being;
	private Being zombie;

	/**
	 * If a box is blocked no item or beings can be contained in
	 * @param blocked
	 */
	public Box(boolean blocked) {
		this.blocked = blocked;
	}
	
	/**
	 * Copy constructor: the box must not contain any Being or Zombie
	 * OtherWise item are taked by deep copy
	 * @param copy
	 */
	public Box(Box copy) {
		this.blocked = copy.blocked;
		
		if (copy.being != null || copy.zombie != null)
			throw new IllegalStateException("Cannot copy construct a box with another Being");
		
		if (copy.item instanceof AntiGhost) {
			AntiGhost itemAsAntiGhost = (AntiGhost) copy.item;
			this.item = new AntiGhost(itemAsAntiGhost.getExpiration());
		} else if (copy.item instanceof Toffee)
			this.item = copy.item;
	}

	public final void setItem(Item item) {
		if (isBlocked())
			throw new UnsupportedOperationException("blocked box must be empty");
		
		this.item = item;
	}
	
	public final void setBeing(Being being) {
		if (isBlocked())
			throw new UnsupportedOperationException("blocked box must be empty");
		
		this.being = being;
	}
	
	public final void setZombie(Being zombie) {
		if (isBlocked() || (zombie != null && !zombie.isDeath()))
			throw new UnsupportedOperationException("blocked box must be empty and zombie must be a death being");
		
		this.zombie = zombie;
	}
	
	public final Item getItem() {
		return item;
	}
	
	public final Being getBeing() {
		return being;
	}
	
	public final Being getZombie() {
		return zombie;
	}
	
	public final boolean isBlocked() {
		return blocked;
	}
	
	public final String toString() {
		if (isBlocked())
			return " / ";
		
		Being being = getBeing();
		if (being != null)
			return " " + being + " ";
		
		Being zombie = getZombie();
		if (zombie != null)
			return " " + zombie + " ";
		
		Item item = getItem();
		if (item != null)
			return " " + item + " ";
		
		return "   ";
	}
}
