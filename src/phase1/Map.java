package phase1;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	
	//Dimensions of the map
	private static final int rows = 120;
	private static final int cols = 160;
	
	//constants that represent the map
	private static final char blockedcell = '0';
	private static final char unblockedcell = '1';
	private static final char hardcell = '2';
	private static final char reghighway = 'a';
	private static final char hardhighway = 'b';
	
	//the grid representing the map
	//private static String[][] grid = new String[rows][cols];
	private static Node[][] grid = new Node[rows][cols];
	
	//coordinates for start/end nodes 
	private Coordinate[] input;
	private Coordinate start;
	private Coordinate end;
	
	//values 
	
	//values 
	
	public Coordinate getStartCoordinate(){
		return this.start;
	}
	public Coordinate getEndCoordinate(){
		return this.end;
	}
	public Map(Coordinate[] input){
		this.input = input;
		this.start = this.input[0];
		this.end = this.input[1];
		createUnblockedCells();
		createHardCell();
		createHighwayCell();
		createBlockedCells();
	}
	
	/**
	 * Generate blocked cells for map
	 */
	
	private void createBlockedCells(){
		
		int blockedcells = 0;
		int x = -1;
		int y = -1;
		Random random = new Random();
		do{
			x = random.nextInt(cols);
			y = random.nextInt(rows);
			if(grid[y][x].getType() != reghighway && grid[y][x].getType() != hardhighway
				/*&& y != start.getY() && y != end.getY() && x != start.getX() && x != end.getX()*/){
				//grid[y][x] = new Node(x,y);
				grid[y][x].setType(blockedcell);
				blockedcells++;
			}
		}while(blockedcells < 3840);
		/*
		grid[0][1].setType(blockedcell);
		grid[1][1].setType(blockedcell);
		grid[2][1].setType(blockedcell);
		*/
	}
	
	/**
	 * Generate unblocked cells for map 
	 */
	private void createUnblockedCells(){
		
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				//Using Node grid
				grid[row][col] = new Node(col,row);
			}
		}
	}
	/**
	 * Generate hard cells for map
	 */
	private void createHardCell(){
		Random random = new Random();
		
		
		int hardx = -1;
		int hardy = -1;
		int testx = -1;
		int testy = -1;
		
		for(int count = 2; count < 10; count++){
			
			
			hardx = random.nextInt(cols);
			hardy = random.nextInt(rows);
			//hardx = this.input[count].getX();
			//hardy =	this.input[count].getY();
			testx = hardx;
			testy = hardy;
			if(testy - 15 < 0){
				testy = 0;
			}else{
				testy -= 15;
			}
			
			if(testx - 15 < 0){
				testx = 0;
			}else{
				testx -= 15;
			}
			
			for(int y = 0; y < 31 && testy + y < rows; y++){
				for(int x = 0; x < 31 && testx + x < cols; x++){
					if(random.nextDouble() < 0.5){
						 //grid[testy + y][testx + x] = new Node(testx + x, testy + y);
						 grid[testy + y][testx + x].setType(hardcell);
					}
				}

			}
		}
	}
	
	/**
	 *  Generates highway
	 */
	private void createHighwayCell(){
		
		int count = 0;
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		
		do{
			
			path = findHighwayPath();
			if(path != null){
				markHighwayPath(path);
				count++;
			}
			path = null;
		}while(count < 4);
	}
	/**
	 * 
	 * Mark the cells on map that represent the highway
	 * 
	 * @param path	contains an ArrayList of Coordinates that represent the highway path
	 */
	private void markHighwayPath(ArrayList<Coordinate> path){
		
		 int hwy = -1;
		 int hwx = -1;
		 
		 for(int count = 0; count < path.size(); count++){
			hwx = path.get(count).getX();
			hwy = path.get(count).getY();
			//set current tile to highway
			if(grid[hwy][hwx].getType() == unblockedcell){
				//grid[hwy][hwx] = new Node(hwx, hwy);
				grid[hwy][hwx].setType(reghighway);
			}else if(grid[hwy][hwx].getType() == hardcell){
				//grid[hwy][hwx] = new Node(hwx, hwy);
				grid[hwy][hwx].setType(hardhighway);
			}
		 }
	}
	
	/**
	 * 
	 * Searches for an appropriate path that represents the highway
	 * 
	 * @return	an ArrayList containing Coordinates that represent the highway
	 */
	private ArrayList<Coordinate> findHighwayPath(){
		
				
		int hwx = -1; //cols
		int hwy = -1; //rows
		int dir = -1; //0 up, 1 down, 2 left, 3 right.
		double dirchange = -1;
		int hwpath = 0;
		int section = -1;
		boolean boundary = false;
		Random random = new Random(); 
		ArrayList<Coordinate> path = new ArrayList<Coordinate>();
		
		section = random.nextInt(4);
		//top
		if(section == 0){
			hwx = random.nextInt(cols);
			hwy = 0;
			dir = 1;
		//bottom
		}else if(section == 1){
			hwx = random.nextInt(cols);
			hwy = 119;
			dir = 0;
		//left
		}else if(section == 2){
			hwx = 0;
			hwy = random.nextInt(rows);
			dir = 3;
		//right
		}else if(section == 3){
			hwx = 159;
			hwy = random.nextInt(rows);
			dir = 2;
		}
		
		//System.out.println("Generating highway number " + count + ". Starting at " + hwx + ":" + hwy);
		hwpath = 0;
		do{
			
			
			//if path hits the boundary
			if((hwx == 0 || hwx == 159 || hwy == 0 || hwy == 119) && hwpath > 1){
				
				//met requirements of highway
				if(hwpath > 100){
					boundary = true;
					//System.out.println(count + ": path too short");
				//highway path is less than 100 squares. Restart.
				}else{
					return null;
				}
			}
			
			//highway runs into another highway. Must reset. 
			if(grid[hwy][hwx].getType() == reghighway || grid[hwy][hwx].getType() == hardhighway){
				//System.out.println(count + ": ran into another highway");
				return null;
			}
			
			//highway runs into itself. Must reset. 
			for(int count = 0; count < path.size(); count++){
				if(hwy == path.get(count).getY() && hwx == path.get(count).getX()){
					return null;
				}
			}
			
			path.add(new Coordinate(hwx, hwy));
			
			if(hwpath != 0 && hwpath % 20 == 0){
				dirchange = random.nextDouble();
				//change direction
				if(dirchange > 0.6){
					//change dir to left
					if(dirchange < 0.8){
						if(dir == 0){
							dir = 2;
						}else if(dir == 1){
							dir = 3;
						}else if(dir == 2){
							dir = 1;
						}else if(dir == 3){
							dir = 0;
						}	
					//change dir to right
					}else{
						if(dir == 0){
							dir = 3;
						}else if(dir == 1){
							dir = 2;
						}else if(dir == 2){
							dir = 0;
						}else if(dir == 3){
							dir = 1;
						}	
					}
				}
			}
			
			
			//move by one tile based on direction
			if(dir == 0){
				hwy -= 1;
			}else if(dir == 1){
				hwy += 1;
			}else if(dir == 2){
				hwx -= 1;
			}else if(dir == 3){
				hwx += 1;
			}
			hwpath++;
		}while(!boundary);
		
		return path; 
	}
	
	public String printCellInfo(int x, int y){
		
		Node cell = grid[y][x];
		
		
		//need to add h, f, and time it took to calculate path.
		return "x: " + x +"\n" +"y: " + y + "\n" + "g:" + cell.get_g() + "\n";
		
	}
	/**
	 * Finds a cell in the map
	 * @param x x coordinate
	 * @param y y coordinate
	 * @return node at the specified coordinates; otherwise null if the coordinates are invalid
	 */
	public Node getCell(int x, int y) {
		if(x < 0 ||x > 159 || y < 0 || y > 119)
			return null;
		else
			return grid[y][x];
	}
}