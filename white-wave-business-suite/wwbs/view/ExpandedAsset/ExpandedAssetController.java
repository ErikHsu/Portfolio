package view.ExpandedAsset;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;
import view.Asset.AssetController;
import data.Asset;
import data.Client;

import org.hibernate.Query;
import org.hibernate.Session;

import app.MainApplication;
import app.popout.IPopout;
import app.popout.PopoutFactory;
import app.popout.PopoutType;


public class ExpandedAssetController {

	private static AssetController assetController;
	
	private ExpandedAssetView expandedAssetView;
	private String currentAsset;
	
	@FXML
	private ToolBar breadCumbBar;
	@FXML
	private Button exitButton;
	@FXML
	private Button saveButton;
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
	private TextField clientField;

	public ExpandedAssetView getExpandedAssetView() {
		return expandedAssetView;
	}
	
	public void exit() {
		this.getExpandedAssetView().getPopout().closePopout();
	}
	
	public void clientSelected() {
		if(clientField.getText() != null) {

			Session session = MainApplication.getInitData().getSessionFactory().openSession();
			session.beginTransaction();
			
			Query query = session.createQuery("from Client where clientName='" + clientField.getText() + "'");
			if(query.list().iterator().hasNext()) {
				Client client = (Client) query.list().iterator().next();
				IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDCLIENT, client.getOrganization());
				newPopout.setupBreadCrumbBar(this.getBreadCumbBar(), client.getOrganization(), this.getExpandedAssetView().getPopout().getStage());
			}
			
			session.getTransaction().commit();
			session.close();
		}
	}
	
	public void setSelectedAsset() {		
		//Begin query for selected asset
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Asset where assetName='" + expandedAssetView.getCurrentAsset() + "'");
        if(query.list().iterator().hasNext()) {
        	Asset asset= (Asset) query.list().iterator().next();
        	this.assetNameField.setText(asset.getAssetName());
        	this.modelNumberField.setText(asset.getModelNumber());
        	this.serialNumberField.setText(asset.getSerialNumber());
        	this.assetDOPField.setText(asset.getAssetDOP());
        	this.warrantyInformationField.setText(asset.getWarrantyInformation());
        	this.assetLocationField.setText(asset.getAssetLocation());;
        	this.assetPriceField.setText(Double.toString(asset.getAssetPrice()));
        	this.assetCumulativePriceField.setText(Double.toString(asset.getAssetCumulativePrice()));
        	this.clientField.setText(asset.getAssetClient().getClientName());
        }
	}

	public void setExpandedAssetView(ExpandedAssetView expandedAssetView) {
		this.expandedAssetView = expandedAssetView;
	}

	public ToolBar getBreadCumbBar() {
		return breadCumbBar;
	}

	public void setBreadCumbBar(ToolBar breadCumbBar) {
		this.breadCumbBar = breadCumbBar;
	}

	public String getCurrentAsset() {
		return currentAsset;
	}

	public void setCurrentAsset(String currentAsset) {
		this.currentAsset = currentAsset;
	}
}
