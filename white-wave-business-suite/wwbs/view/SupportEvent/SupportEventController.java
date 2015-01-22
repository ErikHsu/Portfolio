package view.SupportEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
import data.SupportEvent;
import data.Technician;
import data.Ticket;

public class SupportEventController {

	private static MainApplication application;
	
	private SupportEventView supportEventView;
	private SupportEvent supportEvent;

	@FXML
	private ToolBar breadCumbBar;
	@FXML
	private Button refreshButton;
	@FXML
	private Button exitButton;
	@FXML
	private Button newSupportEventButton;
	@FXML
	private Button openSupportEventButton;
	@FXML
	private TextField supportEventNameField;
	@FXML
	private TextArea supportEventCommentsField;
	@FXML
	private TextField currentUserField;
	@FXML
	private TreeView<String> supportEventTreeView; 
	@FXML
	private Technician currentTechnician;
	
	//Currently selected support event
	private String currentSupportEvent = null;
	
	// Called by JavaFX
	public SupportEventController() {
		
	}
	
	public void showNewSupportEventView() {
		PopoutFactory.createPopout(PopoutType.NEWSUPPORTEVENT, this.getSupportEventView().getCurrentTicket());
	}
	
	//Initializes and pulls clients to the list
	@FXML
	private void initialize() {

		supportEventTreeView.setEditable(true);
	}
	
	// Show expanded Client
	public void expandSupportEvent() {
		if(currentSupportEvent != null) {
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDSUPPORTEVENT, currentSupportEvent);
			newPopout.setupBreadCrumbBar(new ToolBar(), currentSupportEvent, null);
			newPopout.showNewPopout();
		}
	}
	
	public void logout() {
		MainApplication.logout();
	}

	public void refreshSupportEvents() {
		// Open hibernate session
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		// Load tickets from database
		Query query = session.createQuery("from Ticket where ticketName='" + getSupportEventView().getCurrentTicket() + "'");
		@SuppressWarnings("unchecked")
		List<Ticket> tickets = query.list();
		Iterator<Ticket> iterator = tickets.iterator();
		Ticket t;
		if(iterator.hasNext()) {
			t = iterator.next();
		}
		else {
			if(MainApplication.DEBUG_ON)
				System.out.println("Could not load ticket");
			return;
		}
		
		Set s = t.getSupportEvents();
		Iterator itrS = s.iterator();
		
		//Tree item arrays
		TreeItem<String> supportEventNameRoot = new TreeItem<String> ("Support Events");
		
		// Root of tickets
		TreeItem <String> supportEventRoot = new TreeItem<String> ("Support Event Names");
		supportEventRoot.getChildren().add(supportEventNameRoot);
		supportEventRoot.setExpanded(true);

		//Build the TreeView
		while(itrS.hasNext() && MainApplication.isLoggedIn()) {
			SupportEvent se = (SupportEvent) itrS.next();
			supportEventNameRoot.getChildren().add(new TreeItem<String>(se.getSupportEventName()));
		}

		// add tree to view 
		this.supportEventTreeView.setRoot(supportEventRoot);
		this.supportEventTreeView.getSelectionModel().selectedItemProperty().addListener(this.treeViewListener(this));
		session.getTransaction().commit();
		session.close();
	}

	// listens for what ticket is clicked on and updates the ticket view
	public ChangeListener treeViewListener(final SupportEventController SupportEventController) {
		return new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                ObservableValue<? extends TreeItem<String>> observable,
                TreeItem<String> oldValue, TreeItem<String> newValue) {
            	TreeItem<String> selectedSupportEvent = newValue; 

            	if(selectedSupportEvent == null)
            		;
            	else if(MainApplication.DEBUG_ON && selectedSupportEvent.isLeaf()) {
            		System.err.println("Selected Client is: " + selectedSupportEvent.getValue());
            		
            		setCurrentSupportEvent(selectedSupportEvent.getValue());
                
            		// update ticket fields
            		if(selectedSupportEvent.getValue() != null)
            			SupportEventController.refreshSupportEventFields(selectedSupportEvent.getValue());
                	}
            }
        };
	}
	
	// find ticket with ticketName in the database and update ticket fields
	public void refreshSupportEventFields(String supportEventName) {

		 Session session = MainApplication.getInitData().getSessionFactory().openSession();
         session.beginTransaction();

         Query query = session.createQuery("from SupportEvent where supportEventName='" + supportEventName + "'");
         if(query.list().iterator().hasNext()) {
        	SupportEvent supportEvent = (SupportEvent) query.list().iterator().next();
        	this.supportEventNameField.setText(supportEvent.getSupportEventName());
        	this.supportEventCommentsField.setText(supportEvent.getComments());
         }
         
         session.getTransaction().commit();
         session.close();
	}
	
	public void setCurrentUser() {
		Technician currentTech = MainApplication.getLoggedInTechnician();
		currentUserField.setText(currentTech.getFullName());
	}
	
	public SupportEventView getSupportEventView() {
		return supportEventView;
	}
	public void setSupportEventView(SupportEventView supportEventView) {
		this.supportEventView = supportEventView;
	}
	
	public SupportEvent getSelectedSupportEvent() {
		return supportEvent;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		SupportEventController.application = application;
	}
	
	public void returnToMenu() {
		MenuView menu = new MenuView();
		menu.setupMenuView();
		menu.getController().setCurrentUser();
		MainApplication.showNewView(menu.getMenu());
	}

	public String getCurrentSupportEvent() {
		return currentSupportEvent;
	}

	public void setCurrentSupportEvent(String currentSupportEvent) {
		this.currentSupportEvent = currentSupportEvent;
	}
	
	public ToolBar getBreadCumbBar() {
		return breadCumbBar;
	}

	public void setBreadCumbBar(ToolBar breadCumbBar) {
		this.breadCumbBar = breadCumbBar;
	}

}