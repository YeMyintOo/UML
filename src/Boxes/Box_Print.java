package Boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Box_Print extends Stage {
	// WorkSpace Box

	private TreeView<String> treeView;

	// Types
	private RadioButton pdf;
	private RadioButton data;

	private Button okB;
	private Button closeB;
	private Button resetB;

	@SuppressWarnings("unchecked")
	public Box_Print(Stage owner) {
		super();
		// setResizable(false);
		initModality(Modality.WINDOW_MODAL); // Prevent click parent stage
		initOwner(owner);
		setTitle("Print");
		BorderPane pane = new BorderPane();

		BorderPane info = new BorderPane();
		// Project list
		// Tree need to be dynamic
		BorderPane treeP = new BorderPane();
		treeP.setStyle("-fx-padding: 10");
		Label p = new Label("Project List");
		p.setStyle("-fx-text-fill:rgb(0,0,139);" + "-fx-font-size: 15px;");
		treeP.setTop(p);
		treeView = new TreeView<>();
		// Create the root TreeItem
		TreeItem<String> depts = new TreeItem<String>("Projects Name");
		treeView.setRoot(depts);

		// Create children TreeItemsfor the root
		TreeItem<String> isDept = new TreeItem<String>("File1");
		TreeItem<String> claimsDept = new TreeItem<String>("File2");
		TreeItem<String> underwritingDept = new TreeItem<String>("File3");
		// Add children to the root
		depts.getChildren().addAll(isDept, claimsDept, underwritingDept);
		///////////////////
		//ScrollPane sp = new ScrollPane();
		//sp.setContent(treeView);
		treeP.setBottom(treeView);

		// Select Print Type
		BorderPane typeP = new BorderPane();
		typeP.setStyle("-fx-padding: 10");
		VBox box = new VBox();
		ToggleGroup group = new ToggleGroup();
		pdf = new RadioButton("PDF");
		data = new RadioButton("Data");
		box.getChildren().addAll(pdf, data);
		typeP.setBottom(box);
		group.getToggles().addAll(pdf, data);

		info.setRight(typeP);
		info.setLeft(treeP);

		// Button Panel
		HBox btn = new HBox();
		okB = new Button("Finish");
		closeB = new Button("Cancel");
		resetB = new Button("Reset");
		btn.getChildren().addAll(resetB, okB, closeB);
		btn.setSpacing(4);
		btn.setStyle("-fx-padding:10 10 10 10;" + "-fx-background-color:rgb(220,220,220);" + "-fx-cursor: hand;");
		btn.setAlignment(Pos.BASELINE_RIGHT);

		pane.setBottom(btn);
		pane.setCenter(info);

		Scene scene = new Scene(pane, 400, 490, Color.WHITE);
		setScene(scene);
	}

}
