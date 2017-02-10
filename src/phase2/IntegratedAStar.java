package phase2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import phase1.Map;
import phase1.Node;

public class IntegratedAStar extends Search {

	ArrayList<PriorityQueue<Node>> openList; 
	HashSet<Node> closedAnchor;
	HashSet<Node> closedInad;

	public IntegratedAStar(Map m, Double weight1, Double weight2, int numberOfHueristics) {
		super(m, weight1, weight2, numberOfHueristics);
	}

	
	//Matt: added this in for you. Change if need be.
	public double getKey(Node s, int index){
		return 0;
	}
	
	@Override
	public void expandState(Node s) {
		for (int i = 0; i < numberOfHueristics; i++) {
			PriorityQueue<Node> open = openList.get(i);
			open.remove(s);
		}
		s.setV(s.get_g());

		for (Node neighbor : this.findSuccessorSet(s)) {

			PriorityQueue<Node> openAnchor = openList.get(0);
			PriorityQueue<Node> currentOpen;

			if (neighbor.get_g() > s.get_g() + s.cost(neighbor)) {
				neighbor.set_g(s.get_g() + s.cost(neighbor));
				neighbor.setBp(s);
				if (!closedAnchor.contains(neighbor)) {
					openAnchor.remove(neighbor);
					neighbor.setKey(getKey(neighbor, 0));
					openAnchor.add(neighbor);
					if (!closedInad.contains(neighbor)) {
						for (int i = 1; i < numberOfHueristics; i++) {
							currentOpen = openList.get(i);
							if (getKey(neighbor, i) <= w2 * getKey(neighbor, 0)){
								currentOpen.remove(neighbor);
								neighbor.setKey(getKey(neighbor, i));
								currentOpen.add(neighbor);
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

		start.setBp(null);
		goal.setBp(null);

		start.setU(Double.POSITIVE_INFINITY);
		goal.setU(Double.POSITIVE_INFINITY);

		for (int i = 0; i < numberOfHueristics; i++) {
			PriorityQueue<Node> open = new PriorityQueue<Node>(new Node.NodeKeyComparator()); // NodeKeyComparator
			start.setKey(getKey(start, i));
			open.add(start);
		}

		closedAnchor = new HashSet<Node>();
		closedInad = new HashSet<Node>();

	}

	public boolean findPath() {
		PriorityQueue<Node> openAnchor = openList.get(0);
		PriorityQueue<Node> currentOpen;
		Node goal = map.getCell(goalX, goalY);
		Node s;
		
		long starttime = System.nanoTime();
		double startMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; 
		double endMemory = 0;

		while (openAnchor.peek().getKey() < Double.POSITIVE_INFINITY) {
			for (int i = 1; i < numberOfHueristics; i++) {
				currentOpen = openList.get(i);
				if (currentOpen.peek().getKey() <= w2 * openAnchor.peek().getKey()) {
					if (goal.get_g() <= openAnchor.peek().getKey()) {
						if (goal.get_h() < Double.POSITIVE_INFINITY){
							time = (System.nanoTime() - starttime)/1000000;
							endMemory = (Runtime.getRuntime().totalMemory() -  Runtime.getRuntime().freeMemory())/ 1024d; //KB output
							memory = endMemory - startMemory;
							if(memory < 0){
								memory = 0;
							}
							return true;
						}
							
					} else {
						s = currentOpen.remove();
						expandState(s);
						closedInad.add(s);
					}
				} else {
					if (goal.get_g() <= openAnchor.peek().getKey()) {
						if (goal.get_h() < Double.POSITIVE_INFINITY)
							return true;
					} else {
						s = openAnchor.remove();
						expandState(s);
						closedAnchor.add(s);
					}
				}
			}
		}

		return false;
	}

}
