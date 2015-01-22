package view.InvoiceGenerator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Client.ClientController;
import app.MainApplication;
import app.popout.IPopout;
import app.popout.PopoutFactory;
import app.popout.PopoutType;
import data.Client;
import data.SupportEvent;
import data.Ticket;


public class InvoiceGeneratorController {

	private static ClientController clientController;
	
	private InvoiceGeneratorView invoiceGeneratorView;
	private String currentClient;
	private double hourlyRate;
	private double totalAmountOwed;
	private String testString;
	private String otherTestString;
	
	
	
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
	private TextField DateField;
	@FXML
	private TextArea commentsField;
	@FXML
	private TextField rateField;
	@FXML
	private ComboBox ticketChoiceBox;
	
	
	public InvoiceGeneratorView getInvoiceGeneratorView() {
		return invoiceGeneratorView;
	}
	
	public void setSelectedClient() {		
		//Begin query for selected client
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Client where organization='" + invoiceGeneratorView.getCurrentClient() + "'");
		if(query.list().iterator().hasNext()) {
			Date date = new Date();
			Timestamp InvoiceDate = new Timestamp(date.getTime());
			Client client = (Client) query.list().iterator().next();
			hourlyRate = client.getHourlyRate();
			clientNameField.setText(client.getClientName());
			organizationField.setText(client.getOrganization());
			DateField.setText(InvoiceDate.toString());
			
			otherTestString = client.getClientName();
			
			
			
		}
		
		
		
		session.getTransaction().commit();
		session.close();
		
		
	}
	
	public void getClientTickets() {
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		
		//declared the list to store the ticket names for the choice box.
		
		//initializing totalAmountOwed in case no support events.
		totalAmountOwed = 0;
		
		Query queryT = session.createQuery("from Ticket");
		Iterator itrT = queryT.list().iterator();
		
		ObservableList<String> listTicket = FXCollections.observableArrayList();
		
		while(itrT.hasNext()){
			Ticket t = (Ticket) itrT.next();
			
			if(t.getAssignedClient() != null){	
				if(t.getAssignedClient().getClientName().equals( otherTestString)){
					listTicket.add(t.getTicketName());
					Iterator itrS = t.getSupportEvents().iterator();
					while(itrS.hasNext()) {
						SupportEvent event = (SupportEvent) itrS.next();
						totalAmountOwed = totalAmountOwed + (event.getHoursWorked() * hourlyRate);
					}
				}
			}
		}		
		rateField.setText(Double.toString(totalAmountOwed));
		ticketChoiceBox.setItems(listTicket);
		
		session.getTransaction().commit();
		session.close();
	}
	
	
	public void handleSelectTicket() {
		
		
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		Query queryTi = session.createQuery("from Ticket");
		Iterator itrTi = queryTi.list().iterator();
		
		testString = ticketChoiceBox.getValue().toString();
		
		while(itrTi.hasNext() ){
			Ticket ti = (Ticket) itrTi.next();
			
			if(ti.getAssignedClient() != null){	
				if(ti.getTicketName().equals( testString)){
					
					if(ti != null) {
						testString = ti.getTicketName();
						
						IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDTICKET, testString);
						newPopout.setupBreadCrumbBar(this.getBreadCumbBar(), testString, this.getInvoiceGeneratorView().getPopout().getStage());
						newPopout.showNewPopout();
						
						}
					
				}
			}
		}	
		
		
		
		
		
	}
	
	
		
	
	
	public void exit() {
		this.getInvoiceGeneratorView().getPopout().closePopout();
	}

	public void setInvoiceGeneratorView(InvoiceGeneratorView invoiceGeneratorView) {
		this.invoiceGeneratorView = invoiceGeneratorView;
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

