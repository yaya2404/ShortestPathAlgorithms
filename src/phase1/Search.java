package phase1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class Search {
	
	Map map;
	PriorityQueue<Node> open;
	int size = 10; //need to determine optimal size for queue
	//BinaryHeap<Node> open;
	HashSet<Node> successors;
	HashSet<Node> closed; 
	protected int goalX;
	protected int goalY;
	
	public Search(Map m){
		map = m;
		goalX = map.getEndCoordinate().getX();
		goalY = map.getEndCoordinate().getY();
		closed = new HashSet<Node>();
	}
	
	
	public boolean findPath(){
		
		Node current;
		
		while(!open.isEmpty()){
			
			current = open.remove();
			
			
			if(current.equals(map.getCell(goalX, goalY)))
				return true;
				
			closed.add(current);
			
			successors = findSuccessorSet(current);
		
			if(successors.isEmpty())
				System.out.println("Sucessor set is empty");
			
			for(Node s : successors){
				//for debugging
				//s.setType(Node.searched); 
				if(!closed.contains(s)){
					//if(!open.contains(s)){ //from the pseudo code
						//s.set_g(Double.MAX_VALUE);
					 	//s.setParent(null);
					//}
					updateVertex(current,s);
				}
			}
		}
		
		//error has occurred
		System.out.println("error has occurred during the path search");
		return true;	
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
				if(successor != null && successor.getType() != Node.blockedcell)
						successors.add(successor);
				}
				
			}	
		return successors;
	}
	
	public abstract void updateVertex(Node current, Node neighbor);
	
	public abstract void setupFringe (Comparator<Node> compare);

	
	public void printPath(){
		
		try{
			Node s = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());
			map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY()).setType(Node.path);
			while(s!=null){
				System.out.println(s.getType());
				s.setType(Node.path);
				s = s.getParent();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
