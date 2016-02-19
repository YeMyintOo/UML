package Main;

import java.io.File;

import Boxes.Box_NFile;
import Calculate.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Window extends Application {
	protected Stage stage;
	protected Scene scene;
	protected BorderPane root;

	protected MenuItem nProject; // New Project
	protected MenuItem oProject; // Open Project
	protected MenuItem cWorkSpace; // WorkSpace
	protected MenuItem nFile; // New File
	protected MenuItem save; // Save
	protected MenuItem exit; // Exit;

	protected MenuItem copy; // Copy
	protected MenuItem paste;// Paste
	protected MenuItem cut; // Cut
	protected MenuItem select; // Select
	protected MenuItem selectAll; // Select All
	protected MenuItem delete; // Delete

	protected MenuItem ruler; // Ruler
	protected MenuItem zoomin; // Zoom In
	protected MenuItem zoomout; // Zoom out
	protected MenuItem normal; // Normal

	protected MenuItem mail; // Mail
	protected MenuItem clean; // Clean
	protected MenuItem print; // Print

	protected MenuItem guide;// User Guide
	protected MenuItem version;// Version
	protected MenuItem feedback;// Feed Back

	// Screen Detail
	protected ScreenDetail screen;

	// WorkSpace
	protected TabPane tabPane; // Multiple WorkSpaces;
	protected WorkSpace workspace;

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		screen = new ScreenDetail();
		tabPane = new TabPane();

		root = new BorderPane();
		MenuBar bar = new MenuBar();
		Menu file = new Menu("File");
		Menu edit = new Menu("Edit");
		Menu view = new Menu("View");
		Menu project = new Menu("Project");
		Menu help = new Menu("Help");
		

		// File Menu
		nProject = new MenuItem("New Project");
		oProject = new MenuItem("Open Project");
		cWorkSpace = new MenuItem("WorkSpace");
		nFile = new MenuItem("New Diagram");
		save = new MenuItem("Save");
		exit = new MenuItem("Exit");
		file.getItems().addAll(nProject, oProject, cWorkSpace, nFile, save, exit);

		// Edit Menu
		copy = new MenuItem("Copy");
		paste = new MenuItem("Paste");
		cut = new MenuItem("Cut");
		select = new MenuItem("Select");
		selectAll = new MenuItem("Select All");
		delete = new MenuItem("Delete");
		edit.getItems().addAll(copy, paste, cut, select, selectAll, delete);

		// View Menu
		ruler = new MenuItem("GridLines");
		zoomin = new MenuItem("Zoom In");
		zoomout = new MenuItem("Zoom Out");
		normal = new MenuItem("Normal");
		view.getItems().addAll(ruler,zoomin,normal);

		// Project Menu
		mail = new MenuItem("Mail");
		print = new MenuItem("Print");
		clean = new MenuItem("Clean");
		project.getItems().addAll(mail, print, clean);

		// Help Menu
		guide = new MenuItem("User Guide");
		version = new MenuItem("Version");
		feedback = new MenuItem("FeedBack");
		help.getItems().addAll(guide, version, feedback);

		bar.getMenus().addAll(file, edit, view, project, help);
		root.setTop(bar);
		root.setCenter(tabPane);
		scene = new Scene(root, screen.getWidth(), screen.getHeight());

		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setTitle("UML Deisgn Tool");
		stage.centerOnScreen();
		stage.show();
		
		//Design
		File f = new File("Resources/Css/MenuDesign.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
		

		addWorkSpace("Circle");
		addWorkSpace("Rectangle");
		addWorkSpace("Polar");
		addWorkSpace("Rotation");

		nFile.setOnAction(e -> {
			Box_NFile box = new Box_NFile(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); //Wait until close This Dialog
			root.setDisable(false);
			

		});
	}

	// Add Tab in TabPane (New WorkSpace)
	public void addWorkSpace(String name) {
		Tab tab = new Tab();
		tab.setText(name);
		workspace = new WorkSpace();
		tab.setContent(workspace);
		tabPane.getTabs().add(tab);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
