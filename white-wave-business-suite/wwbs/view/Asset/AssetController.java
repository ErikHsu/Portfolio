package view.Asset;

import java.util.Iterator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import org.hibernate.Query;
import org.hibernate.Session;

import view.Menu.MenuView;
import app.MainApplication;
import app.popout.IPopout;
import app.popout.PopoutFactory;
import app.popout.PopoutType;
import data.Asset;
import data.Technician;

public class AssetController {

	private static MainApplication application;
	
	private AssetView assetView;
	private Asset asset;
	
	@FXML
	private Button refreshButton;
	@FXML
	private Button menuButton;
	@FXML
	private Button newAssetButton;
	@FXML
	private Button openAssetButton;
	@FXML
	private Button logoutButton;
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
	private TextField currentUserField;
	@FXML
	private TreeView<String> assetTreeView; 
	@FXML
	private Technician currentTechnician;
	
	//Currently selected client
	private String currentAsset = null;
	
	// Called by JavaFX
	public AssetController() {
		
	}
	
	public void showNewAssetView() {
		PopoutFactory.createPopout(PopoutType.NEWASSET, null);
	}
	
	//Initializes and pulls assets to the list
	@FXML
	private void initialize() {

		assetTreeView.setEditable(true);
	}
	
	// Show expanded Asset
	public void expandAsset() {
		if(currentAsset != null) {
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDASSET, currentAsset);
			newPopout.setupBreadCrumbBar(new ToolBar(), currentAsset, null);
			newPopout.showNewPopout();
		}
	}
	
	public void logout() {
		MainApplication.logout();
	}

	public void refreshAssets() {
		// Open hibernate session
		Session session = MainApplication.getInitData().getSessionFactory().openSession();
		session.beginTransaction();

		// Load assets from database
		Query query = session.createQuery("from Asset");
		@SuppressWarnings("unchecked")
		List<Asset> assets = query.list();
		Iterator<Asset> iterator = assets.iterator();

		//Tree item arrays
		TreeItem<String> assetNameRoot = new TreeItem<String> ("Assets");
		
		// Root of tickets
		TreeItem <String> assetRoot = new TreeItem<String> ("Asset Name");
		assetRoot.getChildren().add(assetNameRoot);
		assetRoot.setExpanded(true);

		//Build the TreeView
		while(iterator.hasNext() && MainApplication.isLoggedIn()) {
			Asset asset = iterator.next();
			assetNameRoot.getChildren().add(new TreeItem<String>(asset.getAssetName()));
		}

		// add tree to view 
		this.assetTreeView.setRoot(assetRoot);
		this.assetTreeView.getSelectionModel().selectedItemProperty().addListener(this.treeViewListener(this));
		session.getTransaction().commit();
		session.close();
	}

	// listens for what ticket is clicked on and updates the ticket view
	public ChangeListener treeViewListener(final AssetController AssetController) {
		return new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                ObservableValue<? extends TreeItem<String>> observable,
                TreeItem<String> oldValue, TreeItem<String> newValue) {
            	TreeItem<String> selectedAsset = newValue; 

            	if(selectedAsset== null)
            		;
            	else if(MainApplication.DEBUG_ON && selectedAsset.isLeaf()) {
            		System.err.println("Selected Asset is: " + selectedAsset.getValue());
            		
            		setCurrentAsset(selectedAsset.getValue());
                
            		// update ticket fields
            		if(selectedAsset.getValue() != null)
            			AssetController.refreshAssetFields(selectedAsset.getValue());
                	}
            }
        };
	}
	
	// find ticket with ticketName in the database and update ticket fields
	public void refreshAssetFields(String assetName) {

		 Session session = MainApplication.getInitData().getSessionFactory().openSession();
         session.beginTransaction();

         Query query = session.createQuery("from Asset where assetName='" + assetName + "'");
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
         }
	}
	
	public void setCurrentUser() {
		Technician currentTech = MainApplication.getLoggedInTechnician();
		currentUserField.setText(currentTech.getFullName());
	}
	
	public AssetView getAssetView() {
		return assetView;
	}
	public void setAssetView(AssetView assetView) {
		this.assetView = assetView;
	}
	
	public Asset getSelectedAsset() {
		return asset;
	}
	
	public static MainApplication getApplication() {
		return application;
	}
	public static void setApplication(MainApplication application) {
		AssetController.application = application;
	}
	
	public void returnToMenu() {
		MenuView menu = new MenuView();
		menu.setupMenuView();
		menu.getController().setCurrentUser();
		MainApplication.showNewView(menu.getMenu());
	}

	public String getCurrentAsset() {
		return currentAsset;
	}

	public void setCurrentAsset(String currentAsset) {
		this.currentAsset= currentAsset;
	}
	
}