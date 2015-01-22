package view.expandedClient;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

public class ExpandedClientView {
	
	private AnchorPane expandedClient;
	private ExpandedClientController expandedClientController;
	private IPopout popout;
	private String currentClient;
	
	public void setupExpandedClientView(String content) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/expandedClient/expandedClient.fxml"));
			expandedClient = (AnchorPane) loader.load();
			
			setCurrentClient(content);
			
			expandedClientController = loader.getController();
			expandedClientController.setExpandedClientView(this);
			expandedClientController.setSelectedClient();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnchorPane getExpandedClient() {
		return expandedClient;
	}

	public void setExpandedClient(AnchorPane expandedClient) {
		this.expandedClient = expandedClient;
	}

	public ExpandedClientController getExpandedClientController() {
		return expandedClientController;
	}

	public void setExpandedClientController(
			ExpandedClientController expandedClientController) {
		this.expandedClientController = expandedClientController;
	}
	
	public IPopout getPopout() {
		return popout;
	}

	public void setPopout(IPopout popout) {
		this.popout = popout;
	}

	public String getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(String currentClient) {
		this.currentClient = currentClient;
	}
}
