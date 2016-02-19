package Boxes;

import java.io.File;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Box_NFile extends Stage {
	// New File Dialog Box

	public Box_NFile(Stage owner) {
		super();
		initOwner(owner);
		setTitle("Choose New Diagram");

		BorderPane pane = new BorderPane();

		// Button bar
		HBox btn = new HBox();
		Button okB = new Button("Finish");
		Button closeB = new Button("Cancel");
		Button resetB = new Button("Reset");
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);
		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 400, Color.WHITE);
		File f = new File("Resources/Css/ButtonDesign.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		setScene(scene);
		
		closeB.setCancelButton(true);
		closeB.setOnAction(e->{
			close();
		});

	}
}
