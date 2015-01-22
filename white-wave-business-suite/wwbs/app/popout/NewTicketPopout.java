package app.popout;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.NewTicket.NewTicketView;
import app.MainApplication;

public class NewTicketPopout extends PopoutWindow {

	public NewTicketPopout(String content) {
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
		NewTicketView newTicketView = new NewTicketView();
		newTicketView.setupNewTicketView();
		this.setScene(new Scene(newTicketView.getNewTicket()));
		newTicketView.setPopout(this);
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
