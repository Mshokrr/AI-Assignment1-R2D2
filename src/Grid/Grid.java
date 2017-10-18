package Grid;

import java.util.ArrayList;

public class Grid {

	private int width;
	private int height;
	private Cell[][] cells;

	private Cell agentPosition;
	public static Cell telePosition;
	private int numberOfPads;
	private int numberOfObstacles;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}


	public Grid() {
		this.width = 3 + ((int) (Math.random() * 4));
		this.height = 3 + ((int) (Math.random() * 4));
		// Random number of obstacles depending on bigger of width and height
		this.numberOfObstacles = (int) (Math.random() * ((this.height > this.width) ? this.height
				: this.width));
		// Random number of pad depending on summation of width and height - 2
		this.numberOfPads = 1 + ((int) (Math.random() * (this.height
				+ this.width - 2)));
		genGrid(numberOfObstacles, numberOfPads);
	}

	public Grid(int width, int height, int numberOfPads, int numberOfObstacles) {
		this.width = width;
		this.height = height;
		this.numberOfPads = numberOfPads;
		this.numberOfObstacles = numberOfObstacles;
		this.genGrid(numberOfObstacles, numberOfPads);
	}

	// Generates a grid with a random dimensions, number of obstacles, rocks and
	// pads
	public static Grid genGrid() {
		return new Grid();

	}

	// Generates a grid with a certain dimensions, number of obstacles, rocks
	// and pads
	public void genGrid(int numberOfObstacles, int numberOfPads) {
		System.out.println("\n\n\t\tWELCOME\n");
		System.out.println("Escape mission of R2D2 from the Death Star");
		System.out.println("========\n");
		System.out.println("=> Width = " + width);
		System.out.println("=> Height = " + height);
		System.out.println("=> Generating grid..\n");
		System.out.println("Initialization Log:\n");

		// Initialization Constraints
		if (this.width < 3) {
			System.out
					.println("=> Width should be a minimum of 3, auto-initializing to 3");
			this.width = 3;
		}
		if (this.height < 3) {
			System.out
					.println("=> Height should be a minimum of 3, auto-initializing to 3");
			this.height = 3;
		}

		this.cells = new Cell[this.width][this.height];
		// Initializing Cells
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				Cell c = new Cell();
				c.setX(i);
				c.setY(j);
				c.setName("R" + i + " " + "C" + j);
				this.cells[i][j] = c;
			}
		}

		// Initializing Obstacles, Pads and Rocks
		while (((this.width * this.height) / 7) < numberOfPads) {
			numberOfPads /= 2;
			this.numberOfPads /= 2;
		}
		while (((this.width * this.height) / 7) < numberOfObstacles) {
			numberOfObstacles /= 2;
		}

		// Initializing Agent position
		this.agentPosition = cells[(int) (Math.random() * this.width)][(int) (Math
				.random() * height)];
		System.out.println("=> Initializing agent at "
				+ this.agentPosition.getName());

		// Initializing Teleport position
		int teleportPositionX = (int) (Math.random() * this.width);
		int teleportPositionY = (int) (Math.random() * this.height);
		this.cells[teleportPositionX][teleportPositionY]
				.setStatus(CellStatus.teleport);
		System.out.println("=> Initializing teleport position at "
				+ this.cells[teleportPositionX][teleportPositionY].getName());

		System.out.println("=> Initializing with " + numberOfObstacles
				+ " obstacles");
		System.out.println("=> Initializing with " + numberOfObstacles
				+ " pads and rocks");

		// Initializing Obstacles position
		for (int i = 0; i < numberOfObstacles; i++) {
			int obstaclePositionX = (int) Math
					.floor(Math.random() * this.width);
			int obstaclePositionY = (int) Math.floor(Math.random()
					* this.height);
			// checking random Cell can have a obstacle
			if (!(this.agentPosition.getX() == obstaclePositionX && this.agentPosition
					.getY() == obstaclePositionY)
					&& (this.cells[obstaclePositionX][obstaclePositionY]
							.getStatus() == CellStatus.free)) {
				System.out.println("=> Obstacle at: R" + obstaclePositionX
						+ " C" + obstaclePositionY);
				this.cells[obstaclePositionX][obstaclePositionY]
						.setStatus(CellStatus.obstacle);
			}
		}

		// Initializing Pressure Pads position
		for (int i = 0; i < numberOfPads; i++) {
			int padPositionX = (int) Math.floor(Math.random() * this.width);
			int padPositionY = (int) Math.floor(Math.random() * this.height);
			// checking random Cell can have a pressure pad
			while (this.cells[padPositionX][padPositionY].getHasRock()
					|| !(this.cells[padPositionX][padPositionY].getStatus() == CellStatus.free)) {
				padPositionX = (int) Math.floor(Math.random() * this.width);
				padPositionY = (int) Math.floor(Math.random() * this.height);
			}
			System.out.println("=> Pad at: R" + padPositionX + " C"
					+ padPositionY);
			this.cells[padPositionX][padPositionY]
					.setStatus(CellStatus.pressurePad);

		}

		// Initializing Rocks position
		int rockPositionX = 0;
		int rockPositionY = 0;
		boolean f = false;
		for (int i = 0; i < numberOfPads; i++) {
			do {
				f = false;
				rockPositionX = (int) Math.floor(Math.random() * this.width);
				// Handling no Rocks on the corner
				if (rockPositionX == 0 || rockPositionX == this.width - 1)
					rockPositionY = 1 + ((int) Math.floor(Math.random()
							* (this.height - 2)));
				else
					rockPositionY = (int) Math.floor(Math.random()
							* this.height);
				// Handling if a rock is on the edge there should be a Pad on
				// this edge
				if (rockPositionX == 0 || rockPositionX == this.width - 1)
					for (int j = 0; j < this.height; j++) {
						if (this.cells[rockPositionX][j].getStatus() == CellStatus.pressurePad)
							f = true;
					}
				else if (rockPositionY == 0 || rockPositionY == this.height - 1)
					for (int j = 0; j < this.width; j++) {
						if (this.cells[j][rockPositionY].getStatus() == CellStatus.pressurePad)
							f = true;
					}
				else
					f = true;
				// checking random Cell can have a rock
			} while (this.cells[rockPositionX][rockPositionY].getHasRock()
					|| !(this.cells[rockPositionX][rockPositionY].getStatus() == CellStatus.free)
					|| (this.agentPosition.getX() == rockPositionX && this.agentPosition
							.getY() == rockPositionY) || !f);

			System.out.println("=> Rock at: R" + rockPositionX + " C"
					+ rockPositionY);
			this.cells[rockPositionX][rockPositionY].setHasRock(true);
		}
	}

	public int getNumberOfPads() {
		return numberOfPads;
	}

	public void setNumberOfPads(int numberOfPads) {
		this.numberOfPads = numberOfPads;
	}

	// Polling the Rocks from the grid to array of rockPositions
	public Cell[] getRockPositions() {
		ArrayList<Cell> res = new ArrayList<>();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (cells[i][j].getHasRock()) {
					res.add(cells[i][j]);
				}
			}
		}
		Cell[] resArr = new Cell[res.size()];
		for (int k = 0; k < res.size(); k++) {
			resArr[k] = res.get(k);
		}
		return resArr;
	}

	// Polling the Pressure Pads from the grid to array of padPositions
	public Cell[] getPadPositions() {
		ArrayList<Cell> res = new ArrayList<>();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (cells[i][j].getStatus() == CellStatus.pressurePad) {
					res.add(cells[i][j]);
				}
			}
		}
		Cell[] resArr = new Cell[res.size()];
		for (int k = 0; k < res.size(); k++) {
			resArr[k] = res.get(k);
		}
		return resArr;
	}

	// Polling the obstacles from the grid to array of obstaclePositions
	public Cell[] getObstaclePositions() {
		ArrayList<Cell> res = new ArrayList<>();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (cells[i][j].getStatus() == CellStatus.obstacle) {
					res.add(cells[i][j]);
				}
			}
		}
		Cell[] resArr = new Cell[res.size()];
		for (int k = 0; k < res.size(); k++) {
			resArr[k] = res.get(k);
		}
		return resArr;
	}

	// Polling the Teleport from the grid to cell representing teleportPositions
	public Cell getTeleportPosition() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (cells[i][j].getStatus() == CellStatus.teleport) {
					telePosition = cells[i][j];
					return cells[i][j];
				}
			}
		}
		return null;
	}

	// Polling the Agent from the grid to cell representing agentPositions
	public Cell getAgentPosition() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (this.agentPosition.getX() == i
						& this.agentPosition.getY() == j) {
					return cells[i][j];
				}
			}
		}
		return null;
	}

	// Printing the grid for a better visualization
	public void displayGrid() {
		System.out.println("========\n\nThe Grid:\n");

		for (int i = 0; i < this.width; i++) {
			System.out.print("   _  ");
		}
		System.out.println();
		for (int i = 0; i < this.height; i++) {
			System.out.println();
			for (int j = 0; j < this.width; j++) {
				if (this.cells[j][i].getStatus() == CellStatus.obstacle)
					System.out.print("|  o  ");
				else {
					if (this.cells[j][i].getStatus() == CellStatus.pressurePad)
						if (this.cells[j][i] == getAgentPosition())
							System.out.print("| A p ");
						else
							System.out.print("|  p  ");
					else {
						if (this.cells[j][i].getHasRock())
							System.out.print("|  r  ");
						else if (this.cells[j][i] == getTeleportPosition())
							if (this.cells[j][i] == getAgentPosition())
								System.out.print("| A T ");
							else
								System.out.print("|  T  ");
						else if (this.cells[j][i] == getAgentPosition())
							System.out.print("|  A  ");
						else
							System.out.print("|     ");
					}
				}
			}
			System.out.println("|");
			for (int k = 0; k < this.width; k++) {
				System.out.print("   _  ");
			}
			System.out.println();
		}

		System.out.println();

		// Printing Cells log
		// System.out.println("Cells Log\n");
		// System.out.println("Name: status, hasRock, Agent");
		// for (int i = 0; i < this.width; i++){
		// for(int j = 0; j < this.height; j++){
		// System.out.print("Cell " + this.cells[i][j].getName()+ ": ");
		// System.out.print(this.cells[i][j].getStatus() + ", ");
		// System.out.print(this.cells[i][j].getHasRock() + ", ");
		// System.out.print(this.agentPosition.getX() == i &&
		// this.agentPosition.getY() == j);
		// System.out.println();
		// }
		// }
	}

}
