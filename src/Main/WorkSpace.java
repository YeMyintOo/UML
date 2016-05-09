package Main;

import java.io.File;

import CanvaBoxs.*;
import Database.ToolHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class WorkSpace extends BorderPane {
	private BorderPane work; // Drawing Area
	private BorderPane tool; // Drawing Tool
	private ColorPicker color; // Color Control
	private CheckBox grid; // Grid lines

	private int type;
	private ToolHandler toolHandler; // To set and get Selected Tool;
	private Scene owner;
	public WorkSpace(int type, File file,Scene owner) {
		this.owner=owner;
		this.type = type;
		toolHandler = new ToolHandler();
		// Drawing Area
		work = new BorderPane();
		work.setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		// Tool Box

		tool = new BorderPane();
		tool.setStyle("-fx-padding:10 10 10 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		color = new ColorPicker();
		String colorS = toolHandler.getColor();
		Color defaultColor = Color.web(colorS); // Dynamic color from
												// ToolHander.xml
		color.setValue(defaultColor);

		grid = new CheckBox("Grid Lines");
		tool.setLeft(grid);
		tool.setRight(color);

		color.setOnAction(e -> {
			toolHandler.setColor(color.getValue().toString());
		});
		grid.setOnAction(e -> {
			if (grid.isSelected()) {
				toolHandler.setGrid("Show");
			} else {
				toolHandler.setGrid("Hide");
			}
		});

		RenderTool(); // Render Tool base on select

		setCenter(work);
		setBottom(tool);

	}

	public void RenderTool() {

		switch (type) {
		case 1: // Use_Case
			work.setCenter(new UseCaseCanvaBox2(owner));
			tool.setCenter(UseCaseToolBox());
			break;
		case 2:// Object
			work.setCenter(new ObjectCanvaBox());
			tool.setCenter(ObjectToolBox());
			break;
		case 3:// Sequence
			work.setCenter(new SequenceCanvaBox());
			tool.setCenter(SequenceToolBox());
			break;
		case 4:// Collaboration
			work.setCenter(new CollaborationCanvaBox());
			tool.setCenter(CollaborationToolBox());
			break;
		case 5:// Class
			work.setCenter(new ClassCanvaBox());
			tool.setCenter(ClassToolBox());
			break;
		case 6:// State Chart
			work.setCenter(new StatechartCanvaBox());
			tool.setCenter(StateChartToolBox());
			break;
		case 7:// Activity
			work.setCenter(new ActivityCanvaBox());
			tool.setCenter(ActivityToolBox());
			break;
		case 8:// Component
			work.setCenter(new ComponentCanvaBox());
			tool.setCenter(ComponentToolBox());
			break;
		case 9:// Deployment
			work.setCenter(new DeploymentCanvaBox());
			tool.setCenter(DeploymentToolBox());
			break;
		}
	}

	public HBox UseCaseToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton actor = new ToggleButton("Actor"); // Actor
		ToggleButton action = new ToggleButton("Action"); // Action
		ToggleButton rec = new ToggleButton("Box"); // Rectangle
		ToggleButton line = new ToggleButton("Process"); // Process
		ToggleButton extend = new ToggleButton("Extend"); // Extend
		ToggleButton include = new ToggleButton("Include"); // Include
		ToggleButton type = new ToggleButton("Type Of"); // Type of
		ToggleGroup group = new ToggleGroup();
		actor.setToggleGroup(group);
		action.setToggleGroup(group);
		rec.setToggleGroup(group);
		line.setToggleGroup(group);
		extend.setToggleGroup(group);
		include.setToggleGroup(group);
		type.setToggleGroup(group);

		actor.setOnAction(e -> {
			toolHandler.setTool("UseCase_Actor");
		});
		action.setOnAction(e -> {
			toolHandler.setTool("UseCase_Action");
		});
		rec.setOnAction(e -> {
			toolHandler.setTool("UseCase_Box");
		});
		line.setOnAction(e -> {
			toolHandler.setTool("UseCase_Process");
		});
		extend.setOnAction(e -> {
			toolHandler.setTool("UseCase_Extend");
		});
		include.setOnAction(e -> {
			toolHandler.setTool("UseCase_Include");
		});
		type.setOnAction(e -> {
			toolHandler.setTool("UseCase_Type");
		});
		btnP.getChildren().addAll(actor, action, rec, line, extend, include, type);
		return btnP;
	}

	public HBox ObjectToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Object"); // Object Box
		ToggleButton link = new ToggleButton("Link"); // Link

		ToggleGroup group = new ToggleGroup();
		object.setToggleGroup(group);
		link.setToggleGroup(group);

		object.setOnAction(e -> {
			toolHandler.setTool("ObjectD_Object");
		});
		link.setOnAction(e -> {
			toolHandler.setTool("ObjectD_link");
		});

		btnP.getChildren().addAll(object, link);
		return btnP;
	}

	public HBox SequenceToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton role = new ToggleButton("Classifier Role"); // Classifier_Role
		ToggleButton process = new ToggleButton("Activation"); // Activation(Normal)
		ToggleButton processC = new ToggleButton("Activation (New Object)"); // Activation
		ToggleButton processD = new ToggleButton("Activation (Object Destroy)");
		ToggleButton processS = new ToggleButton("Activation (Self Loop)");

		ToggleGroup group = new ToggleGroup();
		role.setToggleGroup(group);
		process.setToggleGroup(group);
		processC.setToggleGroup(group);
		processD.setToggleGroup(group);
		processS.setToggleGroup(group);

		role.setOnAction(e -> {
			toolHandler.setTool("Sequence_Role");
		});
		process.setOnAction(e -> {
			toolHandler.setTool("Sequence_ANormal");
		});
		processC.setOnAction(e -> {
			toolHandler.setTool("Sequence_ANObject");
		});
		processD.setOnAction(e -> {
			toolHandler.setTool("Sequence_ADObject");
		});
		processS.setOnAction(e -> {
			toolHandler.setTool("Sequence_ASLoop");
		});

		btnP.getChildren().addAll(role, process, processC, processD, processS);
		return btnP;
	}

	public HBox CollaborationToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Object");
		ToggleButton link = new ToggleButton("Link");

		ToggleGroup group = new ToggleGroup();
		object.setToggleGroup(group);
		link.setToggleGroup(group);

		object.setOnAction(e -> {
			toolHandler.setTool("Collaboration_Object");
		});
		link.setOnAction(e -> {
			toolHandler.setTool("Collaboration_Link");
		});

		btnP.getChildren().addAll(object, link);
		return btnP;
	}

	public HBox ClassToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton classD = new ToggleButton("Class");
		ToggleButton asso = new ToggleButton("Association");

		ToggleGroup group = new ToggleGroup();
		classD.setToggleGroup(group);
		asso.setToggleGroup(group);

		classD.setOnAction(e -> {
			toolHandler.setTool("Class_Class");
		});
		asso.setOnAction(e -> {
			toolHandler.setTool("Class_Association");
		});

		btnP.getChildren().addAll(classD, asso);
		return btnP;
	}

	public HBox StateChartToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton init = new ToggleButton("Initial State");
		ToggleButton fin = new ToggleButton("Final State");
		ToggleButton state = new ToggleButton("State");
		ToggleButton subState = new ToggleButton("Substate");
		ToggleButton hisState = new ToggleButton("History State");
		ToggleButton tran = new ToggleButton("Transition");

		ToggleGroup group = new ToggleGroup();
		init.setToggleGroup(group);
		fin.setToggleGroup(group);
		state.setToggleGroup(group);
		tran.setToggleGroup(group);

		init.setOnAction(e -> {
			toolHandler.setTool("Statechart_InitState");
		});
		fin.setOnAction(e -> {
			toolHandler.setTool("Statechart_FinalState");
		});
		subState.setOnAction(e -> {
			toolHandler.setTool("Statechart_Substate");
		});
		hisState.setOnAction(e -> {
			toolHandler.setTool("Statechart_HistoryState");
		});
		state.setOnAction(e -> {
			toolHandler.setTool("Statechart_State");
		});
		tran.setOnAction(e -> {
			toolHandler.setTool("Statechart_Transition");
		});

		btnP.getChildren().addAll(init, fin, state, subState, hisState, tran);
		return btnP;
	}

	public HBox ActivityToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Activity Tool");

		ToggleGroup group = new ToggleGroup();

		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox ComponentToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton component = new ToggleButton("Component");
		ToggleButton dependence = new ToggleButton("Dependence");
		ToggleButton sart = new ToggleButton("Source Artefact");
		ToggleButton scomp = new ToggleButton("Source Component");
		ToggleButton pack = new ToggleButton("Package");
		ToggleButton lib = new ToggleButton("Library");

		ToggleGroup group = new ToggleGroup();
		component.setToggleGroup(group);
		dependence.setToggleGroup(group);
		sart.setToggleGroup(group);
		scomp.setToggleGroup(group);
		pack.setToggleGroup(group);
		lib.setToggleGroup(group);

		component.setOnAction(e -> {
			toolHandler.setTool("Component_Component");
		});
		dependence.setOnAction(e -> {
			toolHandler.setTool("Component_Dependence");
		});
		sart.setOnAction(e -> {
			toolHandler.setTool("Component_SArtefact");
		});
		scomp.setOnAction(e -> {
			toolHandler.setTool("Component_SComponent");
		});
		pack.setOnAction(e -> {
			toolHandler.setTool("Component_Package");
		});
		lib.setOnAction(e -> {
			toolHandler.setTool("Component_Library");
		});

		btnP.getChildren().addAll(component, dependence, sart, scomp, pack, lib);
		return btnP;
	}

	public HBox DeploymentToolBox() {
		HBox btnP = new HBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton hardware = new ToggleButton("Hardware");
		ToggleButton software = new ToggleButton("Software");
		ToggleButton database = new ToggleButton("Database");
		ToggleButton protocol = new ToggleButton("Protocol");
		ToggleButton file = new ToggleButton("File");
		ToggleButton component = new ToggleButton("Component");
		ToggleButton system = new ToggleButton("System");

		ToggleGroup group = new ToggleGroup();
		hardware.setToggleGroup(group);
		software.setToggleGroup(group);
		database.setToggleGroup(group);
		protocol.setToggleGroup(group);
		file.setToggleGroup(group);
		component.setToggleGroup(group);
		system.setToggleGroup(group);

		hardware.setOnAction(e -> {
			toolHandler.setTool("Deployment_Hardware");
		});
		software.setOnAction(e -> {
			toolHandler.setTool("Deployment_Software");
		});
		database.setOnAction(e -> {
			toolHandler.setTool("Deployment_Database");
		});
		protocol.setOnAction(e -> {
			toolHandler.setTool("Deployment_Protocol");
		});
		file.setOnAction(e -> {
			toolHandler.setTool("Deployment_File");
		});
		component.setOnAction(e -> {
			toolHandler.setTool("Deployment_Component");
		});
		system.setOnAction(e -> {
			toolHandler.setTool("Deployment_System");
		});

		btnP.getChildren().addAll(hardware, software, database, protocol, file, component, system);
		return btnP;
	}
}
