package Main;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;

public class WorkSpace extends BorderPane {
	private BorderPane work; // Drawing Area
	private Paint paint; // Draw Diagrams;

	private ScrollPane sp;
	private BorderPane tool; // Drawing Tool
	private ColorPicker color; // Color Control
	private CheckBox grid; // Grid lines
	

	public WorkSpace() {
		// Drawing Area
		work = new BorderPane();
		work.setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		sp = new ScrollPane();
		work.setCenter(sp);
		paint=new Paint();
		sp.setContent(paint);
		// Tool Box

		tool = new BorderPane();
		tool.setStyle("-fx-padding:10 10 10 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		color = new ColorPicker();
		grid = new CheckBox("Grid Lines");
		tool.setLeft(grid);
		tool.setRight(color);

		RenderTool();

		setCenter(work);
		setBottom(tool);
		// b1.setOnAction(e -> RenderTool());

	}

	public void RenderTool() {
		HBox btnB = new HBox();
		btnB.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton b1 = new ToggleButton("circle");
		ToggleButton b2 = new ToggleButton("rectangle");
		ToggleButton b3 = new ToggleButton("ellipse");
		ToggleButton b4 = new ToggleButton("Parabolar");
		ToggleGroup group = new ToggleGroup();

		b1.setToggleGroup(group);
		b2.setToggleGroup(group);
		b3.setToggleGroup(group);
		b4.setToggleGroup(group);
		btnB.getChildren().addAll(b1, b2, b3, b4);
		tool.setCenter(btnB);
	}
}
