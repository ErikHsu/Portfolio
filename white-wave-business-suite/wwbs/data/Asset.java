package data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Erik
 *Any information about internal and external assets
 */

@Entity
public class Asset {

	private int assetID;
	private String assetName;
	private String modelNumber;
	private String serialNumber;
	private String assetDOP;
	private String warrantyInformation;
	private String assetLocation;
	private double assetPrice;
	private double assetCumulativePrice;
	private Client assetClient;
	
	public Asset(String assetName, String modelNumber, String serialNumber, String assetDOP, String warrantyInformation, String assetLocation, 
		double assetPrice, double assetCumulativePrice) {
		setAssetName(assetName);
		setModelNumber(modelNumber);
		setSerialNumber(serialNumber);
		setAssetDOP(assetDOP);
		setWarrantyInformation(warrantyInformation);
		setAssetLocation(assetLocation);
		setAssetPrice(assetPrice);
		setAssetCumulativePrice(assetCumulativePrice);
	}
	
	public Asset() {
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name = "Asset_ID")
	public int getAssetID() {
		return assetID;
	}
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
	@Column (name = "Asset_Name")
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	@Column (name = "Model_Number")
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	
	@Column (name = "Serial_Number")
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@Column (name = "Asset_DoP")
	public String getAssetDOP() {
		return assetDOP;
	}
	public void setAssetDOP(String assetDOP) {
		this.assetDOP = assetDOP;
	}
	
	@Column (name = "Warranty_Information")
	public String getWarrantyInformation() {
		return warrantyInformation;
	}
	public void setWarrantyInformation(String warrantyInformation) {
		this.warrantyInformation = warrantyInformation;
	}
	
	@Column (name = "Asset_Location")
	public void setAssetLocation(String assetLocation) {
		this.assetLocation = assetLocation;
	}
	public String getAssetLocation() {
		return assetLocation;
	}
	
	@Column (name = "Asset_Price")
	public double getAssetPrice() {
		return assetPrice;
	}
	public void setAssetPrice(double assetPrice) {
		this.assetPrice = assetPrice;
	}
	
	@Column (name = "Asset_Cumulative_Price")
	public double getAssetCumulativePrice() {
		return assetCumulativePrice;
	}
	public void setAssetCumulativePrice(double assetCumulativePrice) {
		this.assetCumulativePrice = assetCumulativePrice;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="FK_Client")
	public Client getAssetClient() {
		return assetClient;
	}

	public void setAssetClient(Client assetClient) {
		this.assetClient = assetClient;
	}
	
}