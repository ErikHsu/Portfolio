package view.NewUser;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import app.MainApplication;
import app.popout.IPopout;
import app.popout.IPopout;

/**
 * @author Erik
 * The New User creation screen for the application
 */

public class NewUserView {

	private AnchorPane newUser;
	private NewUserController newUserController;
	private IPopout popout;
	
	public void setupNewUserView() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplication.class.getResource("/view/NewUser/NewUser.fxml"));
			newUser = (AnchorPane) loader.load();
			
			this.newUserController = loader.getController();
			newUserController.setNewUserView(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AnchorPane getNewUser() {
		return newUser;
	}

	public void setNewUser(AnchorPane newUser) {
		this.newUser = newUser;
	}

	public NewUserController getNewUserController() {
		return newUserController;
	}

	public void setNewUserController(NewUserController newUserController) {
		this.newUserController = newUserController;
	}

	public IPopout getPopout() {
		return popout;
	}

	public void setPopout(IPopout popout) {
		this.popout = popout;
	}
}
