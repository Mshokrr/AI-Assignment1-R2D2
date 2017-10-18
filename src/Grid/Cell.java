package Grid;

public class Cell {
	
	private CellStatus status;
	private Boolean hasRock;
	private String name;
	private int x;
	private int y;
	
	public CellStatus getStatus() {
		return status;
	}

	public void setStatus(CellStatus status) {
		this.status = status;
	}

	public Boolean getHasRock() {
		return hasRock;
	}

	public void setHasRock(Boolean hasRock) {
		this.hasRock = hasRock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	// A Cell can have obstacle, pressurePad, free, teleport. Also, some combinations can support having a rock 
	public Cell(CellStatus status, Boolean hasRock){
		this.status = status;
		this.hasRock = hasRock;
	}
	
	public Cell(){
		this.status = CellStatus.free;
		this.hasRock = false;
		this.name = "";
	}
	
	//A pressure pad is active if and only if it has a rock on top of a pressure pad
	public boolean isActivated(){
		return (this.status == CellStatus.pressurePad && this.hasRock == true);
	}
	
}
