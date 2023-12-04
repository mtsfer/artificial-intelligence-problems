package solutions.paris_metro;

public class Arc {

	private Arc previous; // from -- util for path traversal
	private final ParisMetroNode node; // to
	private final int cost;
	private int accumulatedCost;

	public Arc(ParisMetroNode node, int cost) {
		this.previous = null;
		this.node = node;
		this.cost = cost;
		this.accumulatedCost = cost;
	}

	public int getAccumulatedCost() {
		return accumulatedCost;
	}

	public Arc getPrevious() {
		return previous;
	}

	public ParisMetroNode getNode() {
		return node;
	}

	public int getCost() {
		return cost;
	}

	public void setPrevious(Arc previous) {
		this.previous = previous;
		accumulatedCost += previous.getAccumulatedCost();
	}
}
