package app.popout;

import java.util.Iterator;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.InvoiceGenerator.InvoiceGeneratorController;
import view.InvoiceGenerator.InvoiceGeneratorView;
import app.MainApplication;

public class InvoiceGeneratorPopout extends BreadcrumbPopout {
	
	InvoiceGeneratorController controller;
	
	public InvoiceGeneratorPopout(String content) {
		super();
		this.setPopoutType(PopoutType.INVOICEGENERATOR);
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
		InvoiceGeneratorView invoiceGenerator = new InvoiceGeneratorView();
		invoiceGenerator.setupInvoiceGeneratorView(content);
		controller = invoiceGenerator.getInvoiceGeneratorController();
		setScene(new Scene(invoiceGenerator.getInvoiceGenerator()));
		invoiceGenerator.setPopout(this);
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
			IPopout newPopout = PopoutFactory.createPopout(PopoutType.INVOICEGENERATOR, content);
			newPopout.setupBreadCrumbBar(toolBar, content, getStage());
		});

		controller.getBreadCumbBar().getItems().add(b);
		getStage().setScene(this.getScene());
	}

}
