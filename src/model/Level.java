package model;

import java.io.IOException;

import controller.FieldLoaderFromFile;
import controller.GhostsLoaderFromFile;

public class Level {
	
	private final Ghost[] ghosts;
	private final Position[] ghostsStartPositions;
	private final Field field;

	public Level(String fieldFileName, String ghostFileName) {
		FieldLoaderFromFile fieldLoader = null;
		
		try {
			fieldLoader = new FieldLoaderFromFile(fieldFileName);
		} catch (NumberFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		this.field = fieldLoader.getField();
		
		GhostsLoaderFromFile ghostsLoader = null;
		
		Pacman apparentPacman = new Pacman(1, 1, 0);
		
		try {
			ghostsLoader = new GhostsLoaderFromFile(ghostFileName, apparentPacman);
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ghosts = ghostsLoader.getGhosts();
		ghostsStartPositions = ghostsLoader.getStartPositions();
	}

	public Field getField() {
		return new Field(this.field);
	}
	
	public Ghost[] getGhosts(final Pacman pacman) {
		Ghost[] ghostsCopy = new Ghost[ghosts.length];
		
		for (int index = 0; index < ghosts.length; index++)
			ghostsCopy[index] = new Ghost(ghosts[index]){

				@Override
				public boolean isScared() {
					return pacman.haveAntiGhost();
				}			
		};
		
		return ghostsCopy;
	}
	
	public Position[] getGhostsStartPosition() {
		return ghostsStartPositions;
	}
}
