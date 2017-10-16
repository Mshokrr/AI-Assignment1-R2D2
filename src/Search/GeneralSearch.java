package Search;

import java.util.*;

import Assignment1.HelpR2D2;
import Assignment1.MyState;
import Grid.Cell;
import Grid.Grid;

public class GeneralSearch {

	private Deque<Node> nodes;
	private QueuingFunction qingFunc;
	private PriorityQueue<Node> nodesPrio = new PriorityQueue<>();
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

	public Node search() {
		Node initialNode = new Node(null, this.problem.getInitState(), 0, 0, "");

		if (this.qingFunc == QueuingFunction.BF || this.qingFunc == QueuingFunction.DF) {
			nodes.addFirst(initialNode);
			while (!nodes.isEmpty()) {
				Node node = nodes.pop();
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}

				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				switch (this.qingFunc) {
				case BF:
					nodes = this.BFS(nodes, node);
					break;
				case DF:
					nodes = this.DFS(nodes, node);
					break;
				default:
					break;
				}
			}
		} else if (this.qingFunc == QueuingFunction.ID) {
			return IDS(nodes,initialNode);
		} else {
			nodesPrio.add(initialNode);
			while (!nodesPrio.isEmpty()) {
				Node node = nodesPrio.poll();
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}

				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				switch (this.qingFunc) {
				case UC:
					nodesPrio = this.uniformedCost(node, nodesPrio);
					break;
				case Greedy:
					nodesPrio = this.greedy(node, nodesPrio);
					break;
				case AStar:
					nodesPrio = this.AStar(node, nodesPrio);
					break;
				default:
					break;
				}
			}
		}
		return null;
	}

	public Deque<Node> BFS(Deque<Node> nodes, Node node) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			nodes.addLast(childNode);
		}
		return nodes;
	}

	public Deque<Node> DFS(Deque<Node> nodes, Node node) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			nodes.addFirst(childNode);
		}
		return nodes;
	}

	public Node IDS(Deque<Node> nodes, Node intialNode) {
		int counter = 0;
		do {
			nodes.add(intialNode);
			while (!nodes.isEmpty()) {
				System.out.println("Counter"+counter);
				Node node = nodes.pop();
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				nodes = IDS(nodes, node, counter);
				System.out.println("IDS");
			}
			counter++;
			this.problem.clearPastState();
		} while (true);
	}

	public Deque<Node> IDS(Deque<Node> nodes, Node node, int counter) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			//System.out.println(childNode.getPathCost());
			if (counter < childNode.getPathCost())
				return nodes;
			nodes.addFirst(childNode);
		}
		//System.out.println("size"+ nodes.size());
		return nodes;
	}

	public PriorityQueue<Node> uniformedCost(Node node, PriorityQueue<Node> nodes) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			nodes.add(childNode);
		}
		return nodes;
	}
	
	public PriorityQueue<Node> greedy(Node node, PriorityQueue<Node> nodes) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			int heuristicValue = node.getCurrentState().heuristic();
			childNode.setOrder(heuristicValue);
			nodes.add(childNode);
		}
		return nodes;
	}
	
	public PriorityQueue<Node> AStar(Node node, PriorityQueue<Node> nodes) {
		ArrayList<Node> children = this.problem.Expand(node);
		// int i = 0;
		for (Node childNode : children) {
			// System.out.println(i++);
			int heuristicValue = 3;
			childNode.setOrder(childNode.getPathCost() + heuristicValue);
			nodes.add(childNode);
		}
		return nodes;
	}

	public static void main(String[] args) {
		Cell currentPosition = new Cell();
		currentPosition.setX(0);
		currentPosition.setY(2);

		Cell rockPosition = new Cell();
		rockPosition.setX(1);
		rockPosition.setY(1);
		
		Cell rockPosition2 = new Cell();
		rockPosition.setX(3);
		rockPosition.setY(1);
		
		Cell rockPosition3 = new Cell();
		rockPosition.setX(1);
		rockPosition.setY(3);

		Cell[] rockPositions = new Cell[3];
		rockPositions[0] = rockPosition;
		rockPositions[1] = rockPosition2;
		rockPositions[2] = rockPosition3;

		MyState initState = new MyState(currentPosition, 1, rockPositions);

		Cell teleport = new Cell();
		teleport.setX(3);
		teleport.setY(3);

		Cell obstacle = new Cell();
		obstacle.setX(0);
		obstacle.setY(0);
		
		Cell obstacle1 = new Cell();
		obstacle.setX(2);
		obstacle.setY(0);
		
		Cell[] obstacles = new Cell[2];
		obstacles[0] = obstacle;
		obstacles[1] = obstacle1;

		Cell pad = new Cell();
		pad.setX(3);
		pad.setY(0);
		
		Cell pad1 = new Cell();
		pad.setX(0);
		pad.setY(1);
		
		Cell pad2 = new Cell();
		pad.setX(2);
		pad.setY(3);
		
		Cell[] pads = new Cell[3];
		pads[0] = pad;
		pads[1] = pad1;
		pads[2] = pad2;

		String[] ops = new String[4];
		MyState[] stateSpace = new MyState[4];

		HelpR2D2 problemR2D2 = new HelpR2D2(ops, initState, stateSpace, teleport, obstacles, pads, 5, 5);

		GeneralSearch gs = new GeneralSearch(problemR2D2, QueuingFunction.BF);
		Node n = gs.search();
		System.out.println("max depth: "+ n.getDepth());
		Deque<String> path = new LinkedList<String>();
		while (n != null) {
			path.addFirst(n.getOperator());
			n = n.getParent();
			
		}
		while(!path.isEmpty()) {
			System.out.print(path.pop() + "->");
		}
		

		
	}
}
