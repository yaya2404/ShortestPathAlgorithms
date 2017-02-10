package phase2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import phase1.Map;
import phase1.Node;

public class IntegratedAStar extends Search {

	ArrayList<HashMap<Double, Node>> openList; // Changing to queue
	HashSet<Node> closedAnchor;
	HashSet<Node> closedInad;

	public IntegratedAStar(Map m, Double weight1, Double weight2, int numberOfHueristics) {
		super(m, weight1, weight2, numberOfHueristics);

		// TODO Auto-generated constructor stub
	}

	@Override
	public void expandState(Node s) {
		for (int i = 0; i < numberOfHueristics; i++) {
			HashMap<Double, Node> open = openList.get(i);
			open.remove(open.get(open));
		}
		s.setV(s.get_g());

		for (Node neighbor : this.findSuccessorSet(s)) {
			if (!neighbor.isGenerated()) {
				neighbor.set_g(Double.POSITIVE_INFINITY);
				neighbor.setBp(null);
				neighbor.setV(Double.POSITIVE_INFINITY);
			}

			HashMap<Double, Node> openAnchor = openList.get(0);
			HashMap<Double, Node> currentOpen;

			if (neighbor.get_g() > s.get_g() + s.cost(neighbor)) {
				neighbor.set_g(s.get_g() + s.cost(neighbor));
				neighbor.setBp(s);
				if (!closedAnchor.contains(neighbor)) {
					openAnchor.put(getKey(neighbor, 0), neighbor);
					if (!closedInad.contains(neighbor)) {
						for (int i = 1; i < numberOfHueristics; i++) {
							currentOpen = openList.get(i);
							if (getKey(neighbor, i) <= w2 * getKey(neighbor, 0))
								openAnchor.put(getKey(neighbor, 0), neighbor);

						}
					}
				}
			}
		}
	}

	@Override
	public void setupFringe() {
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		Node goal = map.getCell(goalX, goalY);

		start.set_g(0);
		goal.set_g(Double.POSITIVE_INFINITY);

		start.setBp(null);
		goal.setBp(null);

		start.setU(Double.POSITIVE_INFINITY);
		goal.setU(Double.POSITIVE_INFINITY);

		for (int i = 0; i < numberOfHueristics; i++) {
			HashMap<Double, Node> open = new HashMap<Double, Node>();
			openList.add(open);
			open.put(getKey(start, i), start);
		}

		closedAnchor = new HashSet<Node>();
		closedInad = new HashSet<Node>();

	}

	public boolean search(int numberOfHueristics) {
		HashMap<Double, Node> openAnchor = openList.get(0);
		HashMap<Double, Node> currentOpen;
		Node goal = map.getCell(goalX, goalY);
		Node s;

		while (minKey(openAnchor) < Double.POSITIVE_INFINITY) {
			for (int i = 1; i < numberOfHueristics; i++) {
				currentOpen = openList.get(i);
				if (minKey(currentOpen) <= w2 * minKey(openAnchor)) {
					if (goal.get_g() <= minKey(openAnchor)) {
						if (goal.get_h() < Double.POSITIVE_INFINITY)
							return true;
					} else {
						s = top(currentOpen);
						expandState(s);
						closedInad.add(s);
					}
				} else {
					if (goal.get_g() <= minKey(openAnchor)) {
						if (goal.get_h() < Double.POSITIVE_INFINITY)
							return true;
					} else {
						s = top(openAnchor);
						expandState(s);
						closedAnchor.add(s);
					}
				}
			}
		}

		return false;
	}

}
