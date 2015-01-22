package app.popout;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.NewAsset.NewAssetView;

public class NewAssetPopout extends PopoutWindow {

	public NewAssetPopout(String content) {
		super();
		setupStage();
		setupScene(content);
		setupOnCloseOperation();
		showNewPopout();
	}

	@Override
	public boolean checkPopoutExists() {
		return false;
	}

	@Override
	public void showNewPopout() {
		this.getStage().setScene(this.getScene());
		this.getStage().show();
	}

	@Override
	public void setupScene(String content) {
		NewAssetView newAssetView = new NewAssetView();
		newAssetView.setupNewAssetView();
		this.setScene(new Scene(newAssetView.getNewAsset()));
		newAssetView.setPopout(this);
	}

	@Override
	public void setupBreadCrumbBar(ToolBar breadCrumbBar, String content,
			Stage stage) {
		
	}

	@Override
	public ToolBar getBreadCrumbBar() {
		return null;
	}

}
