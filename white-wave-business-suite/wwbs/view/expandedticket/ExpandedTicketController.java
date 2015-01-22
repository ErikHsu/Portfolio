package view.expandedticket;

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

import app.MainApplication;
import app.popout.IPopout;
import app.popout.PopoutFactory;
import app.popout.PopoutType;
import data.Client;
import data.SupportEvent;
import data.Ticket;


public class ExpandedTicketController {

	private ExpandedTicketView expandedTicketView;
	
	@FXML
	private ToolBar breadCumbBar;
	@FXML
	private Button exitButton;
	@FXML
	private Button supportEventButton;
	@FXML
	private TextField ticketNameField;
	@FXML
	private TextArea descriptionField;
	@FXML
	private TextField assignedTechnicianField;
	@FXML
	private TextField creationDateField;
	@FXML
	private TextField priorityField;
	@FXML
	private TextField clientField;
	@FXML
	private ComboBox supportEventField;

	public ExpandedTicketView getExpandedTicketView() {
		return expandedTicketView;
	}

	public void setExpandedTicketView(ExpandedTicketView expandedTicketView) {
		this.expandedTicketView = expandedTicketView;
	}
	
	public void exit() {
		this.getExpandedTicketView().getPopout().closePopout();
	}
	
	public void supportEvents() {
		IPopout newPopout = PopoutFactory.createPopout(PopoutType.BASICSUPPORTEVENT, getExpandedTicketView().getCurrentTicket());
		newPopout.showNewPopout();
	}
	
	public void clientFieldSelected() {
		if(clientField.getText() != null) {

			Session session = MainApplication.getInitData().getSessionFactory().openSession();
			session.beginTransaction();
			
			Query query = session.createQuery("from Client where clientName='" + clientField.getText() + "'");
			if(query.list().iterator().hasNext()) {
				Client client = (Client) query.list().iterator().next();
				IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDCLIENT, client.getOrganization());
				newPopout.setupBreadCrumbBar(this.getBreadCumbBar(), client.getOrganization(), this.getExpandedTicketView().getPopout().getStage());
			}
			
			session.getTransaction().commit();
			session.close();
		}
	}
	
	public void setSelectedTicket() {
		//Begin query for selected client
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Ticket where ticketName='" + expandedTicketView.getCurrentTicket() + "'");
		if(query.list().iterator().hasNext()) {
			Ticket ticket = (Ticket) query.list().iterator().next();
			ticketNameField.setText(ticket.getTicketName());
			descriptionField.setText(ticket.getTicketDescription());

			if(ticket.getAssignedTechnician() != null)
				assignedTechnicianField.setText(ticket.getAssignedTechnician().getFullName());
			
			if(ticket.getAssignedClient() != null)
				clientField.setText(ticket.getAssignedClient().getClientName());
			
			if(ticket.getSupportEvents() != null) {

				ObservableList<String> list = FXCollections.observableArrayList();
				Iterator itr = ticket.getSupportEvents().iterator();
				while(itr.hasNext()) {
					SupportEvent event = (SupportEvent) itr.next();
					list.add(event.getSupportEventName());
				}

				supportEventField.setItems(list);
			}

			creationDateField.setText(ticket.getTicketStart().toString());
			priorityField.setText(ticket.getTicketPriority().toString());
			
			session.getTransaction().commit();
			session.close();
			
		}
	}
	
	public void handleSelectSupportEvent() {
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();

		String se = (String) supportEventField.getSelectionModel().getSelectedItem();
		IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDSUPPORTEVENT, se);
		newPopout.setupBreadCrumbBar(this.getBreadCumbBar(), se, this.getExpandedTicketView().getPopout().getStage());
	}

	public ToolBar getBreadCumbBar() {
		return breadCumbBar;
	}

	public void setBreadCumbBar(ToolBar breadCumbBar) {
		this.breadCumbBar = breadCumbBar;
	}
}
