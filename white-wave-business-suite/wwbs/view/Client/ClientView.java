package view.Client;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;

/**
 * @author Erik
 * Basic client view for selecting available clients
 */

public class ClientView {
	
	private AnchorPane client;
	private ClientController controller;
	
	public void setupClientView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/Client/Client.fxml"));;
			client = (AnchorPane) loader.load();
			
			controller = loader.getController();
			controller.setClientView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getClient() {
		return client;
	}
	
	public void setClient(AnchorPane client) {
		this.client = client;
	}

	public ClientController getController() {
		return controller;
	}

	public void setController(ClientController controller) {
		this.controller = controller;
	}
}
