package Tests;

import java.util.Deque;

import Grid.Grid;
import Search.GeneralSearch;
import Search.QueuingFunction;

public class Test {


	public static void main(String[] args) {
		boolean finish = false;
		do {
			try {
				Grid g = Grid.genGrid();
				Deque<Grid> grids = GeneralSearch.search(g, QueuingFunction.BF, true);
				grids.pop();
				while (!grids.isEmpty()) {
					grids.pop().displayGrid();
				}
//				GeneralSearch.search(g, QueuingFunction.DF, true);
//				GeneralSearch.search(g, QueuingFunction.BF, false);
//				GeneralSearch.search(g, QueuingFunction.ID, false);
//				GeneralSearch.search(g, QueuingFunction.GR1, false);
//				GeneralSearch.search(g, QueuingFunction.AS1, false);
//				GeneralSearch.search(g, QueuingFunction.GR2, false);
//				GeneralSearch.search(g, QueuingFunction.AS2, false);
				finish = true;
			} catch (NoSolutionException e) {

			}
		} while (!finish);
	}
}
