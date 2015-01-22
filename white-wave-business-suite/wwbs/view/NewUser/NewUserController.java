package view.NewUser;

import java.sql.Timestamp;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.hibernate.Session;

import view.Login.LoginController;
import app.MainApplication;
import data.Technician;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException; 

public class NewUserController {

	private static MainApplication application;
	
	private NewUserView newUserView;
	
	@FXML
	private Button exitButton;
	@FXML
	private Button createUserButton;
	@FXML
	private TextField fullNameField;
	@FXML
	private TextField userNameField;
	@FXML
	private TextField emailAddressField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField repeatPasswordField;
	
	//Called by JavaFX
	public NewUserController() {
		
	}
	
	/*
	 * User has pressed the exit button
	 */
	public void exit() {
		/*
		 * Close the popout
		 */
		this.getNewUserView().getPopout().closePopout();
	}
	
	/*
	 * Attempt to add a new user to the system
	 */
	public void addNewUser() {
		
		/*
		 * Get data from the fields in the view
		 */
		String fullName = fullNameField.getText();
		String userName = userNameField.getText();
		String emailAddress = emailAddressField.getText();
		String pass1 = passwordField.getText();
		String pass2 = repeatPasswordField.getText(); 

		/*
		 * Verify the entered data
		 */
		if(verifyFullName(fullName)
		   && verifyUserName(userName)
		   && verifyEmailAddress(emailAddress)
		   && verifyPassword(pass1, pass2)
		   ) {

			   /*
				* Connect to the database
				*/
			   Session session = MainApplication.getInitData().getSessionFactory().openSession();
			   session.beginTransaction();

			   /*
			    * Setup the date object to get a time stamp
			    */
			   Date date = new Date();

			   /*
			    * If all entries are valid add the user the the system
			    */
			   
			   /*
			    * Create the new technician object (should be done in a better way)
			    */
			   Technician tech = new Technician(fullName, 
						userName, 
						emailAddress, 
						LoginController.encryptPassword(pass1, emailAddress),
						false,  // user is not admin
						new Timestamp(date.getTime())
						);
			   
			   /*
			    * Try to add the tech to the database
			    */
			   try {
				   session.save(tech);
				   session.getTransaction().commit();
			   }

			   catch (Exception e) {
					if (session.getTransaction() != null) {
						session.getTransaction().rollback();
					}
				} finally {
					/*
		            * Close the session
		           	*/
					session.close();
				}
			   
			   /*
			    * Debugging messages 
			    */
			   if(MainApplication.DEBUG_ON) {
				   System.err.println("Added new user with");
				   System.err.println("\tFullName: " + fullName);
				   System.err.println("\tUserName: " + userName);
				   System.err.println("\tEmail Address: " + emailAddress);
				   System.err.println("\tPassword: " + pass1);
			   }
			   /*
				 * Close the popout
				 */
			   this.getNewUserView().getPopout().closePopout();
		   }
		/*
		 * The user did not enter valid info
		 */
		else {
			if(MainApplication.DEBUG_ON) 
				System.err.println("User did not enter vaild new user information");
				
			/*
			 * reset the forum
			 */
			fullNameField.clear();
			userNameField.clear();
			emailAddressField.clear();
			passwordField.clear();
			repeatPasswordField.clear();
		}
	}
	
	public boolean verifyFullName(String fName) {
		boolean result = false;
		if (fName.contains("[0-9]+") || !fName.contains(" ")) {
			result = false;
		}
		else {
			result = true;
		}
		return result;
	}
	
	// TODO
	public boolean verifyUserName(String uName) {
		return true;
	}
	
	public boolean verifyEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;			
	}
	
	public boolean verifyPassword(String pass1, String pass2) {
		boolean result = false;
		if (pass1.equals(pass2)) {
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}
	
	public NewUserView getNewUserView() {
		return newUserView;
	}
	public void setNewUserView(NewUserView newUserView) {
		this.newUserView = newUserView;
	}

	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		NewUserController.application = application;
	}

}
