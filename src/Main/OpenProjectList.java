package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import Database.SystemHandler;
import Library.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class OpenProjectList extends BorderPane {
	// For open Project Menu Action

	private TableView<Project> table;
	private ObservableList<Project> data;

	// Action Bar
	private Button selectB;
	private Button defaultB;

	// Start Button
	private Button startB; // Start drawing
	private String workspacePath; // Workspace Path
	private String project;// Selected Project Path
	private SystemHandler sysHandler;

	public OpenProjectList(String workspacePath) {
		this.workspacePath = workspacePath;
		// Action Bar
		BorderPane tableP = new BorderPane();
		table = new TableView<>();

		table.setPlaceholder(new Label("No Project exist in this Workspace"));
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
		selectB = new Button("Select");
		defaultB = new Button("Default");
		box.getChildren().addAll(selectB, defaultB);

		setTop(tableP);
		setCenter(box);
		setBottom(sbox);

		if (sysHandler == null) {
			sysHandler = new SystemHandler();
		}
		selectB.setOnAction(e -> {
			Project project = table.getSelectionModel().getSelectedItem();
			sysHandler.setSelectProject(project.getWspace());
		});
		defaultB.setOnAction(e -> {
			Project project = table.getSelectionModel().getSelectedItem();
			sysHandler.setDefaultProject(project.getWspace());
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
		TableColumn name = new TableColumn("Project Name");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn cdate = new TableColumn("Created Date");
		cdate.setCellValueFactory(new PropertyValueFactory<>("cdate"));
		TableColumn wspace = new TableColumn("WorkSpace");
		wspace.setCellValueFactory(new PropertyValueFactory<>("wspace"));
		// TableColumn mdate = new TableColumn("Modified Name");
		// mdate.setCellValueFactory(new PropertyValueFactory<>("mdate"));

		System.out.println("Width" + table.getWidth());

		name.setMinWidth(400);
		cdate.setMinWidth(400);
		wspace.setMinWidth(400);

		// Add add
		data = FXCollections.observableArrayList();
		table.setItems(data);
		// table.getColumns().addAll(name, cdate, mdate);
		table.getColumns().addAll(name, cdate, wspace);

		// Loop
		File path = new File(workspacePath);
		File[] directories = path.listFiles(File::isDirectory);
		for (int i = 0; i < directories.length; i++) {
			Path p = directories[i].toPath();
			try {
				BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
				Date create = new Date(view.creationTime().toMillis());
				Date modi = new Date(view.lastModifiedTime().toMillis());
				data.add(new Project(directories[i].getName(), create.toString(), modi.toString(),
						directories[i].toString()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public Button getStartB() {
		return startB;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}
}
