package view.expandedClient;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import view.Client.ClientController;
import data.Client;

import org.hibernate.Query;
import org.hibernate.Session;

import app.MainApplication;


public class ExpandedClientController {

	private static ClientController clientController;
	
	private ExpandedClientView expandedClientView;
	private String currentClient;
	
	@FXML
	private ToolBar breadCumbBar;
	@FXML
	private Button exitButton;
	@FXML
	private Button saveButton;
	@FXML
	private TextField clientNameField;
	@FXML
	private TextField organizationField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextArea commentsField;
	@FXML
	private TextField rateField;

	public ExpandedClientView getExpandedClientView() {
		return expandedClientView;
	}
	
	public void setSelectedClient() {		
		//Begin query for selected client
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Client where organization='" + expandedClientView.getCurrentClient() + "'");
		if(query.list().iterator().hasNext()) {
			Client client = (Client) query.list().iterator().next();
			clientNameField.setText(client.getClientName());
			organizationField.setText(client.getOrganization());
			phoneNumberField.setText(client.getClientPhoneNumber());
			commentsField.setText(client.getComments());
			rateField.setText(Double.toString(client.getHourlyRate()));
		}
	}
	
	public void exit() {
		this.getExpandedClientView().getPopout().closePopout();
	}

	public void setExpandedClientView(ExpandedClientView expandedClientView) {
		this.expandedClientView = expandedClientView;
	}

	public ToolBar getBreadCumbBar() {
		return breadCumbBar;
	}

	public void setBreadCumbBar(ToolBar breadCumbBar) {
		this.breadCumbBar = breadCumbBar;
	}

	public String getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(String currentClient) {
		this.currentClient = currentClient;
	}
}
