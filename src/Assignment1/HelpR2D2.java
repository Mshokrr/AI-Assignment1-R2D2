package Assignment1;

import Grid.Cell;
import Search.Node;
import Search.Problem;
import Search.State;

public class HelpR2D2 extends Problem {
	private Cell telePosition;

	public Cell getTelePosition() {
		return telePosition;
	}

	public void setTelePosition(Cell telePosition) {
		this.telePosition = telePosition;
	}

	public HelpR2D2(String[] operators, State initState, State[] stateSpace, Cell telePosition) {
		super.setOperators(operators);
		super.setInitState(initState);
		this.telePosition = telePosition;
		// super.stateSpace?
	}

	public int pathCost(Node n) {

		// return path cost later
		return 0;
	}

	@Override
	public boolean goalTest(Node node) {
		MyState state = (MyState) node.getCurrentState();
		if (state.getCurrentPosition().equals(this.telePosition)) { // comparing issue
			System.out.println("R2D2 on teleport cell!");
			if (state.getUnactivatedPads() == 0) {
				System.out.println("Goal success");
				return true;
			}

		}
		return false;
	}
}
