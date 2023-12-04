package solutions.eight_queens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EightQueensSearch {

	private final EightQueensNode initialState;

	public EightQueensSearch(EightQueensNode initialState) {
		this.initialState = initialState;
	}

	public void findSolution() {
		EightQueensNode initialState = this.initialState;
		LinkedList<EightQueensNode> queue = new LinkedList<>();
		List<EightQueensNode> visited = new ArrayList<>();
		queue.add(initialState);
		while (!queue.isEmpty()) {
			EightQueensNode current = queue.remove();
			current.print();
			if (visited.contains(current)) {
				continue;
			}
			visited.add(current);
			if (current.solutionFound()) {
				System.out.println("This is the solution with only " + current.getCost() + " attacks!");
				return;
			}
			List<EightQueensNode> children = current.generateChildren();

			// Find where to add each child to keep queue asc order:
			for (EightQueensNode child : children) {
				int bestOptionIndex = 0;
				for (int i = 0; i < queue.size(); i++) {
					if (queue.get(i).getCost() <= child.getCost()) {
						bestOptionIndex = i + 1;
					}
				}
				queue.add(bestOptionIndex, child);
			}

			System.out.println("QUEUE SIZE: " + queue.size());
			System.out.println("VISITED SIZE: " + visited.size());
		}
		System.out.println("There is no solution for this puzzle!");
	}
}
