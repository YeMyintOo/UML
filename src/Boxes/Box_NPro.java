package Boxes;

import java.io.File;
import java.io.IOException;

import Database.SystemHandler;
import Library.BuildPropertiesXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_NPro extends Stage {
	// New Project Box
	private Label nameL;
	private TextField nameF;
	private Label wsL;
	private TextField wsF;
	private Button wsB;

	private Button okB;
	private Button closeB;
	private Button resetB;

	// Return Value
	private String value;
	private SystemHandler systemHandler;

	public Box_NPro(Stage owner, String workspacePath) {
		super();
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		if (systemHandler == null) {
			systemHandler = new SystemHandler();
		}

		value = "default";
		setTitle("New Project");
		BorderPane pane = new BorderPane();

		BorderPane infoP = new BorderPane();
		// Name
		GridPane nameP = new GridPane();
		nameP.setStyle("-fx-padding: 10;");
		nameL = new Label("Name");
		nameL.setStyle("-fx-padding: 5 20 0 0;");
		nameF = new TextField();
		nameP.addRow(0, nameL, nameF);

		// WorkSpace
		GridPane wsP = new GridPane();
		wsP.setStyle("-fx-padding: 10;");
		wsP.setHgap(10);
		wsL = new Label("Workspace");
		wsL.setStyle("-fx-padding: 5 0 5 0;");
		wsF = new TextField();
		wsF.setPrefWidth(200);
		// Default workspace
		wsF.setText(systemHandler.getDefaultWorkspace());
		wsB = new Button("Change");

		wsP.addRow(0, wsL);
		wsP.addRow(1, wsF, wsB);

		infoP.setTop(nameP);
		infoP.setCenter(wsP);

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Finish");
		closeB = new Button("Cancel");
		resetB = new Button("Reset");
		File f = new File("Resources/Css/ButtonDesign.css");
		okB.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		closeB.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		resetB.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setCenter(infoP);
		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 200, Color.WHITE);
		setScene(scene);

		// Actions
		wsB.setOnAction(e -> {
			System.out.println("Workspace Change");
			Box_WS box = new Box_WS(this);
			box.sizeToScene();
			box.setAlwaysOnTop(true);
			box.showAndWait();
			wsF.setText(box.getPath());
		});

		resetB.setOnAction(e -> {
			nameF.setText("");
			wsF.setText("");
		});
		closeB.setOnAction(e -> {
			setValue("close");
			close();
		});
		okB.setOnAction(e -> {
			// Create Project
			// Create Folder
			if (!nameF.getText().equals("")) {
				File file = new File(systemHandler.getDefaultWorkspace() + "\\" + nameF.getText());
				try {
					if (!file.exists()) {
						file.mkdirs(); // Make directory
						System.out.println("Project Folder is created");
						createProjectProperties(file.getAbsolutePath());
						// Make default Project
						systemHandler.setDefaultProject(file.getAbsolutePath());
						System.out.println("Make Default Project");
						setValue("finish");
						close();
					} else {
						// File exist
						Alert alert = new Alert(AlertType.ERROR);
						alert.initModality(Modality.APPLICATION_MODAL);
						alert.initOwner(this);
						alert.setTitle("Error");
						alert.setHeaderText("File alwary exits");
						alert.setContentText("Choose different project name");
						alert.showAndWait();
					}
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}

		});
	}

	public void createProjectProperties(String path) throws IOException, InterruptedException {
		File file = new File(path + "\\Properties.xml");
		// file.createNewFile();
		new BuildPropertiesXML(file);
		System.out.println("Create Project Properties File");
		// Hidden Properties File
		// Window Only
		String spacing = "\"" + file.getAbsolutePath() + "\"";
		Process p = Runtime.getRuntime().exec("cmd.exe /c attrib +h " + spacing);
		p.waitFor();
		System.out.println("Project Properties File is hidden");

	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
