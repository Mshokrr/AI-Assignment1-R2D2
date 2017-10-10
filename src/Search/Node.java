package Search;

public class Node {
	private Node parent;
	private State currentState;
	private int depth;
	private int pathCost;
	private String operator;
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Node(Node parent, State currentState, int depth, int pathCost, String operator){
		this.parent = parent;
		this.currentState = currentState;
		this.depth = depth;
		this.pathCost = pathCost;
		this.operator = operator;
	}
	
}
