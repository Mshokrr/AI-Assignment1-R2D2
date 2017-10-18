package Search;

import java.util.ArrayList;

public abstract class Problem {
	
	//Abstract Class Containing the 5 tuples and Describing basic operations for a general Search problem
	private String [] operators;
	//Initial space
	private State initState;
	private State [] stateSpace;
	
	public abstract boolean goalTest(Node node);
	
	public abstract int pathCost(Node n);
	
	public abstract ArrayList<Node> Expand(Node node);
	
	public abstract void clearPastState();
	
	public abstract boolean pastState(Node node);
	
	public String[] getOperators() {
		return operators;
	}

	public void setOperators(String[] operators) {
		this.operators = operators;
	}

	public State getInitState() {
		return initState;
	}

	public void setInitState(State initState) {
		this.initState = initState;
	}

	public State[] getStateSpace() {
		return stateSpace;
	}

	public void setStateSpace(State[] stateSpace) {
		this.stateSpace = stateSpace;
	}
	
}
