package app;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.Login.LoginView;
import app.popout.IPopout;
import data.InitData;
import data.Technician;

/**
 * @author Austin
 * Starting point for the application
 */
public class MainApplication extends Application{
	
	// if this is on print debug messages
	public static final boolean DEBUG_ON = true;
	
	// is there a user logged in
	private static boolean isLoggedIn = false;
	private static Technician loggedInTechnician = null;
	
	private static InitData initData;
	// The main window
	private static Stage primaryStage;
	// popout windows
	private static ArrayList<IPopout> popouts;


	public static void main(String[] args) {
		launch(args);

		/*
		 * Close database connection at the end
		 */
		try {
			initData.closeSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		/*
		 * When initData is instantiated we load hibernate and connect to the database 
		 */
		initData = new InitData();
		
		
		/*
		 * Set up the primary stage of the UI
		 */
		MainApplication.setPrimaryStage(primaryStage);
		
		/*
		 * Setup program icon
		 */
		MainApplication.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/res/logo.png")));
		
		/*
		 * Setup primary stage on close behavior
		 */
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			 public void handle(WindowEvent we) {
				 if(MainApplication.DEBUG_ON)
					 System.err.println("Closing the UI");
				 Platform.exit();
	          }
		});
		
		/*
		 * Set up a list of secondary popout stages
		 */
		MainApplication.setPopouts(new ArrayList<IPopout>());

		/*
		 * Load the login screen
		 */
		LoginView loginView = new LoginView();
		loginView.setupLoginView();

		/*
		 * set the scene and show the first view
		 */
		showNewView(loginView.getLogin());
		
		/*
		 * Set stage dimensions
		 */
		primaryStage.show();
	}

	/*
	 * Create a new scene and add it to the primary stage.
	 * Base UI elements on an anchor pane as the top level parent feed them into this method 
	 * to show the AnchorPane and replace the currently shown view
	 */
	public static void showNewView(AnchorPane aPane) {
		Scene scene = new Scene(aPane);
		MainApplication.primaryStage.setScene(scene);
	}
	
	/*
	 * Call this when the user trys to log out
	 */
	
	public static void logout() {
		/*
		 * Load the login screen
		 */
		LoginView loginView = new LoginView();
		loginView.setupLoginView();

		/*
		 * set the scene and show the first view
		 */
		MainApplication.showNewView(loginView.getLogin());
		MainApplication.setLoggedIn(false);
		MainApplication.setLoggedInTechnician(null);
		
		for(IPopout popout : popouts)
			popout.closePopout();
	}
	
	public static InitData getInitData() {
		return initData;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		MainApplication.primaryStage = primaryStage;
	}

	public static boolean isLoggedIn() {
		return isLoggedIn;
	}

	public static void setLoggedIn(boolean isLoggedIn) {
		MainApplication.isLoggedIn = isLoggedIn;
	}

	public static Technician getLoggedInTechnician() {
		return loggedInTechnician;
	}

	public static void setLoggedInTechnician(Technician loggedInTechnician) {
		MainApplication.loggedInTechnician = loggedInTechnician;
	}

	public static ArrayList<IPopout> getPopouts() {
		return popouts;
	}

	public static void setPopouts(ArrayList<IPopout> popouts) {
		MainApplication.popouts = popouts;
	}
}