package app.popout;

import java.util.ArrayList;
import java.util.ListIterator;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import app.MainApplication;

/**
 * 
 * @author austin 
 * A popout stage that remembers what it was just showing
 */

public abstract class PopoutWindow implements IPopout {
	
	/*
	 * Current total number of popouts
	 */
	public static long TOTAL_POPOUTS = 0;

	private long popoutID;
	private Stage stage;
	private Scene scene;
	private AnchorPane apane;
	private PopoutType popoutType;
	private static ArrayList<IPopoutCloseListener> popoutListeners = new ArrayList<IPopoutCloseListener>();

	public PopoutWindow() {
		this.setPopoutID(TOTAL_POPOUTS++);
	}

	/*
	 * This method is used to check if a popout 
	 * of the concrete class already exists  
	 */
	public abstract boolean checkPopoutExists();
	/*
	 * Show the new stage
	 */
	public abstract void showNewPopout();
	/*
	 * Load the scene for the new stage
	 */
	public abstract void setupScene(String content);
	
	@Override
	public void closePopout() {
		if(MainApplication.DEBUG_ON)
			System.err.println("Closing popout with PopoutID " + this.getPopoutID());
		
		for(IPopout popout : MainApplication.getPopouts()) {
			if(popout.getPopoutID() == getPopoutID()) {
				stage.close();
				popoutType = null;
				popout = null;
			}
		}

	}
	/*
	 * Fire a popout closed event
	 */
	public void closeEvent() {
		for (ListIterator<IPopoutCloseListener> itr = popoutListeners.listIterator(); itr.hasNext();) {
			IPopoutCloseListener listener = itr.next();
			listener.popoutWasClosed(this);
		}
	}

	/*
	 * Setup on close operations for popout window
	 */
	public void setupOnCloseOperation() {
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			 public void handle(WindowEvent we) {
				 /*
				  * Remove the popouts from the list when the window is closed
				  */
				 closePopout();
				 if(MainApplication.DEBUG_ON)
					 System.err.println("Deleting popout from popouts list");
	          }
		});
	}
	
	/*
	 * Add the new popout to the list of popouts in MainApplication
	 */
	public void addToPopouts() {
		MainApplication.getPopouts().add(this);
	}

	public void setupStage() {
		this.setStage(new Stage());
		this.getStage().setScene(this.getScene());
		this.getStage().getIcons().add(new Image(getClass().getResourceAsStream("/res/logo.png")));
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public AnchorPane getApane() {
		return apane;
	}

	public void setApane(AnchorPane apane) {
		this.apane = apane;
	}

	public PopoutType getPopoutType() {
		return popoutType;
	}

	public void setPopoutType(PopoutType popoutType) {
		this.popoutType = popoutType;
	}
	public long getPopoutID() {
		return popoutID;
	}
	public void setPopoutID(long popoutID) {
		this.popoutID = popoutID;
	}
	
	public static void addCloseListener(IPopoutCloseListener listener) {
		PopoutWindow.getPopoutListeners().add(listener);
	}

	public static ArrayList<IPopoutCloseListener> getPopoutListeners() {
		return popoutListeners;
	}
}
