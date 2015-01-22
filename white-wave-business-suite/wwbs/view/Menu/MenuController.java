package view.Menu;

import data.Technician;
import view.Finance.FinanceView;
import view.InvoiceGenerator.InvoiceGeneratorView;
import view.Login.LoginView;
import view.ticket.TicketView;
import view.Asset.AssetView;
import view.Client.ClientView;
import app.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MenuController {
		
	private static MainApplication application;
	
	private MenuView menuView;
	
	@FXML
	private Button logoutButton;
	@FXML
	private Button TicketButton;
	@FXML
	private Button ClientButton;
	@FXML
	private Button AssetButton;
	@FXML
	private Button FinancialButton;
	@FXML
	private Technician currentTechnician;
	@FXML
	private TextField currentUserField;
	
	//Called by JavaFX
	public MenuController() {
		
	}
	
	public void ticketViewPressed() {
		TicketView ticket = new TicketView();
		ticket.setupTicketView();
		ticket.getController().refreshTickets();
		ticket.getController().setCurrentUser();
		MainApplication.showNewView(ticket.getTicket());
	}
	
	public void clientViewPressed() {
		ClientView client = new ClientView();
		client.setupClientView();
		client.getController().refreshClients();
		client.getController().setCurrentUser();
		MainApplication.showNewView(client.getClient());
	}

	public void assetViewPressed() {
		AssetView asset = new AssetView();
		asset.setupAssetView();
		asset.getController().refreshAssets();
		asset.getController().setCurrentUser();
		MainApplication.showNewView(asset.getAsset());
	}
	
//TODO	
	public void financialViewPressed() {
		FinanceView finance = new FinanceView();
		finance.setupFinanceView();
		finance.getController().refreshClients();
		finance.getController().setCurrentUser();
		MainApplication.showNewView(finance.getFinance());
	}
	
	public void setCurrentUser() {
	Technician currentTech = MainApplication.getLoggedInTechnician();
	currentUserField.setText(currentTech.getFullName());
	}
	
	public void logout() {
		MainApplication.logout();
	}
	
	public MenuView getMenuView() {
		return menuView;
	}
	
	public void setMenuview(MenuView menuView) {
		this.menuView = menuView;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	
	public static void setApplication(MainApplication application) {
		MenuController.application = application;
	}
}
