package Tests;

import java.io.IOException;

import Grid.Grid;

public class TestProlog {
	
	public static void main(String[] args) throws IOException {
		Grid g = Grid.genGrid();
		g.displayGrid();
		g.getPrologFile();
	}
}
