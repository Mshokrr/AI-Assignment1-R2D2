package Grid;

public class Cell {
	
	CellStatus status;
	Boolean hasRock;
	
	public Cell(CellStatus status, Boolean hasRock){
		this.status = status;
		this.hasRock = hasRock;
	}
	
	public boolean activated(){
		return (this.status == CellStatus.pressurePad && this.hasRock == false);
	}
	
	
}
