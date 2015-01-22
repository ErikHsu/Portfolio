package data;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import java.sql.Timestamp
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Erik
 * Any information about current tickets including their time, open/close status,
 * and other relevant information
 */

@Entity
public class Ticket {
	
	private int ticketID;
	private String ticketName;
	private Date ticketStart;
	private Date ticketEnd;
	private double ticketTotalTime;
	private String ticketCreator;
	private boolean isOpen;
	private Priority ticketPriority;
	private String ticketDescription;
	private Technician assignedTechnician;
	private Client assignedClient;
	private Set<SupportEvent> supportEvents;
	
	public enum Priority {
		LOW, NORMAL, HIGH, URGENT
	}
	
	public Ticket(String ticketName, Date ticketStart, String ticketCreator, boolean isOpen, Priority ticketPriority, String ticketDescription) {
		setTicketName(ticketName);
		setTicketStart(ticketStart);
		setTicketCreator(ticketCreator);
		setIsOpen(isOpen);
		setTicketPriority(ticketPriority);
		setTicketDescription(ticketDescription);
	}
	
	public Ticket () {

	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name = "Ticket_ID")
	public int getTicketID() {
		return ticketID;
	}
	public void setTicketID(int ticketID) {
		this.ticketID = ticketID;
	}
	
	@Column (name="Ticket_Name")
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	
	@Column (name="Ticket_Start")
	@Temporal(TemporalType.TIMESTAMP) 
	public Date getTicketStart() {
		return ticketStart;
	}
	public void setTicketStart(Date ticketStart) {
		this.ticketStart = ticketStart;
	}
	
	@Column (name="Ticket_End")
	@Temporal(TemporalType.TIMESTAMP) 
	public Date getTicketEnd() {
		return ticketEnd;
	}
	public void setTicketEnd(Date ticketEnd) {
		this.ticketEnd = ticketEnd;
	}
	
	@Column (name="Ticket_Total_Time")
	public double getTicketTotalTime() {
		return ticketTotalTime;
	}
	public void setTicketTotalTime(double ticketTotalTime) {
		this.ticketTotalTime = ticketTotalTime;
	}
	
	@Column (name="Ticket_Creator")
	public String getTicketCreator() {
		return ticketCreator;
	}
	public void setTicketCreator(String ticketCreator) {
		this.ticketCreator = ticketCreator;
	}
	
	@Column (name="is_Open")
	public boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	@Column (name="Ticket_Priority")
	public Priority getTicketPriority() {
		return ticketPriority;
	}
	public void setTicketPriority(Priority ticketPriority) {
		this.ticketPriority = ticketPriority;
	}

	@Column (name="Ticket_Description")
	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_Technician")
	public Technician getAssignedTechnician() {
		return assignedTechnician;
	}

	public void setAssignedTechnician(Technician assignedTechnician) {
		this.assignedTechnician = assignedTechnician;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_Client")
	public Client getAssignedClient() {
		return assignedClient;
	}

	public void setAssignedClient(Client assignedClient) {
		this.assignedClient = assignedClient;
	}

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_SupportEvents")
	public Set<SupportEvent> getSupportEvents() {
		return supportEvents;
	}

	public void setSupportEvents(Set<SupportEvent> supportEvents) {
		this.supportEvents = supportEvents;
	}
}