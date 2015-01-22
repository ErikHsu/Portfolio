package app.popout;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

public abstract class BreadcrumbPopout extends PopoutWindow {
	
	private static final double arrowWidth = 10;
	private static final double arrowHeight = 30; 
	
	private ToolBar breadCrumbBar;
	
	// all the views in this stage accessible by the bread crumb bar
	private ArrayList<Scene> views = new ArrayList<Scene>();
	
	public BreadcrumbPopout() {
		super();
	}
	
	public abstract void setupBreadCrumbBar(ToolBar breadCrumbBar, String content, Stage stage);
	
	// draw one breadcrumb button
	public void drawBreadCrumb(Button b) {
		Path path = new Path();

		MoveTo v0 = new MoveTo(0, 0);

		HLineTo v1 = new HLineTo();
		v1.xProperty().bind(b.widthProperty().subtract(arrowWidth));

		LineTo v2 = new LineTo();
		v2.xProperty().bind(v1.xProperty().add(arrowWidth));
		v2.setY(arrowHeight / 2.0);

		LineTo v3 = new LineTo();
		v3.xProperty().bind(v1.xProperty());
		v3.setY(arrowHeight);

		HLineTo v4 = new HLineTo(0);

		LineTo v5 = new LineTo(arrowWidth, arrowHeight / 2.0);

		ClosePath v6 = new ClosePath();
		
		path.getElements().addAll(v0, v1, v2, v3, v4, v5, v6);
		
		path.setFill(Color.BLACK);
		
		b.setClip(path);
	}
	
	public void setCrumbStyle(Button b) {
		b.setStyle("-fx-effect: dropshadow( one-pass-box , rgba(0,0,0,0.9) , 1, 0.0 , 0 , 1 ); -fx-background-color: #090a0c,        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),        linear-gradient(#20262b, #191d22),        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0)); -fx-background-radius: 0,0,0,0; -fx-background-insets: 0,1,2,0; -fx-text-fill: white; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 ); -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-font-size: 11px; -fx-padding: 5 10 5 10;");
	}
	
	public double getArrowWidth() {
		return arrowWidth;
	}

	public ToolBar getBreadCrumbBar() {
		return breadCrumbBar;
	}

	public void setBreadCrumbBar(ToolBar breadCrumbBar) {
		this.breadCrumbBar = breadCrumbBar;
	}

	public ArrayList<Scene> getViews() {
		return views;
	}

	public void setViews(ArrayList<Scene> views) {
		this.views = views;
	}
}
