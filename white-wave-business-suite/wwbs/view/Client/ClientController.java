package view.Client;

import java.util.Iterator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Menu.MenuView;
import app.MainApplication;
import app.popout.IPopout;
import app.popout.PopoutFactory;
import app.popout.PopoutType;
import data.Client;
import data.Technician;

public class ClientController {

	private static MainApplication application;
	
	private ClientView clientView;
	private Client client;
	
	@FXML
	private Button refreshButton;
	@FXML
	private Button menuButton;
	@FXML
	private Button newClientButton;
	@FXML
	private Button openClientButton;
	@FXML
	private TextField clientNameField;
	@FXML
	private TextField organizationField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextField commentsField;
	@FXML
	private TextField rateField;
	@FXML
	private TextField currentUserField;
	@FXML
	private TreeView<String> clientTreeView; 
	@FXML
	private Technician currentTechnician;
	
	//Currently selected client
	private String currentClient = null;
	
	// Called by JavaFX
	public ClientController() {
		
	}
	
	public void showNewClientView() {
		PopoutFactory.createPopout(PopoutType.NEWCLIENT, null);
	}
	
	//Initializes and pulls clients to the list
	@FXML
	private void initialize() {

		clientTreeView.setEditable(true);
	}
	
	// Show expanded Client
	public void expandClient() {
		if(currentClient != null) {
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDCLIENT, currentClient);
			newPopout.setupBreadCrumbBar(new ToolBar(), currentClient, null);
			newPopout.showNewPopout();
		}
	}
	
	public void logout() {
		MainApplication.logout();
	}

	public void refreshClients() {
		// Open hibernate session
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();

		// Load tickets from database
		Query query = session.createQuery("from Client");
		@SuppressWarnings("unchecked")
		List<Client> clients = query.list();
		Iterator<Client> iterator = clients.iterator();

		//Tree item arrays
		TreeItem<String> organizationRoot = new TreeItem<String> ("Organizations");
		
		// Root of tickets
		TreeItem <String> clientsRoot = new TreeItem<String> ("Clients");
		clientsRoot.getChildren().add(organizationRoot);
		clientsRoot.setExpanded(true);

		//Build the TreeView
		while(iterator.hasNext() && MainApplication.isLoggedIn()) {
			Client client = iterator.next();
			organizationRoot.getChildren().add(new TreeItem<String>(client.getOrganization()));
		}

		// add tree to view 
		this.clientTreeView.setRoot(clientsRoot);
		this.clientTreeView.getSelectionModel().selectedItemProperty().addListener(this.treeViewListener(this));
		session.getTransaction().commit();
		session.close();
	}

	// listens for what ticket is clicked on and updates the ticket view
	public ChangeListener treeViewListener(final ClientController ClientController) {
		return new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                ObservableValue<? extends TreeItem<String>> observable,
                TreeItem<String> oldValue, TreeItem<String> newValue) {
            	TreeItem<String> selectedClient = newValue; 

            	if(selectedClient == null)
            		;
            	else if(MainApplication.DEBUG_ON && selectedClient.isLeaf()) {
            		System.err.println("Selected Client is: " + selectedClient.getValue());
            		
            		setCurrentClient(selectedClient.getValue());
                
            		// update ticket fields
            		if(selectedClient.getValue() != null)
            			ClientController.refreshClientFields(selectedClient.getValue());
                	}
            }
        };
	}
	
	// find ticket with ticketName in the database and update ticket fields
	public void refreshClientFields(String clientName) {

		 Session session = MainApplication.getInitData().getSessionFactory().openSession();
         session.beginTransaction();

         Query query = session.createQuery("from Client where organization='" + clientName + "'");
         if(query.list().iterator().hasNext()) {
         	Client client = (Client) query.list().iterator().next();
         	this.clientNameField.setText(client.getClientName());
         	this.organizationField.setText(client.getOrganization());
         	this.phoneNumberField.setText(client.getClientPhoneNumber());
         	this.rateField.setText(Double.toString(client.getHourlyRate()));
         }
	}
	
	public void setCurrentUser() {
		Technician currentTech = MainApplication.getLoggedInTechnician();
		currentUserField.setText(currentTech.getFullName());
	}
	
	public ClientView getClientView() {
		return clientView;
	}
	public void setClientView(ClientView clientView) {
		this.clientView = clientView;
	}
	
	public Client getSelectedClient() {
		return client;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		ClientController.application = application;
	}
	
	public void returnToMenu() {
		MenuView menu = new MenuView();
		menu.setupMenuView();
		menu.getController().setCurrentUser();
		MainApplication.showNewView(menu.getMenu());
	}

	public String getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(String currentClient) {
		this.currentClient = currentClient;
	}
	
}