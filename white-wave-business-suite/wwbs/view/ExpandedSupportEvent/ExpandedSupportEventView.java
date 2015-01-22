package view.ExpandedSupportEvent;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

public class ExpandedSupportEventView {
	
	private AnchorPane expandedSupportEvent;
	private ExpandedSupportEventController expandedSupportEventController;
	private IPopout popout;
	private String currentSupportEvent;
	private String currentTicket;
	
	public void setupExpandedSupportEventView(String content) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/ExpandedSupportEvent/ExpandedSupportEvent.fxml"));
			expandedSupportEvent = (AnchorPane) loader.load();
			
			setCurrentSupportEvent(content);
			
			expandedSupportEventController = loader.getController();
			expandedSupportEventController.setExpandedSupportEventView(this);
			expandedSupportEventController.setSelectedSupportEvent();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnchorPane getExpandedSupportEvent() {
		return expandedSupportEvent;
	}

	public void setExpandedSupportEvent(AnchorPane expandedSupportEvent) {
		this.expandedSupportEvent = expandedSupportEvent;
	}

	public ExpandedSupportEventController getExpandedSupportEventController() {
		return expandedSupportEventController;
	}

	public void setExpandedSupportEventController(
			ExpandedSupportEventController expandedSupportEventController) {
		this.expandedSupportEventController = expandedSupportEventController;
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
		this.currentSupportEvent= currentSupportEvent;
	}
}
