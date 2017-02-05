package phase1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class Search {
	
	Map map;
	PriorityQueue<Node> open;
	int size = 1000; //need to determine optimal size for queue
	HashSet<Node> successors;
	HashSet<Node> closed; 
	protected int goalX;
	protected int goalY;
	private long time;
	private int pathlength;
	
	public Search(Map m){
		map = m;
		goalX = map.getEndCoordinate().getX();
		goalY = map.getEndCoordinate().getY();
		closed = new HashSet<Node>(3000); //should decrease amount of rehashing
	}
	

	
	public boolean findPath(){
		
		Node current;
		
		long starttime = System.nanoTime();
		
		while(!open.isEmpty()){
			
			current = open.remove();
			
			
			if(current.equals(map.getCell(goalX, goalY))){
				time = (System.nanoTime() - starttime);///100000;
				return true;
			}
				
				
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
	
		return false;	
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

	public int getPathLength(){
		return this.pathlength;
	}
	public long getTime(){
		return this.time;
	}
	public int getNumOfExpandedNodes(){
		return this.closed.size();
	}
	
	public void printSummary(){
		System.out.println("\nSearch Summary\n");
		System.out.println("Starting Cell: (" + map.getStartCoordinate().getX() + "," + map.getStartCoordinate().getY() + ")");
		System.out.println("Goal Cell: (" + goalX + "," + goalY + ")");
		Node goal = map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY());
		System.out.println("Total Cost to Reach Goal: " + goal.get_g());
		System.out.println("Number of Nodes in Path: " + getPathLength());
		System.out.println("Number of Expanded Nodes: " + getNumOfExpandedNodes());
		System.out.println("Total Search Time (in milliseconds) : " + getTime());
		
		
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
