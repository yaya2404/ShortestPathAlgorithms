package phase1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Map{
	
	
	//Dimensions of the map
	private static final int rows = 120;
	private static final int cols = 160;
	
	//constants that represent the map
	private static final char blockedcell = '0';
	private static final char unblockedcell = '1';
	private static final char hardcell = '2';
	private static final char reghighway = 'a';
	private static final char hardhighway = 'b';
	
	//the grid representing the map to be modified when path is found
	private static Node[][] grid = new Node[rows][cols];
	
	//the grid represented by a String[][] that does not show path. Used strictly for outputting into file.
	private static String[][] sgrid = new String[rows][cols];
	
	//coordinates for start/end nodes 
	private Coordinate[] input;
	private Coordinate start;
	private Coordinate end;
	
	public Coordinate getStartCoordinate(){
		return this.start;
	}
	public Coordinate getEndCoordinate(){
		return this.end;
	}

	/**
	 * Generates map from file
	 * @param file valid map file
	 */
	public Map(File file){
		try {
			readMapFromFile(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readMapFromFile(File file) throws Exception  {
		FileReader freader = new FileReader(file);
		BufferedReader breader = new BufferedReader(freader);
		char cellType;
		
		input = new Coordinate[10];
		
		//read input
		String a[];
		for(int count = 0; count < 10; count++){
			a = breader.readLine().split(" ");
			input[count] = new Coordinate(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
		}
		
		this.start = this.input[0];
		this.end = this.input[1];
		
		
		
		String line = "";
		//read map
		for(int row = 0; row < rows; row++){
			line = breader.readLine();
			for(int col = 0; col < cols; col++){
				//Using Node grid
				cellType = line.charAt(col);
				sgrid[row][col] = String.valueOf(cellType);
				grid[row][col] = new Node(col,row,cellType);
				grid[row][col].calculate_h(end.getX(), end.getY());
			}
		}
		breader.close();
	}
	/**
	 * Code below is used to generate a random map
	 */
	/*
	private void createBlockedCells(){
		
		int blockedcells = 0;
		int x = -1;
		int y = -1;
		Random random = new Random();
		do{
			x = random.nextInt(cols);
			y = random.nextInt(rows);
			if(grid[y][x].getType() != reghighway && grid[y][x].getType() != hardhighway){
				if(((x != start.getX() || y != start.getY()) && (x != end.getX() || y != end.getY()))){
					grid[y][x].setType(blockedcell);
					blockedcells++;
				}
					
			}
		}while(blockedcells < 3840);
	}
	
	
	private void createUnblockedCells(){
		
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				//Using Node grid
				grid[row][col] = new Node(col,row);
				grid[row][col].calculate_h(end.getX(), end.getY());
			}
		}
	}
	

	private void createHardCell(){
		Random random = new Random();
		
		
		int hardx = -1;
		int hardy = -1;
		int testx = -1;
		int testy = -1;
		
		for(int count = 2; count < 10; count++){
			
			hardx = this.input[count].getX();
			hardy =	this.input[count].getY();
			
			
			
			
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
						 grid[testy + y][testx + x].setType(hardcell);
					}
				}

			}
		}
	}
	

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

	private void markHighwayPath(ArrayList<Coordinate> path){
		
		 int hwy = -1;
		 int hwx = -1;
		 
		 for(int count = 0; count < path.size(); count++){
			hwx = path.get(count).getX();
			hwy = path.get(count).getY();
			//set current tile to highway
			if(grid[hwy][hwx].getType() == unblockedcell){
				grid[hwy][hwx].setType(reghighway);
			}else if(grid[hwy][hwx].getType() == hardcell){
				grid[hwy][hwx].setType(hardhighway);
			}
		 }
	}
	
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
				//highway path is less than 100 squares. Restart.
				}else{
					return null;
				}
			}
			
			//highway runs into another highway. Must reset. 
			if(grid[hwy][hwx].getType() == reghighway || grid[hwy][hwx].getType() == hardhighway){
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
	*/
	public String printCellInfo(int x, int y){
		
		Node cell = grid[y][x];
		
		if(cell.getType() == Node.blockedcell)
			return "x: " + x +"\n" +"y: " + y + "\n";
		else
			return "x: " + x +"\n" +"y: " + y + "\n" + "g:" + cell.get_g() + "\n" + "h:" + cell.get_h() + "\n"
				+ "f:" + cell.get_f() + "\n";
		
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
	
	
	//only for testing 
	public void printMap(){
		//print out map to console
		//should be used for debugging purposes
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				System.out.print(grid[row][col].getType() + " ");
			}
			System.out.println();
		}
	}
	
	public String toString(){
		
		StringBuilder out = new StringBuilder();
		//add coordinates for start, end, and center of hard cells.
		for(int count = 0; count < 10; count++){
			out.append(input[count].getX() + " " + input[count].getY());
			out.append("\n");
		}
		//adds the map
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				out.append(sgrid[row][col]);
				//out.append(" ");
			}
			out.append("\n");
		}
		return out.toString();
	}
	
	/**
	 * Generates random start and goal nodes according to the project guidelines
	 * Sets start/end coordinate
	 */
	
	public void generateCoordinates(){
		Random random = new Random();
		Random probability = new Random();

		
		int startX, startY, goalX, goalY;
		int dX,dY;
		
		do{
			dX= random.nextInt(20);
			dY= random.nextInt(20);
			startX = (probability.nextDouble() < .5) ? 159-dX : 0 + dX;
			startY = (probability.nextDouble() < .5) ? 119-dY : 0 + dY;
		}while(grid[startY][startX].getType() == Node.blockedcell);
		
		do{
			dX= random.nextInt(20);
			dY= random.nextInt(20);
			goalX = (probability.nextDouble() < .5) ? 159-dX : 0 + dX;
			goalY = (probability.nextDouble() < .5) ? 119-dY : 0 + dY;
		}while(grid[goalY][goalX].getType() == Node.blockedcell || findDistance(startX, startY, goalX, goalY) < 100);
		
		start = new Coordinate(startX,startY);
		end =  new Coordinate(goalX,goalY);
		
	}
	
	private int findDistance(int startX, int startY, int goalX, int goalY) {
		return Math.abs(startX - goalX) + Math.abs(startX - goalY);
	}
	
}