package phase1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class test {
	
	public static final int rows = 120;
	public static final int cols = 160;
	public static final String file = "testing.txt";
	public static final int numofinputs = 10;
	
	/*
	public static final String blockedcell = "0";
	public static final String unblockedcell = "1";
	public static final String hardcell = "2";
	public static final String reghighway = "a";
	public static final String hardhighway = "b";
	
	static String[][] grid = new String[rows][cols];
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		//generate standard map with unblocked cells 
		/*
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				grid[row][col] = unblockedcell;
			}
		}
		
		Random random = new Random();
		//ArrayList<Integer> duplicates = new ArrayList<Integer>();
		
		
		//generate hard cells
		int hardx = -1;
		int hardy = -1;
		int testx = -1;
		int testy = -1;
		
		for(int count = 0; count < 8; count++){
			hardx = random.nextInt(cols);
			hardy = random.nextInt(rows);
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
						 grid[testy + y][testx + x] = "2";
					}
				}

			}
		}
		
		//generate highway
		
		
		int hwx = -1; //cols
		int hwy = -1; //rows
		int section = -1;
		int dir = -1; //0 up, 1 down, 2 left, 3 right.
		double dirchange = -1;
		int hwpath = 0;
		boolean boundary = false;
		
		
		for(int count = 0; count < 4; count++){
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
			
			System.out.println("Generating highway number " + count + ". Starting at " + hwx + ":" + hwy);
			hwpath = 0;
			do{
				
				
				//if path hits the boundary
				if((hwx == 0 || hwx == 159 || hwy == 0 || hwy == 119) && hwpath > 1){
					
					//met requirements of highway
					if(hwpath > 100){
						boundary = true;
						System.out.println(count + ": path too short");
					//highway path is less than 100 squares. Restart.
					}else{
						break;
					}
				}
				
				
				//highway runs into another highway. Must reset. 
				if(grid[hwy][hwx].compareTo(reghighway) == 0 || grid[hwy][hwx].compareTo(hardhighway) == 0){
					System.out.println(count + ": ran into another highway");
					break;
				}
				
				
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
				
				
				//set current tile to highway
				if(grid[hwy][hwx].compareTo("1") == 0){
					grid[hwy][hwx] = "a";
				}else if(grid[hwy][hwx].compareTo("2") == 0){
					grid[hwy][hwx] = "b";
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
		}
		
		//print out map
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				System.out.print(grid[row][col] + " ");
			}
			System.out.println();
		}
		
		int start = -1;
		int end = -1;
		int hard[][] = new int[8][2];
		*/
		
		

		/*
		Coordinate[] input = new Coordinate[numofinputs];
		//String[] fileinput;
		//int x = -1;
		//int y = -1;
		
		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			for(int count = 0; count < numofinputs; count++){
				fileinput = reader.readLine().split(" ");
				x = Integer.parseInt(fileinput[0]);
				y = Integer.parseInt(fileinput[1]);
				if(x < 0  || x > 160){
					System.out.println("X coordinate: Out of bounds");
					System.exit(0);
				}else if(y < 0 || y > 120){
					System.out.println("Y coordinate: Out of bounds");
					System.exit(0);
				}else{
					input[count] = new Coordinate(x - 1, y - 1);
				}
			}
			reader.close();
		}catch (NumberFormatException n) {
			// TODO Auto-generated catch block
			System.out.println("Input error: numbers only");
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception a){
			a.printStackTrace();
		}
		
		input[0] = new Coordinate(0,0);
		input[1] = new Coordinate(1,3);
		Map map = new Map(input);
		UniformCostSearch testing = new UniformCostSearch(map);
		testing.setupFringe(new Node.NodeComparatorG());
		
		if(map.getCell(map.getStartCoordinate().getX(), map.getStartCoordinate().getY()).getType() == Node.blockedcell){
			System.out.print("Starting cell is a blocked cell");
			System.exit(1);
		}
		
		if(map.getCell(map.getEndCoordinate().getX(), map.getEndCoordinate().getY()).getType() == Node.blockedcell){
			System.out.print("Goal cell is a blocked cell");
			System.exit(1);
		}
		
		
		
		System.out.println("");
		if(testing.findPath()){
			map.printMap();
			testing.printPath();
		}else{
			map.printMap();
			System.out.println("Could not find path");
		}
		*/
		
		
		/*
		File folder = new File("Maps");
		File[] folders = folder.listFiles();
		Coordinate input[];
		
		
		
		try {
			for(int x = 0; x < folders.length; x++){
				File[] files = folders[x].listFiles();
				for(int y = 0; y < files.length; y++){
					//System.out.println(files[y].getPath());
					
					input = new Coordinate[10];
					FileReader reader = new FileReader(files[y]);
					BufferedReader breader = new BufferedReader(reader);
					String a[];
					for(int count = 0; count < 10; count++){
						a = breader.readLine().split(" ");
						input[count] = new Coordinate(Integer.parseInt(a[0]), Integer.parseInt(a[1]));
					}
					Map map = new Map(input);
					FileWriter writer = new FileWriter(files[y]);
					writer.write(map.toString());
					map.printMap();
					writer.close();
					breader.close();
					reader.close();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		*/
		
		
	}

}