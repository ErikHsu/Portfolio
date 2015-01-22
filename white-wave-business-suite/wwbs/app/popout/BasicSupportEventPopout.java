package app.popout;

import app.MainApplication;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.SupportEvent.SupportEventView;

public class BasicSupportEventPopout extends PopoutWindow {

	public BasicSupportEventPopout(String content) {
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
		SupportEventView supportEventView = new SupportEventView();
		supportEventView.setCurrentTicket(content);
		supportEventView.setupSupportEventView();
		this.setScene(new Scene(supportEventView.getSupportEvent()));
		supportEventView.setPopout(this);
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
