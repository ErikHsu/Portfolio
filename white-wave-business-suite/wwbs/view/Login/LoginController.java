package view.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Menu.MenuView;
import view.ticket.TicketView;
import app.MainApplication;
import app.popout.IPopout;
import app.popout.IPopoutCloseListener;
import app.popout.PopoutFactory;
import app.popout.PopoutType;
import app.popout.PopoutWindow;
import data.Technician;

/**
 * 
 * @author Austin
 * This really needs to be cleaned up.
 */
public class LoginController implements IPopoutCloseListener{

	private static MainApplication application;

	private LoginView loginView;

	@FXML
	private Button loginButton;
	@FXML
	private Button newUserButton;
	@FXML
	private TextField userNameField;
	@FXML
	private PasswordField passwordField;
	
	private static ArrayList<ILoginListener> loginListeners = new ArrayList<ILoginListener>();
	
	// Don't remove. Called by javafx
	public LoginController(){
		PopoutWindow.addCloseListener(this);
	}
	
	public void showNewUserView() {
		PopoutFactory.createPopout(PopoutType.NEWUSER, null);
	}

	public void authenticateLogin() {

		String enteredUserName = userNameField.getText();
		String enteredPassword = passwordField.getText();
		
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();

        Query query = session.createQuery("from Technician");
		@SuppressWarnings("unchecked")
		List<Technician> technicians = query.list();
		Iterator<Technician> iterator = technicians.iterator();
		
		boolean loginAuthenticated = false;
		while(iterator.hasNext() && loginAuthenticated == false) {
			Technician technician = iterator.next();
			
			String passHashToCheck = encryptPassword(enteredPassword, technician.getEmailAddress());
			
			// check if entered user details match any entries in the database
			if(enteredUserName.equals(technician.getUserName()) && 
			   passHashToCheck.equals(technician.getHashedPassword())) {

				if(MainApplication.DEBUG_ON)
					System.err.println("Login success you are " + technician.getFullName());

				loginAuthenticated = true;
				MainApplication.setLoggedIn(true);
				MainApplication.setLoggedInTechnician(technician);
				// show the menu !! //TODO for the demo show the ticket view
				
			
				/*TicketView ticket = new TicketView();
				ticket.setupTicketView();
				ticket.getController().refreshTickets();
				ticket.getController().setCurrentUser();*/
				
				
				MenuView menu = new MenuView();
				menu.setupMenuView();
				menu.getController().setCurrentUser();
				
				
				/*ClientView client = new ClientView();
				client.setupClientView();
				client.getController().refreshClients()*/;
				
				
				/*
				 * notify listeners about login event 
				 */
				loginEvent();

				MainApplication.showNewView(menu.getMenu());
				//MainApplication.showNewView(client.getClient());
				//MainApplication.showNewView(ticket.getTicket());
			}
			
			else {
				if(MainApplication.DEBUG_ON)
					System.err.println("You are not " + technician.getFullName());
			}
		}
		session.getTransaction().commit();
		session.close();
		userNameField.clear();
		passwordField.clear();
	}
	
	@Override
	public void popoutWasClosed(IPopout popout) {
		LoginController.getLoginListeners().remove(popout);
	}
	
	/**
	 * Encrypt password with salt SHA-512 
	 * @param password
	 * @param email
	 * @return [SHA-512 encrypted string]
	 */
	public static String encryptPassword(String password, String email) {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.update(password.concat(email).getBytes());
			StringBuffer stringBuffer = new StringBuffer();

			for (byte element : digest.digest()) 
				stringBuffer.append(Integer.toString((element & 0xff) + 0x100, 16).substring(1));

			return stringBuffer.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
	    }
		
		return " ";
	}
	
	private void loginEvent() {
		for (ListIterator<ILoginListener> itr = loginListeners.listIterator(); itr.hasNext();) {
			ILoginListener listener = itr.next();
			listener.userHasLoggedIn();
		}
	}
	
	public LoginView getLoginView() {
		return loginView;
	}
	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}


	public static MainApplication getApplication() {
		return application;
	}


	public static void setApplication(MainApplication application) {
		LoginController.application = application;
	}

	public static void addLoginListener(ILoginListener listener) {
		loginListeners.add(listener);
	}

	public static ArrayList<ILoginListener> getLoginListeners() {
		return loginListeners;
	}
}	
