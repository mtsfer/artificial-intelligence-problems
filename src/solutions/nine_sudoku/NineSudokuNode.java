package solutions.nine_sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NineSudokuNode {

	private static final int EMPTY_SPACE_VALUE = 0;

	private NineSudokuNode parent;
	private final int[] matrix;
	private final int emptySpaceIndex;

	public NineSudokuNode(int[] matrix, int emptySpaceIndex) {
		this.matrix = matrix;
		this.emptySpaceIndex = emptySpaceIndex;
	}

	public NineSudokuNode(int[] matrix, int emptySpaceIndex, NineSudokuNode parent) {
		this.parent = parent;
		this.matrix = matrix;
		this.emptySpaceIndex = emptySpaceIndex;
	}

	private int[] copyMatrix() {
		return Arrays.copyOf(this.matrix, this.matrix.length);
	}

	public boolean solutionFound() {
		boolean solutionFound = true;
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i] == i) continue;
			solutionFound = false;
			break;
		}
		return solutionFound;
	}

	private NineSudokuNode generateChildAtReference(int[] coordDistance) {
		int[] copiedMatrix = copyMatrix();
		boolean isHorizontalMove = coordDistance[1] == 0;
		int newEmptySpaceIndex;
		if (isHorizontalMove) {
			newEmptySpaceIndex = emptySpaceIndex + coordDistance[0];
			// when moving horizontally, changed row
			boolean invalidHorizontalMove = newEmptySpaceIndex < 0 || emptySpaceIndex / 3 != newEmptySpaceIndex / 3;
			if (invalidHorizontalMove) {
				System.out.println("Invalid horizontal move! Skipping...");
				return null;
			}
		}
		else {
			newEmptySpaceIndex = emptySpaceIndex + 3 * coordDistance[1];
			// when moving, get out of field range
			boolean invalidVerticalMove = newEmptySpaceIndex < 0 || newEmptySpaceIndex >= matrix.length;
			if (invalidVerticalMove) {
				System.out.println("Invalid vertical move! Skipping...");
				return null;
			}
		}
		int number = copiedMatrix[newEmptySpaceIndex];
		copiedMatrix[newEmptySpaceIndex] = EMPTY_SPACE_VALUE;
		copiedMatrix[emptySpaceIndex] = number;
		NineSudokuNode child = new NineSudokuNode(copiedMatrix, newEmptySpaceIndex, this);
		System.out.println("Child at distance of " + Arrays.toString(coordDistance));
		child.print();
		return child;
	}

	public void print() {
		for (int i = 0; i < matrix.length; i++) {
			System.out.print(matrix[i]);
			if ((i + 1) % 3 == 0) {
				System.out.println();
			} else {
				System.out.print("  ");
			}
		}
	}

	public List<NineSudokuNode> generateChildren() {
		System.out.println("CURRENT:");
		this.print();
		List<NineSudokuNode> children = new ArrayList<>();
		System.out.println("CHILDREN:");
		int[][] childrenReferenceToParent = { {0, -1}, {1, 0}, {0, 1}, {-1, 0} }; // Up, right, down, left
		for (int[] childReference : childrenReferenceToParent) {
			NineSudokuNode child = generateChildAtReference(childReference);
			if (child == null) continue;
			children.add(child);
		}
		System.out.println("----------------------------");
		return children;
	}

	public int[] getMatrix() {
		return this.matrix;
	}

	public NineSudokuNode getParent() {
		return parent;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NineSudokuNode converted)) {
			return false;
		}
		return Arrays.equals(this.matrix, converted.getMatrix());
	}
}
