package Grid;

public class Cell {
	
	CellStatus status;
	Boolean hasRock;
	String name;
	int x;
	int y;
	
	public Cell(CellStatus status, Boolean hasRock){
		this.status = status;
		this.hasRock = hasRock;
	}
	
	public Cell(){
		this.status = CellStatus.free;
		this.hasRock = false;
		this.name = "";
	}
	
	public boolean isActivated(){
		return (this.status == CellStatus.pressurePad && this.hasRock == false);
	}
	
}
