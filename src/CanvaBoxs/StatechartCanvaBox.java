package CanvaBoxs;

import java.util.ArrayList;

import Canvas.S_FinalState;
import Canvas.S_HistoryState;
import Canvas.S_StartState;
import Canvas.S_State;
import Canvas.S_SubState;
import Canvas.S_Transistion;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class StatechartCanvaBox extends Pane {
	// Only State chart Components Can Draw
	private ToolHandler toolHandler;
	private Color color;
	private DropShadow shape;

	// StartState
	private ArrayList<S_StartState> sStates;
	private S_StartState sState;
	private boolean isStartState;

	// Final State
	private ArrayList<S_FinalState> fStates;
	private S_FinalState fState;
	private boolean isFinalState;

	// State
	private ArrayList<S_State> states;
	private S_State state;
	private boolean isState;

	// Sub State
	private ArrayList<S_SubState> subStates;
	private S_SubState subState;
	private boolean isSubState;

	// History State
	private ArrayList<S_HistoryState> hisStates;
	private S_HistoryState hisState;
	private boolean isHisState;

	// Transition
	private ArrayList<S_Transistion> transitions;
	private S_Transistion transition;
	private boolean isTransitions;

	public StatechartCanvaBox() {
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

				case "Statechart_InitState":
					sState = new S_StartState(e.getX(), e.getY(), 10, color);
					isStartState = true;
					getChildren().add(sState);
					break;

				case "Statechart_FinalState":
					fState = new S_FinalState(e.getX(), e.getY(), 10, color);
					isFinalState = true;
					drawFinalStateOutLine(fState);
					getChildren().add(fState);
					break;

				case "Statechart_State":
					state = new S_State(e.getX(), e.getY(), color);
					isState = true;
					getChildren().add(state);
					break;

				case "Statechart_Substate":
					subState = new S_SubState(e.getX(), e.getY(), color);
					isSubState = true;
					getChildren().add(subState);
					break;
				case "Statechart_HistoryState":
					hisState = new S_HistoryState(e.getX(), e.getY(), color);
					isHisState = true;
					getChildren().add(hisState);
					break;

				case "Statechart_Transition":
					transition = new S_Transistion(e.getX(), e.getY(), 0, 0, e.getX(), e.getY(), color);
					isTransitions = true;
					getChildren().add(transition);

				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isStartState) {
					sState.setCenterX(e.getX());
					sState.setCenterY(e.getY());
				}
				if (isFinalState) {
					fState.setCenterX(e.getX());
					fState.setCenterY(e.getY());
				}
				if (isState) {
					state.setX(e.getX());
					state.setY(e.getY());
				}
				if (isSubState) {
					subState.setX(e.getX());
					subState.setY(e.getY());
				}
				if (isHisState) {
					hisState.setX(e.getX());
					hisState.setY(e.getY());
				}
				if (isTransitions) {
					transition.setEndX(e.getX());
					transition.setEndY(e.getY());
					transition.setControlX(e.getX() + 5);
					transition.setControlY(e.getY() + 5);

				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isStartState) {
					sStates.add(sState);
					isStartState = false;
				}
				if (isFinalState) {
					fStates.add(fState);
					isFinalState = false;
				}
				if (isState) {
					drawStateLabel(state);
					states.add(state);
					isState = false;
				}
				if (isSubState) {
					drawSubStateLabel(subState);
					subStates.add(subState);
					isSubState = false;
				}
				if (isHisState) {
					drawHisStateLabel(hisState);
					hisStates.add(hisState);
					isHisState = false;
				}
				if (isTransitions) {
					drawTransition(transition);
					transitions.add(transition);
					isTransitions = false;
				}
			}
		});

	}

	public void init() {
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);
		sStates = new ArrayList<S_StartState>();
		fStates = new ArrayList<S_FinalState>();
		states = new ArrayList<S_State>();
		subStates = new ArrayList<S_SubState>();
		hisStates = new ArrayList<S_HistoryState>();
		transitions = new ArrayList<S_Transistion>();
	}

	public void drawFinalStateOutLine(S_FinalState fstate) {
		Circle outLine = new Circle(fstate.getCenterX(), fstate.getCenterY(), 15);
		outLine.setFill(Color.WHITE);
		outLine.setStroke(Color.LIGHTGRAY);

		outLine.centerXProperty().bindBidirectional(fstate.centerXProperty());
		outLine.centerYProperty().bindBidirectional(fstate.centerYProperty());

		getChildren().add(outLine);
	}

	public void drawStateLabel(S_State state) {
		Text label = new Text(state.labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(state.labelProperty());

		label.xProperty().bind(state.xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(state.yProperty().add(state.heightProperty().divide(2)));

		getChildren().add(label);
	}

	public void drawSubStateLabel(S_SubState subState) {
		Text label = new Text(subState.labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(subState.labelProperty());

		label.xProperty().bind(subState.xProperty().add(10));
		label.yProperty().bind(subState.yProperty().add(20));

		Line line = new Line(subState.getX(), subState.getY() + 30, subState.getX() + subState.getWidth(),
				subState.getY() + 30);
		line.setStroke(Color.LIGHTGRAY);
		line.startXProperty().bind(subState.xProperty());
		line.startYProperty().bind(subState.yProperty().add(30));
		line.endXProperty().bind(subState.xProperty().add(subState.getWidth()));
		line.endYProperty().bind(subState.yProperty().add(30));

		getChildren().addAll(label, line);
	}

	public void drawHisStateLabel(S_HistoryState hisState) {
		Text label = new Text(hisState.labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(hisState.labelProperty());

		label.xProperty().bind(hisState.xProperty().add(10));
		label.yProperty().bind(hisState.yProperty().add(20));

		Text his = new Text(hisState.historyProperty().get());
		his.textProperty().bind(hisState.historyProperty());

		his.xProperty().bind(hisState.xProperty().add(10));
		his.yProperty().bind(hisState.yProperty().add(50));

		Line line = new Line(hisState.getX(), hisState.getY() + 30, hisState.getX() + hisState.getWidth(),
				hisState.getY() + 30);
		line.setStroke(Color.LIGHTGRAY);
		line.startXProperty().bind(hisState.xProperty());
		line.startYProperty().bind(hisState.yProperty().add(30));
		line.endXProperty().bind(hisState.xProperty().add(hisState.getWidth()));
		line.endYProperty().bind(hisState.yProperty().add(30));

		Line line2 = new Line(hisState.getX(), hisState.getY() + 60, hisState.getX() + hisState.getWidth(),
				hisState.getY() + 60);
		line2.setStroke(Color.LIGHTGRAY);
		line2.startXProperty().bind(hisState.xProperty());
		line2.startYProperty().bind(hisState.yProperty().add(60));
		line2.endXProperty().bind(hisState.xProperty().add(hisState.getWidth()));
		line2.endYProperty().bind(hisState.yProperty().add(60));

		getChildren().addAll(label, line, his, line2);

	}

	public void drawTransition(S_Transistion transition) {
		double startx = transition.getStartX();
		double starty = transition.getStartY();
		double endx = transition.getEndX();
		double endy = transition.getEndY();
		double slope = (starty - endy) / (startx - endx);

		// Distance
		double d = Math.sqrt((Math.pow(endx - startx, 2)) + (Math.pow(endy - starty, 2)));
		double mid = d / 2;

		Rectangle rc = new Rectangle();
		rc.setFill(Color.LIGHTBLUE);
		rc.setRotate(45);
		rc.setX(startx + mid);
		rc.setY(starty);
		rc.setWidth(20);
		rc.setHeight(20);

		getChildren().add(rc);
	}
}
