package phase1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Node{
	
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
	private double u, v, key; //used in phase 2 (not sure is needed yet)
	private Node bp; //Looks like the same as parent
	
	private double[] hList;
	
	
	//used only for Sequential A*
	private double[] gList;
	private Node[] bpList;
	
	public Node(int x, int y, char type){
		xcoordinate = x;
		ycoordinate = y;
		this.type = type;
		parent = null;
		g = Double.POSITIVE_INFINITY;
		h = 0;
		f = g;
		
		//phase 2
		key = Double.POSITIVE_INFINITY;;
		bp =null;
		v = Double.POSITIVE_INFINITY;
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
	
	public void set_h(double H){
		this.h = H;
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
		System.out.println("error:" + neighbor.type + ""+ this.type);
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
		
		//h =  (double)Math.sqrt(2) * Math.sqrt(dx * dx + dy * dy);
		
		//h = .25 * (dx + dy);
		
		//h = ((.25 +  Math.sqrt(2)) / 2)* Math.sqrt(dx * dx + dy * dy);
		
		//h = .25 * (dx + dy) - (Math.sqrt(2)  -  2 * 1) * Math.min(dx,dy); 
		
		h = .25 * (dx + dy) + (Math.sqrt(2) * .25 -  2 * .25) * Math.min(dx,dy);
		
		return h;
	}
	
	public double calculate_h(int goalX, int goalY, int heuristic){
		double dx = Math.abs(xcoordinate - goalX);
		double dy = Math.abs(ycoordinate - goalY);
		
		switch(heuristic){
			case 4:
				return (double)Math.sqrt(2) * Math.min(dx,dy) + Math.max(dx, dy) - Math.min(dx,dy);
			case 1:
				return  (double)Math.sqrt(2) * Math.sqrt(dx * dx + dy * dy);
			case 2:
				return .25 * (dx + dy) - (Math.sqrt(2)  -  2 * 1) * Math.min(dx,dy);
			case 3:
				return ((.25 +  Math.sqrt(2)) / 2)* Math.sqrt(dx * dx + dy * dy);
			case 0:
				return .25 * (dx + dy) + (Math.sqrt(2) * .25 -  2 * .25) * Math.min(dx,dy);
		}
		
		//function is set up for 5 heuristic
		System.out.println("ERROR: HEURISTIC NOT FOUND");
		return -1;
	}

	public boolean equals(Node n){
		return this.xcoordinate == n.xcoordinate && this.ycoordinate == n.ycoordinate;
	}
	
	public Node getBp() {
		return bp;
	}

	public void setBp(Node bp) {
		this.bp = bp;
	}

	public double getU() {
		return u;
	}

	public void setU(double u) {
		this.u = u;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}
	
	public double getKey() {
		return key;
	}

	public void setKey(double key) {
		this.key = key;
		this.f = key;
	}

	

	
	/**
	 * 
	 * There's a dependency between the Node class and the phase 2 Search class.
	 * The number of heuristics is unknown until a Search object is constructed.
	 * 
	 * @param numofheur		the number of heuristics to be tested in the sequential A* search
	 */
	public void initgList(int numofheur){
		gList = new double[numofheur];
	}
	public void inithList(int numofheur){
		hList = new double[numofheur];
		
	}
	public void initbpList(int numofheur){
		bpList = new Node[numofheur];
	}
	
	
	/**
	 * Used in both Integrated and Sequential A*
	 */
	
	public double get_sH(int index){
		return hList[index];
	}
	public void set_sH(int goalX, int goalY, int index){
		hList[index] = calculate_h(goalX, goalY, index);
	}
	
	
	/**
	 * 
	 * Methods used for sequential A* search
	 * 
	 */
	public double get_sG(int index){
		return gList[index];
	}
	
	public void set_sG(double value, int index){
		gList[index] = value;
	}
	
	public Node get_sBp(int index){
		return bpList[index];
	}
	
	public void set_sBp(Node s, int index){
		bpList[index] = s;
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
	
	public static class NodeKeyComparator implements Comparator<Node>{
		public int compare(Node x, Node y){
			if(x.key != y.key) 
				return (x.key < y.key) ? -1 : 1;
			
			else if(x.g != y.g) 
				return (x.g > y.g) ? -1 : 1; //favors the larger g-values
			
			else
				return -1;
				
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

