package solutions.paris_metro;

import java.util.ArrayList;
import java.util.List;

public class ParisMetroNode {

	private final String name;
	private final List<Arc> arcs;
	private int distanceToGoal;

	public ParisMetroNode(String name) {
		this.name = name;
		this.arcs = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public List<Arc> getArcs() {
		return this.arcs;
	}

	public int getDistanceToGoal() {
		return distanceToGoal;
	}

	public void setDistanceToGoal(int distanceToGoal) {
		this.distanceToGoal = distanceToGoal;
	}

	public void addArc(Arc arc) {
		this.arcs.add(arc);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ParisMetroNode converted)) {
			return false;
		}
		return this.name.equals(converted.getName());
	}
}