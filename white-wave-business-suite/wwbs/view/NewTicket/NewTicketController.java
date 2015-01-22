package view.NewTicket;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.hibernate.Query;
//import org.hibernate.Query;
import org.hibernate.Session;

import app.MainApplication;
import data.Client;
import data.Technician;
import data.Ticket;

public class NewTicketController {

	private static MainApplication application;
	
	private NewTicketView newTicketView;
	
	@FXML
	private Button exitButton;
	@FXML
	private Button addButton;
	@FXML
	private TextField ticketNameField;
	@FXML
	private TextArea ticketDescriptionField;
	@FXML
	private ChoiceBox<String> clientChoiceBox;
	@FXML
	private ChoiceBox<String> priorityChoiceBox;
	@FXML
	private ChoiceBox<String> assignedTechChoiceBox;
	@FXML
	private Technician currentTechnician;
	
	@FXML
	private void initialize() {
		// Open hibernate session
			Session session = MainApplication.getInitData().getSessionFactory().openSession();
			session.beginTransaction();
			
			Query queryT = session.createQuery("from Technician");
			Query queryC = session.createQuery("from Client");

			Iterator itrT = queryT.list().iterator();
			Iterator itrC = queryC.list().iterator();
			
			ObservableList<String> listT = FXCollections.observableArrayList();
			Technician t;
			while(itrT.hasNext()) {
				t = (Technician) itrT.next();
				listT.add(t.getFullName());
			}
			
			ObservableList<String> listC = FXCollections.observableArrayList();
			Client c;
			while(itrC.hasNext()) {
				c = (Client) itrC.next();
				listC.add(c.getClientName());
			}
			
			ObservableList<String> listP = FXCollections.observableArrayList("LOW","NORMAL","HIGH","URGENT");
			
			assignedTechChoiceBox.setItems(listT);
			clientChoiceBox.setItems(listC);
			priorityChoiceBox.setItems(listP);
			
			session.getTransaction().commit();
			session.close();
	}
	
	//Called by JavaFX
	public NewTicketController() {
		
	}
	
	//User presses the exit button
	public void exit() {
		/*
		 * Close the popout
		 */
		this.getNewTicketView().getPopout().closePopout();
	}
	
	
	//Attempts to add the client to the database
	public void addNewTicket() {
		//Gets data from text fields		
		String ticketName = ticketNameField.getText();
		String ticketDescription = ticketDescriptionField.getText();

		String ticketPriorityS = null;
		if(priorityChoiceBox.getValue() != null) {
			ticketPriorityS = priorityChoiceBox.getValue().toString();
		}

		String clientS = null;
		if(clientChoiceBox.getValue() != null) {
			clientS = clientChoiceBox.getValue().toString();
		}
		
		String assignedTech = null;
		if(assignedTechChoiceBox.getValue() != null) {
			assignedTech = assignedTechChoiceBox.getValue().toString();
		}

		Technician currentTech = getCurrentUser();
		String ticketCreator = currentTech.getFullName();
		
		Ticket.Priority ticketPriority = null;
		if(ticketPriorityS != null) {
			if(ticketPriorityS.equals(Ticket.Priority.LOW.toString()))
				ticketPriority = Ticket.Priority.LOW;
			else if(ticketPriorityS.equals(Ticket.Priority.NORMAL.toString()))
				ticketPriority = Ticket.Priority.NORMAL;
			else if(ticketPriorityS.equals(Ticket.Priority.HIGH.toString()))
				ticketPriority = Ticket.Priority.HIGH;
			else if(ticketPriorityS.equals(Ticket.Priority.URGENT.toString()))
				ticketPriority = Ticket.Priority.URGENT;
		}

		if(ticketName != null && ticketCreator != null &&
				ticketPriority != null && ticketDescription != null && assignedTech != null 
				&& clientS != null) {
			//Connects to the database
			Session session = MainApplication.getInitData().getSessionFactory().openSession();
			session.beginTransaction();	
			
			//Creates the new Ticket object
			Date date = new Date();
			Timestamp ticketStart = new Timestamp(date.getTime());
			boolean isOpen = true;
			Ticket newTicket= new Ticket(ticketName, ticketStart, ticketCreator, isOpen, ticketPriority, ticketDescription);
			
			// get the assigned tech and the client
			Query queryT = session.createQuery("from Technician where fullName='" + assignedTech + "'");
			Query queryC = session.createQuery("from Client where clientName='" + clientS + "'");

			Iterator itrT = queryT.list().iterator();
			Iterator itrC = queryC.list().iterator();
					
			if(itrT.hasNext()) {
				Technician t = (Technician) itrT.next();
				newTicket.setAssignedTechnician(t);
			}
			if(itrC.hasNext()) {
				Client c = (Client) itrC.next();
				newTicket.setAssignedClient(c);
			}

			//Try to add the ticket to the database
			try {
				session.save(newTicket);
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
				System.err.println("Added new ticket with");
				System.err.println("\tTicket Name: " + ticketName);
				System.err.println("\tTicket Start: " + ticketStart);
				System.err.println("\tTicket Creator: " + ticketCreator);
				System.err.println("\tTicket Priority: " + ticketPriority);
				System.err.println("\tTicket Description: " + ticketDescription);
			}
			//Close the popout
			this.getNewTicketView().getPopout().closePopout();
		}
		else {
			if(MainApplication.DEBUG_ON)
				System.err.println("User did not enter valid new ticket information.");
			
			// Reset the form
			if(ticketName == null)
				ticketNameField.setText("Please enter the ticket's name");
			if(ticketDescription == null)
				ticketDescriptionField.setText("Please enter the ticket description");
		}
	}
	
	public Technician getCurrentUser() {
		Technician currentTech = MainApplication.getLoggedInTechnician();
		return currentTech;
	}
	
	public NewTicketView getNewTicketView() {
		return newTicketView;
	}
	
	public void setNewTicketView(NewTicketView newTicketView) {
		this.newTicketView = newTicketView;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		NewTicketController.application = application;
	}
	
}
