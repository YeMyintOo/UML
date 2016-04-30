package CanvaBoxs;

import java.util.ArrayList;

import Canvas.A_Action;
import Canvas.A_Edge;
import Canvas.A_EndNode;
import Canvas.A_InitNode;
import Canvas.A_Merge;
import Canvas.A_Region;
import Canvas.A_Time;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ActivityCanvaBox extends Pane {
	// Only Activity Components Can Draw
	private ToolHandler toolHandler;
	private Color color;
	private DropShadow shape;

	// Initial Node
	private ArrayList<A_InitNode> initNodes;
	private A_InitNode initNode;
	private boolean isInitNode;

	// End Node
	private ArrayList<A_EndNode> endNodes;
	private A_EndNode endNode;
	private boolean isEndNode;

	// Action
	private ArrayList<A_Action> actions;
	private A_Action action;
	private boolean isAction;

	// Edge
	private ArrayList<A_Edge> edges;
	private A_Edge edge;
	private boolean isEdge;

	// Merge
	private ArrayList<A_Merge> merges;
	private A_Merge merge;
	private boolean isMerge;

	// Time
	private ArrayList<A_Time> times;
	private A_Time time;
	private boolean isTime;

	// Region
	private ArrayList<A_Region> regions;
	private A_Region region;
	private boolean isRegion;

	public ActivityCanvaBox() {
		init();
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();// Check Tool From
													// ToolHandler.xml
				color = Color.web(colorS); // Dynamic color from ToolHander.xml

				switch (tool) {
				case "Activity_InitNode":
					initNode = new A_InitNode(e.getX(), e.getY(), 10, color);
					isInitNode = true;
					getChildren().add(initNode);
					break;
				case "Activity_EndNode":
					endNode = new A_EndNode(e.getX(), e.getY(), 10, color);
					isEndNode = true;
					drawEndNodeBorder(endNode);
					getChildren().add(endNode);
					break;
				case "Activity_Action":
					action = new A_Action(e.getX(), e.getY(), color);
					isAction = true;
					getChildren().addAll(action, action.getLabel());
					break;
				case "Activity_Edge":
					edge = new A_Edge(e.getX(), e.getY(), e.getX(), e.getY(), color);
					isEdge = true;
					getChildren().addAll(edge);
					break;
				case "Activity_Merge":
					merge = new A_Merge(e.getX(), e.getY(), color);
					isMerge = true;
					getChildren().addAll(merge);
					break;
				case "Activity_Time":
					time = new A_Time(e.getX(), e.getY(), color);
					isTime = true;
					getChildren().addAll(time, time.getLine1(), time.getLine2(), time.getLine3());
					break;
				case "Activity_Region":
					region = new A_Region(e.getX(), e.getY(), color);
					isRegion = true;
					getChildren().addAll(region, region.getLabel());
					region.toBack();
					break;
				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isInitNode) {
					initNode.setCenterX(e.getX());
					initNode.setCenterY(e.getY());
				}
				if (isEndNode) {
					endNode.setCenterX(e.getX());
					endNode.setCenterY(e.getY());
				}
				if (isAction) {
					action.setX(e.getX());
					action.setY(e.getY());
				}
				if (isEdge) {
					edge.setEndX(e.getX());
					edge.setEndY(e.getY());
				}
				if (isMerge) {
					merge.setX(e.getX());
					merge.setY(e.getY());
				}
				if (isTime) {
					time.setStartX(e.getX());
					time.setStartY(e.getY());
				}
				if (isRegion) {
					region.setX(e.getX());
					region.setY(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isInitNode) {
					initNodes.add(initNode);
					isInitNode = false;
				}
				if (isEndNode) {
					endNodes.add(endNode);
					isEndNode = false;
				}
				if (isAction) {
					actions.add(action);
					isAction = false;
				}
				if (isEdge) {
					edge.recalculatePoint();
					getChildren().addAll(edge.getTop(), edge.getBot());
					edges.add(edge);
					isEdge = false;
				}
				if (isMerge) {
					merges.add(merge);
					isMerge = false;
				}
				if (isTime) {
					times.add(time);
					isTime = false;
				}
				if (isRegion) {
					regions.add(region);
					isRegion = false;
				}
			}
		});

	}

	public void init() {
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);

		initNodes = new ArrayList<A_InitNode>();
		endNodes = new ArrayList<A_EndNode>();
		actions = new ArrayList<A_Action>();
		edges = new ArrayList<A_Edge>();
		merges = new ArrayList<A_Merge>();
		times = new ArrayList<A_Time>();
		regions = new ArrayList<A_Region>();
	}

	public void drawEndNodeBorder(A_EndNode endNode) {
		Circle c = new Circle();
		c.centerXProperty().bind(endNode.centerXProperty());
		c.centerYProperty().bind(endNode.centerYProperty());
		c.setRadius(15);
		c.setStroke(Color.LIGHTGRAY);
		c.setFill(Color.WHITE);

		getChildren().add(c);
	}
}
