package solutions.nine_sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NineSudokuSearch {

	private final NineSudokuNode initialState;

	public NineSudokuSearch(NineSudokuNode initialState) {
		this.initialState = initialState;
	}

	public void findSolution() {
		NineSudokuNode initialState = this.initialState;
		List<NineSudokuNode> queue = new ArrayList<>();
		List<NineSudokuNode> visited = new ArrayList<>();
		queue.add(initialState);
		while (!queue.isEmpty()) {
			NineSudokuNode current = queue.remove(0);
			if (visited.contains(current)) {
				continue;
			}
			visited.add(current);
			if (current.solutionFound()) {
				System.out.println("Solution found!");
				printSolution(current);
				System.out.println("QUEUE SIZE: " + queue.size());
				System.out.println("VISITED SIZE: " + visited.size());
				return;
			}
			List<NineSudokuNode> children = current.generateChildren();
			queue.addAll(children);
			System.out.println("QUEUE SIZE: " + queue.size());
			System.out.println("VISITED SIZE: " + visited.size());
		}
		System.out.println("There is no solution for this puzzle!");
	}

	public void printSolution(NineSudokuNode result) {
		List<NineSudokuNode> solution = new ArrayList<>();

		solution.add(result);
		NineSudokuNode current = result;

		while (true) {
			NineSudokuNode parent = current.getParent();
			if (parent == null) {
				break;
			}
			solution.add(parent);
			current = parent;
		}

		Collections.reverse(solution);

		int iteration = 0;
		for (NineSudokuNode node : solution) {
			System.out.println("Iteration " + iteration);
			node.print();
			System.out.println("------------");
			iteration++;
		}
	}
}
