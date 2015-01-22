package data;

import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Austin
 * Specifies the name and internal program ID of a 
 * technician and whether they should be allowed administrative
 * privileges.
 */
@Entity
public class Technician {

	private int technicianID;
	private StringProperty fullName;
	private StringProperty userName;
	private StringProperty emailAddress;
	private String hashedPassword;
	private BooleanProperty isAdmin;
	private Date lastLogin;
	private Date creationDate;

	public Technician() {
		
	}

	public Technician(String fullName, String userName, String emailAddress, String hashedPassword, boolean isAdmin, Date creationDate) {
		setFullName(fullName);
		setUserName(userName);
		setEmailAddress(emailAddress);
		setHashedPassword(hashedPassword);
		setAdmin(isAdmin);
		setCreationDate(creationDate);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="Technician_ID", nullable=false)
	public int getTechnicianID() {
		return technicianID;
	}
	public void setTechnicianID(int technicianID) {
		this.technicianID = technicianID;
	}

	@Column (name="Full_Name", nullable=false)
	public String getFullName() {
		return fullName.get();
	}
	public void setFullName(String fullName) {
		if(this.fullName == null)
			this.fullName = new SimpleStringProperty(fullName);
		else
			this.fullName.set(fullName);
	}
	
	@Column (name="Is_Admin", nullable=false)
	public boolean isAdmin() {
		return isAdmin.get();
	}
	public void setAdmin(boolean isAdmin) {
		if(this.isAdmin == null)
			this.isAdmin = new SimpleBooleanProperty(isAdmin);
		else
			this.isAdmin.set(isAdmin);
	}

	@Column (name="password", nullable=false)
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
	}

	@Column (name="User_Name", nullable=false)
	public String getUserName() {
		return userName.get();
	}
	public void setUserName(String userName) {
		if(this.userName == null)
			this.userName = new SimpleStringProperty(userName);
		else
			this.userName.set(userName);
	}
	
	@Column (name="Email_Address", nullable=false)
	public String getEmailAddress() {
		return emailAddress.get();
	}
	public void setEmailAddress(String emailAddress) {
		if(this.emailAddress == null)
			this.emailAddress = new SimpleStringProperty(emailAddress);
		else
			this.emailAddress.set(emailAddress);
	}
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column (name="Last_Login")
	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Temporal(TemporalType.TIMESTAMP) 
	@Column (name="Creation_Date", nullable=false)	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
