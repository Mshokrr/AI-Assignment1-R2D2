package Assignment1;

import Search.Node;
import Search.Problem;
import Search.State;

public class HelpR2D2 extends Problem {
	
	public static int pathCost(Node n){
		
		// return path cost later
		return 0;
	}
	
	public static boolean goalTest(MyState state){
		if(state.currentPosition.equals(state.telePosition)){ //comparing issue
			System.out.println("R2D2 on teleport cell!");
			if(state.unactivatedPads == 0){
				System.out.println("Goal success");
				return true;
			}
			
		}
		return false;
	}
}
