package view.SupportEvent;

import java.io.IOException;

import view.Client.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

/**
 * @author Erik
 * Basic support event view for selecting available support events
 */

public class SupportEventView {
	
	private AnchorPane supportEvent;
	private SupportEventController controller;
	private IPopout popout;
	private String currentSupportEvent;
	private String currentTicket;
	
	public void setupSupportEventView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/SupportEvent/SupportEvent.fxml"));
			supportEvent = (AnchorPane) loader.load();
			
			controller = loader.getController();
			controller.setSupportEventView(this);
			controller.refreshSupportEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getSupportEvent() {
		return supportEvent;
	}
	
	public void setSupportEvent(AnchorPane supportEvent) {
		this.supportEvent = supportEvent;
	}

	public SupportEventController getController() {
		return controller;
	}

	public void setController(SupportEventController controller) {
		this.controller = controller;
	}
	
	public IPopout getPopout() {
		return popout;
	}

	public void setPopout(IPopout popout) {
		this.popout = popout;
	}

	public String getCurrentSupportEvent() {
		return currentSupportEvent;
	}

	public void setCurrentSupportEvent(String currentSupportEvent) {
		this.currentSupportEvent = currentSupportEvent;
	}

	public String getCurrentTicket() {
		return currentTicket;
	}

	public void setCurrentTicket(String currentTicket) {
		this.currentTicket = currentTicket;
	}

}
