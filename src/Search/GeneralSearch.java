package Search;

import java.util.*;

public class GeneralSearch {
	
	private Deque<Node> nodes;
	private QueuingFunction qingFunc;
	private Problem problem;
	
	public Deque<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Deque<Node> nodes) {
		this.nodes = nodes;
	}

	public QueuingFunction getQingFunc() {
		return qingFunc;
	}

	public void setQingFunc(QueuingFunction qingFunc) {
		this.qingFunc = qingFunc;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public GeneralSearch(Problem problem, QueuingFunction qingFunc) {
		this.problem = problem;
		this.qingFunc = qingFunc;
		nodes = new LinkedList<Node>();
	}
	
	public void search(){
		Node initialNode = new Node(null, this.problem.getInitState(), 0, 0, "");
		nodes.addFirst(initialNode);
		while(!nodes.isEmpty()) {
			Node node = nodes.pop();
			
		}
	}
	
}
