package Search;

public class Node {
	Node parent;
	State currentState;
	int depth;
	int pathCost;
	String operator;
	
	public Node(Node parent, State currentState, int depth, int pathCost, String operator){
		this.parent = parent;
		this.currentState = currentState;
		this.depth = depth;
		this.pathCost = pathCost;
		this.operator = operator;
	}
	
}
