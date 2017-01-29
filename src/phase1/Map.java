package phase1;

import java.io.File;
import java.util.Random;

public class Map {
	
	private static final int rows = 120;
	private static final int cols = 160;
	private static final String blockedcell = "0";
	private static final String unblockedcell = "1";
	private static final String hardcell = "2";
	private static final String reghighway = "a";
	private static final String hardhighway = "b";
	private static final String file = "testing";
	private static String[][] grid = new String[rows][cols];
	private String[][] input;
	private int startx, starty;
	private int endx, endy;
	
	public Map(String[][] input){
		this.input = input;
		startx = Integer.getInteger(this.input[0][0]).intValue();
		starty = Integer.getInteger(this.input[0][1]).intValue();
		endx = Integer.getInteger(this.input[1][0]).intValue();
		endy = Integer.getInteger(this.input[1][1]).intValue();
		
	}
	public void createUnblockedCells(){
		//generate standard map with unblocked cells 
		for(int row = 0; row < rows; row++){
			for(int col = 0; col < cols; col++){
				grid[row][col] = unblockedcell;
			}
		}
	}
	public void createHardCell(){
		Random random = new Random();
		
		//generate hard cells
		int hardx = -1;
		int hardy = -1;
		int testx = -1;
		int testy = -1;
		
		for(int count = 2; count < 10; count++){
			
			/*
			hardx = random.nextInt(cols);
			hardy = random.nextInt(rows);
			*/
			hardx = Integer.getInteger(this.input[count][0]).intValue();
			hardy =	Integer.getInteger(this.input[count][1]).intValue();
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
	}
	public void createHighwayCell(){
		
	}
	private 
}
