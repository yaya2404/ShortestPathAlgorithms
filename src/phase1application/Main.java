package phase1application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


import phase1.*;


public class Main extends Application {
	
	public static final int rows = 120;
	public static final int cols = 160;
	public static final String file = "testing.txt";
	public static final int numofinputs = 10;
	
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
			UniformCostSearch testing = new UniformCostSearch(map);
			if(testing.searchPath()){
				testing.printPath();
			}else{
				System.out.println("Could not find path");
			}
			map.printMap();
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
