package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Austin
 * Any information about current clients including their 
 * organization and relevant information.
 */
@Entity
public class Client {

	private int clientID;
	private String clientName;
	private String clientPhoneNumber;
	private String organization;
	private String comments;
	private double hourlyRate;
	
	public Client(String clientName, String clientPhoneNumber, String organization, String comments, double hourlyRate) {
		setClientName(clientName);
		setClientPhoneNumber(clientPhoneNumber);
		setOrganization(organization);
		setComments(comments);
		setHourlyRate(hourlyRate);
	}
	
	public Client() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="Client_ID")
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	
	@Column (name="Client_Name")
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	@Column (name="Client_Phone_Number")
	public String getClientPhoneNumber() {
		return clientPhoneNumber;
	}
	public void setClientPhoneNumber(String clientPhoneNumber) {
		this.clientPhoneNumber = clientPhoneNumber;
	}
	
	@Column (name="Organization")
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	@Column (name="Comments")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	@Column (name="Hourly_Rate")
	public double getHourlyRate() {
		return hourlyRate;
	}
	public void setHourlyRate(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}
	
}