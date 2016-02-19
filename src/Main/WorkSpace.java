package Main;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class WorkSpace extends StackPane {
	private BorderPane work;
	private HBox tool;

	public WorkSpace() {
		// Drawing Area
		work = new BorderPane();
		work.setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		// Tool Box
		tool = new HBox();
		tool.setStyle("-fx-padding:20");
		tool.setAlignment(Pos.BOTTOM_CENTER);
		
		ToggleButton b1 = new ToggleButton("circle");
		ToggleButton b2 = new ToggleButton("rectangle");
		ToggleButton b3 = new ToggleButton("ellipse");
		ToggleButton b4 = new ToggleButton("Parabolar");
		ToggleGroup group = new ToggleGroup();
		b1.setToggleGroup(group);
		b2.setToggleGroup(group);
		b3.setToggleGroup(group);
		b4.setToggleGroup(group);
		
		tool.getChildren().addAll(b1, b2, b3, b4);
		getChildren().addAll(work, tool);
		
		b1.setOnAction(e->RenderTool());
	}
	
	public void RenderTool(){
		
	}
}
