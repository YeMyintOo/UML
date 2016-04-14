package Main;

import java.io.File;

import Boxes.Box_Exit;
import Boxes.Box_Feed;
import Boxes.Box_Guide;
import Boxes.Box_Mail;
import Boxes.Box_NFile;
import Boxes.Box_NPro;
import Boxes.Box_Print;
import Boxes.Box_Save;
import Boxes.Box_Version;
import Boxes.Box_WS;
import Calculate.ScreenDetail;
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
	
	
	//Dynamic Variables and loaded variables
	private String workspaceVar;
	
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
		view.getItems().addAll(ruler, zoomin, zoomout, normal);

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

		// Design
		File f = new File("Resources/Css/MenuDesign.css");
		scene.getStylesheets().clear();
		scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

		// Actions///////////////////////////////////
		nProject.setOnAction(e -> {
			Box_NPro box = new Box_NPro(stage,workspaceVar);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			// Check Return value character
			OpenProjectList list = null;
			if (box.getValue().equals("finish")) {
				list=new OpenProjectList();
				root.setCenter(list);
			} else if (box.getValue().equals("close")) {

			}
			list.getStartB().setOnAction(ee -> {
				nFile.fire();
			});
			root.setDisable(false);
		});
		oProject.setOnAction(e -> {
			OpenProjectList list = new OpenProjectList();
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
			workspaceVar=box.getPath(); //Set workspace 
			root.setDisable(false);
		});
		nFile.setOnAction(e -> {
			Box_NFile box = new Box_NFile(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			// Check Return value character
			if(box.getValue().equals("finish")){
				root.setCenter(tabPane); //Reassign
				addWorkSpace(box.getFileName(), box.getType());
			}else if(box.getValue().equals("close")){
				
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
		
		save.setOnAction(e->{
			Box_Save box = new Box_Save(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});

		mail.setOnAction(e -> {
			Box_Mail box = new Box_Mail(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});
		print.setOnAction(e -> {
			Box_Print box = new Box_Print(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.requestFocus();
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});
		guide.setOnAction(e->{
			Box_Guide box = new Box_Guide(stage);
			box.sizeToScene();
			box.show();
		});
		version.setOnAction(e->{
			Box_Version box = new Box_Version(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.requestFocus();
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});
		feedback.setOnAction(e->{
			Box_Feed box=new Box_Feed(stage);
			box.sizeToScene();
			root.setDisable(true);
			box.setAlwaysOnTop(true);
			box.requestFocus();
			box.showAndWait(); // Wait until close This Dialog
			root.setDisable(false);
		});
		//////////////////////////////////////////
	}

	// Add Tab in TabPane (New WorkSpace)
	public void addWorkSpace(String name, int type) {
		Tab tab = new Tab();
		tab.setText(name);
		workspace = new WorkSpace(type);
		tab.setContent(workspace);
		tabPane.getTabs().add(tab);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
