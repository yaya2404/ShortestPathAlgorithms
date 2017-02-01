package phase1;

import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class Search {
	
	Map map;
	PriorityQueue<Node> open;
	HashSet<Node> sucessors;
	//BinaryHeap<Node> open;
	HashSet<Node> closed;
	
	public Search(Map m){
		map = m;
		open = new PriorityQueue<Node>();
		closed = new HashSet<Node>();
		sucessors = new HashSet<Node>(8);
	}
	
	
	public Node findPath(int startX, int startY, int goalX, int goalY){
		Node start = map.getCell(startX,startY);
		start.set_g(0);
		start.calculate_h(goalX, goalY);
		start.update_f();
		open.add(start);
		
		Node current;
		
		while(!open.isEmpty()){
			current = open.remove();
			
			if(current.equals(map.getCell(goalX, goalY)))
				return current;
			
			closed.add(current);
			
			findSuccessorSet(current);
			
			for(Node s : sucessors){
				if(!closed.contains(s)){
					if(!open.contains(s)){
						s.set_g(Double.MAX_VALUE);
						s.setParent(null);
					}
					UpdateVertex(current,s);
				}
			}
		}
		
		
		
		return null;	
	}
	
	/**
	 * Finds all the neighboring nodes of a specified node
	 * @param s the current node
	 */
	
	public void findSuccessorSet(Node s){
		int currentX = s.getX();
		int currentY = s.getY();
		
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				if(x != 0 && y != 0){
					sucessors.add(map.getCell(currentX + x,currentY + y));
				}
				
			}	
		}
			
	}
	
	public abstract void UpdateVertex(Node current, Node neighbor);

}
