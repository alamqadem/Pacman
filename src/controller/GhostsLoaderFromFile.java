package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Ghost;
import model.Pacman;
import model.Position;

public class GhostsLoaderFromFile {
	private final Ghost[] ghosts;
	private final Position[] startPositions;
	
	public GhostsLoaderFromFile(String fileName, Pacman pacman) throws NumberFormatException, IOException {
		FileReader fileReader = new FileReader(fileName);
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int numberOfGhosts = Integer.valueOf(bufferedReader.readLine());
		
		if (numberOfGhosts <= 0) {
			bufferedReader.close();
			throw new IllegalArgumentException("Number of ghosts must be positive");
		}
		
		ghosts = new Ghost[numberOfGhosts];
		startPositions = new Position[numberOfGhosts];
		
		int index = 0;
		GhostData data = getNextGhost(bufferedReader, pacman);
		while (index < numberOfGhosts && data != null) {
			ghosts[index] = data.ghost;
			startPositions[index] = data.startPosition;
			
			data = getNextGhost(bufferedReader, pacman);
			index++;
		}
		
		bufferedReader.close();
		
		if (index < numberOfGhosts)
			throw new IllegalArgumentException("Too less ghosts in the file");
	}
	
	private GhostData getNextGhost(BufferedReader reader, final Pacman pacman) throws NumberFormatException, IOException {
		String line;
		
		line = reader.readLine();
		if (line == null)
			return null;
		String name = line;
		
		line = reader.readLine();
		if (line == null)
			return null;
		int speed = Integer.valueOf(line);
		
		line = reader.readLine();
		if (line == null)
			return null;
		int aggressiveness = Integer.valueOf(line);
		
		line = reader.readLine();
		if (line == null)
			return null;
		int intelligence = Integer.valueOf(line);
		
		Position startPosition = getPosition(reader);
		if (startPosition == null)
			return null;
		
		Ghost ghost = new Ghost(name, speed, aggressiveness, intelligence){

			@Override
			public boolean isScared() {
				return pacman.haveAntiGhost();
			}
		};

		return new GhostData(ghost, startPosition);
	}
	
	private Position getPosition(BufferedReader reader) throws IOException {
		String text = reader.readLine();
		
		if (text == null)
			return null;
		
		String rowAsString = text.substring(text.indexOf('(')+1, text.indexOf(','));
		String columnAsString = text.substring(text.indexOf(',')+1, text.indexOf(')'));
		
		int row = Integer.valueOf(rowAsString);
		int column = Integer.valueOf(columnAsString);
		
		return new Position(row, column);
	}
	
	private class GhostData {
		public final Ghost ghost;
		public final Position startPosition;
		
		public GhostData(final Ghost ghost, final Position startPosition) {
			this.ghost = ghost;
			this.startPosition = startPosition;
		}
	}

	public Ghost[] getGhosts() {
		return ghosts;
	}
	
	public Position[] getStartPositions() {
		return startPositions;
	}

}
