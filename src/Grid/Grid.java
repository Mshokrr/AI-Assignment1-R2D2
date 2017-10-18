package Grid;

import java.util.ArrayList;

public class Grid {

	private int width;
	private int height;
	private Cell[][] cells;

	private Cell agentPosition;
	private int numberOfPads;

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

	public Grid(int width, int height, int numberOfPads, int numberOfObstacles) {
		this.width = width;
		this.height = height;
		this.numberOfPads = numberOfPads;
		System.out.println("\n\n\t\tWELCOME\n");
		System.out.println("Escape mission of R2D2 from the Death Star");
		System.out.println("========\n");
		this.genGrid(numberOfObstacles, numberOfPads);
	}

	public void genGrid(int numberOfObstacles, int numberOfPads) {
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

		//

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

		for (int i = 0; i < numberOfObstacles; i++) {
			int obstaclePositionX = (int) Math
					.floor(Math.random() * this.width);
			int obstaclePositionY = (int) Math.floor(Math.random()
					* this.height);
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

		for (int i = 0; i < numberOfPads; i++) {
			int padPositionX = (int) Math.floor(Math.random() * this.width);
			int padPositionY = (int) Math.floor(Math.random() * this.height);
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

		int rockPositionX = 0;
		int rockPositionY = 0;
		boolean f = false;
		for (int i = 0; i < numberOfPads; i++) {
			do {
				f = false;
				rockPositionX = (int) Math.floor(Math.random() * this.width);
				rockPositionY = 0;
				if (rockPositionX == 0 || rockPositionX == this.width - 1)
					rockPositionY = 1 + ((int) Math.floor(Math.random()
							* (this.height - 2)));
				else
					rockPositionY = (int) Math.floor(Math.random()
							* this.height);
				if (rockPositionX == 0 || rockPositionX == this.width - 1)
					for (int j = 0; j < this.height; j++) {
						if (this.cells[rockPositionX][j].getStatus() == CellStatus.pressurePad)
							f = true;
					}
				else if (rockPositionY == 0 || rockPositionY == this.width - 1)
					for (int j = 0; j < this.width; j++) {
						if (this.cells[j][rockPositionY].getStatus() == CellStatus.pressurePad)
							f = true;
					}
				else
					f = true;
			} while (this.cells[rockPositionX][rockPositionY].getHasRock()
					|| !(this.cells[rockPositionX][rockPositionY].getStatus() == CellStatus.free)
					|| (this.agentPosition.getX() == rockPositionX && this.agentPosition
							.getY() == rockPositionY) || !f);

			System.out.println("=> Rock at: R" + rockPositionX + " C"
					+ rockPositionY);
			this.cells[rockPositionX][rockPositionY].setHasRock(true);
		}
		System.out.println("========\n\nThe Grid:\n");
		this.displayGrid();
	}

	public int getNumberOfPads() {
		return numberOfPads;
	}

	public void setNumberOfPads(int numberOfPads) {
		this.numberOfPads = numberOfPads;
	}

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

	public Cell getTeleportPosition() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (cells[i][j].getStatus() == CellStatus.teleport) {
					return cells[i][j];
				}
			}
		}
		return null;
	}

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

	public void displayGrid() {
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
						else if (this.cells[j][i] == getAgentPosition())
							System.out.print("|  A  ");
						else if (this.cells[j][i] == getTeleportPosition())
							if (this.cells[j][i] == getAgentPosition())
								System.out.print("| A T ");
							else
								System.out.print("|  T  ");
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

	public static void main(String[] args) {

		new Grid(5, 5, 3, 3);

	}

}