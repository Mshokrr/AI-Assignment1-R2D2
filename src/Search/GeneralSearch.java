package Search;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Assignment1.HelpR2D2;
import Assignment1.MyState;
import Grid.Cell;
import Grid.Grid;
import Tests.NoSolutionException;

//Implementing the General Search algorithm
public class GeneralSearch {

	private QueuingFunction qingFunc;
	private Problem problem;
	// Queue used for BFS, DFS and Iterative Deepening
	private Deque<Node> nodes;
	// Queue used for Uniform Cost, Greedy and A*
	private PriorityQueue<Node> nodesPrio = new PriorityQueue<>();

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

	public GeneralSearch(Grid grid, QueuingFunction qingFunc) {
		this.problem = new HelpR2D2(grid);
		this.qingFunc = qingFunc;
		nodes = new LinkedList<Node>();
	}

	// Search function traversing the tree
	public Node search() {
		// root node, start of the problem
		Node initialNode = new Node(null, this.problem.getInitState(), 0, 0, "");

		if (this.qingFunc == QueuingFunction.BF
				|| this.qingFunc == QueuingFunction.DF) {
			nodes.addFirst(initialNode);
			while (!nodes.isEmpty()) {
				Node node = nodes.pop();
				// for DF only as checking repeated states for BF is very
				// expensive and useless due to checking leaves on same level
				// once which are not repeated
				// this.qingFunc == QueuingFunction.DF&&
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
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
			return IDS(nodes, initialNode);
		} else {
			nodesPrio.add(initialNode);
			while (!nodesPrio.isEmpty()) {
				Node node = nodesPrio.poll();
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before

				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}

				switch (this.qingFunc) {
				case UC:
					nodesPrio = this.uniformedCost(node, nodesPrio);
					break;
				case GR1:
					nodesPrio = this.greedy(node, nodesPrio, 0);
					break;
				case GR2:
					nodesPrio = this.greedy(node, nodesPrio, 1);
					break;
				case AS1:
					nodesPrio = this.AStar(node, nodesPrio, 0);
					break;
				case AS2:
					nodesPrio = this.AStar(node, nodesPrio, 1);
					break;
				default:
					break;
				}
			}
		}
		return null;
	}

	// Breadth First Search Node Expansion
	public Deque<Node> BFS(Deque<Node> nodes, Node node) {
		ArrayList<Node> children = this.problem.Expand(node);
		for (Node childNode : children) {
			nodes.addLast(childNode);
		}
		return nodes;
	}

	// Depth First Search Node Expansion
	public Deque<Node> DFS(Deque<Node> nodes, Node node) {
		ArrayList<Node> children = this.problem.Expand(node);
		for (Node childNode : children) {
			nodes.addFirst(childNode);
		}
		return nodes;
	}

	// Iterative Deepening Search Node Expansion
	public Node IDS(Deque<Node> nodes, Node intialNode) {
		int searchDepth = 0;
		// Starts with a depth of 0
		do {
			nodes.add(intialNode);
			while (!nodes.isEmpty()) {
				Node node = nodes.pop();
				if (this.problem.pastState(node))
					continue; // check if state is already traversed before
				if (problem.goalTest(node)) {
					System.out.println("SUCCESS!!");
					return node;
				}
				// Do a search to a certain depth in this iteration
				nodes = IDS(nodes, node, searchDepth);
			}
			// increase depth for next iteration
			searchDepth++;
			// reset states to start fresh
			this.problem.clearPastState();
			// loops to infinity or finding a solution
		} while (true);
	}

	// Depth First Search Limited Depth Node Expansion
	public Deque<Node> IDS(Deque<Node> nodes, Node node, int depth) {
		ArrayList<Node> children = this.problem.Expand(node);
		for (Node childNode : children) {
			if (depth < childNode.getDepth())
				return nodes;
			nodes.addFirst(childNode);
		}
		return nodes;
	}

	// Uniform Cost Search Node Expansion
	public PriorityQueue<Node> uniformedCost(Node node,
			PriorityQueue<Node> nodes) {
		ArrayList<Node> children = this.problem.Expand(node);
		for (Node childNode : children) {
			nodes.add(childNode);
		}
		return nodes;
	}

	// Greedy Search Node Expansion, n is used to pick between the 2 heuristic
	// functions
	public PriorityQueue<Node> greedy(Node node, PriorityQueue<Node> nodes,
			int n) {
		ArrayList<Node> children = this.problem.Expand(node);
		for (Node childNode : children) {
			int heuristicValue = node.getCurrentState().heuristic(n);
			childNode.setOrder(heuristicValue);
			nodes.add(childNode);
		}
		return nodes;
	}

	// A* Search Node Expansion, n is used to pick between the 2 heuristic
	// functions
	public PriorityQueue<Node> AStar(Node node, PriorityQueue<Node> nodes, int n) {
		ArrayList<Node> children = this.problem.Expand(node);
		for (Node childNode : children) {
			int heuristicValue = node.getCurrentState().heuristic(n);
			childNode.setOrder(childNode.getPathCost() + heuristicValue);
			nodes.add(childNode);
		}
		return nodes;
	}

	// A method to regenerate a Grid from the search nodes to visualize every
	// step
	public static Grid visualize(GeneralSearch gs, Node n) {
		Cell[] pads = ((HelpR2D2) gs.problem).getPadsPositions();
		Cell tele = ((HelpR2D2) gs.problem).getTelePosition();
		Cell[] obstacles = ((HelpR2D2) gs.problem).getObstaclesPositions();
		int width = ((HelpR2D2) gs.problem).getWidth();
		int height = ((HelpR2D2) gs.problem).getHeight();
		Cell[] rocks = ((MyState) n.getCurrentState()).getRocksPositions();
		Cell position = ((MyState) n.getCurrentState()).getCurrentPosition();
		return new Grid(width, height, obstacles, pads, rocks, tele, position);
	}

	// Search Method as required takes a grid and a queuing strategy and boolean
	// for visualizing or not
	public static Deque<Grid> search(Grid grid, QueuingFunction strategy,
			boolean visualization) throws NoSolutionException {

		// Initialize a general search problem
		GeneralSearch gs = new GeneralSearch(grid, strategy);
		// Performs search
		Node n = gs.search();
		// Checks for a solution
		System.out.println("Using Expansion Strategy: " + strategy);
		if (n != null)
			System.out.println("Solution Depth: " + n.getPathCost());
		else {
			System.out.println("No Solution");
			throw new NoSolutionException();
		}
		System.out.println("Expanded Nodes: "
				+ ((HelpR2D2) gs.getProblem()).getNumberOfExpandedNodes());

		// Displaying Solution Path from Start to End
		Deque<String> path = new LinkedList<String>();
		Deque<Grid> grids = new LinkedList<>();
		Deque<Grid> grids1 = new LinkedList<>();
		while (n != null) {
			if (!n.getOperator().equals(""))
				path.addFirst(n.getOperator());
			grids.addFirst(visualize(gs, n));
			grids1.addFirst(visualize(gs, n));
			n = n.getParent();
		}
		while (!path.isEmpty()) {
			System.out.print(path.pop());
			if (!path.isEmpty())
				System.out.print("->");
		}
		System.out.println();
		System.out.println();

		if (visualization)
			while (!grids.isEmpty()) {
				grids.pop().displayGrid();
			}
		
		grids1.pop();
		
		return grids1;
	}

	// public static void main(String[] args) {
	//
	// search(new Grid(), QueuingFunction.DF, true);
	//
	// }
}
