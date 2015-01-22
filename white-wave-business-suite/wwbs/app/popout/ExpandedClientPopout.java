package app.popout;

import java.util.Iterator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.expandedClient.ExpandedClientController;
import view.expandedClient.ExpandedClientView;
import app.MainApplication;

public class ExpandedClientPopout extends BreadcrumbPopout {
	
	ExpandedClientController controller;
	
	public ExpandedClientPopout(String content) {
		super();
		this.setPopoutType(PopoutType.EXPANDEDCLIENT);
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
		ExpandedClientView expandedClient = new ExpandedClientView();
		expandedClient.setupExpandedClientView(content);
		controller = expandedClient.getExpandedClientController();
		setScene(new Scene(expandedClient.getExpandedClient()));
		expandedClient.setPopout(this);
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
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDCLIENT, content);
			newPopout.setupBreadCrumbBar(toolBar, content, getStage());
		});

		controller.getBreadCumbBar().getItems().add(b);
		getStage().setScene(this.getScene());
	}

}
