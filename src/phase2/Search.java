package phase2;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import phase1.Map;
import phase1.Node;

public abstract class Search {

	
	Map map;
	int goalX;
	int goalY;
	double w1,w2;
	int numberOfHeuristics;
	protected long time;
	protected int pathlength;
	protected double memory;
	
	public Search(Map m, Double weight1, Double weight2, int heuristics){
		map = m;
		goalX = map.getEndCoordinate().getX();
		goalY = map.getEndCoordinate().getY();
		w1 = weight1;
		w2 = weight2;
		numberOfHeuristics = heuristics;
	}
	
	
	public abstract double getKey(Node s, int index);
	public abstract void setupFringe();
	public abstract boolean findPath();
		
	
	
	public double minKey(HashMap<Node, Double> open) {
		if(open.isEmpty())
			return Double.MAX_VALUE;
		else
			return Collections.min(open.values());
	}
	
	public Node top(HashMap<Node,Double> open) {
		
		double minKey = Collections.min(open.values());
		
		for(Entry<Node,Double> entry : open.entrySet()) {
			if(entry.getValue() == minKey)
				return entry.getKey();
		}
		
		return null;
	}
	
	/**
	 * Finds all the neighboring nodes of a specified node
	 * @param s the current node
	 */

	public HashSet<Node> findSuccessorSet(Node s){
		int currentX = s.getX();
		int currentY = s.getY();
		
		
		HashSet<Node> successors = new HashSet<Node>();
		Node successor;
		
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				if(x == 0 && y == 0)
					continue;
				successor = map.getCell(currentX + x,currentY + y);
				if(successor != null && successor.getType() != Node.blockedcell){
					successors.add(successor);
				}
			}
				
		}	
		return successors;
	}
	
	public int getPathLength(){
		return this.pathlength;
	}
	public long getTime(){
		return this.time;
	}
	public abstract int getNumOfExpandedNodes();
		
	public double getMemoryUsed(){
		return this.memory;
	}
	
	
	public abstract double getPathCost();
	
	public void printSummary(){
		System.out.println("\nSearch Summary\n");
		System.out.println("Starting Cell: (" + map.getStartCoordinate().getX() + "," + map.getStartCoordinate().getY() + ")");
		System.out.println("Goal Cell: (" + goalX + "," + goalY + ")");
		System.out.println("Total Cost to Reach Goal: " + getPathCost());
		System.out.println("Number of Nodes in Path: " + getPathLength());
		System.out.println("Number of Expanded Nodes: " + getNumOfExpandedNodes());
		System.out.println("Total Search Time (in milliseconds) : " + getTime());
		System.out.println("Memory requirement: " + this.memory + "KB");
		
	}
	
	
	//Needs to be abstract because path for sequential A* depends on the heuristic where as integrated does not depend on heuristic

	public abstract void printPath();
		
}
