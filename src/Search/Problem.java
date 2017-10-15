package Search;

import java.util.ArrayList;

public abstract class Problem {
	
	private String [] operators;
	private State initState;
	private State [] stateSpace;
	
	public abstract boolean goalTest(Node node);
	
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
