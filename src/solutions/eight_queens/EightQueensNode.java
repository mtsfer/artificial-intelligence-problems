package solutions.eight_queens;

import solutions.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EightQueensNode {

	private EightQueensNode parent;
	private final int[] matrix;
	private int cost;

	public EightQueensNode(int[] matrix) {
		this.matrix = matrix;
		initializeCost();
	}

	public EightQueensNode(int[] matrix, EightQueensNode parent) {
		this.parent = parent;
		this.matrix = matrix;
		initializeCost();
	}

	private void initializeCost() {
		this.cost = getQuantityOfAttacks();
	}

	public void print() {
		int matrixLenght = matrix.length;
		char[][] board = new char[matrixLenght][matrixLenght];
		for (int col = 0; col < matrixLenght; col++) {
			int queenRow = matrix[col];
			board[queenRow][col] = 'Q';
		}
		for (int row = 0; row < matrixLenght; row++) {
			for (int col = 0; col < matrixLenght; col++) {
				String queen = Color.PURPLE.getAnsiCode() + " Q " + Color.RESET.getAnsiCode();
				Color color = (row + col) % 2 == 0 ? Color.WHITE_BG : Color.BLACK_BG;
				System.out.print(color.getAnsiCode() + (board[row][col] == 'Q' ? queen : "   ") + Color.RESET.getAnsiCode());
			}
			System.out.println();
		}
	}

	public boolean solutionFound() {
		return this.cost == 0;
	}

	public List<EightQueensNode> generateChildren() {
		System.out.println("CURRENT:");
		this.print();
		List<EightQueensNode> children = new ArrayList<>();
		Random random = new Random();
		int col = random.nextInt(8);
		for (int i = 0; i < matrix.length; i++) {
			if (i == col) {
				continue;
			}
			System.out.println("Generating child " + i + ": Swapping column " + i + " with column " + col);
			int[] matrixClone = cloneMatrix();
			int temp = matrixClone[i];
			matrixClone[i] = matrixClone[col];
			matrixClone[col] = temp;
			EightQueensNode child = new EightQueensNode(matrixClone, this);
			children.add(child);
			child.print();
			System.out.println("-------------------------");
		}
		return children;
	}

	public int getQuantityOfAttacks() {
		// as initial state and subsequent states doesn't allow queens at same row or column
		// should only check diagonals
		int attacks = 0;
		int boardLenght = matrix.length;
		for (int col1 = 0; col1 < boardLenght; col1++) {
			for (int col2 = col1 + 1; col2 < boardLenght; col2++) {
				boolean attacking = Math.abs(col1 - col2) == Math.abs(matrix[col1] - matrix[col2]);
				if (attacking) {
					attacks++;
				}
			}
		}
		System.out.println("There is " + attacks + " happening in this board.");
		return attacks;
	}

	private int[] cloneMatrix() {
		return Arrays.copyOf(this.matrix, this.matrix.length);
	}

	public EightQueensNode getParent() {
		return parent;
	}

	public int[] getMatrix() {
		return matrix;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EightQueensNode converted)) {
			return false;
		}
		return Arrays.equals(this.matrix, converted.getMatrix());
	}
}
