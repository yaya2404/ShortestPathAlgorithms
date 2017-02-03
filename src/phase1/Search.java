package phase1;

import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class Search {
	
	Map map;
	PriorityQueue<Node> open;
	int size = 10; //need to determine optimal size for queue
	HashSet<Node> sucessors;
	//BinaryHeap<Node> open;
	HashSet<Node> closed; 
	protected int goalX;
	protected int goalY;
	
	public Search(Map m, int x, int y){
		map = m;
		goalX = x;
		goalY = y;
		open = new PriorityQueue<Node>(size, new Node.NodeComparator());
		closed = new HashSet<Node>();
		sucessors = new HashSet<Node>(8);
	}
	
	
	public Node findPath(int startX, int startY){
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
		
		//error has occurred
		return null;	
	}
	
	
	/**
	 * Finds all the neighboring nodes of a specified node
	 * @param s the current node
	 */
	/*
	public void findSuccessorSet(Node s){
		int currentX = s.getX();
		int currentY = s.getY();
		
		Node successor;
		
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				if(x != 0 && y != 0){
					successor = map.getCell(currentX + x,currentY + y);
					if(successor != null && successor.getType() != Node.blockedcell)
						sucessors.add(successor);
				}
				
			}	
		}
			
	}
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
	
	public abstract void UpdateVertex(Node current, Node neighbor);
	
	
	 /* Untested of the A* UpdateVertex method
	public void UpdateVertexA(Node current, Node neighbor){
		if(current.get_g() + current.cost(neighbor) < neighbor.get_g()){
			
			neighbor.set_g(current.get_g() + current.cost(neighbor));
			neighbor.calculate_h(goalX, goalY);
			neighbor.update_f();
			neighbor.setParent(current);
			
			if(open.contains(neighbor))
				open.remove(neighbor);
			
			open.add(neighbor);
		}
	}
	
	*/
	
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
