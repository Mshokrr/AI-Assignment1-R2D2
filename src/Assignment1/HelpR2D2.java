package Assignment1;

import java.util.ArrayList;

import Grid.Cell;
import Grid.CellStatus;
import Grid.Grid;
import Search.Node;
import Search.Problem;
import Search.State;

//Extends the General Search Problem and adds extra info and checks for this specific application
public class HelpR2D2 extends Problem {
	
	// Cell having teleport position
	public static Cell telePosition;
	// Array containing a Cell of obstacles
	private Cell[] obstaclesPositions;
	// Array containing a Cell of pressure pads
	private Cell[] padsPositions;
	private int height;
	private int width;
	// ArrayList used to store previous expanded state to avoid exploring same
	// state twice
	private ArrayList<MyState> expandedStates;
	//Counter to get number of expanded nodes
	private int numberOfExpandedNodes;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Cell getTelePosition() {
		return telePosition;
	}

	public void setTelePosition(Cell telePosition) {
		HelpR2D2.telePosition = telePosition;
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

	public HelpR2D2(String[] operators, State initState, State[] stateSpace,
			Cell telePosition, Cell[] obstaclesPosition, Cell[] padsPositions,
			int width, int height) {
		super.setOperators(operators);
		super.setInitState(initState);
		HelpR2D2.telePosition = telePosition;
		this.obstaclesPositions = obstaclesPosition;
		this.padsPositions = padsPositions;
		this.expandedStates = new ArrayList<MyState>();
		this.height = height;
		this.width = width;
		setNumberOfExpandedNodes(0);
		// super.stateSpace?
	}

	public HelpR2D2(Grid grid) {
		super.setOperators(new String[4]);
		HelpR2D2.telePosition = grid.getTeleportPosition();
		this.obstaclesPositions = grid.getObstaclePositions();
		this.padsPositions = grid.getPadPositions();
		this.expandedStates = new ArrayList<MyState>();
		this.height = grid.getHeight();
		this.width = grid.getWidth();
		MyState initState = new MyState(grid.getAgentPosition(),
				grid.getRockPositions().length, grid.getRockPositions(), 0);
		super.setInitState(initState);
		setNumberOfExpandedNodes(0);
		// super.stateSpace?
	}

	public int pathCost(Node n) {
		return n.getPathCost();
	}

	@Override
	public boolean goalTest(Node node) {
		setNumberOfExpandedNodes(getNumberOfExpandedNodes() + 1);
		MyState state = (MyState) node.getCurrentState();
		// Checking agent position is standing on the teleport
		if (state.getCurrentPosition().getX() == this.getTelePosition().getX()
				&& state.getCurrentPosition().getY() == this.getTelePosition()
						.getY()) {
			// Making sure all pressure pads are activated
			if (state.getUnactivatedPads() == 0) {
				return true;
			}

		}
		return false;
	}

	public ArrayList<MyState> getExpandedStates() {
		return expandedStates;
	}

	public void setExpandedStates(ArrayList<MyState> expandedStates) {
		this.expandedStates = expandedStates;
	}

	// Checking if this node is similar to a previous node to avoid closed loops
	// in same direction
	public boolean pastState(Node node) {
		MyState temp = null;
		MyState state = (MyState) node.getCurrentState();
		for (MyState s : expandedStates) {
			// Comparing current position to states with same position
			if (s.getCurrentPosition().getX() == state.getCurrentPosition()
					.getX()
					&& s.getCurrentPosition().getY() == state
							.getCurrentPosition().getY()) {
				// Comparing current active pressure pads to states with same
				// number
				if (s.getUnactivatedPads() == state.getUnactivatedPads()) {
					boolean identical_rocks = true;
					// Comparing current rock positions to states with same
					// positions
					for (int i = 0; i < s.getRocksPositions().length; i++) {
						if (s.getRocksPositions()[i].getX() != state
								.getRocksPositions()[i].getX()
								|| s.getRocksPositions()[i].getY() != state
										.getRocksPositions()[i].getY()) {
							identical_rocks = false;
						}
					}
					if (identical_rocks) {
						// Checking if path length to this state is longer or
						// same
						if (state.getExpandedNodes() >= s.getExpandedNodes())
							return true;
						else
							temp = s;
					}
				}
			}

		}
		// removing similar states with difference in path length leaving
		// shortest one
		if (temp != null)
			expandedStates.remove(temp);
		expandedStates.add(state);
		return false;
	}

	// Applying possible Operators to expand each node
	@Override
	public ArrayList<Node> Expand(Node node) {

		ArrayList<Node> result = new ArrayList<Node>();
		// Up operator
		Node up = this.up(node);
		if (up != null)
			result.add(up);
		// Down operator
		Node down = this.down(node);
		if (down != null)
			result.add(down);
		// right operator
		Node right = this.right(node);
		if (right != null)
			result.add(right);
		// left operator
		Node left = this.left(node);
		if (left != null)
			result.add(left);

		return result;
	}

	// Creating a new object with a new refrence to avoid pass by reference
	public Cell[] instantiateObject(Cell[] rocksPositions) {
		Cell[] result = new Cell[rocksPositions.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new Cell();
			result[i].setHasRock(true);
			result[i].setX(rocksPositions[i].getX());
			result[i].setY(rocksPositions[i].getY());
		}
		return result;
	}

	public Node up(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = instantiateObject(state.getRocksPositions());
		Cell currentPosition = new Cell();
		currentPosition.setX(state.getCurrentPosition().getX());
		currentPosition.setY(state.getCurrentPosition().getY());
		int unactivatedPads = state.getUnactivatedPads();
		Cell[] padsPositions = this.getPadsPositions();
		Cell[] obstaclesPostitions = this.getObstaclesPositions();

		// check if upper cell is a ceiling
		if (currentPosition.getY() == 0)
			return null;

		// get upper cell
		Cell upperCell = new Cell();

		// check if upper cell is an obstacle
		for (int i = 0; i < obstaclesPostitions.length; i++) {
			if (obstaclesPostitions[i].getX() == currentPosition.getX()
					&& obstaclesPostitions[i].getY() == currentPosition.getY() - 1) {
				return null;
			}
		}

		// check if upper cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getX() == currentPosition.getX()
					&& rocksPositions[i].getY() == currentPosition.getY() - 1) {
				rockIndex = i;
				upperCell.setHasRock(true);
				upperCell.setX(rocksPositions[i].getX());
				upperCell.setY(rocksPositions[i].getY());
				// check if the rock is on pad
				for (int j = 0; j < this.padsPositions.length; j++) {
					if (padsPositions[j].getX() == currentPosition.getX()
							&& padsPositions[j].getY() == currentPosition
									.getY() - 1) {
						upperCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}
		// for now if a rock is on a pad don't move it
		if (upperCell.isActivated())
			return null;

		// upper cell is a rock
		if (upperCell.getHasRock()) {

			// check if second to upper cell is a ceiling
			if (currentPosition.getY() == 1)
				return null;

			// get second to upper

			// check if second to upper is a rock
			for (int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getX() == currentPosition.getX()
						&& rocksPositions[i].getY() == currentPosition.getY() - 2) {
					return null;
				}
			}
			// check if second to upper is an obstacle
			for (int i = 0; i < obstaclesPostitions.length; i++) {
				if (obstaclesPostitions[i].getX() == currentPosition.getX()
						&& obstaclesPostitions[i].getY() == currentPosition
								.getY() - 2) {
					return null;
				}
			}
			// check if second to upper is a pad
			for (int i = 0; i < padsPositions.length; i++) {
				if (padsPositions[i].getX() == currentPosition.getX()
						&& padsPositions[i].getY() == currentPosition.getY() - 2) {
					unactivatedPads -= 1;
					break;
				}
			}

			// Move Rock up
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY() - 1);
			rocksPositions[rockIndex]
					.setY(rocksPositions[rockIndex].getY() - 1);

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "UP");

			return newNode;

		}
		// upper cell is free
		else {
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY() - 1);

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "UP");

			return newNode;
		}

	}

	public Node down(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = instantiateObject(state.getRocksPositions());
		Cell currentPosition = new Cell();
		currentPosition.setX(state.getCurrentPosition().getX());
		currentPosition.setY(state.getCurrentPosition().getY());
		int unactivatedPads = state.getUnactivatedPads();
		Cell[] padsPositions = this.getPadsPositions();
		Cell[] obstaclesPostitions = this.getObstaclesPositions();

		// check if lower cell is a floor
		if (currentPosition.getY() == this.getHeight() - 1)
			return null;

		// get lower cell
		Cell lowerCell = new Cell();

		// check if lower cell is an obstacle
		for (int i = 0; i < obstaclesPostitions.length; i++) {
			if (obstaclesPostitions[i].getX() == currentPosition.getX()
					&& obstaclesPostitions[i].getY() == currentPosition.getY() + 1) {
				return null;
			}
		}

		// check if lower cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getX() == currentPosition.getX()
					&& rocksPositions[i].getY() == currentPosition.getY() + 1) {
				rockIndex = i;
				lowerCell.setHasRock(true);
				lowerCell.setX(rocksPositions[i].getX());
				lowerCell.setY(rocksPositions[i].getY());
				// check if the rock is on pad
				for (int j = 0; j < this.padsPositions.length; j++) {
					if (padsPositions[j].getX() == currentPosition.getX()
							&& padsPositions[j].getY() == currentPosition
									.getY() + 1) {
						lowerCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}

		// for now if a rock is on a pad don't move it
		if (lowerCell.isActivated())
			return null;

		// lower cell is a rock
		if (lowerCell.getHasRock()) {

			// check if second to lower cell is a floor
			if (currentPosition.getY() == this.getHeight() - 2)
				return null;

			// get second to lower

			// check if second to lower is a rock
			for (int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getX() == currentPosition.getX()
						&& rocksPositions[i].getY() == currentPosition.getY() + 2) {
					return null;
				}
			}
			// check if second to lower is an obstacle
			for (int i = 0; i < obstaclesPostitions.length; i++) {
				if (obstaclesPostitions[i].getX() == currentPosition.getX()
						&& obstaclesPostitions[i].getY() == currentPosition
								.getY() + 2) {
					return null;
				}
			}
			// check if second to lower is a pad
			for (int i = 0; i < padsPositions.length; i++) {
				if (padsPositions[i].getX() == currentPosition.getX()
						&& padsPositions[i].getY() == currentPosition.getY() + 2) {
					unactivatedPads -= 1;
					break;
				}
			}

			// Move rock down
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY() + 1);
			rocksPositions[rockIndex]
					.setY(rocksPositions[rockIndex].getY() + 1);

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "DOWN");

			return newNode;

		} else {
			// lower cell is free
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY() + 1);

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "DOWN");

			return newNode;
		}
	}

	public Node left(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = instantiateObject(state.getRocksPositions());
		Cell currentPosition = new Cell();
		currentPosition.setX(state.getCurrentPosition().getX());
		currentPosition.setY(state.getCurrentPosition().getY());
		int unactivatedPads = state.getUnactivatedPads();
		Cell[] padsPositions = this.getPadsPositions();
		Cell[] obstaclesPostitions = this.getObstaclesPositions();
		// check if left cell is a wall
		if (currentPosition.getX() == 0)
			return null;

		// get left cell
		Cell leftCell = new Cell();

		// check if left cell is an obstacle
		for (int i = 0; i < obstaclesPostitions.length; i++) {
			if (obstaclesPostitions[i].getY() == currentPosition.getY()
					&& obstaclesPostitions[i].getX() == currentPosition.getX() - 1) {
				return null;
			}
		}
		// check if left cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getY() == currentPosition.getY()
					&& rocksPositions[i].getX() == currentPosition.getX() - 1) {
				rockIndex = i;
				leftCell.setHasRock(true);
				leftCell.setX(rocksPositions[i].getX());
				leftCell.setY(rocksPositions[i].getY());
				// check if the rock is on pad
				for (int j = 0; j < this.padsPositions.length; j++) {
					if (padsPositions[j].getY() == currentPosition.getY()
							&& padsPositions[j].getX() == currentPosition
									.getX() - 1) {
						leftCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}
		// for now if a rock is on a pad don't move it
		if (leftCell.isActivated())
			return null;

		// left cell is a rock
		if (leftCell.getHasRock()) {

			// check if second to left cell is a wall
			if (currentPosition.getX() == 1)
				return null;

			// get second to left

			// check if second to left is a rock
			for (int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getY() == currentPosition.getY()
						&& rocksPositions[i].getX() == currentPosition.getX() - 2) {
					return null;
				}
			}
			// check if second to left is an obstacle
			for (int i = 0; i < obstaclesPostitions.length; i++) {
				if (obstaclesPostitions[i].getY() == currentPosition.getY()
						&& obstaclesPostitions[i].getX() == currentPosition
								.getX() - 2) {
					return null;
				}
			}
			// check if second to left is a pad
			for (int i = 0; i < padsPositions.length; i++) {
				if (padsPositions[i].getY() == currentPosition.getY()
						&& padsPositions[i].getX() == currentPosition.getX() - 2) {

					unactivatedPads -= 1;
					break;
				}
			}
			// Move Rock Left
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX() - 1);
			newPosition.setY(currentPosition.getY());
			rocksPositions[rockIndex]
					.setX(rocksPositions[rockIndex].getX() - 1);

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "LEFT");

			return newNode;

		}
		// left cell is free
		else {
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX() - 1);
			newPosition.setY(currentPosition.getY());

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "LEFT");

			return newNode;
		}
	}

	public Node right(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = instantiateObject(state.getRocksPositions());
		Cell currentPosition = new Cell();
		currentPosition.setX(state.getCurrentPosition().getX());
		currentPosition.setY(state.getCurrentPosition().getY());
		int unactivatedPads = state.getUnactivatedPads();
		Cell[] padsPositions = this.getPadsPositions();
		Cell[] obstaclesPostitions = this.getObstaclesPositions();

		// check if right cell is a wall
		if (currentPosition.getX() >= this.getWidth() - 1)
			return null;

		// get right cell
		Cell rightCell = new Cell();

		// check if right cell is an obstacle
		for (int i = 0; i < obstaclesPostitions.length; i++) {
			if (obstaclesPostitions[i].getY() == currentPosition.getY()
					&& obstaclesPostitions[i].getX() == currentPosition.getX() + 1) {
				return null;
			}
		}

		// check if right cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getY() == currentPosition.getY()
					&& rocksPositions[i].getX() == currentPosition.getX() + 1) {
				rockIndex = i;
				rightCell.setHasRock(true);
				rightCell.setX(rocksPositions[i].getX());
				rightCell.setY(rocksPositions[i].getY());
				// check if the rock is on pad
				for (int j = 0; j < this.padsPositions.length; j++) {
					if (padsPositions[j].getY() == currentPosition.getY()
							&& padsPositions[j].getX() == currentPosition
									.getX() + 1) {
						rightCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}

		// for now if a rock is on a pad don't move it
		if (rightCell.isActivated())
			return null;

		// right cell is a rock
		if (rightCell.getHasRock()) {

			// check if second to right cell is a wall
			if (currentPosition.getX() == this.getWidth() - 2)
				return null;

			// get second to right

			// check if second to right is a rock
			for (int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getY() == currentPosition.getY()
						&& rocksPositions[i].getX() == currentPosition.getX() + 2) {
					return null;
				}
			}
			// check if second to right is an obstacle
			for (int i = 0; i < obstaclesPostitions.length; i++) {
				if (obstaclesPostitions[i].getY() == currentPosition.getY()
						&& obstaclesPostitions[i].getX() == currentPosition
								.getX() + 2) {
					return null;
				}
			}
			// check if second to right is a pad
			for (int i = 0; i < padsPositions.length; i++) {
				if (padsPositions[i].getY() == currentPosition.getY()
						&& padsPositions[i].getX() == currentPosition.getX() + 2) {
					unactivatedPads -= 1;
					break;
				}
			}

			// Move Rock Right
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX() + 1);
			newPosition.setY(currentPosition.getY());
			rocksPositions[rockIndex]
					.setX(rocksPositions[rockIndex].getX() + 1);

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "RIGHT");

			return newNode;

		} else {
			// Right cell is free
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX() + 1);
			newPosition.setY(currentPosition.getY());

			MyState newState = new MyState(newPosition, unactivatedPads,
					rocksPositions, state.getExpandedNodes() + 1);

			Node newNode = new Node(node, newState, node.getDepth() + 1,
					node.getPathCost() + 1, "RIGHT");

			return newNode;
		}
	}

	@Override
	public void clearPastState() {
		this.expandedStates.clear();

	}

	public int getNumberOfExpandedNodes() {
		return numberOfExpandedNodes;
	}

	public void setNumberOfExpandedNodes(int numberOfExpandedNodes) {
		this.numberOfExpandedNodes = numberOfExpandedNodes;
	}
}
