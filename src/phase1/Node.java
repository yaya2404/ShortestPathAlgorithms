package phase1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Node {
	
	public static final char blockedcell = '0';
	public static final char unblockedcell = '1';
	public static final char hardcell = '2';
	public static final char reghighway = 'a';
	public static final char hardhighway = 'b';
	public static final char path = 'P';
	
	//testing only
	//public static final char searched = 'X';
	
	private Node parent;
	private char type;
	private int xcoordinate;
	private int ycoordinate; 
	private double g,h,f; //used in A*
	
	/*
	public Node(int x, int y){
		xcoordinate = x;
		ycoordinate = y;
		
		parent = null;
		type = unblockedcell;
		g = Double.POSITIVE_INFINITY;
		//g = 0;
		h = 0;
		f = g;
	}
	*/
	public Node(int x, int y, char type){
		xcoordinate = x;
		ycoordinate = y;
		this.type = type;
		
		parent = null;
		g = Double.POSITIVE_INFINITY;
		h = 0;
		f = g;
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
		f = g; //changes the value for uniform search
	}
	
	public double get_g(){
		return g;
	}
	
	public double get_h(){
		return h;
	}
	
	public double get_f(){
		return f;
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
	 * calculates f with weight
	 */
	public void update_f(double weight){
		f = g + weight * h;
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
							return 1;
						case hardhighway:
							return 1.5;
					}
				case hardcell:
					switch(neighbor.type){
						case unblockedcell:
							return 1.5;
						case hardcell:
							return 2;
						case reghighway:
							return 1.5;
						case hardhighway:
							return 2;
					}
				case reghighway:
					switch(neighbor.type){
						case unblockedcell:
							return 1;
						case hardcell:
							return 1.5;
						case reghighway:
							return 0.25;
						case hardhighway:
							return 0.375;
					}
				case hardhighway:
					switch(neighbor.type){
						case unblockedcell:
							return 1.5;
						case hardcell:
							return 2;
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
						return (Math.sqrt(2) + Math.sqrt(8)) / 2;
					case reghighway:
						return Math.sqrt(2);
					case hardhighway:
						return (Math.sqrt(2) + Math.sqrt(8)) / 2;
				}
			case hardcell:
				switch(neighbor.type){
					case unblockedcell:
						return (Math.sqrt(2) + Math.sqrt(8)) /2;
					case hardcell:
						return Math.sqrt(8);
					case reghighway:
						return (Math.sqrt(2) + Math.sqrt(8)) / 2;
					case hardhighway:
						return Math.sqrt(8);
				}
		
			case reghighway:
				switch(neighbor.type){
					case unblockedcell:
						return Math.sqrt(2);
					case hardcell:
						return (Math.sqrt(2) + Math.sqrt(8)) / 2;
					case reghighway: 
						return Math.sqrt(2) * .25;
					case hardhighway:
						return ((Math.sqrt(2) + Math.sqrt(8)) / 2) * .375;
				}
			case hardhighway:
				switch(neighbor.type){
					case unblockedcell:
						return (Math.sqrt(2) + Math.sqrt(8)) / 2;
					case hardcell:
						return Math.sqrt(8);
					case reghighway:
						return ((Math.sqrt(2) + Math.sqrt(8)) / 2) * .375;
					case hardhighway:
						return  Math.sqrt(8) * 0.5;
				}
				
			}
		}
		//System.out.println("error:" + neighbor.type + ""+ this.type);
		//System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
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
		//h = (double)Math.sqrt(2) * Math.min(dx,dy) + Math.max(dx, dy) - Math.min(dx,dy);
		
		h =  (double)Math.sqrt(2) * Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		
		/*
		 * Other possible heuristics:
		 * 
		 * D * (dx + dy) + (D2 - 2 * D) * Math.min(dx,dy) where D is vertical/horizontal cost, D2 is diagonal 
		 * 
		 * D2 * Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2))
		 * 
		 * D * (dx + dy)
		 */
		return h;
	}

	public boolean equals(Node n){
		return this.xcoordinate == n.xcoordinate && this.ycoordinate == n.ycoordinate;
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
			
			else if(x.g != y.g) 
				return (x.g > y.g) ? -1 : 1; //favors the larger g-values
			
			else
				return (x.h < y.h) ? -1 : 1;
				
		}
	}
	
	public static class NodeComparatorG implements Comparator<Node>{

		@Override
		public int compare(Node o1, Node o2) {
			if(o1.g != o2.g){
				return (o1.g > o2.g) ? 1 : -1;
			}else{
				Random random = new Random();
				return (random.nextDouble() < 0.5) ? 1 : -1; 
			}
		}
		
	}
	
	
}

