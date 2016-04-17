package Boxes;

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
		if(systemHandler==null){
			systemHandler=new SystemHandler();
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
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setCenter(infoP);
		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 200, Color.WHITE);
		setScene(scene);

		// Actions
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
			setValue("finish");
			close();
		});
		wsB.setOnAction(e -> {
			
		});

		
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
