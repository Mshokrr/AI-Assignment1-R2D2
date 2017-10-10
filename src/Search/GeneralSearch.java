package Search;

import java.util.Deque;

public class GeneralSearch {
	
	Deque<Node> queue;
	QueuingFunction qingFunc;
	Problem problem;
	
	public GeneralSearch(Problem problem, QueuingFunction qingFunc) {
		this.problem = problem;
		this.qingFunc = qingFunc;
	}
	
	public void run(){
		
	}
	
	
}
