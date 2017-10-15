package Search;

public class Node implements Comparable {
	private Node parent;
	private State currentState;
	private int depth;
	private int pathCost;
	private String operator;
	private int order;
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

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
		this.order = this.pathCost;
	}

	@Override
	public int compareTo(Object o) {
		Node n = (Node) o;
		if(n.order > this.order) return -1;
		else if(n.order < this.order) return 1;
		return 0;
	}
	
}
