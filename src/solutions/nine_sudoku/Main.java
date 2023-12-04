package solutions.nine_sudoku;

public class Main {

	public static void main(String[] args) {
//		int[] initial = new int[]{6, 4, 2, 8, 1, 3, 7, 5, 0};
		int[] initial = new int[]{4, 6, 2, 8, 1, 3, 7, 5, 0};
//		int[] initial = new int[]{8, 2, 6, 0, 5, 7 ,1, 4, 3};
		NineSudokuNode initialState = new NineSudokuNode(initial, 8);
		NineSudokuSearch search = new NineSudokuSearch(initialState);
		search.findSolution();
	}

}