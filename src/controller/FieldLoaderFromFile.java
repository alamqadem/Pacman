package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.Field;
import model.Position;

public class FieldLoaderFromFile {
	private final Field field;
	
	public FieldLoaderFromFile(String file) throws NumberFormatException, IOException {
		FileReader fileReader = new FileReader(file);
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int rows = Integer.valueOf(bufferedReader.readLine());
		int columns = Integer.valueOf(bufferedReader.readLine());
		int antiGhostTime = Integer.valueOf(bufferedReader.readLine());
		
		this.field = new Field(rows, columns);
		
		String nextLine = bufferedReader.readLine();
		int index;
		
		for (index = 0; index < rows && nextLine != null; index++) {
			parseRow(index, nextLine, antiGhostTime);	
			nextLine = bufferedReader.readLine();
		}
		
		
		if (nextLine == null && index < rows) {
			bufferedReader.close();
			throw new IllegalStateException("too less rows in the field. Expected rows: " + rows + " Readed: " + index);
		}
		
		bufferedReader.close();
		
	}
	
	private void parseRow(int rowIndex, String row, int antiGhostTime) {
		if (row.length() != field.getColumns())
			throw new IllegalStateException("too less columns in row " + rowIndex + " in the file. Expected columns: " + field.getColumns() +
					" Readed: " + row.length());
		
		int index;
		for (index = 0; index < field.getColumns(); index++)
			switch(row.charAt(index)) {
			
			case 'b': field.setBlocked(new Position(rowIndex, index)); break;
			
			case 't': field.addToffee(new Position(rowIndex, index)); break;
			
			case 'a': field.setItem(new Position(rowIndex, index), new AntiGhost(antiGhostTime)); break;
			
			case ' ': ; break;
			
			default: throw new IllegalArgumentException("Invalid char '" + row.charAt(index) + "' in file");
			}
	}
	
	public Field getField() {
		return field;
	}
}
