package view.Asset;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;

/**
 * @author Erik
 * Basic asset view for selecting available clients
 */

public class AssetView {
	
	private AnchorPane asset;
	private AssetController controller;
	
	public void setupAssetView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/Asset/Asset.fxml"));;
			asset = (AnchorPane) loader.load();
			
			controller = loader.getController();
			controller.setAssetView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getAsset() {
		return asset;
	}
	
	public void setAsset(AnchorPane asset) {
		this.asset = asset;
	}

	public AssetController getController() {
		return controller;
	}

	public void setController(AssetController controller) {
		this.controller = controller;
	}
}
