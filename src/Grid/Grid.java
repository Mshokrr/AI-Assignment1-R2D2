package Grid;

public class Grid {

	private int width;
	private int height;
	private Cell[][] cells;

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

		if (this.width < 6) {
			System.out.println("=> Width should be a minimum of 6, auto-initializing to 6");
			this.width = 6;
		}
		if (this.height < 6) {
			System.out.println("=> Height should be a minimum of 6, auto-initializing to 6");
			this.height = 6;
		}

		//

		this.cells = new Cell[this.width][this.height];

		// Initializing Cells

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				Cell c = new Cell();
				c.setName("R" + i + " " + "C" + j); 
				this.cells[i][j] = c;
			}
		}

		// Initializing Obstacles, Pads and Rocks

		while ((this.width * this.height) < (numberOfPads / 7)) {
			numberOfPads /= 2;
		}
		while ((this.width * this.height) < (numberOfObstacles / 7)) {
			numberOfObstacles /= 2;
		}

		System.out.println("=> Initializing with " + numberOfObstacles + " obstacles");
		System.out.println("=> Initializing with " + numberOfObstacles + " pads and rocks");

		for (int i = 0; i < numberOfObstacles; i++) {
			int obstaclePositionX = (int) Math.floor(Math.random() * this.width);
			int obstaclePositionY = (int) Math.floor(Math.random() * this.height);
			System.out.println("=> Obstacle at: R" + obstaclePositionX + " C" + obstaclePositionY);
			this.cells[obstaclePositionX][obstaclePositionY].setStatus(CellStatus.obstacle);
		}

		for (int i = 0; i < numberOfPads; i++) {
			int padPositionX = (int) Math.floor(Math.random() * this.width);
			int padPositionY = (int) Math.floor(Math.random() * this.height);
			while (this.cells[padPositionX][padPositionY].getHasRock()
					|| !(this.cells[padPositionX][padPositionY].getStatus() == CellStatus.free)) {
				padPositionX = (int) Math.floor(Math.random() * this.width);
				padPositionY = (int) Math.floor(Math.random() * this.height);
			}
			System.out.println("=> Pad at: R" + padPositionX + " C" + padPositionY);
			this.cells[padPositionX][padPositionY].setStatus( CellStatus.pressurePad);
			int rockPositionX = (int) Math.floor(Math.random() * this.width);
			int rockPositionY = (int) Math.floor(Math.random() * this.height);
			// while (!(this.cells[rockPositionX][rockPositionY].status == CellStatus.free
			// || !this.cells[rockPositionX][rockPositionY].hasRock)){
			// rockPositionX = (int) Math.floor(Math.random()*this.width);
			// rockPositionY = (int) Math.floor(Math.random()*this.height);
			// }
			// System.out.println("=> Rock at: R" + rockPositionX + " C" + rockPositionY);
			// this.cells[rockPositionX][rockPositionY].hasRock = true;
		}

		System.out.println("========\n\nThe Grid:\n");
		this.displayGrid();
	}

	public void displayGrid() {
		for (int i = 0; i < this.width; i++) {
			System.out.print("   _  ");
		}
		System.out.println();
		for (int i = 0; i < this.width; i++) {
			System.out.println();
			for (int j = 0; j < this.height; j++) {
				if (this.cells[i][j].getStatus() == CellStatus.obstacle)
					System.out.print("|  o  ");
				else {
					if (this.cells[i][j].getStatus() == CellStatus.pressurePad)
						System.out.print("|  p  ");
					else {
						if (this.cells[i][j].getHasRock())
							System.out.print("|  r  ");
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
		// for (int i = 0; i < this.width; i++){
		// for(int j = 0; j < this.height; j++){
		// System.out.print("Cell " + this.cells[i][j].name+ ": ");
		// System.out.print(this.cells[i][j].status + ", ");
		// System.out.print(this.cells[i][j].hasRock);
		// System.out.println();
		// }
		// }
	}

	public static void main(String[] args) {

		Grid g = new Grid(4, 4, 6, 6);

	}

}
