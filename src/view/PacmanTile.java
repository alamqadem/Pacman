package view;

import javax.swing.ImageIcon;

import model.Direction;
import model.Pacman;

public class PacmanTile extends Tile {
	private final static ImageIcon NORMAL_PACMAN = new ImageIcon("images/pacman.jpg");
	private final static ImageIcon LEFT_PACMAN = new ImageIcon("images/pacmanLeft.jpg");
	private final static ImageIcon DOWN_PACMAN = new ImageIcon("images/pacmanDown.jpg");
	private final static ImageIcon UP_PACMAN = new ImageIcon("images/pacmanUp.jpg");
	
	private final static ImageIcon EATING_PACMAN = new ImageIcon("images/pacmanWhileEating.jpg");
	private final static ImageIcon LEFT_EATING_PACMAN = new ImageIcon("images/pacmanWhileEatingLeft.jpg");
	private final static ImageIcon DOWN_EATING_PACMAN = new ImageIcon("images/pacmanWhileEatingDown.jpg");
	private final static ImageIcon UP_EATING_PACMAN = new ImageIcon("images/pacmanWhileEatingUp.jpg");
	
	private final static ImageIcon NORMAL_DEATH_PACMAN = new ImageIcon("images/pacmanDeath1.jpg");
	private final static ImageIcon LEFT_DEATH_PACMAN = new ImageIcon("images/pacmanDeath1Left.jpg");
	private final static ImageIcon DOWN_DEATH_PACMAN = new ImageIcon("images/pacmanDeath1Down.jpg");
	private final static ImageIcon UP_DEATH_PACMAN = new ImageIcon("images/pacmanDeath1Up.jpg");
	
	private final static ImageIcon EATING_DEATH_PACMAN = new ImageIcon("images/pacmanDeath2.jpg");
	private final static ImageIcon LEFT_DEATH_EATING_PACMAN = new ImageIcon("images/pacmanDeath2Left.jpg");
	private final static ImageIcon DOWN_DEATH_EATING_PACMAN = new ImageIcon("images/pacmanDeath2Down.jpg");
	private final static ImageIcon UP_DEATH_EATING_PACMAN = new ImageIcon("images/pacmanDeath2Up.jpg");
	
	private final static Direction LEFT = new Direction(0, -1);
	private final static Direction UP = new Direction(-1, 0);
	private final static Direction DOWN = new Direction(1, 0);
	
	private static int my_time = 0;
	private static boolean normalImage = true;
	
	public PacmanTile(Pacman pacman, Direction pacmanDirection) {
		super( normalImage? selectImage(pacmanDirection, pacman.isDeath()) : 
							selectEatingImage(pacmanDirection, pacman.isDeath()));
		
		my_time++;
	
		if (my_time%pacman.getSpeed() == 0)
			normalImage = !normalImage;
	}
	
	private static ImageIcon selectImage(Direction direction, boolean isDeath) {
		if (direction.equals(LEFT))
			return isDeath?LEFT_DEATH_PACMAN : LEFT_PACMAN;
		
		if (direction.equals(UP))
			return isDeath?UP_DEATH_PACMAN:UP_PACMAN;
		
		if (direction.equals(DOWN))
			return isDeath?DOWN_DEATH_PACMAN:DOWN_PACMAN;
	
		return isDeath?NORMAL_DEATH_PACMAN:NORMAL_PACMAN;
	}
	
	private static ImageIcon selectEatingImage(Direction direction, boolean isDeath) {
		if (direction.equals(LEFT))
			return isDeath?LEFT_DEATH_EATING_PACMAN:LEFT_EATING_PACMAN;
		
		if (direction.equals(UP))
			return isDeath?UP_DEATH_EATING_PACMAN:UP_EATING_PACMAN;
		
		if (direction.equals(DOWN))
			return isDeath?DOWN_DEATH_EATING_PACMAN:DOWN_EATING_PACMAN;
	
		return isDeath?EATING_DEATH_PACMAN:EATING_PACMAN;
	}
	
	
	@Override
	public String toString() {
		return "pacman";
	}
}
