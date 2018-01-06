package test;

import java.io.IOException;

import model.Ghost;
import model.Pacman;
import model.Position;

import org.junit.Test;

import controller.GhostsLoaderFromFile;

public class GhostLoaderFromFileTest {

	@Test
	public void test() {
		Pacman pacman = new Pacman(2, 3, 0);
		
		
		GhostsLoaderFromFile ghostLoader = null;
		
		try {
			ghostLoader = new GhostsLoaderFromFile("files/ghosts.txt", pacman);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Ghost[] ghosts = ghostLoader.getGhosts();
		Position[] startPositions = ghostLoader.getStartPositions();
		
		for (int index = 0; index < ghosts.length; index++)
			System.out.println("name: " + ghosts[index].getName() + 
								"\nspeed: " + ghosts[index].getSpeed() + 
								"\naggressiveness: " + ghosts[index].getAggressiveness() + 
								"\nintelligence: " + ghosts[index].getIntelligence() + 
								"\nstart position: " + startPositions[index]);
	}

}
