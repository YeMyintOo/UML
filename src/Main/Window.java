package Main;

import java.io.File;

import Boxes.Box_Exit;
import Boxes.Box_Feed;
import Boxes.Box_Guide;
import Boxes.Box_NFile;
import Boxes.Box_NPro;
import Boxes.Box_OFile;
import Boxes.Box_Print;
import Boxes.Box_Save;
import Boxes.Box_Version;
import Boxes.Box_WS;
import Calculate.ScreenDetail;
import Database.SystemHandler;
import Database.ToolHandler;
import Library.BuildCanvaXML;
import Library.LoadCanvaXML;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	protected MenuItem oFile;// Open File
	protected MenuItem save; // Save
	protected MenuItem exit; // Exit;

	protected MenuItem copy; // Copy
	protected MenuItem paste;// Paste
	protected MenuItem cut; // Cut
	protected MenuItem select; // Select
	protected MenuItem selectAll; // Select Alls
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
	protected WorkSpace2 workspace;

	// Dynamic Variables and loaded variables
	private String workspaceVar;
	private SystemHandler sysHandler;
	private ToolHandler toolHandler;

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
		oFile = new MenuItem("Open Diagram");
		save = new MenuItem("Save");
		exit = new MenuItem("Exit");
		file.getItems().addAll(nProject, oProject, cWorkSpace, nFile, oFile, save, exit);

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
		view.getItems().addAll(ruler);

		// Project Menu
		print = new MenuItem("Print");
		clean = new MenuItem("Clean");
		project.getItems().addAll(print, clean);

		// Help Menu
		guide = new MenuItem("User Guide");
		version = new MenuItem("Version");
		feedback = new MenuItem("FeedBack");
		help.getItems().addAll(guide, version, feedback);

		bar.getMenus().addAll(file, edit, view, project, help);
		root.setTop(bar);
		root.setCenter(tabPane);
		scene = new Scene(root, screen.getWidth(), screen.getHeight());

		// Design
		File f = new File("Resources/Css/MenuDesign.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setTitle("UML Deisgn Tool");
		stage.centerOnScreen();
		stage.show();

		if (sysHandler == null) {
			sysHandler = new SystemHandler();
		}
		if (toolHandler == null) {
			toolHandler = new ToolHandler();
		}

		workspaceVar = sysHandler.getDefaultWorkspace();

		// Actions///////////////////////////////////
		nProject.setOnAction(e -> {
			Box_NPro box = new Box_NPro(stage, workspaceVar);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			// Check Return value character
			OpenProjectList list = null;
			if (box.getValue().equals("finish")) {
				list = new OpenProjectList(workspaceVar);
				list.getStartB().setOnAction(ee -> {
					nFile.fire();
				});

				root.setCenter(list);
			} else {

			}
			root.setDisable(false);
		});
		oProject.setOnAction(e -> {
			OpenProjectList list = new OpenProjectList(workspaceVar);
			root.setCenter(list);
			list.getStartB().setOnAction(ee -> {
				nFile.fire();
			});
		});
		cWorkSpace.setOnAction(e -> {
			Box_WS box = new Box_WS(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			workspaceVar = box.getPath(); // Set workspace
			root.setDisable(false);
		});
		nFile.setOnAction(e -> {
			Box_NFile box = new Box_NFile(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			// Check Return value character
			if (box.getValue().equals("finish")) {
				root.setCenter(tabPane); // Reassign
				addWorkSpace(box.getFileName(), box.getType(), box.getPath());
			}
			root.setDisable(false);
		});
		oFile.setOnAction(e -> {
			Box_OFile box = new Box_OFile(stage);
			box.sizeToScene();
			box.setAlwaysOnTop(true);
			box.showAndWait();
			if (box.getValue().equals("finish")) {
				root.setCenter(tabPane);
				loadWorkSpace(box.getPath());
			}
			root.setDisable(false);
		});

		exit.setOnAction(e -> {
			Box_Exit box = new Box_Exit(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});

		save.setOnAction(e -> {
			Box_Save box = new Box_Save(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});
		/*
		 * mail.setOnAction(e -> { Box_Mail box = new Box_Mail(stage);
		 * box.sizeToScene(); root.setDisable(true); box.setAlwaysOnTop(true);
		 * box.showAndWait(); // Wait until close This Dialog
		 * root.setDisable(false); });
		 */
		print.setOnAction(e -> {
			Box_Print box = new Box_Print(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.requestFocus();
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});
		guide.setOnAction(e -> {
			Box_Guide box = new Box_Guide(stage);
			box.sizeToScene();
			box.show();
		});
		version.setOnAction(e -> {
			Box_Version box = new Box_Version(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.requestFocus();
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});
		feedback.setOnAction(e -> {
			Box_Feed box = new Box_Feed(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.requestFocus();
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});

		ruler.setOnAction(e -> {
			if (toolHandler.getGrid().equals("Hide")) {
				toolHandler.setGrid("Show");
			} else {
				toolHandler.setGrid("Hide");
			}
		});
		//////////////////////////////////////////

	}

	// Add Tab in TabPane (New WorkSpace)
	public void addWorkSpace(String name, int type, String path) {
		// Create File (Filename.xml)
		File file = new File(path + "\\" + name + ".xml");
		try {
			new BuildCanvaXML(file, name, type); // Build XML file
			Tab tab = new Tab();
			tab.setText(name);
			workspace = new WorkSpace2(type, file, scene, false);
			tab.setContent(workspace);
			tabPane.getTabs().add(tab);
		} catch (Exception e) {
		}

	}

	public void loadWorkSpace(String path) {
		// Read XML File
		File file = new File(path);
		int type = 0;
		try {
			LoadCanvaXML xml = new LoadCanvaXML(file);
			switch (xml.getDiagram()) {
			case "UseCase":
				type = 1;
				break;
			case "Object":
				type = 2;
				break;
			case "Sequence":
				type = 3;
				break;
			case "Collaboration":
				type = 4;
				break;
			case "Class":
				type = 5;
				break;
			case "StateChart":
				type = 6;
				break;
			case "Activity":
				type = 7;
				break;
			case "Component":
				type = 8;
				break;
			case "Deployment":
				type = 9;
				break;
			}
			Tab tab = new Tab();
			workspace = new WorkSpace2(type, file, scene, true);
			tab.setContent(workspace);
			tabPane.getTabs().add(tab);
			tab.setText(xml.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Main Function
	public static void main(String[] args) {
		Application.launch(args);
	}

}
