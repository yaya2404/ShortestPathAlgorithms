package phase1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class Search {
	
	Map map;
	PriorityQueue<Node> open;
	int size = 10; //need to determine optimal size for queue
	//BinaryHeap<Node> open;
	HashSet<Node> sucessors;
	HashSet<Node> closed; 
	protected int goalX;
	protected int goalY;
	
	public Search(Map m){
		map = m;
		goalX = map.getEndCoordinate().getX();
		goalY = map.getEndCoordinate().getY();
		closed = new HashSet<Node>();
		sucessors = new HashSet<Node>(8);
	}
	
	
	public Node findPath(){
		
		Node current;
		
		while(!open.isEmpty()){
			current = open.remove();
			
			if(current.equals(map.getCell(goalX, goalY)))
				return current;
			
			closed.add(current);
			
			sucessors = findSuccessorSet(current);
			
			for(Node s : sucessors){
				if(!closed.contains(s)){
					if(!open.contains(s)){
						s.set_g(Double.MAX_VALUE);
						s.setParent(null);
					}
					updateVertex(current,s);
				}
			}
		}
		
		//error has occurred
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
				if(x != 0 && y != 0){
					successor = map.getCell(currentX + x,currentY + y);
					if(successor != null && successor.getType() != Node.blockedcell)
						successors.add(successor);
				}
				
			}	
		}
		return successors;
	}
	
	public abstract void updateVertex(Node current, Node neighbor);
	
	public abstract void setupFringe (Comparator<Node> compare);

	
	public void printPath(){
		
		try{
			Node s = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());
			while(!s.equals(map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY()))){
				s.setType(Node.path);
				s = s.getParent();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
