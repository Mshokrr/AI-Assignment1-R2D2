package Assignment1;

import Grid.Cell;
import Search.State;

public class MyState extends State {

	Cell currentPosition;
	int unactivatedPads;

	public MyState(Cell currentPosition, int unactivatedPads, Cell telePosition) {
		this.currentPosition = currentPosition;
		this.unactivatedPads = unactivatedPads;
	}

}
