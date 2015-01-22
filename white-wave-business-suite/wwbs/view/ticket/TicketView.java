package view.ticket;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;

/**
 * @author Erik
 * Basic ticket screen for selecting available tickets
 */

public class TicketView {
	
	private AnchorPane ticket;
	private TicketController controller;
	
	public void setupTicketView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/ticket/Ticket.fxml"));;
			ticket = (AnchorPane) loader.load();
			
			controller = loader.getController();
			controller.setTicketView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getTicket() {
		return ticket;
	}
	
	public void setTicket(AnchorPane ticket) {
		this.ticket = ticket;
	}

	public TicketController getController() {
		return controller;
	}

	public void setController(TicketController controller) {
		this.controller = controller;
	}
}
