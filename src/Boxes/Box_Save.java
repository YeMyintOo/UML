package Boxes;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Box_Save extends Stage {
	private ProgressIndicator pi;
	public Box_Save(Stage owner) {
		super();
		initOwner(owner);
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		pi = new ProgressIndicator();
		
		BorderPane pane = new BorderPane();
		//pane.setStyle("-fx-background-color:rgb(230,230,250)");
		pane.setCenter(pi);

		// Set Style of Stage
		initStyle(StageStyle.TRANSPARENT);

		Scene scene = new Scene(pane,150, 150, Color.WHITE);
		setScene(scene);
	}
}
