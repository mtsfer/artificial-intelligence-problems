package solutions.eight_queens;

public class Main {

	public static void main(String[] args) {
		int[] initialConfiguration = new int[]{7, 0, 1, 3, 5, 2, 6, 4};
		EightQueensNode initialState = new EightQueensNode(initialConfiguration);
		EightQueensSearch search = new EightQueensSearch(initialState);
		search.findSolution();
	}

}
