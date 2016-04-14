package Main;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class OpenProjectList extends BorderPane {
	// For open Project Menu Action

	private TableView<String> table;

	// Action Bar
	private Button browseB;
	private Button selectB;
	private Button defaultB;

	// Start Button
	private Button startB; // Start drawing

	public OpenProjectList() {

		// Action Bar
		BorderPane tableP = new BorderPane();
		table = new TableView<String>();
		tableP.setStyle("-fx-padding:100 50 0 50;");
		loadColumns(); // Call Calumns
		tableP.setTop(table);

		// Start Button
		HBox sbox = new HBox();
		sbox.setAlignment(Pos.BOTTOM_RIGHT);
		startB = new Button("Start Drawing");
		startB.setStyle("-fx-background-color: rgb(176,196,222);" + "-fx-text-fill: rgb(255,255,255);"
				+ "  -fx-font-size: 20 pt;");
		sbox.setStyle("-fx-padding:20 50 50 50;");
		sbox.getChildren().add(startB);

		HBox box = new HBox();
		box.setSpacing(10);
		box.setAlignment(Pos.TOP_RIGHT);
		box.setStyle("-fx-padding:20 50 0 50;");
		browseB = new Button("Browse");
		selectB = new Button("Select");
		defaultB = new Button("Default");
		box.getChildren().addAll(browseB, selectB, defaultB);

		setTop(tableP);
		setCenter(box);
		setBottom(sbox);
		
		browseB.setOnAction(e -> {

		});
		selectB.setOnAction(e -> {

		});
		defaultB.setOnAction(e -> {

		});
		startB.setOnMouseEntered(e -> {
			startB.setStyle("-fx-background-color: rgb(176,196,222);" + "-fx-text-fill: rgb(0,0,255);"
					+ "-fx-font-size: 20 pt;");
		});
		startB.setOnMouseExited(e -> {
			startB.setStyle("-fx-background-color: rgb(176,196,222);" + "-fx-text-fill: rgb(255,255,255);"
					+ "  -fx-font-size: 20 pt;");
		});

	}

	// Create Columns
	@SuppressWarnings("unchecked")
	public void loadColumns() {
		TableColumn<String, ?> name = new TableColumn<String, Object>("Project Name");
		TableColumn<String, ?> workspace = new TableColumn<String, Object>("Workspace");
		TableColumn<String, ?> cdate = new TableColumn<String, Object>("Created Date"); // Created
																						// date
		System.out.println("Width" + table.getWidth());

		name.setMinWidth(400);
		workspace.setMinWidth(500);
		cdate.setMinWidth(400);

		// Add add

		table.getColumns().addAll(name, workspace, cdate);
	}
	public Button getStartB() {
		return startB;
	}
}
