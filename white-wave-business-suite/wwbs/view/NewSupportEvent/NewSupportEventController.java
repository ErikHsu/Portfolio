package view.NewSupportEvent;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.hibernate.Query;
//import org.hibernate.Query;
import org.hibernate.Session;

import app.MainApplication;
import data.SupportEvent;
import data.Ticket;

public class NewSupportEventController {

	private static MainApplication application;
	
	private NewSupportEventView newSupportEventView;
	
	@FXML
	private Button exitButton;
	@FXML
	private Button addButton;
	@FXML
	private TextField supportEventNameField;
	@FXML
	private TextArea commentsField;
	@FXML
	private TextField ticketField;
	@FXML
	private TextField creationDateField;
	@FXML
	private TextField hoursSpentField;
	
	//Called by JavaFX
	public NewSupportEventController() {
		
	}
	
	//User presses the exit button
	public void exit() {
		/*
		 * Close the popout
		 */
		this.getNewSupportEventView().getPopout().closePopout();
	}
	
	//Attempts to add the support event to the database
	public void addNewSupportEvent() {
		Date date = new Date();
		//Gets data from text fields
		String supportEventName = supportEventNameField.getText();
		String comments = commentsField.getText();
		String hoursSpent = hoursSpentField.getText();
		
		if(supportEventName != null && comments != null && hoursSpent != null) {
			
			Double hoursSpentD = Double.parseDouble(hoursSpent);
			//Connects to the database
			Session session = MainApplication.getInitData().getSessionFactory().openSession();
			session.beginTransaction();	
			
			Query q = session.createQuery("from Ticket where ticketName='" + newSupportEventView.getCurrentTicket() + "'");
			if(!q.list().iterator().hasNext())
				return;
			
			Ticket t = (Ticket) q.list().iterator().next();
			//Creates the new client object
			SupportEvent newSupportEvent = new SupportEvent(supportEventName, comments);
			newSupportEvent.setCreationDate(new Timestamp(date.getTime()));
			newSupportEvent.setHoursWorked(hoursSpentD);
			
			if(t.getSupportEvents() == null) {
				HashSet<SupportEvent> hs = new HashSet<SupportEvent>();
				hs.add(newSupportEvent);
			}
			else {
				t.getSupportEvents().add(newSupportEvent);
			}
			newSupportEvent.setTicket(t);
			
			//Try to add the client to the database
			try {
				session.save(newSupportEvent);
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
				System.err.println("Added new supportEvent with");
				System.err.println("\tSupportEventName: " + supportEventName);
				System.err.println("\tComments: " + comments);
			}
			//Close the popout
			this.getNewSupportEventView().getPopout().closePopout();
		}
		else {
			if(MainApplication.DEBUG_ON)
				System.err.println("User did not enter valid new support event information.");
			
			// Reset the form
			if(supportEventName == null)
				supportEventNameField.setText("Please enter the support event's name");
			if(comments == null)
				commentsField.setText("Please enter the comment");
		}
	}
	
	public void initializeFields() {
		Date date = new Date();
		creationDateField.setText(new Timestamp(date.getTime()).toString());
		ticketField.setText(this.getNewSupportEventView().getCurrentTicket());
	}
	
	public NewSupportEventView getNewSupportEventView() {
		return newSupportEventView;
	}
	
	public void setNewSupportEventView(NewSupportEventView newSupportEventView) {
		this.newSupportEventView = newSupportEventView;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		NewSupportEventController.application = application;
	}
	
}
