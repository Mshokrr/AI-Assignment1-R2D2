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
	private int numberOfExpandedNodes;
	
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
		numberOfExpandedNodes = 0;
	}

	public Node search() {
		Node initialNode = new Node(null, this.problem.getInitState(), 0, 0, "");

		if (this.qingFunc == QueuingFunction.BF || this.qingFunc == QueuingFunction.DF) {
			nodes.addFirst(initialNode);
			while (!nodes.isEmpty()) {
				Node node = nodes.pop();
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				numberOfExpandedNodes++;
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}

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
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				numberOfExpandedNodes++;
				
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}

				
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
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				numberOfExpandedNodes++;
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}
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
			if (counter < childNode.getDepth())
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
		currentPosition.setX(1);
		currentPosition.setY(2);

		Cell rockPosition = new Cell();
		rockPosition.setX(1);
		rockPosition.setY(2);
		
		Cell rockPosition2 = new Cell();
		rockPosition2.setX(1);
		rockPosition2.setY(3);
		
		Cell rockPosition3 = new Cell();
		rockPosition3.setX(2);
		rockPosition3.setY(4);
		
		Cell rockPosition4 = new Cell();
		rockPosition4.setX(3);
		rockPosition4.setY(3);
		
		Cell[] rockPositions = new Cell[3];
		rockPositions[0] = rockPosition;
		//rockPositions[1] = rockPosition2;
		rockPositions[1] = rockPosition3;
		rockPositions[2] = rockPosition4;
		
		MyState initState = new MyState(currentPosition, rockPositions.length, rockPositions, 0);
		
		Cell teleport = new Cell();
		teleport.setX(2);
		teleport.setY(2);

		Cell obstacle = new Cell();
		obstacle.setX(0);
		obstacle.setY(0);
		
		Cell obstacle1 = new Cell();
		obstacle1.setX(2);
		obstacle1.setY(0);
		
		Cell[] obstacles = new Cell[2];
		obstacles[0] = obstacle;
		obstacles[1] = obstacle1;

		Cell pad = new Cell();
		pad.setX(0);
		pad.setY(4);
		
		Cell pad1 = new Cell();
		pad1.setX(1);
		pad1.setY(0);
		
		Cell pad2 = new Cell();
		pad2.setX(3);
		pad2.setY(0);
		
		Cell pad3 = new Cell();
		pad3.setX(4);
		pad3.setY(1);
		
		Cell[] pads = new Cell[3];
		pads[0] = pad;
		//pads[1] = pad1;
		pads[1] = pad2;
		pads[2] = pad3;

		String[] ops = new String[4];
		MyState[] stateSpace = new MyState[4];
		
		HelpR2D2 problemR2D2 = new HelpR2D2(ops, initState, stateSpace, teleport, obstacles, pads, 5, 5);
		
//		HelpR2D2 problemR2D2 = new HelpR2D2(new Grid(5, 5, 3, 2));
		
//		for(Cell rockk: ((MyState)((Problem)problemR2D2).getInitState()).getRocksPositions()) {
//			System.out.println("ROCK: "+ rockk.getX()+ ", "+ rockk.getY());
//		}
//		for(Cell padd: problemR2D2.getPadsPositions()) {
//			System.out.println("Pad: "+ padd.getX()+ ", "+ padd.getY());
//		}
//		
//		for(Cell obs: problemR2D2.getObstaclesPositions()) {
//			System.out.println("Obstacle: "+ obs.getX()+ ", "+ obs.getY());
//		}
		
		GeneralSearch gs = new GeneralSearch(problemR2D2, QueuingFunction.BF);
		Node n = gs.search();
		if(n != null)
			System.out.println("max depth: "+ n.getDepth());
		else
			System.out.println("No Solution");
		Deque<String> path = new LinkedList<String>();
		System.out.println(gs.numberOfExpandedNodes);
		while (n != null) {
			path.addFirst(n.getOperator());
			n = n.getParent();
			  
		}
		while(!path.isEmpty()) {
			System.out.print(path.pop() + "->");
		}
		

	}
}
