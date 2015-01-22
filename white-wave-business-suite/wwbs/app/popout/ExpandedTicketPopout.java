package app.popout;

import java.util.Iterator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.expandedticket.ExpandedTicketController;
import view.expandedticket.ExpandedTicketView;
import app.MainApplication;

public class ExpandedTicketPopout extends BreadcrumbPopout{
	
	ExpandedTicketController controller;

	public ExpandedTicketPopout(String content) {
		super();
		this.setPopoutType(PopoutType.EXPANDEDTICKET);
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
		ExpandedTicketView expandedTicket = new ExpandedTicketView();
		expandedTicket.setupExpandedTicketView(content);
		controller = expandedTicket.getExpandedTicketController();
		setScene(new Scene(expandedTicket.getExpandedTicket()));
		setBreadCrumbBar(expandedTicket.getExpandedTicketController().getBreadCumbBar());
		expandedTicket.setPopout(this);
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
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.EXPANDEDTICKET, content);
			newPopout.setupBreadCrumbBar(toolBar, content, getStage());
		});

		controller.getBreadCumbBar().getItems().add(b);
		getStage().setScene(this.getScene());
	}
}
