package Assignment1;

import Grid.Cell;
import Search.Node;
import Search.Problem;
import Search.State;

public class HelpR2D2 extends Problem {
	Cell telePosition;
	
	public HelpR2D2(String [] operators, State initState, State [] stateSpace, Cell telePosition) {
		super.operators = operators;
		super.initState = initState;
		this.telePosition = telePosition;
		//super.stateSpace?
	}
	public int pathCost(Node n){
		
		// return path cost later
		return 0;
	}
	
	public boolean goalTest(MyState state){
		if(state.currentPosition.equals(this.telePosition)){ //comparing issue
			System.out.println("R2D2 on teleport cell!");
			if(state.unactivatedPads == 0){
				System.out.println("Goal success");
				return true;
			}
			
		}
		return false;
	}
}
