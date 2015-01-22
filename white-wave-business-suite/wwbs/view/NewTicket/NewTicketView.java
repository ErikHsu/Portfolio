package view.NewTicket;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

/**
 * @author Erik
 * The new ticket creation screen for the application
 */

public class NewTicketView {

	private AnchorPane newTicket;
	private NewTicketController newTicketController;
	private IPopout popout;
	
	public void setupNewTicketView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/NewTicket/NewTicket.fxml"));
			newTicket= (AnchorPane) loader.load();
			
			NewTicketController controller = loader.getController();
			controller.setNewTicketView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getNewTicket() {
		return newTicket;
	}

	public void setNewTicket(AnchorPane newTicket) {
		this.newTicket = newTicket;
	}
	
	public IPopout getPopout() {
		return popout;
	}
	
	public void setPopout(IPopout popout) {
		this.popout = popout;
	}
}
