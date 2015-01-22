package view.InvoiceGenerator;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;

public class InvoiceGeneratorView {
	
	private AnchorPane invoiceGenerator;
	private InvoiceGeneratorController invoiceGeneratorController;
	private IPopout popout;
	private String currentClient;
	
	public void setupInvoiceGeneratorView(String content) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/InvoiceGenerator/InvoiceGenerator.fxml"));
			invoiceGenerator = (AnchorPane) loader.load();
			
			setCurrentClient(content);
			
			invoiceGeneratorController = loader.getController();
			invoiceGeneratorController.setInvoiceGeneratorView(this);
			invoiceGeneratorController.setSelectedClient();
			invoiceGeneratorController.getClientTickets();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnchorPane getInvoiceGenerator() {
		return invoiceGenerator;
	}

	public void setInvoiceGenerator(AnchorPane invoiceGenerator) {
		this.invoiceGenerator = invoiceGenerator;
	}

	public InvoiceGeneratorController getInvoiceGeneratorController() {
		return invoiceGeneratorController;
	}

	public void setInvoiceGeneratorController(
			InvoiceGeneratorController invoiceGeneratorController) {
		this.invoiceGeneratorController = invoiceGeneratorController;
	}

	public String getCurrentClient() {
		return currentClient;
	}
	
	public IPopout getPopout() {
		return popout;
	}

	public void setPopout(IPopout popout) {
		this.popout = popout;
	}
	public void setCurrentClient(String currentClient) {
		this.currentClient = currentClient;
	}
}
