package phase1;

import java.util.Comparator;

public class Node {
	
	public static final char blockedcell = '0';
	public static final char unblockedcell = '1';
	public static final char hardcell = '2';
	public static final char reghighway = 'a';
	public static final char hardhighway = 'b';
	public static final char path = 'P';
	
	
	private Node parent;
	private char type;
	private int xcoordinate;
	private int ycoordinate; 
	private double g,h,f; //used in A*
	
	public Node(int x, int y){
		xcoordinate = x;
		ycoordinate = y;
		
		parent = null;
		type = unblockedcell;
		g = 0;
		h = 0;
		f = 0;
	}
	
	public int getX(){
		return xcoordinate;
	}
	
	public int getY(){
		return ycoordinate;
	}


	/**
	 * used for backtracking once the path is found
	 * @return parent of current node
	 */

	public Node getParent(){
		return parent;
	}
	/**
	 * 
	 * @param p the parent node
	 */
	public void setParent(Node p){
		parent = p;
	}
	
	/**
	 * returns the type of cell
	 * @return
	 */
	public char getType(){
		return type;
	}
	/**
	 * 
	 * @param gValue the new value
	 */
	public void set_g(double gValue){
		g = gValue;
	}
	
	public double get_g(){
		return g;
	}
	
	/**
	 * Changes unblocked cells to other cell types
	 * @param cellType type of cell
	 */
	public void setType(char cellType){
		type = cellType;
	}
	
	/**
	 * calculates f
	 */
	public void update_f(){
		f = g + h;
	}
	
	/**
	 * The costs to move from the current cell to a neighboring cell
	 * @param neighbor 
	 * @return the cost to travel between the two nodes
	 */
	public double cost(Node neighbor){
		//horizontal/vertical costs
		if(xcoordinate == neighbor.xcoordinate|| ycoordinate == neighbor.ycoordinate){
			//checks type of current cell first
			switch(type){
				case unblockedcell:
					switch(neighbor.type){
						case unblockedcell:
							return 1;
						case hardcell:
							return 1.5;
						case reghighway:
							return 0.5;
						case hardhighway:
							return 0.75;
					}
				case hardcell:
					switch(neighbor.type){
						case unblockedcell:
							return 1.5;
						case hardcell:
							return 2;
						case reghighway:
							return 0.75;
						case hardhighway:
							return 1;
					}
				case reghighway:
					switch(neighbor.type){
						case unblockedcell:
							return 0.5;
						case hardcell:
							return 0.75;
						case reghighway:
							return 0.25;
						case hardhighway:
							return 0.375;
					}
				case hardhighway:
					switch(neighbor.type){
						case unblockedcell:
							return 0.75;
						case hardcell:
							return 1;
						case reghighway:
							return 0.375;
						case hardhighway:
							return 0.5;
					}
					
			}			
		}
		//diagonal costs
		else{
			//checks type of current cell first
			switch(type){
			case unblockedcell:
				switch(neighbor.type){
					case unblockedcell:
						return Math.sqrt(2);
					case hardcell:
						return (Math.sqrt(2) + Math.sqrt(8))/2;
					case reghighway:
						return Math.sqrt(2)/2;
					case hardhighway:
						return (Math.sqrt(2) + Math.sqrt(8)) / 4;
				}
			case hardcell:
				switch(neighbor.type){
					case unblockedcell:
						return (Math.sqrt(2) + Math.sqrt(8))/2;
					case hardcell:
						return Math.sqrt(8);
					case reghighway:
						return (Math.sqrt(2) + Math.sqrt(8)) / 4;
					case hardhighway:
						return Math.sqrt(8)/2;
				}
				//needs clarification (diagonal movement to a different highway, etc)
			case reghighway:
				switch(neighbor.type){
					case unblockedcell:
						return Math.sqrt(2)/2;
					case hardcell:
						return (Math.sqrt(2) + Math.sqrt(8)) / 4;
					case reghighway: 
						return Math.sqrt(2)/4;
					case hardhighway:
						return (Math.sqrt(2) + Math.sqrt(8)) / 8;
				}
			case hardhighway:
				switch(neighbor.type){
					case unblockedcell:
						return (Math.sqrt(2) + Math.sqrt(8)) / 4;
					case hardcell:
						return Math.sqrt(8)/2;
					case reghighway:
						return (Math.sqrt(2) + Math.sqrt(8)) / 8;
					case hardhighway:
						return  Math.sqrt(8)/4;
				}
				
			}
		}
		return -1;
	}
	/**
	 * 
	 * @param goalX the goal's x coordinate 
	 * @param goalY the goal's y coordinate 
	 * @return h value
	 */
	public double calculate_h(int goalX, int goalY){
		double dx = Math.abs(xcoordinate - goalX);
		double dy = Math.abs(ycoordinate - goalY);
		
		//example from the assignment
		return (double)Math.sqrt(2) * Math.min(dx,dy) + Math.max(dx, dy) - Math.min(dx,dy);
	}
	/*
	 * the heuristic value with a weight
	 */
	public double calculate_h(int goalX, int goalY, double weight){
		double dx = Math.abs(xcoordinate - goalX);
		double dy = Math.abs(ycoordinate - goalY);
		
		//example from the assignment
		return weight * ((double)Math.sqrt(2) * Math.min(dx,dy) + Math.max(dx, dy) - Math.min(dx,dy));
	}
	
	/**
	 * 
	 * Used for the priority queue
	 *
	 */
	public static class NodeComparator implements Comparator<Node>{
		public int compare(Node x, Node y){
			if(x.f != y.f)
				return (x.f < y.f) ? -1 : 1;
			
			else //tie-breaker (sample equation)
				return (x.h < y.f) ? -1 : 1;	
				
		}
	}
	
	public static class NodeComparatorG implements Comparator<Node>{

		@Override
		public int compare(Node o1, Node o2) {
			if(o1.g != o2.g){
				return (o1.g < o2.g) ? -1 : 1;
			}else{
				return 0;
			}
		}
		
	}
	
	
}

