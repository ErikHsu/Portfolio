package view.NewAsset;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

/**
 * @author Erik
 * The new asset creation screen for the application
 */

public class NewAssetView {

	private AnchorPane newAsset;
	private NewAssetController newAssetController;
	private IPopout popout;
	
	public void setupNewAssetView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/NewAsset/NewAsset.fxml"));
			newAsset = (AnchorPane) loader.load();
			
			NewAssetController controller = loader.getController();
			controller.setNewAssetView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getNewAsset() {
		return newAsset;
	}

	public void setNewAsset(AnchorPane newAsset) {
		this.newAsset = newAsset;
	}
	
	public IPopout getPopout() {
		return popout;
	}
	
	public void setPopout(IPopout popout) {
		this.popout = popout;
	}
	
}
