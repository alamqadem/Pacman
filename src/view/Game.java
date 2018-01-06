package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.AntiGhost;
import controller.GhostPuppeteer;
import controller.Item;
import controller.Move;
import controller.PacmanMove;
import controller.Toffee;

import model.Being;
import model.Direction;
import model.Field;
import model.Ghost;
import model.Level;
import model.Pacman;
import model.Position;

public class Game extends JFrame {
	
	private Field field;
	private Pacman pacman;
	private Ghost[] ghosts;
	private Position pacmanPosition;
	private Position[] ghostsPosition;
	private Position[] ghostsStartPositions;
	
	private Direction lastPacmanDirection = new Direction(0, 1);
	private boolean gameOver = false;
	private int time = 0;
	private final InputManager input;
	private Timer timer;
	private int currentLevelIndex = 0;
	private Level[] levels;
	
	private final static int NUMBER_OF_LEVELS = 3;
	private final static int PACMAN_START_LIVES = 3;
	private final static int PACMAN_SPEED = 2;
	private final static Position PACMAN_START_POSITION = new Position(24, 13);
	
	private static final int HEIGHT = 600;
	private static final int WIDTH = 600;
	
	private final JPanel fieldPanel;
	private final StateBar stateBar;
	
	public Game() {
		super("Play!");
		
		input = new InputManager();
		addKeyListener(input);
		
		setLayout(new BorderLayout());
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createLevels(NUMBER_OF_LEVELS);
		
		pacman = new Pacman(PACMAN_SPEED, PACMAN_START_LIVES, 0);
		setStart();
		
		fieldPanel = createFieldPanel();
		stateBar = new StateBar();
				
		add(fieldPanel, BorderLayout.CENTER);
		add(stateBar, BorderLayout.SOUTH);
		
		showField();
		
		timer = new Timer();
		
		timer.scheduleAtFixedRate(new GameUpdateTask(), 1500, 150);
				
	}
	
	private class GameUpdateTask extends TimerTask {
		
		@Override
		public void run() {
			doGameUpdates();
			
			fieldPanel.removeAll();
			
			showField();
			
	/*		if (gameOver) {
				timer.cancel();
				timer.purge();
				
				Game.this.getContentPane().removeAll();
				Game.this.add(new GameOverPanel(!pacman.isDeath(),pacman.getPoints()), BorderLayout.CENTER);
			}*/
			
			revalidate();
		}
	}
	
	private JPanel createFieldPanel() {
		JPanel result = new JPanel();
		
		result.setLayout(new GridLayout(field.getRows(), field.getColumns()));
		
		return result;
	}
	
	private void showField() {
		for (int row = 0; row < field.getRows(); row++)
			for (int column = 0; column < field.getColumns(); column++)
				fieldPanel.add(getTile(row, column));
		
		stateBar.updateState(pacman, null);
	}

	private Tile getTile(int row, int column) {
		Position tilePosition = new Position(row, column);
		
		if (field.isBlocked(tilePosition))
			return new BlockedTile();
		
		if (field.thereIsBeing(tilePosition)) {
			Being being = field.getBeing(tilePosition);
			
			if (being instanceof Pacman)
				return new PacmanTile(pacman, lastPacmanDirection);
			else {
				Ghost beingAsGhost = (Ghost) being;
				return new GhostTile(beingAsGhost, (pacman.haveAntiGhost())? pacman.getAntiGhostRemainingTime() : 0,
										getAliveGhosts());
			}
		}
		
		if (field.thereIsZombie(tilePosition))
			return new ZombieTile();
		
		if (field.thereIsItem(tilePosition)) {
			Item item = field.getItem(tilePosition);
			
			if (item instanceof AntiGhost)
				return new AntiGhostTile();
			else if (item instanceof Toffee)
				return new ToffeeTile();
		}
		
		return new EmptyTile();
	}
	
	private void resetScreenAfter(int timeInSeconds) {
		timer.cancel();
		timer.purge();
		
		timer = new Timer();
		
		TimerTask resetTask = new TimerTask() {

			@Override
			public void run() {
				timer.cancel();
				timer.purge();
				
				timer = new Timer();
				
				if (gameOver) {
					timer.cancel();
					timer.purge();
					
					Game.this.getContentPane().removeAll();
					Game.this.add(new GameOverPanel(!pacman.isDeath(),pacman.getPoints()), BorderLayout.CENTER);
					
					revalidate();
				} else {
					resetLevel();
					timer.scheduleAtFixedRate(new GameUpdateTask(), 1500, 150);
				}
			}
		};
		
		TimerTask pacmanDeathAnimationTask = new TimerTask() {

			@Override
			public void run() {
				fieldPanel.removeAll();
				
				showField();
				
				revalidate();
			}			
		};
		
		timer.schedule(resetTask, timeInSeconds*1000);
		timer.scheduleAtFixedRate(pacmanDeathAnimationTask, 300, 500);
	}
	
	private void createLevels(int numberOfLevels) {
		levels = new Level[numberOfLevels];
		
		for (int levelIndex = 0; levelIndex < numberOfLevels; levelIndex++) {
			String path = "files/level" + (levelIndex+1) + "/";
			String fieldFileName = path + "field.txt";
			String ghostFileName = path + "ghosts.txt";
			
			levels[levelIndex] = new Level(fieldFileName, ghostFileName);
		}
	}
	
	private void setStart() {
		field = levels[currentLevelIndex].getField();
		
		field.setBeing(PACMAN_START_POSITION, pacman);
		
		pacmanPosition = PACMAN_START_POSITION;
		
		ghosts = createGhosts();
		
		ghostsPosition = new Position[ghosts.length];
		
		ghostsStartPositions = levels[currentLevelIndex].getGhostsStartPosition(); 
		
		for (int index = 0; index < ghosts.length; index++) {
			ghostsPosition[index] = ghostsStartPositions[index]; 
			field.setBeing(ghostsPosition[index], ghosts[index]);
		}
	}
	
	private int getAliveGhosts() {
		int deathGhosts = 0;
		
		for (Ghost ghost : ghosts)
			if (ghost.isDeath())
				deathGhosts++;
		
		return ghosts.length - deathGhosts;
	}
	
	public static void main(String[] args) {
		new Game().setVisible(true);
	}
	
	private void doGameUpdates() {
		time++;
		
		pacmanMove();
		
		if (pacmanWin()) {
			if (currentLevelIndex+1 >= NUMBER_OF_LEVELS)
				gameOver = true;
			else
				nextLevel();
		} else if (!checkPacmanDeath() && !gameOver) {				
			moveGhosts();
			checkPacmanDeath();
		}
	}
	
	private void nextLevel() {
		currentLevelIndex++;
		
		pacman.setAntiGhost(null);  // remove the antiGhost to pacman
		
		setStart();
	}

	private boolean pacmanWin() {
		return field.getNumberOfToffees() == 0;
	}

	private final Ghost[] createGhosts(){
		return levels[currentLevelIndex].getGhosts(pacman);
	}
	private final void moveGhosts() {
		for (int index = 0; index < ghosts.length; index++) {
			if (ghosts[index].isDeath() || time%ghosts[index].getSpeed() == 0) {
				GhostPuppeteer ghostPuppeteer = new GhostPuppeteer(ghosts[index], ghostsPosition[index], 
																	ghostsStartPositions[index], pacman, 
																	pacmanPosition,	field);
				ghostsPosition[index] = ghostPuppeteer.getEndPosition();
				
				System.out.println(ghostPuppeteer.getMove());
				
				/* is Zombie arrived to his original position ? */
				if (ghosts[index].isDeath() && ghostsPosition[index].equals(ghostsStartPositions[index])
					&& !field.thereIsBeing(ghostsPosition[index])) {
					
					field.deleteZombie(ghostsPosition[index]); // delete the zombie
					
					ghosts[index] = createGhosts()[index];     // recreate the ghost
					
					field.setBeing(ghostsPosition[index], ghosts[index]);  // set the ghost in the field
				}
			}
		}
	}
	
	private final void pacmanMove() {
		if (time%pacman.getSpeed() == 0) {
			Move pacmanMove = pacmanInput();
			pacmanPosition = pacmanMove.execute();
			System.out.println(pacmanMove);
		} else 
			System.out.println("debug: pacman no move\n");
	}
	
	private final Move pacmanInput() {
		System.out.print("Pacman move[w,a,s,d]: ");
		
		Direction pacmanDirection;
		
		if (input.isKeyDown(KeyEvent.VK_UP))
			pacmanDirection = new Direction(-1, 0);
		else if (input.isKeyDown(KeyEvent.VK_DOWN))
			pacmanDirection = new Direction( 1, 0);
		else if (input.isKeyDown(KeyEvent.VK_LEFT))
			pacmanDirection = new Direction( 0,-1);	
		else if (input.isKeyDown(KeyEvent.VK_RIGHT))
				pacmanDirection = new Direction( 0, 1);
		else
			pacmanDirection = lastPacmanDirection;
		
		lastPacmanDirection = pacmanDirection;
		
		return new PacmanMove(field, pacmanPosition, pacmanDirection, pacman);
	}
	
	private final boolean checkPacmanDeath() {
		if (!pacman.isDeath())
			return false;
		
		pacman.decreaseLives();
		
		if (pacman.getLives() < 0)
			gameOver = true;
		
		resetScreenAfter(5);
		
		return true;
	}
	
	private final void resetLevel() {
		pacman = new Pacman(PACMAN_SPEED, pacman.getLives(), pacman.getPoints());
		field.deleteBeing(pacmanPosition);
		field.setBeing(PACMAN_START_POSITION, pacman);
		pacmanPosition = PACMAN_START_POSITION;
		lastPacmanDirection = new Direction(0, 1);   // RIGHT DIRECTION
		
		for (int index = 0; index < ghosts.length; index++) // clean the field from the old ghosts
			if (!ghosts[index].isDeath())
				field.deleteBeing(ghostsPosition[index]);

		ghosts = createGhosts();                            // create new ghosts 
		for (int index = 0; index < ghosts.length; index++) {
			field.setBeing(ghostsStartPositions[index], ghosts[index]);
			ghostsPosition[index] = ghostsStartPositions[index];
		}
	}
	

}
