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
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_WS extends Stage {
	// WorkSpace Box
	private Label pathL;
	private TextField pathF;
	private Button browseB;

	private Button okB;
	private Button closeB;
	private Button resetB;

	// Selected workspace
	private String path;
	private SystemHandler systemHandler;

	public Box_WS(Stage owner) {
		super();
		setResizable(false);
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setTitle("Change Workspace");
		// Default path value
		path = "";

		BorderPane pane = new BorderPane();

		// WorkSpace
		GridPane pathP = new GridPane();
		pathP.setStyle("-fx-padding: 10;");
		pathP.setHgap(10);
		pathL = new Label("Workspace");
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
			// FileChooser wsChooser = new FileChooser();
			DirectoryChooser wsChooser = new DirectoryChooser();
			wsChooser.setTitle("Select New Workspace");
			File selectedDirectory = wsChooser.showDialog(this);
			pathF.setText(selectedDirectory.toString());
			if(systemHandler==null){
				systemHandler=new SystemHandler();
			}
			systemHandler.setDefaultWorkspace(selectedDirectory.toString());
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
