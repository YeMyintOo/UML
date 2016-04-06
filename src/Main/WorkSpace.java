package Main;

import Database.ToolHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;

public class WorkSpace extends BorderPane {
	private BorderPane work; // Drawing Area
	private BorderPane tool; // Drawing Tool
	private ColorPicker color; // Color Control
	private CheckBox grid; // Grid lines

	private int type;
	private CanvaBox canva;
	private ToolHandler toolHandler; // To set and get Selected Tool;
	
	public WorkSpace(int type) {
		this.type = type;
		toolHandler=new ToolHandler();
		// Drawing Area
		work = new BorderPane();
		work.setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
		canva=new CanvaBox();
		work.setCenter(canva);
		// Tool Box

		tool = new BorderPane();
		tool.setStyle("-fx-padding:10 10 10 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		color = new ColorPicker();
		grid = new CheckBox("Grid Lines");
		tool.setLeft(grid);
		tool.setRight(color);
		
		
		color.setOnAction(e->{
			toolHandler.setColor(color.getValue().toString());
		});
		grid.setOnAction(e->{
			if(grid.isSelected()){
				toolHandler.setGrid("Show");
			}else{
				toolHandler.setGrid("Hide");
			}
		});
		
		RenderTool(); //Render Tool base on select
		
		
		setCenter(work);
		setBottom(tool);
		

	}

	public void RenderTool() {
		
		switch (type) {
		case 1:
			tool.setCenter(UseCaseToolBox());
			break;
		case 2:
			tool.setCenter(ObjectToolBox());
			break;
		case 3:
			tool.setCenter(SequenceToolBox());
			break;
		case 4:
			tool.setCenter(CollaborationToolBox());
			break;
		case 5:
			tool.setCenter(ClassToolBox());
			break;
		case 6:
			tool.setCenter(StateChartToolBox());
			break;
		case 7:
			tool.setCenter(ActivityToolBox());
			break;
		case 8:
			tool.setCenter(ComponentToolBox());
			break;
		case 9:
			tool.setCenter(DeploymentToolBox());
			break;
		}
	}

	public HBox UseCaseToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton actor = new ToggleButton("Actor");
		ToggleButton action = new ToggleButton("Action");
		ToggleButton rec = new ToggleButton("Rectangle");
		ToggleButton line = new ToggleButton("Process");
		ToggleButton extend = new ToggleButton("Extend");
		ToggleButton include = new ToggleButton("Include");
		ToggleButton type = new ToggleButton("Type Of");
		ToggleGroup group = new ToggleGroup();
		actor.setToggleGroup(group);
		action.setToggleGroup(group);
		rec.setToggleGroup(group);
		line.setToggleGroup(group);
		extend.setToggleGroup(group);
		include.setToggleGroup(group);
		type.setToggleGroup(group);
		
		actor.setOnAction(e->{
			toolHandler.setTool("Actor");
		});
		
		
		btnP.getChildren().addAll(actor, action, rec, line, extend, include, type);
		return btnP;
	}

	public HBox ObjectToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Object Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox SequenceToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Sequence Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox CollaborationToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Collaboration Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox ClassToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Class Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox StateChartToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("StateChart Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox ActivityToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Activity Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox ComponentToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Component Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}

	public HBox DeploymentToolBox() {
		HBox btnP = new HBox();
		btnP.setAlignment(Pos.BOTTOM_CENTER);
		ToggleButton object = new ToggleButton("Deployment Tool");
		btnP.getChildren().addAll(object);
		return btnP;
	}
}
