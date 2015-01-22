package view.NewClient;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

/**
 * @author Erik
 * The new client creation screen for the application
 */

public class NewClientView {

	private AnchorPane newClient;
	private NewClientController newClientController;
	private IPopout popout;
	
	public void setupNewClientView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/NewClient/NewClient.fxml"));
			newClient = (AnchorPane) loader.load();
			
			NewClientController controller = loader.getController();
			controller.setNewClientView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getNewClient() {
		return newClient;
	}

	public void setNewClient(AnchorPane newClient) {
		this.newClient = newClient;
	}
	
	public IPopout getPopout() {
		return popout;
	}
	
	public void setPopout(IPopout popout) {
		this.popout = popout;
	}
}
