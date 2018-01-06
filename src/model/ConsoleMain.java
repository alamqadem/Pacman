package model;

import model.Ghost;

import java.util.Scanner;

import controller.FactoryOfFields;
import controller.FactoryOfOriginalField;
import controller.GhostPuppeteer;
import controller.Move;
import controller.PacmanMove;

public class ConsoleMain {
	
	
	private static Field field;
	private static Pacman pacman;
	private static Ghost[] ghosts;
	private static Position pacmanPosition;
	private static Position[] ghostsPosition;
	private static boolean gameOver = false;
	private static int time = 0;
	
	private final static Scanner keyboard = new Scanner(System.in);
	
	private final static int PACMAN_START_LIVES = 3;
	private final static int PACMAN_SPEED = 2;
	private final static Position PACMAN_START_POSITION = new Position(24, 13);
	private final static Position[] GHOST_START_POSITION = new Position[]{  new Position(14, 12), new Position(14, 15), 
																			new Position(16, 12), new Position(16, 15) };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FactoryOfFields factory = new FactoryOfOriginalField();
		
		field = factory.mkRandom();
		
		pacman = new Pacman(PACMAN_SPEED, PACMAN_START_LIVES, 0);
		
		field.setBeing(PACMAN_START_POSITION, pacman);
		
		pacmanPosition = PACMAN_START_POSITION;
		
		ghosts = createGhosts();
		
		ghostsPosition = new Position[ghosts.length];
		
		for (int index = 0; index < ghosts.length; index++) {
			ghostsPosition[index] = GHOST_START_POSITION[index];
			field.setBeing(ghostsPosition[index], ghosts[index]);
		}
		
		do {
			time++;
			
			printField();
			
			System.out.println("debug: pacman move\n");
			pacmanMove();
			
			printField();
			
			if (!checkPacmanDeath()) {				
				if (gameOver)
					break;
				
				System.out.println("debug: ghost move\n");
				moveGhosts();
				
				printField();
				
				checkPacmanDeath();
			}
			
		} while(!gameOver);
		
		System.out.println("YOU LOOSE :(");
		
		keyboard.close();
	}
	
	private final static Ghost[] createGhosts(){
		Ghost blinky = new Ghost("Blinky", PACMAN_SPEED+1, 28, 0){
			
			@Override
			public int getSpeed() {
				return getSpeed(pacman);
			}

			@Override
			public String toString() {
				return (isScared()?"€":"£");
			}

			@Override
			public boolean isScared() {
				return pacman.haveAntiGhost();
			}
			
		};
		
		Ghost pinky =  new Ghost("Pinky", PACMAN_SPEED, 20, 1) {

			@Override
			public String toString() {
				return (isScared()?"€":"$");
			}

			@Override
			public boolean isScared() {
				return pacman.haveAntiGhost();
			}

			@Override
			public int getSpeed() {
				return getSpeed(pacman);
			}
			
		};
		
		Ghost inky = new Ghost("Inky", PACMAN_SPEED+2, 18, 3){

			@Override
			public String toString() {
				return (isScared()?"€":"%");
			}

			@Override
			public boolean isScared() {
				return pacman.haveAntiGhost();
			}
			
			@Override
			public int getSpeed() {
				return getSpeed(pacman);
			}
			
		};
		
		Ghost clyde = new Ghost("Clyde", PACMAN_SPEED+3, 13, 0){
			@Override
			public String toString() {
				return (isScared()?"€":"&");
			}

			@Override
			public boolean isScared() {
				return pacman.haveAntiGhost();
			}

			@Override
			public int getSpeed() {
				return getSpeed(pacman);
			}
			
		};
		
		return new Ghost[]{blinky, pinky, inky, clyde};

	}
	
	private final static void moveGhosts() {
		for (int index = 0; index < ghosts.length; index++) {
			if (ghosts[index].isDeath() || time%ghosts[index].getSpeed() == 0) {
				GhostPuppeteer ghostPuppeteer = new GhostPuppeteer(ghosts[index], ghostsPosition[index], 
																	GHOST_START_POSITION[index], pacman, pacmanPosition, 
																	field);
				ghostsPosition[index] = ghostPuppeteer.getEndPosition();
				
				System.out.println(ghostPuppeteer.getMove());
			}
		}
	}
	
	private final static void pacmanMove() {
		if (time%pacman.getSpeed() == 0) {
			Move pacmanMove = pacmanInput();
			pacmanPosition = pacmanMove.execute();
			System.out.println(pacmanMove);
		} else 
			System.out.println("debug: pacman no move\n");
	}
	
	private final static Move pacmanInput() {
		System.out.print("Pacman move[w,a,s,d]: ");
		
		Direction pacmanDirection;
		
		switch(keyboard.nextLine()) {
		case "w": pacmanDirection = new Direction(-1, 0);break;
		case "s": pacmanDirection = new Direction( 1, 0);break;
		case "a": pacmanDirection = new Direction( 0,-1);break;
		case "d": pacmanDirection = new Direction( 0, 1);break;
		default: pacmanDirection = new Direction(0,0);
		}
		
		return new PacmanMove(field, pacmanPosition, pacmanDirection, pacman);
	}
	
	private final static void printField() {
		System.out.println(field);
		System.out.println(field.toStringStateBar(pacman, null));
	}
	
	private final static boolean checkPacmanDeath() {
		if (!pacman.isDeath())
			return false;
		
		pacman.decreaseLives();
		
		if (pacman.getLives() >= 0)
			resetLevel();
		else
			gameOver = true;
		
		return true;
	}
	
	private final static void resetLevel() {
		FactoryOfFields factory = new FactoryOfOriginalField();
		
		field = factory.mkRandom();
		
		for (int index = 0; index < ghosts.length; index++) // clean the field from the old ghosts
			if (!ghosts[index].isDeath())
				field.deleteBeing(ghostsPosition[index]);

		ghosts = createGhosts();
		for (int index = 0; index < ghosts.length; index++) {
			field.setBeing(GHOST_START_POSITION[index], ghosts[index]);
			ghostsPosition[index] = GHOST_START_POSITION[index];
		}
		
		pacman = new Pacman(PACMAN_SPEED, pacman.getLives(), pacman.getPoints());
		field.deleteBeing(pacmanPosition);
		field.setBeing(PACMAN_START_POSITION, pacman);
		pacmanPosition = PACMAN_START_POSITION;
	}

	
}
