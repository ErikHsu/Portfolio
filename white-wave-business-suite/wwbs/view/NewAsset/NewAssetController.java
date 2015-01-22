package view.NewAsset;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import org.hibernate.Query;
//import org.hibernate.Query;
import org.hibernate.Session;

import app.MainApplication;
import data.Asset;
import data.Client;
import data.Technician;

public class NewAssetController {

	private static MainApplication application;
	
	private NewAssetView newAssetView;
	
	@FXML
	private Button exitButton;
	@FXML
	private Button addButton;
	@FXML
	private TextField assetNameField;
	@FXML
	private TextField modelNumberField;
	@FXML
	private TextField serialNumberField;
	@FXML
	private TextField assetDOPField;
	@FXML
	private TextField warrantyInformationField;
	@FXML
	private TextField assetLocationField;
	@FXML
	private TextField assetPriceField;
	@FXML
	private TextField assetCumulativePriceField;
	@FXML
	private ChoiceBox clientChoiceBox;
	
	@FXML
	private void initialize() {
		// Open hibernate session
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
					
		Query queryC = session.createQuery("from Client");

		Iterator itrC = queryC.list().iterator();
					
		ObservableList<String> listC = FXCollections.observableArrayList();
		Client c;
		while(itrC.hasNext()) {
			c = (Client) itrC.next();
			listC.add(c.getClientName());
		}
					
		clientChoiceBox.setItems(listC);

		session.getTransaction().commit();
		session.close();
	}
	
	//Called by JavaFX
	public NewAssetController() {
		
	}
	
	//User presses the exit button
	public void exit() {
		/*
		 * Close the popout
		 */
		this.getNewAssetView().getPopout().closePopout();
	}
	
	//Attempts to add the client to the database
	public void addNewAsset() {
		//Gets data from text fields
		String assetName = assetNameField.getText();
		String modelNumber = modelNumberField.getText();
		String serialNumber= serialNumberField.getText();
		String assetDOP = assetDOPField.getText();
		String warrantyInformation = warrantyInformationField.getText();
		String assetLocation = assetLocationField.getText();
		Double assetPrice = Double.valueOf(assetPriceField.getText());
		Double assetCumulativePrice = Double.valueOf(assetCumulativePriceField.getText());
		
		String clientS = null;
		if(clientChoiceBox.getValue() != null) {
			clientS = clientChoiceBox.getValue().toString();
		}
	
		if(assetName != null && modelNumber != null && serialNumber != null &&
				assetDOP != null && warrantyInformation != null && assetLocation != null &&
				assetPrice != 0 && assetCumulativePrice != 0) {
			//Connects to the database
			Session session = MainApplication.getInitData().getSessionFactory().openSession();
			session.beginTransaction();	
			
			Query q = session.createQuery("from Client where clientName='" + clientS + "'");
			Iterator itrC = q.list().iterator();
			Client c = null;
			if(itrC.hasNext()) {
				c = (Client) itrC.next();
			}
			
			//Creates the new client object
			Asset newAsset = new Asset(assetName, modelNumber, serialNumber, assetDOP, warrantyInformation,
					assetLocation, assetPrice, assetCumulativePrice);
			newAsset.setAssetClient(c);

			//Try to add the client to the database
			try {
				session.save(newAsset);
				session.getTransaction().commit();
			}
			catch (Exception e) {
				if(session.getTransaction() != null) {
					session.getTransaction().rollback();
				}	
			} finally {
				//Close the session
				session.close();
			}
		
			//Debugging messages
			if(MainApplication.DEBUG_ON) {
				System.err.println("Added new client with");
				System.err.println("\tAsset Name: " + assetName);
				System.err.println("\tModel Number: " + modelNumber);
				System.err.println("\tSerial Number: " + serialNumber);
				System.err.println("\tAsset DOP: " + assetDOP);
				System.err.println("\tWarranty Information: " + warrantyInformation);
				System.err.println("\tAsset Location: " + assetLocation);
				System.err.println("\tAsset Price: " + assetPrice);
				System.err.println("\tAsset Cumulative Price: " + assetCumulativePrice);
			}
			//Close the popout
			this.getNewAssetView().getPopout().closePopout();
		}
		else {
			if(MainApplication.DEBUG_ON)
				System.err.println("User did not enter valid new asset information.");
			
			// Reset the form
			if(assetName == null)
				assetNameField.setText("Please enter the asset name");
			if(modelNumber == null)
				modelNumberField.setText("Please enter the model number");
			if(serialNumber == null)
				serialNumberField.setText("Please enter the serial number");
			if(assetDOP == null)
				assetDOPField.setText("Please enter the DOP");
			if(warrantyInformation == null)
				warrantyInformationField.setText("Please enter the warranty information");
			if(assetLocation == null)
				assetLocationField.setText("Please enter the asset location");
			if(assetPrice == 0)
				assetPriceField.setText("Please enter the asset price");
			if(assetCumulativePrice == 0)
				assetCumulativePriceField.setText("Please enter the cumulative price");
		}
		
		this.getNewAssetView().getPopout().closePopout();
	}
	
	public NewAssetView getNewAssetView() {
		return newAssetView;
	}
	
	public void setNewAssetView(NewAssetView newAssetView) {
		this.newAssetView = newAssetView;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		NewAssetController.application = application;
	}
	
}
