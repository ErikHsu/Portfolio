package app.popout;

import java.util.ListIterator;

import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import view.Login.ILoginListener;
import view.Login.LoginController;
import view.NewUser.NewUserView;
import app.MainApplication;

/**
 * @author Austin
 * A popout window for creating a new user 
 */
public class NewUserPopout extends PopoutWindow implements ILoginListener{

	public NewUserPopout(String content) {
		super();
		if(checkPopoutExists()) {
			// Do not create a new popout
			if(MainApplication.DEBUG_ON)
				System.err.println("New user popout already exists in MainApplication.popouts");
			return;
		}
		else {
			this.setPopoutType(PopoutType.NEWUSER);
			setupScene(content);
			setupStage();
			setupOnCloseOperation();
			// setup login event listener
			LoginController.addLoginListener(this);
			// Add the new popout to the main application's list of active popouts
			MainApplication.getPopouts().add(this);
			this.showNewPopout();
			if(MainApplication.DEBUG_ON)
				System.err.println("Showing new user popout");
		}
	}

	@Override
	public boolean checkPopoutExists() {
		for(IPopout popout : MainApplication.getPopouts()) {
			if(popout.getPopoutType() == PopoutType.NEWUSER)
				return true;
		}
		return false;
	}

	@Override
	public void showNewPopout() {
		this.getStage().setScene(this.getScene());
		this.getStage().show();
	}

	@Override
	public void setupScene(String content) {
		NewUserView newUserView = new NewUserView();
		newUserView.setupNewUserView();
		this.setScene(new Scene(newUserView.getNewUser()));
		newUserView.setPopout(this);
	}

	@Override
	public void userHasLoggedIn() {
		closePopout();
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