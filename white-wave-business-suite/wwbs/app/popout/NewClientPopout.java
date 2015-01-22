package app.popout;

import app.MainApplication;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.NewClient.NewClientView;

public class NewClientPopout extends PopoutWindow {

	public NewClientPopout(String content) {
		super();
		setupStage();
		setupScene(content);
		setupOnCloseOperation();
		showNewPopout();
	}

	@Override
	public boolean checkPopoutExists() {
		return false;
	}

	@Override
	public void showNewPopout() {
		this.getStage().setScene(this.getScene());
		MainApplication.getPopouts().add(this);
		this.getStage().show();
	}

	@Override
	public void setupScene(String content) {
		NewClientView newClientView = new NewClientView();
		newClientView.setupNewClientView();
		this.setScene(new Scene(newClientView.getNewClient()));
		newClientView.setPopout(this);
	}

	@Override
	public void setupBreadCrumbBar(ToolBar breadCrumbBar, String content,
			Stage stage) {
		
	}

	@Override
	public ToolBar getBreadCrumbBar() {
		return null;
	}

}
