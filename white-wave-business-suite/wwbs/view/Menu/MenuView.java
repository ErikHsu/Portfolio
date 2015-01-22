package view.Menu;

import java.io.IOException;

import view.Menu.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;

public class MenuView {

	private AnchorPane menu;
	private MenuController controller;	

	public void setupMenuView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/Menu/Menu.fxml"));
			menu = (AnchorPane) loader.load();
			controller = loader.getController();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnchorPane getMenu() {
		return menu;
	}

	public void setMenu(AnchorPane menu) {
		this.menu = menu;
	}
	
	public MenuController getController() {
		return controller;
	}
	
	public void setController(MenuController controller) {
		this.controller = controller;
	}
}