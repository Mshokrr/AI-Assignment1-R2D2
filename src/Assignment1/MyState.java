package Assignment1;

import Grid.Cell;
import Grid.Grid;
import Search.State;

public class MyState extends State {

	// implements the general state of a search problem and adds extra
	// information for the state to problem specific to the HelpR2-D2 problem
	private Cell currentPosition;
	private int unactivatedPads;
	private Cell[] rocksPositions;
	private int expandedNodes;

	public int getExpandedNodes() {
		return expandedNodes;
	}

	public void setExpandedNodes(int expandedNodes) {
		this.expandedNodes = expandedNodes;
	}

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

	public MyState(Cell currentPosition, int unactivatedPads,
			Cell[] rocksPositions, int expandedNodes) {
		this.currentPosition = currentPosition;
		this.unactivatedPads = unactivatedPads;
		this.rocksPositions = rocksPositions;
		this.expandedNodes = expandedNodes;
	}

	// calculates a 2 heuristic values depending on value of n is 0 it generates
	// first heuristic formula
	// and if n is 1 is will use the heuristic formula
	public int heuristic(int n) {
		if (n == 0)
			return this.unactivatedPads;
		else
			return Math.abs(HelpR2D2.telePosition.getX() - currentPosition.getX())
					+ Math.abs(HelpR2D2.telePosition.getY()
							- currentPosition.getY());
	}

}
