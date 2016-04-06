package Boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_OPro extends Stage{
	// New Project Box

	private Button okB;
	private Button closeB;
	private Button resetB;

	public Box_OPro(Stage owner) {
		super();
		setResizable(false);
		initModality(Modality.WINDOW_MODAL); //Prevent click parent stage
		initOwner(owner);
		setTitle("Open Project");
		BorderPane pane = new BorderPane();

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Finish");
		closeB = new Button("Cancel");
		resetB = new Button("Reset");
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 400, Color.WHITE);
		setScene(scene);
	}

}
