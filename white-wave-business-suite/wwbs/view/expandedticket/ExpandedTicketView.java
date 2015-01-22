package view.expandedticket;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

public class ExpandedTicketView {
	
	private AnchorPane expandedTicket;
	private ExpandedTicketController expandedTicketController;
	private IPopout popout;
	private String currentTicket;
	
	public void setupExpandedTicketView(String content) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/expandedticket/ExpandedTicket.fxml"));
			expandedTicket = (AnchorPane) loader.load();
			
			expandedTicketController = loader.getController();
			expandedTicketController.setExpandedTicketView(this);
			setCurrentTicket(content);
			expandedTicketController.setSelectedTicket();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnchorPane getExpandedTicket() {
		return expandedTicket;
	}

	public void setExpandedTicket(AnchorPane expandedTicket) {
		this.expandedTicket = expandedTicket;
	}

	public ExpandedTicketController getExpandedTicketController() {
		return expandedTicketController;
	}

	public void setExpandedTicketController(
			ExpandedTicketController expandedTicketController) {
		this.expandedTicketController = expandedTicketController;
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
		this.currentTicket= currentTicket;
	}
}
