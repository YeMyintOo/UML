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

public class StatechartCanvaBox extends CanvasPane {
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
		sStates = new ArrayList<S_StartState>();
		fStates = new ArrayList<S_FinalState>();
		states = new ArrayList<S_State>();
		subStates = new ArrayList<S_SubState>();
		hisStates = new ArrayList<S_HistoryState>();
		transitions = new ArrayList<S_Transistion>();
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
					getChildren().addAll(fState.getOuter(), fState);
					break;

				case "Statechart_State":
					state = new S_State(e.getX(), e.getY(), color);
					isState = true;
					getChildren().addAll(state, state.getLabel(), state.getText(false));
					break;

				case "Statechart_Substate":
					subState = new S_SubState(e.getX(), e.getY(), color);
					isSubState = true;
					getChildren().addAll(subState, subState.getLabel(), subState.getBr(), subState.getText(false));
					break;
				case "Statechart_HistoryState":
					hisState = new S_HistoryState(e.getX(), e.getY(), color);
					isHisState = true;
					getChildren().addAll(hisState, hisState.getLabel(), hisState.gethLabel(), hisState.getBr1(),
							hisState.getBr2());
					break;

				case "Statechart_Transition":
					transition = new S_Transistion(e.getX(), e.getY(), 0, 0, e.getX(), e.getY(), color);
					isTransitions = true;
					getChildren().addAll(transition,transition.getNode());

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
					transition.setControlX(e.getX());
					transition.setControlY(e.getY());
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
					states.add(state);
					isState = false;
				}
				if (isSubState) {
					subStates.add(subState);
					isSubState = false;
				}
				if (isHisState) {
					hisStates.add(hisState);
					isHisState = false;
				}
				if (isTransitions) {
					transition.recalculatePoint();
					getChildren().addAll(transition.getTop(),transition.getBot());
					transitions.add(transition);
					isTransitions = false;
				}
				toolHandler.setTool("");
			}
		});
	}

}
