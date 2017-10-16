package Tests;

import Grid.*;
import Search.*;
import Assignment1.*;

public class Test {
	
  public static void main(String [] args){
    Grid g = new Grid(3, 3, 1, 1);
		System.out.println();
		System.out.println("Testing");
		Cell [] rockPositions = g.getRockPositions();
		System.out.println("Rocks");
		for (int i = 0; i < rockPositions.length; i++){
			Cell c = rockPositions[i];
			System.out.print("R" + c.getX() + " C" + c.getY() + " ");
		}
		System.out.println();
		Cell [] obstaclePositions = g.getObstaclePositions();
		System.out.println("Obstacles");
		for (int i = 0; i < obstaclePositions.length; i++){
			Cell c = obstaclePositions[i];
			System.out.print("R" + c.getX() + " C" + c.getY() + " ");
		}
		System.out.println();
		Cell [] padPositions = g.getPadPositions();
		System.out.println("Pads");
		for (int i = 0; i < padPositions.length; i++){
			Cell c = padPositions[i];
			System.out.print("R" + c.getX() + " C" + c.getY() + " ");
		}
		System.out.println();
		Cell teleport = g.getTeleportPosition();
		Cell currentPosition = g.getAgentPosition();

		MyState initState = new MyState(currentPosition, g.getNumberOfPads(), rockPositions);
		String [] ops = new String[4];
		MyState [] stateSpace = new MyState[4];
		HelpR2D2 problem1 = new HelpR2D2(ops, initState, stateSpace, teleport, obstaclePositions, padPositions, g.getWidth(), g.getHeight());

		GeneralSearch gs = new GeneralSearch(problem1, QueuingFunction.DF);
		Node n = gs.search();
		while(n != null){
			System.out.println("Operator: " + n.getOperator());
			n = n.getParent();
		}
  }
}
