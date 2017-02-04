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