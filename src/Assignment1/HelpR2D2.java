package Assignment1;

import java.util.ArrayList;

import Grid.Cell;
import Search.Node;
import Search.Problem;
import Search.State;

public class HelpR2D2 extends Problem {
	private Cell telePosition;
	private Cell[] obstaclesPositions;
	private Cell[] padsPositions;

	public Cell getTelePosition() {
		return telePosition;
	}

	public void setTelePosition(Cell telePosition) {
		this.telePosition = telePosition;
	}

	public Cell[] getObstaclesPositions() {
		return obstaclesPositions;
	}

	public void setObstaclesPositions(Cell[] obstaclesPositions) {
		this.obstaclesPositions = obstaclesPositions;
	}

	public Cell[] getPadsPositions() {
		return padsPositions;
	}

	public void setPadsPositions(Cell[] padsPositions) {
		this.padsPositions = padsPositions;
	}

	public HelpR2D2(String[] operators, State initState, State[] stateSpace, Cell telePosition,
			Cell[] obstaclesPosition, Cell[] padsPositions) {
		super.setOperators(operators);
		super.setInitState(initState);
		this.telePosition = telePosition;
		this.obstaclesPositions = obstaclesPosition;
		this.padsPositions = padsPositions;
		// super.stateSpace?
	}

	public int pathCost(Node n) {

		// return path cost later
		return 0;
	}

	@Override
	public boolean goalTest(Node node) {
		MyState state = (MyState) node.getCurrentState();
		if (state.getCurrentPosition().equals(this.telePosition)) { // comparing
																	// issue
			System.out.println("R2D2 on teleport cell!");
			if (state.getUnactivatedPads() == 0) {
				System.out.println("Goal success");
				return true;
			}

		}
		return false;
	}

	@Override
	public ArrayList<Node> Expand(Node node, String operator) {
		switch (operator) {
		case "UP": return up(node);
		case "DOWN": return down(node);
		case "LEFT": return left(node);
		case "RIGHT": return right(node);
		}
		return null;
	}
	public ArrayList<Node> up(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell [] rocksPositions = state.getRocksPositions();
		Cell currentPosition = state.getCurrentPosition();
		
		return null;
	}
	public static ArrayList<Node> down(Node node) {
		return null;
	}
	public static ArrayList<Node> left(Node node) {
		return null;
	}
	public static ArrayList<Node> right(Node node) {
		return null;
	}
}
