package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

import phase1.AStarSearch;
import phase1.Coordinate;
import phase1.Map;
import phase1.Node;
import phase1.Search;
import phase1.UniformCostSearch;
import phase1.WeightedAStarSearch;
import phase1.Node.NodeComparator;
import phase1.Node.NodeComparatorG;

/**
 * NOTE: This is mainly used for testing. THIS IS NOT THE MAIN APPLICATION
 * MAIN APPLICATION IS IN THE application PACKAGE
 * @author matth
 *
 */


public class Phase1Test {
	
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
		
		
		File folder = new File("Maps");
		File[] folders = folder.listFiles();
		
		double maptotalruntime = 0;
		int maptotalpathlength = 0;
		int maptotalnodesexpanded = 0;
		double maptotalmemoryused = 0;
		double maptotalcost = 0;
		
		double totalruntime = 0;
		int totalpathlength = 0;
		int totalnodesexpanded = 0;
		double totalmemoryused = 0;
		double totalpathcost = 0;
		
	
		
		/*
		 * Testing for part E of Phase 1
		 * 
		 * 
		 * 
		 * Testing for UniformCostSearch
		 * Average run time
		 * Average path length
		 * Average path cost
		 * Average number of nodes expanded
		 * 
		 */
		
		
		//test on a single map
		/*
		File test = new File("Maps/Map1/Map1_Setup1.txt");
		Map map = new Map(test);
		map.printMap();
		*/
		
		try {
			
			for(int algo = 2; algo <= 4; algo++){
				switch(algo){
				case 1:
					System.out.println("Uniform Cost");
					break;
				case 2:
					System.out.println("A*");
					break;
				case 3:
					System.out.println("Weighted A* (1.25)");
					break;
				case 4:
					System.out.println("Weighted A* (2.00)");
					break;
				}
			
			for(int x = 0; x < folders.length; x++){
				File[] files = folders[x].listFiles();
				maptotalruntime = 0;
				maptotalpathlength = 0;
				maptotalnodesexpanded = 0;
				maptotalmemoryused = 0;
				for(int y = 0; y < files.length; y++){
					
					Map map = new Map(files[y]);
					Search testing;
					
					if (algo == 1) {
						testing = new UniformCostSearch(map);
						testing.setupFringe(new Node.NodeComparatorG());
					} else if (algo > 2) {
						double weight;
						if(algo == 3)
							weight = 1.25;
						else
							weight = 2.00;
						
						testing = new WeightedAStarSearch(map, weight);
						testing.setupFringe(new Node.NodeComparator());

					} else {
						testing = new AStarSearch(map);
						testing.setupFringe(new Node.NodeComparator());
					}
					
					if(testing.findPath()){
						testing.printPath();
						/*testing only
						if(algo == 2){
							if(!testing.isAdmissible())
								admissible = false;
							if(!testing.isConsistent())
								consistent = false;
						} */
					}else{
						System.out.println("error has occurred during the path search on: " + files[y].getName());
					}
					//FileWriter writer = new FileWriter(files[y]);
					//writer.write(map.toString());
					//map.printMap();
					//writer.close();
					//breader.close();
					//reader.close();
					
					maptotalruntime += testing.getTime();
					maptotalpathlength += testing.getPathLength();
					maptotalnodesexpanded += testing.getNumOfExpandedNodes();
					maptotalmemoryused += testing.getMemoryUsed();
					maptotalcost += testing.getPathCost();
					
				}
				
				//System.out.println(folders[x].getName());
			//	System.out.println("Average run time: " + maptotalruntime/10 + "ms");
			//	System.out.println("Average path length (number of nodes): " + maptotalpathlength/10);
			//	System.out.println("Average path cost: " + maptotalcost/10);
			//	System.out.println("Average nodes expanded: " + maptotalnodesexpanded/10);
				System.out.println("Average memory used: " + maptotalmemoryused/10 + "KB");
			//	System.out.println();
				
				totalruntime += maptotalruntime;
				totalpathlength += maptotalpathlength;
				totalpathcost	+= maptotalcost;
				totalnodesexpanded += maptotalnodesexpanded;
				totalmemoryused += maptotalmemoryused;
			}
			
			System.out.println("Total average run time: " + totalruntime/50 + "ms");
			System.out.println("Total average path length (number of nodes): " + totalpathlength/50);
			System.out.println("Total average path cost: " + totalpathcost/50);
			System.out.println("Total average nodes expanded: " + totalnodesexpanded/50);
			System.out.println("Total average memory used: " + totalmemoryused/50 + "KB");
			
			
			maptotalruntime = 0;
			maptotalpathlength = 0;
			maptotalnodesexpanded = 0;
			maptotalmemoryused = 0;
			maptotalcost = 0;
			
			totalruntime = 0;
			totalpathlength = 0;
			totalnodesexpanded = 0;
			totalmemoryused = 0;
			totalpathcost = 0;
			}
			
			/*NOT A COMPLETE TEST
			
			if(admissible)
				System.out.println("** Heuristic is admissible **");
			else
				System.out.println("** Heuristic  is NOT admissible **");
			if(consistent)
				System.out.println("** Heuristic is consistent **");
			else
				System.out.println("** Heuristic is NOT consistent **");
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}

}