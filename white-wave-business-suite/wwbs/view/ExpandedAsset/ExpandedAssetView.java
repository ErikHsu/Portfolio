package view.ExpandedAsset;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

public class ExpandedAssetView {
	
	private AnchorPane expandedAsset;
	private ExpandedAssetController expandedAssetController;
	private IPopout popout;
	private String currentAsset;
	
	public void setupExpandedAssetView(String content) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/ExpandedAsset/ExpandedAsset.fxml"));
			expandedAsset = (AnchorPane) loader.load();
			
			setCurrentAsset(content);
			
			expandedAssetController = loader.getController();
			expandedAssetController.setExpandedAssetView(this);
			expandedAssetController.setSelectedAsset();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnchorPane getExpandedAsset() {
		return expandedAsset;
	}

	public void setExpandedAsset(AnchorPane expandedAsset) {
		this.expandedAsset = expandedAsset;
	}

	public ExpandedAssetController getExpandedAssetController() {
		return expandedAssetController;
	}

	public void setExpandedAssetController(ExpandedAssetController expandedAssetController) {
		this.expandedAssetController = expandedAssetController;
	}
	
	public IPopout getPopout() {
		return popout;
	}

	public void setPopout(IPopout popout) {
		this.popout = popout;
	}

	public String getCurrentAsset() {
		return currentAsset;
	}

	public void setCurrentAsset(String currentAsset) {
		this.currentAsset = currentAsset;
	}
}
