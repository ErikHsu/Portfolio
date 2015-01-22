package view.NewClient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

//import org.hibernate.Query;
import org.hibernate.Session;

import view.Client.ClientController;
import app.MainApplication;
import data.Client;

public class NewClientController {

	private static MainApplication application;
	
	private NewClientView newClientView;
	
	@FXML
	private Button exitButton;
	@FXML
	private Button addButton;
	@FXML
	private TextField clientNameField;
	@FXML
	private TextField phoneNumberField;
	@FXML
	private TextField organizationField;
	@FXML
	private TextField commentsField;
	@FXML
	private TextField hourlyRateField;
	
	//Called by JavaFX
	public NewClientController() {
		
	}
	
	//User presses the exit button
	public void exit() {
		/*
		 * Close the popout
		 */
		this.getNewClientView().getPopout().closePopout();
	}
	
	//Attempts to add the client to the database
	public void addNewClient() {
		//Gets data from text fields
		String clientName = clientNameField.getText();
		String clientPhoneNumber = phoneNumberField.getText();
		String organization = organizationField.getText();
		String comments = commentsField.getText();
		double hourlyRate = Double.valueOf(hourlyRateField.getText());
	
		if(clientName != null && clientPhoneNumber != null && organization != null &&
				comments != null && hourlyRate != 0) {
			//Connects to the database
			Session session = MainApplication.getInitData().getSessionFactory().openSession();
			session.beginTransaction();	
			
			//Creates the new client object
			Client newClient = new Client(clientName, clientPhoneNumber, organization, 
					comments, hourlyRate);
			
			//Try to add the client to the database
			try {
				session.save(newClient);
				session.getTransaction().commit();
			}
			catch (Exception e) {
				if(session.getTransaction() != null) {
					session.getTransaction().rollback();
				}	
			} finally {
				//Close the session
				session.close();
			}
		
			//Debugging messages
			if(MainApplication.DEBUG_ON) {
				System.err.println("Added new client with");
				System.err.println("\tClientName: " + clientName);
				System.err.println("\tPhoneNumber: " + clientPhoneNumber);
				System.err.println("\tOrganization: " + organization);
				System.err.println("\tComments: " + comments);
				System.err.println("\tHourly Rate: " + hourlyRate);
			}
			//Close the popout
			this.getNewClientView().getPopout().closePopout();
		}
		else {
			if(MainApplication.DEBUG_ON)
				System.err.println("User did not enter valid new client information.");
			
			// Reset the form
			if(clientName == null)
				clientNameField.setText("Please enter the client's name");
			if(clientPhoneNumber == null)
				phoneNumberField.setText("Please enter the phone number");
			if(organization == null)
				organizationField.setText("Please enter the organization");
			if(comments == null)
				commentsField.setText("Please enter the comment");
			if(hourlyRate == 0)
				hourlyRateField.setText("Please enter the hourly rate");
		}
	}
	
	public NewClientView getNewClientView() {
		return newClientView;
	}
	
	public void setNewClientView(NewClientView newClientView) {
		this.newClientView = newClientView;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		NewClientController.application = application;
	}
	
}
