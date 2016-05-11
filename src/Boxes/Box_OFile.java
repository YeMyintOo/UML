package Boxes;

import java.io.File;

import Database.SystemHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_OFile extends Stage {
	// WorkSpace Box
	private Label pathL;
	private TextField pathF;
	private Button browseB;

	private Button okB;
	private Button closeB;
	private Button resetB;
	
	private String path;

	public Box_OFile(Stage owner) {
		super();
		setResizable(false);
		initModality(Modality.WINDOW_MODAL);
		initOwner(owner);
		setTitle("Open UML Diagram");

		BorderPane pane = new BorderPane();

		// WorkSpace
		GridPane pathP = new GridPane();
		pathP.setStyle("-fx-padding: 10;");
		pathP.setHgap(10);
		pathL = new Label("Open Diagram");
		pathL.setStyle("-fx-padding: 30 30 10 0;");
		pathF = new TextField();
		browseB = new Button("Browse");
		pathP.addRow(0, pathL);
		pathP.addRow(1, pathF, browseB);

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Finish");
		closeB = new Button("Cancel");
		resetB = new Button("Reset");
		// Style Button
		File f = new File("Resources/Css/ButtonDesign.css");
		okB.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		closeB.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		resetB.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setCenter(pathP);
		pane.setBottom(btn);

		Scene scene = new Scene(pane, 300, 200, Color.WHITE);
		setScene(scene);

		okB.setOnAction(e -> {
			setPath(pathF.getText().trim());
			close();
		});
		closeB.setOnAction(e -> {
			setPath("none");
			close();
		});
		resetB.setOnAction(e -> {
			pathF.setText("");
		});
		browseB.setOnAction(e -> {
			FileChooser wsChooser = new FileChooser();
			wsChooser.setTitle("Select New Workspace");
			File selectedDirectory = wsChooser.showOpenDialog(owner);
			pathF.setText(selectedDirectory.toString());
		});
	}

	// Get
	public String getPath() {
		return path;
	}

	// Set
	public void setPath(String path) {
		this.path = path;
	}

}
