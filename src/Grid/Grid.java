package Grid;

public class Grid {
	
	int width;
	int height;
	Cell [][] cells;
	
	public Grid(int width, int height, int numberOfPads, int numberOfObstacles){
		this.width = width;
		this.height = height;
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
		for (int i = 0; i < numberOfPads; i++){
			this.cells[(int) Math.floor(Math.random()*this.width)][(int) Math.floor(Math.random()*this.height)].status = CellStatus.pressurePad;
		}
		for (int i = 0; i < numberOfObstacles; i++){
			this.cells[(int) Math.floor(Math.random()*this.width)][(int) Math.floor(Math.random()*this.height)].status = CellStatus.obstacle;
		}
	}
	
	public void displayGrid(){
		for (int i = 0; i < this.width; i++){
			System.out.println();
			for (int j = 0; j < this.height; j++){
				if (this.cells[i][j].status == CellStatus.obstacle)
					System.out.print("|  o  ");
				else if (this.cells[i][j].status == CellStatus.pressurePad)
					System.out.print("|  p  ");
				else System.out.print("|     ");
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
		g.displayGrid();
		
//		Trying to create a grid in console
//		for (int i = 0; i < 5; i++){
//			System.out.println();
//			for (int j = 0; j < 6; j++){
//				System.out.print("|  b  ");
//			}
//			System.out.println();
//			for (int k = 0; k < 6; k++){
//				System.out.print("   _  ");
//			}
//			System.out.println();
//		}
	}

}
