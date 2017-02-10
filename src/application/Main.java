package application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import phase1.*;

public class Main extends Application {

	public static final int rows = 120;
	public static final int cols = 160;
	public static final int numofinputs = 10;

	public static final String blockedcell = "black";
	public static final String unblockedcell = "white";
	public static final String hardcell = "orange";
	public static final String reghighway = "cyan";
	public static final String hardhighway = "blue";
	public static final String path = "purple";
	public static final String start = "limegreen";
	public static final String end = "red";

	private GridPane root = new GridPane();
	private long time;

	@Override
	public void start(Stage primaryStage) {
		try {

			// Coordinate[] input = new Coordinate[numofinputs];
			// String[] fileinput;
			// int x = -1;
			// int y = -1;
			/*
			 * try {
			 * 
			 * BufferedReader reader = new BufferedReader(new FileReader(file));
			 * for(int count = 0; count < numofinputs; count++){ fileinput =
			 * reader.readLine().split(" "); x = Integer.parseInt(fileinput[0]);
			 * y = Integer.parseInt(fileinput[1]); if(x < 0 || x > 160){
			 * System.out.println("X coordinate: Out of bounds");
			 * System.exit(0); }else if(y < 0 || y > 120){
			 * System.out.println("Y coordinate: Out of bounds");
			 * System.exit(0); }else{ input[count] = new Coordinate(x - 1, y -
			 * 1); } } reader.close(); }catch (NumberFormatException n) { //
			 * TODO Auto-generated catch block
			 * System.out.println("Input error: numbers only");
			 * }catch(IOException e){ e.printStackTrace(); }catch(Exception a){
			 * a.printStackTrace(); }
			 */

			/*
			 * input[0] = new Coordinate(0,0); input[1] = new
			 * Coordinate(100,111); Map map = new Map(input); //map.printMap();
			 * 
			 * //Debugging only
			 * 
			 * if(map.getCell(map.getStartCoordinate().getX(),
			 * map.getStartCoordinate().getY()).getType() == Node.blockedcell){
			 * System.out.print("Starting cell is a blocked cell");
			 * System.exit(1);
			 * 
			 * }
			 * 
			 * if(map.getCell(map.getEndCoordinate().getX(),
			 * map.getEndCoordinate().getY()).getType() == Node.blockedcell){
			 * System.out.print("Goal cell is a blocked cell"); System.exit(1);
			 * }
			 * 
			 * //end of debugging
			 */

			/*
			 * UniformCostSearch testing = new UniformCostSearch(map);
			 * testing.setupFringe(new Node.NodeComparatorG());
			 * 
			 * AStarSearch testing2 = new AStarSearch(map);
			 * testing2.setupFringe(new Node.NodeComparator());
			 * 
			 * WeightedAStarSearch testing3 = new WeightedAStarSearch(map, 1.3);
			 * testing3.setupFringe(new Node.NodeComparator());
			 * 
			 * if(testing3.findPath()){ testing3.printPath();
			 * 
			 * }else System.out.println("Could not find path");
			 */

			//This should get the current directory of the project.
			//Coordinate[] input;
			//String[][] smap;
			File f = new File(".");
			f = new File(f.getAbsolutePath() + "/Maps");
			FileChooser fc = new FileChooser();
			fc.setInitialDirectory(f);
			File file = fc.showOpenDialog(primaryStage);

			//input = getInput(file);
			//smap = getMap(file);
			//if (input != null & !(input.length < 10)) {

				// setting up map
				Map map = new Map(file);

				Search pathSearch;
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Select an algorithm: (1) for Uniform Cost, (2) for A*, (3) for Weighted A* :");
				String userInput = reader.readLine();
				if (userInput.equals("1")) {
					pathSearch = new UniformCostSearch(map);
					pathSearch.setupFringe(new Node.NodeComparatorG());
				} else if (userInput.equals("3")) {
					System.out.println("Enter a weight value :");
					userInput = reader.readLine();
					double weight;
					try {
						weight = Double.parseDouble(userInput);
						if (weight <= 0)
							throw new IllegalArgumentException();
					} catch (Exception e) {
						System.out.println("Invalid weight...Using default value of 2");
						weight = 2;
					}

					pathSearch = new WeightedAStarSearch(map, weight);
					pathSearch.setupFringe(new Node.NodeComparator());

				} else {
					pathSearch = new AStarSearch(map);
					pathSearch.setupFringe(new Node.NodeComparator());
				}

				System.out.println("Generating Map.....");
				if (pathSearch.findPath()){
					pathSearch.printPath();
				}else{
					System.out.println("Failed to find path");
					System.exit(0);
				}

				time = pathSearch.getTime();

				setMap(map);

				// writing out map to file
				FileWriter writer = new FileWriter(file);
				writer.write(map.toString());
				writer.close();
				Scene scene = new Scene(root, 1280, 960);
				primaryStage.setTitle(file.getName());
				primaryStage.setScene(scene);
				primaryStage.show();
				
				pathSearch.printSummary();
				/*
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(map);
				oos.close();

				System.out.println("size of data structure : " + baos.size() / 1024d / 1024d + " MB");
				*/
			//}

		} catch (NullPointerException e) {
			//e.printStackTrace();
			e.printStackTrace();
			System.out.println("A file was not selected");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Could not write out to file");
		}
	}
	
	private void setMap(Map map) {

		// setting up the visuals
		String color = "";
		char type = 0;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {

				final int x = col;
				final int y = row;
				StackPane square = new StackPane();

				type = map.getCell(col, row).getType();

				switch (type) {
				case phase1.Node.blockedcell:
					color = blockedcell;
					break;
				case phase1.Node.unblockedcell:
					color = unblockedcell;
					break;
				case phase1.Node.hardcell:
					color = hardcell;
					break;
				case phase1.Node.reghighway:
					color = reghighway;
					break;
				case phase1.Node.hardhighway:
					color = hardhighway;
					break;
				case phase1.Node.path:
					color = path;
					break;
				}

				if (x == map.getStartCoordinate().getX() && y == map.getStartCoordinate().getY()) {
					color = start;
				} else if (x == map.getEndCoordinate().getX() && y == map.getEndCoordinate().getY()) {
					color = end;
				}

				square.setStyle("-fx-background-color: " + color + ";-fx-border-color: black;-fx-border-width: 1;");

				Tooltip tp;
				// add time elapsed in ms onto end node
				if (x == map.getEndCoordinate().getX() && y == map.getEndCoordinate().getY()) {
					tp = new Tooltip(map.printCellInfo(x, y) + "Time: " + time + "ms\n");
				} else {
					tp = new Tooltip(map.printCellInfo(x, y));
				}
				Tooltip.install(square, tp);

				root.add(square, col, row);
			}
		}
		for (int i = 0; i < cols; i++) {
			root.getColumnConstraints().add(new ColumnConstraints(1, Control.USE_COMPUTED_SIZE,
					Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
			if (i < rows) {
				root.getRowConstraints().add(new RowConstraints(1, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY,
						Priority.ALWAYS, VPos.CENTER, true));
			}
		}
		
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
