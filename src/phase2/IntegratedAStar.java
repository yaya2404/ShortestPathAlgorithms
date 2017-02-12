package phase2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import phase1.Map;
import phase1.Node;

public class IntegratedAStar extends Search {

	ArrayList<PriorityQueue<Node>> openList;
	//ArrayList<HashMap<Node,Double>> openList;
	HashSet<Node> closedAnchor;
	HashSet<Node> closedInad;

	public IntegratedAStar(Map m, Double weight1, Double weight2, int numberOfHueristics) {
		super(m, weight1, weight2, numberOfHueristics);
		openList = new ArrayList<PriorityQueue<Node>>(numberOfHueristics);
		//openList = new ArrayList<HashMap<Node,Double>>(numberOfHueristics);

	}

	public double getKey(Node s, int index){
		if(s == null)
			return Double.MAX_VALUE;
		return s.get_g() + w1 * s.calculate_h(goalX, goalY, index);
	}
	
/*	public void expandState(Node s) {
		for (int i = 0; i < numberOfHueristics; i++) {
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
						for (int i = 1; i < numberOfHueristics; i++) {
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

		for (int i = 0; i < numberOfHueristics; i++) {
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
			for (int i = 1; i < numberOfHueristics; i++) {
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
*/
	
//priority queue version. new set keys calls in other queues should not affect priority in other queues
	
	public void expandState(Node s) {
		for (int i = 0; i < numberOfHueristics; i++) {
			PriorityQueue<Node> open = openList.get(i);
			open.remove(s);
		}

		for (Node neighbor : this.findSuccessorSet(s)) {
			
			PriorityQueue<Node> openAnchor = openList.get(0);
			PriorityQueue<Node> currentOpen;

			if (neighbor.get_g() > s.get_g() + s.cost(neighbor)) {
				neighbor.set_g(s.get_g() + s.cost(neighbor));
				neighbor.setParent(s);
				if (!closedAnchor.contains(neighbor)) {
					openAnchor.remove(neighbor);
					//neighbor.set_h(neighbor.calculate_h(goalX, goalY, 0));
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

		start.setParent(null);
		goal.setParent(null);

		for (int i = 0; i < numberOfHueristics; i++) {
			PriorityQueue<Node> open = new PriorityQueue<Node>(new Node.NodeComparator()); // NodeKeyComparator
			start.setKey(getKey(start, i));
			open.add(start);
			openList.add(open);
			
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

		while (getKey(openAnchor.peek(), 0) < Double.POSITIVE_INFINITY) {
			for (int i = 1; i < numberOfHueristics; i++) {
				currentOpen = openList.get(i);
				if (getKey(currentOpen.peek(), i) <= w2 * getKey(openAnchor.peek(), 0)) {
					if (goal.get_g() <= getKey(currentOpen.peek(), 0)) {
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
						s = currentOpen.remove();
						expandState(s);
						closedInad.add(s);
					}
				} else {
					if (goal.get_g() <= getKey(openAnchor.peek(), 0)) {
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
						s = openAnchor.remove();
						expandState(s);
						closedAnchor.add(s);
					}
				}
			}
		}

		return false;
	}
	
	public void printPath(){
		pathlength = 0;
		Node s = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());

		while(s!=null){
			//System.out.println(s.getType());
			s.setType(Node.path);
			s = s.getParent();
			pathlength++;
		}
	}
	
}
