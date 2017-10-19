package Tests;

import Grid.Grid;
import Search.GeneralSearch;
import Search.QueuingFunction;

public class Test {


	public static void main(String[] args) {
		boolean finish = false;
		do {
			try {
				Grid g = Grid.genGrid();
				GeneralSearch.search(g, QueuingFunction.DF, false);
				GeneralSearch.search(g, QueuingFunction.BF, true);
				GeneralSearch.search(g, QueuingFunction.ID, false);
				GeneralSearch.search(g, QueuingFunction.GR1, false);
				GeneralSearch.search(g, QueuingFunction.AS1, false);
				GeneralSearch.search(g, QueuingFunction.GR2, false);
				GeneralSearch.search(g, QueuingFunction.AS2, false);
				finish = true;
			} catch (NoSolutionException e) {

			}
		} while (!finish);
	}
}
