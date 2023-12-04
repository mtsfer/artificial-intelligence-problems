package solutions.paris_metro;

import solutions.Color;

import java.util.*;

public class Main {
	private static final List<Distance> STATIONS_REAL_DISTANCES = List.of(
			new Distance("E1", "E2", 11),
			new Distance("E2", "E3", 9),
			new Distance("E2", "E9", 11),
			new Distance("E2", "E10", 4),
			new Distance("E3", "E4", 7),
			new Distance("E3", "E9", 10),
			new Distance("E3", "E13", 19),
			new Distance("E4", "E5", 14),
			new Distance("E4", "E8", 16),
			new Distance("E4", "E13", 12),
			new Distance("E5", "E6", 3),
			new Distance("E5", "E7", 2),
			new Distance("E5", "E8", 33),
			new Distance("E8", "E9", 10),
			new Distance("E8", "E12", 7),
			new Distance("E9", "E11", 14),
			new Distance("E13", "E14", 5)
	);

	private static final int[][] STATIONS_STRAIGHT_DISTANCES = new int[][]{
			{0, 11, 20, 27, 40, 43, 39, 28, 18, 10, 18, 30, 30, 32},
			{11, 0, 9, 16, 29, 32, 28, 19, 11, 4, 17, 23, 21, 24},
			{20, 9, 0, 7, 20, 22, 19, 15, 10, 11, 21, 21, 13, 18},
			{27, 16, 7, 0, 13, 16, 12, 13, 13, 18, 26, 21, 11, 17},
			{40, 29, 20, 13, 0, 3, 2, 21, 25, 31, 38, 27, 16, 20},
			{43, 32, 22, 16, 3, 0, 4, 23, 28, 33, 41, 30, 17, 20},
			{39, 28, 19, 12, 2, 4, 0, 22, 25, 29, 38, 28, 13, 17},
			{28, 19, 15, 13, 21, 23, 22, 0, 9, 22, 18, 7, 25, 30},
			{18, 11, 10, 13, 25, 28, 25, 9, 0, 13, 12, 12, 23, 28},
			{10, 4, 11, 18, 31, 33, 29, 22, 13, 0, 20, 27, 20, 23},
			{18, 17, 21, 26, 38, 41, 38, 18, 12, 20, 0, 15, 35, 39},
			{30, 23, 21, 21, 27, 30, 28, 7, 12, 27, 15, 0, 31, 37},
			{30, 21, 13, 11, 16, 17, 13, 25, 23, 20, 35, 31, 0, 5},
			{32, 24, 18, 17, 20, 20, 17, 30, 28, 23, 39, 37, 5, 0}
	};

	public static void main(String[] args) throws InterruptedException {
		List<Distance> distances = convertDistanceMatrixToList();
		List<ParisMetroNode> graph = constructGraph();

		System.out.println(Color.PURPLE.getAnsiCode() + "=====================" + Color.RESET.getAnsiCode());
		System.out.println(Color.BLUE_BOLD.getAnsiCode() + "PARIS METRO ASSISTANT" + Color.RESET.getAnsiCode());
		System.out.println(Color.PURPLE.getAnsiCode() + "=====================" + Color.RESET.getAnsiCode());

		System.out.println("These are the stations of Paris with the adjacent stations and the relative distance in km:");
		printGraph(graph);

		System.out.println(Color.PURPLE.getAnsiCode() + "----------------------------------------------------------------------" + Color.RESET.getAnsiCode());
		Thread.sleep(500);

		System.out.println("Give me informations about your trip and I'll give you the best route!");

		ParisMetroNode origin = getNodeFromInput(graph, "> Departure station [i.e. E3]: ");
		ParisMetroNode destination = getNodeFromInput(graph, "> Destination station [i.e. E8]: ");

		System.out.println(Color.PURPLE.getAnsiCode() + "-------------------------------------" + Color.RESET.getAnsiCode());
		System.out.println("Calculating the best route for you...");

		ParisMetroSearch search = new ParisMetroSearch(origin, destination, graph, distances);
		search.findSolution();
	}

	private static List<Distance> convertDistanceMatrixToList() {
		List<Distance> distances = new ArrayList<>();
		for (int i = 0; i < Main.STATIONS_STRAIGHT_DISTANCES.length; i++) {
			String origin = "E" + (i + 1);
			for (int j = 0; j < Main.STATIONS_STRAIGHT_DISTANCES.length; j++) {
				String destination = "E" + (j + 1);
				Distance distance = new Distance(origin, destination, Main.STATIONS_STRAIGHT_DISTANCES[i][j]);
				distances.add(distance);
			}
		}
		return distances;
	}

	private static ParisMetroNode getNodeFromInput(List<ParisMetroNode> graph, String inputText) {
		ParisMetroNode desired;
		while (true) {
			Scanner in = new Scanner(System.in);
			System.out.print(inputText);
			String input = in.nextLine();
			Optional<ParisMetroNode> inputNode = graph
					.stream()
					.filter(node -> node.getName().equals(input))
					.findFirst();
			if (inputNode.isEmpty()) {
				System.out.println("There ir no " + input + " station. Try again!");
				continue;
			}
			desired = inputNode.get();
			break;
		}
		return desired;
	}

	private static List<ParisMetroNode> constructGraph() {
		List<ParisMetroNode> graph = new ArrayList<>();
		for (Distance distance : Main.STATIONS_REAL_DISTANCES) {
			ParisMetroNode fromNode = findOrCreateNode(graph, distance.origin());
			ParisMetroNode toNode = findOrCreateNode(graph, distance.destination());

			int weight = distance.distance();
			fromNode.addArc(new Arc(toNode, weight));
			toNode.addArc(new Arc(fromNode, weight));
		}
		return graph;
	}

	private static ParisMetroNode findOrCreateNode(List<ParisMetroNode> graph, String destination) {
		return graph
				.stream()
				.filter(node -> node.getName().equals(destination))
				.findFirst()
				.orElseGet(() -> {
					ParisMetroNode node = new ParisMetroNode(destination);
					graph.add(node);
					return node;
				});
	}

	private static void printGraph(List<ParisMetroNode> graph) throws InterruptedException {
		List<ParisMetroNode> sorted = List.copyOf(graph)
				.stream()
				.sorted(Comparator.comparingInt(node -> Integer.parseInt(node.getName().substring(1))))
				.toList();

		for (ParisMetroNode node : sorted) {
			Thread.sleep(250);
			System.out.print("~ " + node.getName() + " -> ");
			List<Arc> arcs = node.getArcs();
			for (int i = 0; i < arcs.size(); i++) {
				Arc arc = arcs.get(i);
				System.out.print(arc.getNode().getName() + " (" + arc.getCost() + "km)");
				if (i < arcs.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println();
		}
	}

}
