package view.NewSupportEvent;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

/**
 * @author Erik
 * The new support event creation screen for the application
 */

public class NewSupportEventView {

	private AnchorPane newSupportEvent;
	private NewSupportEventController newSupportEventController;
	private IPopout popout;
	private String currentTicket;
	
	public void setupNewSupportEventView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/NewSupportEvent/NewSupportEvent.fxml"));
			newSupportEvent = (AnchorPane) loader.load();
			
			newSupportEventController = loader.getController();
			newSupportEventController.setNewSupportEventView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getNewSupportEvent() {
		return newSupportEvent;
	}

	public void setNewSupportEvent(AnchorPane newSupportEvent) {
		this.newSupportEvent= newSupportEvent;
	}
	
	public IPopout getPopout() {
		return popout;
	}
	
	public void setPopout(IPopout popout) {
		this.popout = popout;
	}

	public String getCurrentTicket() {
		return currentTicket;
	}

	public void setCurrentTicket(String currentTicket) {
		this.currentTicket = currentTicket;
	}

	public NewSupportEventController getNewSupportEventController() {
		return newSupportEventController;
	}

	public void setNewSupportEventController(
			NewSupportEventController newSupportEventController) {
		this.newSupportEventController = newSupportEventController;
	}
}
