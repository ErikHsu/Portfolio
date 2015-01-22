package view.Finance;

import java.io.IOException;

import data.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;

/**
 * @author Erik
 * Basic client view for selecting available clients
 */

public class FinanceView {
	
	private Client client;
	
	private AnchorPane finance;
	private FinanceController controller;
	
	public void setupFinanceView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/Finance/Finance.fxml"));;
			finance = (AnchorPane) loader.load();
			
			controller = loader.getController();
			controller.setClientView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public AnchorPane getFinance() {
		return finance;
	}
	
	public void setFinance(AnchorPane finance) {
		this.finance = finance;
	}

	public FinanceController getController() {
		return controller;
	}

	public void setController(FinanceController controller) {
		this.controller = controller;
	}
}
