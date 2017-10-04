package Grid;

public class Grid {
	
	int width;
	int height;
	Cell [][] cells;
	
	public Grid(int width, int height, int numberOfPads, int numberOfObstacles){
		this.width = width;
		this.height = height;
		System.out.println("WELCOME\n");
		System.out.println("Escape mission of R2D2 from the Death Star");
		System.out.println("========\n\n");
		System.out.println("Initialization Log:\n");
		if (width < 5){
			System.out.println("=> Width should be a minimum of 5, auto-initializing to 5");
			this.width = 5;
		}
		if (height < 5){
			System.out.println("=> Height should be a minimum of 5, auto-initializing to 5");
			this.height = 5;
		}
		this.cells = new Cell[this.width][this.height];
		
		for (int i = 0; i < this.width; i++){
			for (int j = 0; j < this.height; j++){
				Cell c = new Cell();
				c.name = "R" + i + " " + "C"+ j;
				this.cells[i][j] = c;
			}	
		}
		
		while ((this.width * height) < (numberOfPads / 4)){
			numberOfPads /= 2;
		}
		while ((this.width * height) < (numberOfObstacles / 4)){
			numberOfObstacles /= 2;
		}
		
		for (int i = 0; i < numberOfObstacles; i++){
			int obstaclePositionX = (int) Math.floor(Math.random()*this.width);
			int obstaclePositionY = (int) Math.floor(Math.random()*this.height);
			System.out.println("=> Obstacle at: R" + obstaclePositionX + " C" + obstaclePositionY);
			this.cells[obstaclePositionX][obstaclePositionY].status = CellStatus.obstacle;
		}
		
		for (int i = 0; i < numberOfPads; i++){
			int padPositionX = (int) Math.floor(Math.random()*this.width);
			int padPositionY = (int) Math.floor(Math.random()*this.height);
			System.out.println("=> Pad at: R" + padPositionX + " C" + padPositionY);
			this.cells[padPositionX][padPositionY].status = CellStatus.pressurePad;
			int rockPositionX = (int) Math.floor(Math.random()*this.width);
			int rockPositionY = (int) Math.floor(Math.random()*this.height);
			while (this.cells[rockPositionX][rockPositionY].status == CellStatus.pressurePad){
				rockPositionX = (int) Math.floor(Math.random()*this.width);
				rockPositionY = (int) Math.floor(Math.random()*this.height);
			}
			System.out.println("=> Rock at: R" + rockPositionX + " C" + rockPositionY);
			this.cells[rockPositionX][rockPositionY].hasRock = true;
		}
		
		System.out.println("========\n\nThe Grid:\n");
		this.displayGrid();
	}
	
	public void displayGrid(){
		for (int i = 0; i < this.width; i++){
			System.out.println();
			for (int j = 0; j < this.height; j++){
				if (this.cells[i][j].status == CellStatus.obstacle)
					System.out.print("|  o  ");
				else {	
					if (this.cells[i][j].status == CellStatus.pressurePad)
						System.out.print("|  p  ");
				
				else {
					if (this.cells[i][j].hasRock)
						System.out.print("|  r  ");
					else 
					System.out.print("|     ");
					}
				}
			}
			System.out.println();
			for (int k = 0; k < this.width; k++){
				System.out.print("   _  ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		Grid g = new Grid(4,4,6,6);

	}

}
