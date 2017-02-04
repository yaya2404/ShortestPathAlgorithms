package phase1;

import java.util.HashSet;
import java.util.PriorityQueue;

/*
 * Entire class can be deleted.
 */


public abstract class searchTemplate {

	
	Map map;
	PriorityQueue<Node> open;
	int size = 10; //need to determine optimal size for queue
	//BinaryHeap<Node> open;
	HashSet<Node> closed; 
	protected int goalX;
	protected int goalY;
	
	public searchTemplate(Map m){
		map = m;
		goalX = map.getEndCoordinate().getX();
		goalY = map.getEndCoordinate().getY();
		//open = new PriorityQueue<Node>(size, new Node.NodeComparator());
		closed = new HashSet<Node>();
	}
	
	
	public abstract boolean searchPath();
	
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
	
	public void printPath(){
		
		try{
			Node s = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());
			map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY()).setType(Node.path);
			while(!s.equals(map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY()))){
				s.setType(Node.path);
				s = s.getParent();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
