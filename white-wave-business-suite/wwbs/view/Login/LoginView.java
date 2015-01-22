package view.Login;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;

/**
 * @author Austin
 * The login screen for the application.
 */
public class LoginView {
	
	private AnchorPane login;
	
	public void setupLoginView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/Login/Login.fxml"));
			login = (AnchorPane) loader.load();
		
			LoginController controller = loader.getController();
			controller.setLoginView(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getLogin() {
		return login;
	}

	public void setLogin(AnchorPane login) {
		this.login = login;
	}
}

