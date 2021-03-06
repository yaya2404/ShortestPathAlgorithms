package phase2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import phase1.Map;
import phase1.Node;

public class IntegratedAStar extends Search {

	ArrayList<HashMap<Node,Double>> openList;
	HashSet<Node> closedAnchor;
	HashSet<Node> closedInad;

	public IntegratedAStar(Map m, Double weight1, Double weight2, int numberOfHeuristics) {
		super(m, weight1, weight2, numberOfHeuristics);
		openList = new ArrayList<HashMap<Node,Double>>(numberOfHeuristics);

	}

	public double getKey(Node s, int index) {
		return s.get_g() + w1 * s.calculate_h(goalX, goalY, index);
	}

	
	public void expandState(Node s) {
		for (int i = 0; i < numberOfHeuristics; i++) {
			HashMap<Node,Double> open = openList.get(i);
			open.remove(s);
		}

		for (Node neighbor : this.findSuccessorSet(s)) {
			
			HashMap<Node,Double> openAnchor = openList.get(0);
			HashMap<Node,Double> currentOpen;

			if (neighbor.get_g() > s.get_g() + s.cost(neighbor)) {
				neighbor.set_g(s.get_g() + s.cost(neighbor));
				neighbor.setParent(s);
				if (!closedAnchor.contains(neighbor)) {
					openAnchor.put(neighbor,getKey(neighbor, 0));
					if (!closedInad.contains(neighbor)) {
						for (int i = 1; i < numberOfHeuristics; i++) {
							currentOpen = openList.get(i);
							if (getKey(neighbor, i) <= w2 * getKey(neighbor, 0)){
								currentOpen.put(neighbor,getKey(neighbor, i));
							}
								
						}
					}
				}
			}
		}
	}

	
	public int getNumOfExpandedNodes(){
		return this.closedAnchor.size() + this.closedInad.size();
	}
	
	@Override
	public void setupFringe() {
		Node start = map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY());
		Node goal = map.getCell(goalX, goalY);

		start.set_g(0);
		goal.set_g(Double.POSITIVE_INFINITY);

		start.setParent(null);
		goal.setParent(null);

		for (int i = 0; i < numberOfHeuristics; i++) {
			HashMap<Node,Double> open = new HashMap<Node,Double>(); 
			open.put(start,getKey(start, i));
			openList.add(open);
			
		}

		closedAnchor = new HashSet<Node>();
		closedInad = new HashSet<Node>();

	}

	public boolean findPath() {
		HashMap<Node,Double> openAnchor = openList.get(0);
		HashMap<Node,Double> currentOpen;
		Node goal = map.getCell(goalX, goalY);
		Node s;
		
		long starttime = System.nanoTime();
		double startMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; 
		double endMemory = 0;

		while (minKey(openAnchor) < Double.POSITIVE_INFINITY) {
			for (int i = 1; i < numberOfHeuristics; i++) {
				currentOpen = openList.get(i);
				if (minKey(currentOpen) <= w2 * minKey(openAnchor)) {
					if (goal.get_g() <= minKey(currentOpen)) {
						if (goal.get_g() < Double.POSITIVE_INFINITY){
							time = (System.nanoTime() - starttime)/1000000;
							endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; //KB output
							memory = endMemory - startMemory;
							if(memory < 0){
								memory = 0;
							}
							return true;
						}
							
					} else {
						s = top(currentOpen);
						expandState(s);
						closedInad.add(s);
					}
				} else {
					if (goal.get_g() <= minKey(openAnchor)) {
						if (goal.get_g() < Double.POSITIVE_INFINITY){
							time = (System.nanoTime() - starttime)/1000000;
							endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; //KB output
							memory = endMemory - startMemory;
							if(memory < 0){
								memory = 0;
							}
							return true;
						}
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


	public double getPathCost() {
		Node goal = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());
		return goal.get_g();
	}

	public void printPath() {
		pathlength = 0;
		Node s = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());

		while (s != null) {
			s.setType(Node.path);
			s.update_f(w1);
			s = s.getParent();
			pathlength++;
		}
	}

}
