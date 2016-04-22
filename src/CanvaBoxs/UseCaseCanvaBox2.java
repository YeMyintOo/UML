package CanvaBoxs;

import java.util.ArrayList;

import Canvas.UC_ActionLine;
import Canvas.UC_Actor;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UseCaseCanvaBox2 extends Pane {

	private boolean isNew;

	private ToolHandler toolHandler;
	private Color color;
	private DropShadow shape;

	// Actor
	private ArrayList<UC_Actor> actors;
	private UC_Actor actor;
	private boolean isActor;

	// Action
	private ArrayList<UC_ActionLine> actionLines;
	private UC_ActionLine actionLine;
	private boolean isActionLine;

	public UseCaseCanvaBox2() {
		init();
		// Life Cycle////////////////////////////////////////
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				// Is New or Edit///////////////
				isNewOrEdit(key);
				////////////////////////////////

				if (isNew) {
					/// Get Color and Tool//////////
					toolHandler = new ToolHandler();
					color = Color.web(toolHandler.getColor());
					//////////////////////////////
					switch (toolHandler.getTool()) {
					case "UseCase_Actor":
						actor = new UC_Actor(key.getX(), key.getY(), 20, color, Color.GRAY);
						drawActorBody(actor);// Body
						isActor = true;
						getChildren().add(actor);
						break;
					default:
						break;
					}
				} // End of New
			}
		});
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse Dragged Function");
				if (isActor) {
					actor.setCenterX(e.getX());
					actor.setCenterY(e.getY());
				}
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (isActor) {
					actors.add(actor);
					isActor = false;
				}
			}
		});
		////////////////////////////////////////////////////
	}

	public void init() {
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);

		actors = new ArrayList<UC_Actor>();
		actionLines = new ArrayList<UC_ActionLine>();
	}

	public void isNewOrEdit(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		// Actor
		for (int i = 0; i < actors.size(); i++) {

			if (actors.get(i).contains(point)) {
				isNew = false;
				// Edition Process////////////
				int index = i;
				actors.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						actors.get(index).setCenterX(key.getX());
						actors.get(index).setCenterY(key.getY());
					}
				});
				actors.get(i).addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						if (key.getClickCount() == 2) {
							TextField data = new TextField();
							data.layoutXProperty().bind(actors.get(index).centerXProperty().subtract(60));
							data.layoutYProperty().bind(actors.get(index).centerYProperty().add(60));
							getChildren().add(data);
							data.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									if (e.getCode() == KeyCode.ENTER) {
										if (!data.getText().equals("")) {
											actors.get(index).labelProperty().set(data.getText().trim());
										}
										getChildren().remove(data);
									}
								}
							});

						}
					}
				});
				actors.get(i).setEffect(shape);
				/////////////////////////////
				// break;
			} else {
				actors.get(i).setEffect(null);// Remove Shape
			}

		}
	}

	public void drawActorBody(UC_Actor actor) {

		double centerx = actor.getCenterX();
		double centery = actor.getCenterY();
		Text label = new Text(actor.labelProperty().getValue());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));

		Line body = new Line(centerx, centery + 20, centerx, centery + 40);
		body.startXProperty().bind(actor.centerXProperty());
		body.startYProperty().bind(actor.centerYProperty().add(20));
		body.endXProperty().bind(actor.centerXProperty());
		body.endYProperty().bind(actor.centerYProperty().add(40));

		Line leg = new Line(centerx, centery + 20, centerx + 10, centery + 20 + 10);
		leg.startXProperty().bind(actor.centerXProperty());
		leg.startYProperty().bind(actor.centerYProperty().add(20));
		leg.endXProperty().bind(actor.centerXProperty().add(10));
		leg.endYProperty().bind(actor.centerYProperty().add(30));

		Line leg2 = new Line(centerx, centery + 20, centerx - 10, centery + 20 + 10);
		leg2.startXProperty().bind(actor.centerXProperty());
		leg2.startYProperty().bind(actor.centerYProperty().add(20));
		leg2.endXProperty().bind(actor.centerXProperty().subtract(10));
		leg2.endYProperty().bind(actor.centerYProperty().add(30));

		Line leg3 = new Line(centerx, centery + 40, centerx + 10, centery + 40 + 10);
		leg3.startXProperty().bind(actor.centerXProperty());
		leg3.startYProperty().bind(actor.centerYProperty().add(40));
		leg3.endXProperty().bind(actor.centerXProperty().add(10));
		leg3.endYProperty().bind(actor.centerYProperty().add(50));

		Line leg4 = new Line(centerx, centery + 40, centerx - 10, centery + 40 + 10);
		leg4.startXProperty().bind(actor.centerXProperty());
		leg4.startYProperty().bind(actor.centerYProperty().add(40));
		leg4.endXProperty().bind(actor.centerXProperty().subtract(10));
		leg4.endYProperty().bind(actor.centerYProperty().add(50));

		label.textProperty().bind(actor.labelProperty());
		System.out.println(" Label bound :" +label.getLayoutBounds().getWidth());
		label.layoutXProperty().bind(actor.centerXProperty().subtract(label.getLayoutBounds().getWidth() / 3));
		label.layoutYProperty().bind(actor.centerYProperty().add(80));

		getChildren().addAll(body, leg, leg2, leg3, leg4, label);

	}
}
