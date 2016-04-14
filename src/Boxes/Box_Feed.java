package Boxes;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_Feed extends Stage {
	// WorkSpace Box

	private Label msgL;
	private TextArea msgA;

	// Type
	private RadioButton info;
	private RadioButton bug;
	private RadioButton req;

	private Circle fb;
	private Circle gmail;
	private Circle linkin;

	private Button okB;
	private Button closeB;
	private Button resetB;

	public Box_Feed(Stage owner) {
		super();
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setResizable(false);
		setTitle("Feedback");
		BorderPane pane = new BorderPane();

		BorderPane infoP = new BorderPane();
		infoP.setStyle("-fx-padding:10;");
		// Message
		BorderPane msgP = new BorderPane();
		msgL = new Label("Messsage");
		msgL.setStyle("-fx-text-fill:rgb(0,0,139);" + "-fx-font-size: 13px;" + "-fx-padding:0 0 10 0");
		msgA = new TextArea();
		msgA.setPrefRowCount(6);
		msgP.setTop(msgL);
		msgP.setBottom(msgA);
		// Type
		GridPane typeP = new GridPane();
		typeP.setVgap(10);
		ToggleGroup group = new ToggleGroup();
		info = new RadioButton("Information");
		bug = new RadioButton("Bugs");
		req = new RadioButton("Requirements");
		group.getToggles().addAll(info, bug, req);
		typeP.addRow(0, info);
		typeP.addRow(1, bug);
		typeP.addRow(2, req);

		// Methods
		BorderPane social = new BorderPane();
		HBox hbox = new HBox();
		hbox.setSpacing(20);
		hbox.setStyle("-fx-padding: 10");

		
		fb = new Circle(20);
		gmail = new Circle(20);
		linkin = new Circle(20);
		hbox.getChildren().addAll(fb, gmail, linkin);

		social.setLeft(typeP);
		social.setRight(hbox);

		infoP.setTop(msgP);
		infoP.setBottom(social);

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Send");
		closeB = new Button("Cancel");
		resetB = new Button("Reset");
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setCenter(infoP);
		pane.setBottom(btn);

		Scene scene = new Scene(pane, 400, 300, Color.WHITE);
		setScene(scene);
	}

}
