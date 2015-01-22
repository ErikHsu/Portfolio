package test;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 
 * @author amker_000
 * Mostly copied from http://code.makery.ch/java/javafx-8-tutorial-part1/
 * Just learning
 * I'm tired and doing this on a laptop just wanted to put something up
 * I know this is shit and impossible to understand will fix
 */
public class JavaFXTest extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private AnchorPane ticketViewer;
	private Scene scene;

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("FXTest");
		
		init();

		scene = new Scene(rootLayout);
		rootLayout.setCenter(ticketViewer);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void init() throws IOException {
		// get BorderPane
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(JavaFXTest.class.getResource("/test/JavaFXBorder.fxml"));
		rootLayout = (BorderPane) loader.load();
		// get anchor pane
		loader = new FXMLLoader();
		loader.setLocation(JavaFXTest.class.getResource("/test/JavaFXTest.fxml"));
		ticketViewer = (AnchorPane) loader.load();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
