package app.popout;

import java.util.Iterator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.ExpandedSupportEvent.ExpandedSupportEventController;
import view.ExpandedSupportEvent.ExpandedSupportEventView;
import app.MainApplication;

public class ExpandedSupportEventPopout extends BreadcrumbPopout {
	
	ExpandedSupportEventController controller;
	
	public ExpandedSupportEventPopout(String content) {
		super();
		this.setPopoutType(PopoutType.EXPANDEDSUPPORTEVENT);
		setupScene(content);
	}

	@Override
	public boolean checkPopoutExists() {
		return false;
	}

	@Override
	public void showNewPopout() {
		setupOnCloseOperation();
		MainApplication.getPopouts().add(this);
		getStage().setScene(getScene());
		getStage().show();
	}

	@Override
	public void setupScene(String content) {
		ExpandedSupportEventView expandedSupportEvent= new ExpandedSupportEventView();
		expandedSupportEvent.setupExpandedSupportEventView(content);
		controller = expandedSupportEvent.getExpandedSupportEventController();
		setScene(new Scene(expandedSupportEvent.getExpandedSupportEvent()));
		expandedSupportEvent.setPopout(this);
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
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDSUPPORTEVENT, content);
			newPopout.setupBreadCrumbBar(toolBar, content, getStage());
		});

		controller.getBreadCumbBar().getItems().add(b);
		getStage().setScene(this.getScene());
	}

}
