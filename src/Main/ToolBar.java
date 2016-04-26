package Main;

import Database.ToolHandler;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ToolBar extends VBox {
	private ToolHandler toolHandler;
	private ColorPicker color;

	public ToolBar(int type) {
		toolHandler = new ToolHandler();
		setStyle("-fx-padding:10 10 10 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: rgb(78,238,148);");
		setVisible(false);
		setPrefHeight(0);
		setMinHeight(0);

		color = new ColorPicker();
		Color defaultColor = Color.web(toolHandler.getColor()); 
		color.setValue(defaultColor);
		color.setOnAction(e -> {
			toolHandler.setColor(color.getValue().toString());
		});
		
		switch (type) {

		case 1: // Use_Case
			getChildren().addAll(UseCaseToolBox());
			break;
		case 2:// Object
			getChildren().addAll(ObjectToolBox());
			break;
		case 3:// Sequence
			getChildren().addAll(SequenceToolBox());
			break;
		case 4:// Collaboration
			getChildren().addAll(CollaborationToolBox());
			break;
		case 5:// Class
			getChildren().addAll(ClassToolBox());
			break;
		case 6:// State Chart
			getChildren().addAll(StateChartToolBox());
			break;
		case 7:// Activity
			getChildren().addAll(ActivityToolBox());
			break;
		case 8:// Component
			getChildren().addAll(ComponentToolBox());
			break;
		case 9:// Deployment
			getChildren().addAll(DeploymentToolBox());
			break;
		}
		
		getChildren().add(color);

	}

	public void slideHide() {

		// Create an animation to hide the panel.
		final Animation hidePanel = new Transition() {
			{
				setCycleDuration(Duration.millis(250));
			}

			@Override
			protected void interpolate(double frac) {
				final double size = 200 * (1.0 - frac);
				setPrefHeight(size);
			}
		};

		hidePanel.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				setVisible(false);
			}
		});
		if (isVisible()) {
			hidePanel.play();
		}

	}

	public void slideShow() {
		// Create an animation to show the panel.
		final Animation showPanel = new Transition() {
			{
				setCycleDuration(Duration.millis(250));
			}

			@Override
			protected void interpolate(double frac) {
				final double size = 200 * frac;
				setPrefHeight(size);
			}
		};

		showPanel.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
			}
		});
		if (!isVisible()) {
			setVisible(true);
			showPanel.play();
		}
	}

	public VBox UseCaseToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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

	public VBox ObjectToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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

	public VBox SequenceToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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

	public VBox CollaborationToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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

	public VBox ClassToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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

	public VBox StateChartToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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

	public VBox ActivityToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
		ToggleButton object = new ToggleButton("Activity Tool");

		ToggleGroup group = new ToggleGroup();

		btnP.getChildren().addAll(object);
		return btnP;
	}

	public VBox ComponentToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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

	public VBox DeploymentToolBox() {
		VBox btnP = new VBox();
		btnP.setSpacing(10);
		btnP.setAlignment(Pos.CENTER_LEFT);
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
