package phase2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import phase1.Map;
import phase1.Node;

public abstract class Search {

	
	Map map;
	int goalX;
	int goalY;
	double w1,w2;
	int numberOfHueristics;
	
	public Search(Map m, Double weight1, Double weight2, int hueristics){
		map = m;
		goalX = map.getEndCoordinate().getX();
		goalY = map.getEndCoordinate().getY();
		w1 = weight1;
		w2 = weight2;
		numberOfHueristics = hueristics;
	}
	
	
	//returns g(s) + w1 * h(s)
	public double getKey(Node s, int i){
		return s.get_g() + w1 * s.get_h();
	}
	
	public abstract void expandState(Node s);
	public abstract void setupFringe();
	
	public boolean findPath(){
		return true;
	}
	
	public double minKey(HashMap<Double, Node> open) {
		double minKey = Double.MAX_VALUE;
		for(Double key : open.keySet()){
			if(key < minKey){
				minKey = key;
			}
			
		}
		return minKey;
	}
	
	public Node top(HashMap<Double, Node> open) {
		double minKey = Double.MAX_VALUE;
		for(Double key : open.keySet()){
			if(key < minKey){
				minKey = key;
			}
			
		}
		return open.get(minKey);
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
}
