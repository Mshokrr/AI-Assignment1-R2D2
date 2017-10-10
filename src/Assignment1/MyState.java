package Assignment1;

import Grid.Cell;
import Search.State;

public class MyState extends State {

	private Cell currentPosition;
	private int unactivatedPads;

	public Cell getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Cell currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getUnactivatedPads() {
		return unactivatedPads;
	}

	public void setUnactivatedPads(int unactivatedPads) {
		this.unactivatedPads = unactivatedPads;
	}

	public MyState(Cell currentPosition, int unactivatedPads, Cell telePosition) {
		this.currentPosition = currentPosition;
		this.unactivatedPads = unactivatedPads;
	}

}
