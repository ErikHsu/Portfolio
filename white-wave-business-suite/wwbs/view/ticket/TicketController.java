package view.ticket;

import java.util.Iterator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Menu.MenuView;
import app.MainApplication;
import app.popout.IPopout;
import app.popout.PopoutFactory;
import app.popout.PopoutType;
import data.Technician;
import data.Ticket;

public class TicketController {

	private static MainApplication application;
	
	private TicketView ticketView;
	
	@FXML
	private Button refreshButton;
	@FXML
	private Button menuButton;
	@FXML
	private Button newTicketButton;
	@FXML
	private Button openTicketButton;
	@FXML
	private TextField ticketNameField;
	@FXML
	private TextField priorityField;
	@FXML
	private TextField clientField;
	@FXML
	private TextField creatorField;
	@FXML
	private TextField createdOnField;
	@FXML
	private TextField currentUserField;
	@FXML
	private TreeView<String> treeView; 
	@FXML
	private Technician currentTechnician;
	
	// Icon nodes 
	private Node lowPriorityIcon;
	private Node normalPriorityIcon;
	private Node highPriorityIcon;
	private Node urgentPriorityIcon;
	private Node ticketsIcon;

	// The name of the currently selected ticket
	private String currentTicket = null;

	// Called by JavaFX
	public TicketController() {
		
	}
	
	//Initializes and pulls tickets to the list
	@FXML
	private void initialize() {
		lowPriorityIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/folder_blue.png")));
		normalPriorityIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/folder_green.png")));
		highPriorityIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/folder_yellow.png")));
		urgentPriorityIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/folder_orange.png")));
		ticketsIcon = new ImageView(new Image(getClass().getResourceAsStream("/res/help_icon.png")));
		treeView.setEditable(true);
	}
	
	public void newTicket() {
		IPopout newTicket = PopoutFactory.createPopout(PopoutType.NEWTICKET, null);
		newTicket.showNewPopout();
	}
	
	// Show expanded Ticket
	public void expandTicket() {
		if(currentTicket != null) {
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDTICKET, currentTicket);
			newPopout.setupBreadCrumbBar(null, currentTicket, null);
			newPopout.showNewPopout();
		}
	}
	
	public void logout() {
		MainApplication.logout();
	}

	public void refreshTickets() {
		// Open hibernate session
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();

		// Load tickets from database
		Query query = session.createQuery("from Ticket");
		@SuppressWarnings("unchecked")
		List<Ticket> tickets = query.list();
		Iterator<Ticket> iterator = tickets.iterator();

		// Tree item arrays
		TreeItem<String> lowPriorityRoot = new TreeItem<String> ("Low Priority", this.lowPriorityIcon);
		TreeItem<String> normalPriorityRoot = new TreeItem<String> ("Normal Priority", this.normalPriorityIcon);
		TreeItem<String> highPriorityRoot = new TreeItem<String> ("High Priority", this.highPriorityIcon);
		TreeItem<String> urgentPriorityRoot = new TreeItem<String> ("Urgent Priority", this.urgentPriorityIcon);
		// Root of tickets
		TreeItem<String> ticketsRoot = new TreeItem<String> ("Tickets", this.ticketsIcon);
		ticketsRoot.getChildren().add(lowPriorityRoot);
		ticketsRoot.getChildren().add(normalPriorityRoot);
		ticketsRoot.getChildren().add(highPriorityRoot);
		ticketsRoot.getChildren().add(urgentPriorityRoot);
		ticketsRoot.setExpanded(true);
	
		// build the TreeView
		while(iterator.hasNext() && MainApplication.isLoggedIn()) {
			Ticket ticket = iterator.next();
			if(ticket.getTicketPriority() == Ticket.Priority.LOW)
				lowPriorityRoot.getChildren().add(new TreeItem<String>(ticket.getTicketName()));
			else if(ticket.getTicketPriority() == Ticket.Priority.NORMAL)
				normalPriorityRoot.getChildren().add(new TreeItem<String>(ticket.getTicketName()));
			else if(ticket.getTicketPriority() == Ticket.Priority.HIGH)
				highPriorityRoot.getChildren().add(new TreeItem<String>(ticket.getTicketName()));
			else if(ticket.getTicketPriority() == Ticket.Priority.URGENT)
				urgentPriorityRoot.getChildren().add(new TreeItem<String>(ticket.getTicketName()));
		}
		
		// add tree to view 
		this.treeView.setRoot(ticketsRoot);
		this.treeView.getSelectionModel().selectedItemProperty().addListener(this.treeViewListener(this));
		session.getTransaction().commit();
		session.close();
	}

	// listens for what ticket is clicked on and updates the ticket view
	public ChangeListener treeViewListener(final TicketController ticketController) {
		return new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                ObservableValue<? extends TreeItem<String>> observable,
                TreeItem<String> oldValue, TreeItem<String> newValue) {
            	TreeItem<String> selectedTicket = newValue; 

            	if(selectedTicket == null)
            		;
            	else if(MainApplication.DEBUG_ON && selectedTicket.isLeaf()) {
            		System.err.println("Selected Ticket is: " + selectedTicket.getValue());
                
            		// update ticket fields
            		if(selectedTicket.getValue() != null)
            			ticketController.refreshTicketFields(selectedTicket.getValue());
            			setCurrentTicket(selectedTicket.getValue());
                	}
            }
        };
	}
	
	// find ticket with ticketName in the database and update ticket fields
	public void refreshTicketFields(String ticketName) {

		 Session session = MainApplication.getInitData().getSessionFactory().openSession();
         session.beginTransaction();

         Query query = session.createQuery("from Ticket where ticketName='" + ticketName + "'");
         if(query.list().iterator().hasNext()) {
         	Ticket ticket = (Ticket) query.list().iterator().next();
         	this.ticketNameField.setText(ticket.getTicketName());
         	this.priorityField.setText(ticket.getTicketPriority().toString());
         	// TODO no client data for tickets
         	this.clientField.setText("Test Client");
         	if(ticket.getAssignedTechnician() != null)
         		this.creatorField.setText(ticket.getAssignedTechnician().getFullName());
         	else
         		this.creatorField.setText(null);
         	this.createdOnField.setText(ticket.getTicketStart().toString());
         }
	}
	
	public void setCurrentUser() {
	Technician currentTech = MainApplication.getLoggedInTechnician();
	currentUserField.setText(currentTech.getFullName());
	}
	
	public TicketView getTicketView() {
		return ticketView;
	}
	public void setTicketView(TicketView ticketView) {
		this.ticketView = ticketView;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		TicketController.application = application;
	}

	public String getCurrentTicket() {
		return currentTicket;
	}

	public void setCurrentTicket(String currentTicket) {
		this.currentTicket = currentTicket;
	}
	
	public void returnToMenu() {
		MenuView menu = new MenuView();
		menu.setupMenuView();
		menu.getController().setCurrentUser();
		MainApplication.showNewView(menu.getMenu());
	}
}
