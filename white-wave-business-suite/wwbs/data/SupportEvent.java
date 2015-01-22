package data;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Erik
 * Any information about the support events associated with the tickets
 */

@Entity
public class SupportEvent {

	private int supportEventID;
	private String supportEventName;
	private String comments;
	private double hoursWorked;
	private Date creationDate;
	private Ticket ticket;
	
	public SupportEvent(String supportEventName, String comments) {
		setSupportEventName(supportEventName);
		setComments(comments);
	}
	
	public SupportEvent() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name = "Support_Event_ID")
	public int getSupportEventID() {
		return supportEventID;
	}
	public void setSupportEventID(int supportEventID) {
		this.supportEventID = supportEventID;
	}
	
	@Column (name="Support_Event_Name")
	public String getSupportEventName() {
		return supportEventName;
	}
	public void setSupportEventName(String supportEventName) {
		this.supportEventName = supportEventName;
	}
	
	@Column (name="Comments")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column (name="Hours_Worked")
	public double getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(double hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	@Column (name="Creation_Date")
	@Temporal(TemporalType.TIMESTAMP) 
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_Ticket")
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	

}
