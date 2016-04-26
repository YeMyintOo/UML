package Boxes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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

		ImageView fbv = new ImageView();
		ImageView gov = new ImageView();
		ImageView liv = new ImageView();

		fbv.setFitHeight(50);
		fbv.setFitWidth(50);

		gov.setFitHeight(50);
		gov.setFitWidth(50);

		liv.setFitHeight(50);
		liv.setFitWidth(50);

		Image fbimg, goimg, limg;
		try {
			fbimg = new Image(new FileInputStream("Resources/Image/fb.png"));
			goimg = new Image(new FileInputStream("Resources/Image/google.png"));
			limg = new Image(new FileInputStream("Resources/Image/link.png"));
			fbv.setImage(fbimg);
			gov.setImage(goimg);
			liv.setImage(limg);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fb = new Circle(20);
		fb.centerXProperty().bind(fbv.xProperty().add(25));
		fb.centerYProperty().bind(fbv.yProperty().add(25));
		fbv.setClip(fb);

		gmail = new Circle(20);
		gmail.centerXProperty().bind(gov.xProperty().add(25));
		gmail.centerYProperty().bind(gov.yProperty().add(25));
		gov.setClip(gmail);

		linkin = new Circle(20);
		linkin.centerXProperty().bind(liv.xProperty().add(25));
		linkin.centerYProperty().bind(liv.yProperty().add(25));
		liv.setClip(linkin);
		
		
		hbox.getChildren().addAll(fbv, gov, liv);

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
