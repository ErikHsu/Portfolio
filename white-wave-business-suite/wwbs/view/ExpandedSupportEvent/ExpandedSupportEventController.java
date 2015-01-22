package view.ExpandedSupportEvent;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import view.SupportEvent.SupportEventController;
import data.Client;
import data.SupportEvent;

import org.hibernate.Query;
import org.hibernate.Session;

import app.MainApplication;
import app.popout.IPopout;
import app.popout.PopoutFactory;
import app.popout.PopoutType;


public class ExpandedSupportEventController {

	private static SupportEventController supportEventController;
	
	private ExpandedSupportEventView expandedSupportEventView;
	private String currentSupportEvent;
	
	@FXML
	private ToolBar breadCumbBar;
	@FXML
	private Button exitButton;
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


	public ExpandedSupportEventView getExpandedSupportEventView() {
		return expandedSupportEventView;
	}
	
	public void setSelectedSupportEvent() {		
		//Begin query for selected support event
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from SupportEvent where supportEventName='" + expandedSupportEventView.getCurrentSupportEvent() + "'");
		if(query.list().iterator().hasNext()) {
			SupportEvent supportEvent= (SupportEvent) query.list().iterator().next();
			supportEventNameField.setText(supportEvent.getSupportEventName());
			commentsField.setText(supportEvent.getComments());
			creationDateField.setText(supportEvent.getCreationDate().toString());
			hoursSpentField.setText(String.valueOf(supportEvent.getHoursWorked()));
			ticketField.setText(supportEvent.getTicket().getTicketName());
		}
	}
	
	public void ticketSelected() {
		IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDTICKET, ticketField.getText());
		newPopout.setupBreadCrumbBar(this.getBreadCumbBar(), ticketField.getText(), this.getExpandedSupportEventView().getPopout().getStage());
	}
	
	public void exit() {
		this.getExpandedSupportEventView().getPopout().closePopout();
	}

	public void setExpandedSupportEventView(ExpandedSupportEventView expandedSupportEventView) {
		this.expandedSupportEventView = expandedSupportEventView;
	}

	public ToolBar getBreadCumbBar() {
		return breadCumbBar;
	}

	public void setBreadCumbBar(ToolBar breadCumbBar) {
		this.breadCumbBar = breadCumbBar;
	}

	public String getCurrentSupportEvent() {
		return currentSupportEvent;
	}

	public void setCurrentSupportEvent(String currentSupportEvent) {
		this.currentSupportEvent= currentSupportEvent;
	}
}
