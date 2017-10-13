package Assignment1;

import java.util.ArrayList;

import Grid.Cell;
import Grid.CellStatus;
import Grid.Grid;
import Search.Node;
import Search.Problem;
import Search.State;

public class HelpR2D2 extends Problem {
	private Cell telePosition;
	private Cell[] obstaclesPositions;
	private Cell[] padsPositions;
    private int height;
    private int width;
    
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
		this.telePosition = telePosition;
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

	public HelpR2D2(String[] operators, State initState, State[] stateSpace, Cell telePosition,
			Cell[] obstaclesPosition, Cell[] padsPositions) {
		super.setOperators(operators);
		super.setInitState(initState);
		this.telePosition = telePosition;
		this.obstaclesPositions = obstaclesPosition;
		this.padsPositions = padsPositions;
		// super.stateSpace?
	}

	public int pathCost(Node n) {

		// return path cost later
		return 0;
	}

	@Override
	public boolean goalTest(Node node) {
		MyState state = (MyState) node.getCurrentState();
		if (state.getCurrentPosition().equals(this.telePosition)) { // comparing
																	// issue
			System.out.println("R2D2 on teleport cell!");
			if (state.getUnactivatedPads() == 0) {
				System.out.println("Goal success");
				return true;
			}

		}
		return false;
	}

	@Override
	public ArrayList<Node> Expand(Node node) {

		return null;
	}

	public Node up(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = state.getRocksPositions();
		Cell currentPosition = state.getCurrentPosition();
		int unactivatedPads = state.getUnactivatedPads();
		Cell [] padsPositions = this.getPadsPositions();
		Cell [] obstaclesPostitions = this.getObstaclesPositions();
		
		// check if upper cell is a ceiling
		if (currentPosition.getY() == 0)
			return null;
		
		//get upper cell
		Cell upperCell = new Cell();
		
		//check if upper cell is an obstacle
		for(int i = 0; i < obstaclesPostitions.length; i++) {
			if(obstaclesPostitions[i].getX() == currentPosition.getX()
					&& obstaclesPostitions[i].getY() == currentPosition.getY() - 1) {
				return null;
			}
		}
		
		//check if upper cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getX() == currentPosition.getX() 
					&& rocksPositions[i].getY() == currentPosition.getY() - 1) {
				rockIndex = i;
				upperCell.setHasRock(true);
				upperCell.setX(rocksPositions[i].getX());
				upperCell.setY(rocksPositions[i].getY());
				//check if the rock is on pad
				for(int j = 0; j < this.padsPositions.length; j++) {
					if(padsPositions[i].getX() == currentPosition.getX()
							&& padsPositions[i].getY() == currentPosition.getY() - 1) {
						upperCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}
		//for now if a rock is on a pad don't move it
		if(upperCell.isActivated()) return null;
		
		//upper cell is a rock
		if(upperCell.getHasRock()) {
			
			//check if second to upper cell is a ceiling
			if(currentPosition.getY() == 1) return null;
			
			//get second to upper
			
			//check if second to upper is a rock
			for(int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getX() == currentPosition.getX() 
						&& rocksPositions[i].getY() == currentPosition.getY() - 2) {
					return null;
				}
			}
			//check if second to upper is an obstacle
			for(int i = 0; i < obstaclesPostitions.length; i++) {
				if(obstaclesPostitions[i].getX() == currentPosition.getX()
						&& obstaclesPostitions[i].getY() == currentPosition.getY() - 2) {
					return null;
				}
			}
			//check if second to upper is a pad
			for(int i = 0; i < padsPositions.length; i++) {
				if(padsPositions[i].getX() == currentPosition.getX()
						&& padsPositions[i].getY() == currentPosition.getY() - 2) {
					unactivatedPads -= 1; break;
				}
			}
			
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY()-1);
			rocksPositions[rockIndex].setY(rocksPositions[rockIndex].getY() - 1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "UP");
			
			return newNode;
				
		}
		//upper cell is free
		else {
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY()-1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "UP");
			
			return newNode;
		}

	}

	public Node down(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = state.getRocksPositions();
		Cell currentPosition = state.getCurrentPosition();
		int unactivatedPads = state.getUnactivatedPads();
		Cell [] padsPositions = this.getPadsPositions();
		Cell [] obstaclesPostitions = this.getObstaclesPositions();
		
		// check if lower cell is a floor
		if (currentPosition.getY() == this.getHeight() - 1) return null;
		
		//get lower cell
		Cell lowerCell = new Cell();
		
		//check if lower cell is an obstacle
		for(int i = 0; i < obstaclesPostitions.length; i++) {
			if(obstaclesPostitions[i].getX() == currentPosition.getX()
					&& obstaclesPostitions[i].getY() == currentPosition.getY() + 1) {
					return null;
			}
		}
		
		//check if lower cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getX() == currentPosition.getX() 
					&& rocksPositions[i].getY() == currentPosition.getY() + 1) {
				rockIndex = i;
				lowerCell.setHasRock(true);
				lowerCell.setX(rocksPositions[i].getX());
				lowerCell.setY(rocksPositions[i].getY());
				//check if the rock is on pad
				for(int j = 0; j < this.padsPositions.length; j++) {
					if(padsPositions[i].getX() == currentPosition.getX()
							&& padsPositions[i].getY() == currentPosition.getY() + 1) {
						lowerCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}
		
		//for now if a rock is on a pad don't move it
		if(lowerCell.isActivated()) return null;
		
		//lower cell is a rock
		if(lowerCell.getHasRock()) {
			
			//check if second to lower cell is a floor
			if(currentPosition.getY() == this.getHeight() - 2) return null;
			
			//get second to lower
			
			//check if second to lower is a rock
			for(int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getX() == currentPosition.getX() 
						&& rocksPositions[i].getY() == currentPosition.getY() + 2) {
					return null;
				}
			}
			//check if second to lower is an obstacle
			for(int i = 0; i < obstaclesPostitions.length; i++) {
				if(obstaclesPostitions[i].getX() == currentPosition.getX()
						&& obstaclesPostitions[i].getY() == currentPosition.getY() + 2) {
					return null;
				}
			}
			//check if second to lower is a pad
			for(int i = 0; i < padsPositions.length; i++) {
				if(padsPositions[i].getX() == currentPosition.getX()
						&& padsPositions[i].getY() == currentPosition.getY() + 2) {
					unactivatedPads -= 1; break;
				}
			}
			
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY()+1);
			rocksPositions[rockIndex].setY(rocksPositions[rockIndex].getY() + 1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "DOWN");
			
			return newNode;
				
		}
		else {
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX());
			newPosition.setY(currentPosition.getY()+1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "DOWN");
			
			return newNode;
		}
	}

	public Node left(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = state.getRocksPositions();
		Cell currentPosition = state.getCurrentPosition();
		int unactivatedPads = state.getUnactivatedPads();
		Cell [] padsPositions = this.getPadsPositions();
		Cell [] obstaclesPostitions = this.getObstaclesPositions();
		
		// check if left cell is a wall
		if (currentPosition.getX() == 0)
			return null;
		
		//get left cell
		Cell leftCell = new Cell();
		
		//check if left cell is an obstacle
		for(int i = 0; i < obstaclesPostitions.length; i++) {
			if(obstaclesPostitions[i].getY() == currentPosition.getY()
					&& obstaclesPostitions[i].getX() == currentPosition.getX() - 1) {
				return null;
			}
		}
		//check if left cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getY() == currentPosition.getY() 
					&& rocksPositions[i].getX() == currentPosition.getX() - 1) {
				rockIndex = i;
				leftCell.setHasRock(true);
				leftCell.setX(rocksPositions[i].getX());
				leftCell.setY(rocksPositions[i].getY());
				//check if the rock is on pad
				for(int j = 0; j < this.padsPositions.length; j++) {
					if(padsPositions[i].getY() == currentPosition.getY()
							&& padsPositions[i].getX() == currentPosition.getX() - 1) {
						leftCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}
		//for now if a rock is on a pad don't move it
		if(leftCell.isActivated()) return null;
		
		//left cell is a rock
		if(leftCell.getHasRock()) {
			
			//check if second to left cell is a wall
			if(currentPosition.getX() == 1) return null;
			
			//get second to left
			
			//check if second to left is a rock
			for(int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getY() == currentPosition.getY() 
						&& rocksPositions[i].getX() == currentPosition.getX() - 2) {
					return null;
				}
			}
			//check if second to left is an obstacle
			for(int i = 0; i < obstaclesPostitions.length; i++) {
				if(obstaclesPostitions[i].getY() == currentPosition.getY()
						&& obstaclesPostitions[i].getX() == currentPosition.getX() - 2) {
					return null;
				}
			}
			//check if second to left is a pad
			for(int i = 0; i < padsPositions.length; i++) {
				if(padsPositions[i].getY() == currentPosition.getY()
						&& padsPositions[i].getX() == currentPosition.getX() - 2) {
					unactivatedPads -= 1; break;
				}
			}
			
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX()-1);
			newPosition.setY(currentPosition.getY());
			rocksPositions[rockIndex].setY(rocksPositions[rockIndex].getX() - 1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "LEFT");
			
			return newNode;
				
		}
		//left cell is free
		else {
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX()-1);
			newPosition.setY(currentPosition.getY());
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "LEFT");
			
			return newNode;
		}
	}

	public Node right(Node node) {
		MyState state = (MyState) node.getCurrentState();
		Cell[] rocksPositions = state.getRocksPositions();
		Cell currentPosition = state.getCurrentPosition();
		int unactivatedPads = state.getUnactivatedPads();
		Cell [] padsPositions = this.getPadsPositions();
		Cell [] obstaclesPostitions = this.getObstaclesPositions();
		
		// check if right cell is a wall
		if (currentPosition.getX() == this.getWidth() - 1) return null;
		
		//get right cell
		Cell rightCell = new Cell();
		
		//check if right cell is an obstacle
		for(int i = 0; i < obstaclesPostitions.length; i++) {
			if(obstaclesPostitions[i].getY() == currentPosition.getY()
					&& obstaclesPostitions[i].getX() == currentPosition.getX() + 1) {
					return null;
			}
		}
		
		//check if right cell is a rock
		int rockIndex = 0;
		for (int i = 0; i < rocksPositions.length; i++) {
			if (rocksPositions[i].getY() == currentPosition.getY() 
					&& rocksPositions[i].getX() == currentPosition.getX() + 1) {
				rockIndex = i;
				rightCell.setHasRock(true);
				rightCell.setX(rocksPositions[i].getX());
				rightCell.setY(rocksPositions[i].getY());
				//check if the rock is on pad
				for(int j = 0; j < this.padsPositions.length; j++) {
					if(padsPositions[i].getY() == currentPosition.getY()
							&& padsPositions[i].getX() == currentPosition.getX() + 1) {
						rightCell.setStatus(CellStatus.pressurePad);
					}
				}
				break;
			}
		}
		
		//for now if a rock is on a pad don't move it
		if(rightCell.isActivated()) return null;
		
		//right cell is a rock
		if(rightCell.getHasRock()) {
			
			//check if second to right cell is a wall
			if(currentPosition.getX() == this.getWidth() - 2) return null;
			
			//get second to right
			
			//check if second to right is a rock
			for(int i = 0; i < rocksPositions.length; i++) {
				if (rocksPositions[i].getY() == currentPosition.getY() 
						&& rocksPositions[i].getX() == currentPosition.getX() + 2) {
					return null;
				}
			}
			//check if second to right is an obstacle
			for(int i = 0; i < obstaclesPostitions.length; i++) {
				if(obstaclesPostitions[i].getY() == currentPosition.getY()
						&& obstaclesPostitions[i].getX() == currentPosition.getX() + 2) {
					return null;
				}
			}
			//check if second to right is a pad
			for(int i = 0; i < padsPositions.length; i++) {
				if(padsPositions[i].getY() == currentPosition.getY()
						&& padsPositions[i].getX() == currentPosition.getX() + 2) {
					unactivatedPads -= 1; break;
				}
			}
			
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX()+1);
			newPosition.setY(currentPosition.getY());
			rocksPositions[rockIndex].setY(rocksPositions[rockIndex].getX() + 1);
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "RIGHT");
			
			return newNode;
				
		}
		else {
			Cell newPosition = new Cell();
			newPosition.setX(currentPosition.getX()+1);
			newPosition.setY(currentPosition.getY());
			
			MyState newState = new MyState(newPosition, unactivatedPads, rocksPositions);
			
			Node newNode = new Node(node, newState, node.getDepth()+1, node.getPathCost()+1, "RIGHT");
			
			return newNode;
		}
	}
//	public static void main(String[] args) {
//		Cell currentPosition = new Cell();
//		currentPosition.setX(1);
//		currentPosition.setY(2);
//		
//		Cell rockPosition = new Cell();
//		rockPosition.setX(0);
//		rockPosition.setY(1);
//		
//		Cell [] rockPositions = new Cell [1];
//		rockPositions[0] = rockPosition;
//		
//		MyState initState = new MyState(currentPosition, 1, rockPositions);
//		
//		Cell teleport = new Cell();
//		teleport.setX(2);
//		teleport.setY(2);
//		
//		Cell obstacle = new Cell();
//		obstacle.setX(2);
//		obstacle.setY(0);
//		
//		Cell [] obstacles = new Cell[1];
//		obstacles[0] = obstacle;
//		
//		Cell pad = new Cell();
//		pad.setX(0);
//		pad.setY(1);
//		
//		Cell [] pads = new Cell[1];
//		pads[0] = pad;
//		
//		String [] ops = new String [4];
//		MyState [] stateSpace = new MyState[4];
//		
//		HelpR2D2 problem = new HelpR2D2(ops, initState, stateSpace, teleport, obstacles, pads);
//		
//		Node node = new Node(null, initState, 0, 0, "UP");
//		Node x = problem.up(node);
//		
//		MyState newState = (MyState) x.getCurrentState();
//		
//		System.out.println(newState.getCurrentPosition().getY());
//		
//	}
}
