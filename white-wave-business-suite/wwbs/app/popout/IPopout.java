package app.popout;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public interface IPopout {
	/*
	 * This method is used to check if a popout 
	 * of the concrete class already exists  
	 */
	public abstract boolean checkPopoutExists();

	/*
	 * Show the new stage
	 */
	public void showNewPopout();
	public PopoutType getPopoutType();
	public long getPopoutID();
	public void setPopoutType(PopoutType popoutType);
	public void closePopout();
	public void setScene(Scene scene);
	public Scene getScene();
	public void setupBreadCrumbBar(ToolBar breadCrumbBar, String content, Stage stage);
	public ToolBar getBreadCrumbBar();
	public Stage getStage();
}
