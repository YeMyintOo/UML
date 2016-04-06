package Boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_Exit extends Stage {
	// Exit Box

	private Button yesB;
	private Button noB;

	public Box_Exit(Stage owner) {
		super();
		initModality(Modality.WINDOW_MODAL); //Prevent click parent stage
		initOwner(owner);
		setTitle("Sure?");
		BorderPane pane = new BorderPane();

		// Button Panel
		HBox btn = new HBox();
		yesB = new Button("Yes");
		noB = new Button("No");

		btn.getChildren().addAll(yesB, noB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;"
					+"-fx-text-fill:rgb(0,0,139);"
				);
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 200, Color.WHITE);
		setScene(scene);
	}

}
