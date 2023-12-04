package solutions.paris_metro;

import solutions.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParisMetroSearch {

	private final static int METRO_SPEED = 30;
	private final static int DELAY_FOR_STATION_SWITCH_IN_MINUTES = 5;

	private final Arc initialState;
	private final ParisMetroNode searchGoal;
	private final List<ParisMetroNode> graph;
	private final List<Distance> distances;

	public ParisMetroSearch(
			ParisMetroNode initialState,
			ParisMetroNode searchGoal,
			List<ParisMetroNode> graph,
			List<Distance> distances) {
		this.initialState = new Arc(initialState, 0);
		this.searchGoal = searchGoal;
		this.graph = graph;
		this.distances = distances;
	}

	private int getDistanceToGoal(ParisMetroNode node) {
		return distances
				.stream()
				.filter(distance -> distance.origin().equals(node.getName()) &&
						distance.destination().equals(this.searchGoal.getName()))
				.findFirst()
				.orElseThrow()
				.distance();
	}

	public void findSolution() throws InterruptedException {
		for (ParisMetroNode node : graph) {
			int distance = getDistanceToGoal(node);
			node.setDistanceToGoal(distance);
		}

		List<ParisMetroNode> visited = new ArrayList<>();
		LinkedList<Arc> priorityQueue = new LinkedList<>();
		priorityQueue.add(initialState);

		while (!priorityQueue.isEmpty()) {
			Arc currentArc = priorityQueue.remove();
			ParisMetroNode currentNode = currentArc.getNode();

			if (visited.contains(currentNode)) {
				continue;
			}
			visited.add(currentNode);

			if (arrivedAtDestination(currentNode)) {
				printSolution(currentArc);
				return;
			}

			List<Arc> childrenArcs = currentArc.getNode().getArcs(); // equivalent to children
			for (Arc arc : childrenArcs) {
				arc.setPrevious(currentArc);
				int position = getArcPositionInPriorityQueue(arc, priorityQueue);
				priorityQueue.add(position, arc);
			}
		}
		System.out.println("There is no solution for this puzzle");
	}

	private int getArcPositionInPriorityQueue(Arc entry, LinkedList<Arc> queue) {
		int arcPosition = 0;
		for (int i = 0; i < queue.size(); i++) {
			Arc current = queue.get(i);

			int currentOverallCost = current.getAccumulatedCost() + current.getNode().getDistanceToGoal();
			int entryOverallCost = entry.getAccumulatedCost() + entry.getNode().getDistanceToGoal();

			if (currentOverallCost <= entryOverallCost) {
				arcPosition = i + 1;
			}
		}

		return arcPosition;
	}

	private boolean arrivedAtDestination(ParisMetroNode currentNode) {
		return currentNode.equals(searchGoal);
	}

	private void printSolution(Arc endArc) throws InterruptedException {
		List<Arc> solution = new ArrayList<>();

		Arc current = endArc;
		solution.add(current);

		int numberOfStationSwitches = 0;
		while (true) {
			Arc parentArc = current.getPrevious();
			if (parentArc == null) {
				break;
			}
			current = parentArc;
			solution.add(parentArc);

			numberOfStationSwitches++;
		}

		Collections.reverse(solution);

		Thread.sleep(500);
		for (int i = 0; i < solution.size(); i++) {
			Arc arc = solution.get(i);

			String initialSymbol = "#";
			String text = "Station " + arc.getNode().getName();

			Thread.sleep(500);
			if (i == 0) {
				text = text + Color.BLUE_BOLD.getAnsiCode() + " (Origin)" + Color.RESET.getAnsiCode();
				initialSymbol = Color.BLUE_BG.getAnsiCode() + "#" + Color.RESET.getAnsiCode();
			}
			else if (i == solution.size() - 1) {
				text = text + Color.PURPLE.getAnsiCode() + " (Destination)" + Color.RESET.getAnsiCode();
				initialSymbol = Color.PURPLE_BG.getAnsiCode() + "#" + Color.RESET.getAnsiCode();
			}
			System.out.println();
			System.out.println(initialSymbol + " " + text);

			if (i < solution.size() - 1) {
				System.out.print("|\n|  " + solution.get(i + 1).getCost() + " km\n|");
			}
		}
		System.out.println(Color.PURPLE.getAnsiCode() + "-------------------------------------" + Color.RESET.getAnsiCode());

		Thread.sleep(500);
		int distance = endArc.getAccumulatedCost();

		int timeInTransit = (int) (((float) distance / METRO_SPEED) * 60);
		int timeInStationSwitch = numberOfStationSwitches * DELAY_FOR_STATION_SWITCH_IN_MINUTES;
		int estimatedTimeInMinutes =  timeInTransit + timeInStationSwitch;
		int hours = estimatedTimeInMinutes / 60;
		int minutes = estimatedTimeInMinutes % 60;

		System.out.println("Distance: " + endArc.getAccumulatedCost() + "km");
		if (estimatedTimeInMinutes == 0) {
			System.out.println("Estimated time: 0min");
		} else {
			System.out.println("Estimated time: " + (hours == 0 ? "" : hours + "h") + (minutes == 0 ? "" : minutes + "min"));
		}
	}
}
