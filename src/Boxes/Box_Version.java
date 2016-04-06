package Boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_Version extends Stage{
	// WorkSpace Box

	private Button okB;
	
	public Box_Version(Stage owner) {
		super();
		initModality(Modality.WINDOW_MODAL); //Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		setTitle("Software Information");
		BorderPane pane = new BorderPane();

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Close");
		btn.getChildren().addAll(okB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 300, Color.WHITE);
		setScene(scene);
	}





}
