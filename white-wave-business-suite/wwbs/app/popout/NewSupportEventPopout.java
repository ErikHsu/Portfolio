package app.popout;

import app.MainApplication;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.NewSupportEvent.NewSupportEventView;

public class NewSupportEventPopout extends PopoutWindow {

	public NewSupportEventPopout(String content) {
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
		NewSupportEventView newSupportEventView = new NewSupportEventView();
		newSupportEventView.setupNewSupportEventView();
		newSupportEventView.setCurrentTicket(content);
		newSupportEventView.getNewSupportEventController().initializeFields();
		this.setScene(new Scene(newSupportEventView.getNewSupportEvent()));
		newSupportEventView.setPopout(this);
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
