package phase1application;
	
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Control;
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
	public static final String file = "testing.txt";
	public static final int numofinputs = 10;
	
	
	public static final String blockedcell = "black";
	public static final String unblockedcell = "white";
	public static final String hardcell = "orange";
	public static final String reghighway = "cyan";
	public static final String hardhighway = "blue";
	public static final String path = "red";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			Coordinate[] input = new Coordinate[numofinputs];
			//String[] fileinput;
			//int x = -1;
			//int y = -1;
			/*
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
			*/
			
			
			input[0] = new Coordinate(0,0);
			input[1] = new Coordinate(1,3);
			Map map = new Map(input);
			//map.printMap();
			
			
			UniformCostSearch testing = new UniformCostSearch(map);
			if(testing.searchPath()){
				testing.printPath();
			}else{
				System.out.println("Could not find path");
			}
			
			
			GridPane root = new GridPane();
			//root.getStyleClass().add("game-grid");
			
			
			String color = "";
			char type = 0;
			for(int row = 0; row < rows; row++){
				for(int col = 0; col < cols; col++){
					
					final int x = col;
					final int y = row;
					StackPane square = new StackPane();
					
					type = map.getCell(col, row).getType();
					
					switch(type){
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
					}
					
					square.setStyle("-fx-background-color: "+color+";-fx-border-color: black;-fx-border-width: 1");
					square.setOnMouseClicked(e -> {
						System.out.println(map.printCellInfo(x, y));
					});
					root.add(square, col, row);
				}
			}
			
			
			for (int i = 0; i < cols; i++) {
	            root.getColumnConstraints().add(new ColumnConstraints(1, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
	            if(i < rows){
	            	root.getRowConstraints().add(new RowConstraints(1, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
	            }
	        }
			//root.setGridLinesVisible(true);
			Scene scene = new Scene(root,1280,960);
			//scene.getStylesheets().add(getClass().getResource("grid.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
