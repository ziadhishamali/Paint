package eg.edu.alexu.csd.oop.draw.cs19;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIFX extends Application {

	public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	primaryStage.setTitle("Paint");
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
    	
    	Parent root = loader.load();
    	
    	MainController control = loader.getController();
    	
    	control.initiateController(new ShapeFactory(new IDrawingEngine()), primaryStage);
    	
    	Scene scene = new Scene(root, 1500, 900);
    	
    	scene.setOnKeyPressed(e -> {
    	    try {
				control.canvasListenerKeyPressed(e);
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
    	});
        
        primaryStage.setScene(scene);
    	
    	primaryStage.show();
    	
    }
}
