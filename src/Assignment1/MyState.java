package Assignment1;

import Grid.Cell;
import Search.State;

public class MyState extends State {

	private Cell currentPosition;
	private int unactivatedPads;
	private Cell [] rocksPositions;

	public Cell[] getRocksPositions() {
		return rocksPositions;
	}

	public void setRocksPositions(Cell[] rocks) {
		this.rocksPositions = rocks;
	}

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

	public MyState(Cell currentPosition, int unactivatedPads, Cell [] rocksPositions) {
		this.currentPosition = currentPosition;
		this.unactivatedPads = unactivatedPads;
		this.rocksPositions = rocksPositions;
	}
	public int heuristic() {
		return this.unactivatedPads;
	}

}
