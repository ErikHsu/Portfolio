package app.popout;

import java.util.Iterator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.ExpandedAsset.ExpandedAssetController;
import view.ExpandedAsset.ExpandedAssetView;
import app.MainApplication;

public class ExpandedAssetPopout extends BreadcrumbPopout {

ExpandedAssetController controller;
	
	public ExpandedAssetPopout(String content) {
		super();
		this.setPopoutType(PopoutType.EXPANDEDASSET);
		setupScene(content);
	}

	@Override
	public boolean checkPopoutExists() {
		return false;
	}

	@Override
	public void showNewPopout() {
		setupOnCloseOperation();
		getStage().setScene(getScene());
		getStage().show();
	}

	@Override
	public void setupScene(String content) {
		ExpandedAssetView expandedAsset = new ExpandedAssetView();
		expandedAsset.setupExpandedAssetView(content);
		controller = expandedAsset.getExpandedAssetController();
		setScene(new Scene(expandedAsset.getExpandedAsset()));
		expandedAsset.setPopout(this);
		MainApplication.getPopouts().add(this);
	}

	@Override
	public void setupBreadCrumbBar(ToolBar breadCrumbBar, String content, Stage stage) {

		ToolBar breadCpy = controller.getBreadCumbBar();

		if(stage == null) {
			setupStage();
		}
		
		else {
			setStage(stage);
		}

		if(breadCrumbBar == null) {
			breadCrumbBar = new ToolBar();
		}

		breadCpy = new ToolBar();
		Iterator itr = breadCrumbBar.getItems().iterator();
		while(itr.hasNext()) {
			Button btt = (Button) itr.next();
			controller.getBreadCumbBar().getItems().add(btt);
			breadCpy.getItems().add(btt);
		}

		setBreadCrumbBar(controller.getBreadCumbBar());
		Button b = new Button(content);
		drawBreadCrumb(b);
		setCrumbStyle(b);
		
		final ToolBar toolBar = breadCpy;
		
		b.setOnAction((event) -> {
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDASSET, content);
			newPopout.setupBreadCrumbBar(toolBar, content, getStage());
		});

		controller.getBreadCumbBar().getItems().add(b);
		getStage().setScene(this.getScene());
	}
}
