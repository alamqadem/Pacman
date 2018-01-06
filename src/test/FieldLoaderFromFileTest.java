package test;

import java.io.IOException;

import org.junit.Test;

import controller.FieldLoaderFromFile;

public class FieldLoaderFromFileTest {

	@Test
	public void test() {
		FieldLoaderFromFile fieldLoader = null;
		try {
			fieldLoader = new FieldLoaderFromFile("files/original_field.txt");
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(fieldLoader.getField());
	}

}
