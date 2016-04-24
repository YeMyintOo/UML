package CanvaBoxs;

import java.util.ArrayList;

import Canvas.UC_ActionLine;
import Canvas.UC_Actor;
import Canvas.UC_Box;
import Canvas.UC_ProcessCycle;
import Database.ToolHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
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

	// Box
	private ArrayList<UC_Box> boxs;
	private UC_Box box;
	private boolean isBox;

	// Process
	private ArrayList<UC_ProcessCycle> processCycles;
	private UC_ProcessCycle processCycle;
	private boolean isProcessCycle;

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
					case "UseCase_Action":
						actionLine = new UC_ActionLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isActionLine = true;
						getChildren().add(actionLine);
						break;
					case "UseCase_Box":
						box = new UC_Box(key.getX(), key.getY(), 300, 400, color, Color.GRAY);
						// drawBoxLabel(box);
						isBox = true;
						getChildren().add(box);
						break;
					case "UseCase_Process":
						processCycle = new UC_ProcessCycle(key.getX(), key.getY(), 60, 30, color, Color.BLACK);
						isProcessCycle = true;
						getChildren().add(processCycle);
						drawProcessLabel(processCycle);
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
				if (isActor) {
					actor.setCenterX(e.getX());
					actor.setCenterY(e.getY());
				}
				if (isActionLine) {
					actionLine.setEndX(e.getX());
					actionLine.setEndY(e.getY());
				}
				if (isBox) {
					box.setX(e.getX() - 100);
					box.setY(e.getY() - 100);
				}
				if (isProcessCycle) {
					processCycle.setCenterX(e.getX());
					processCycle.setCenterY(e.getY());
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
				if (isActionLine) {
					actionLines.add(actionLine);
					isActionLine = false;
				}
				if (isBox) {
					boxs.add(box);
					isBox = false;
				}
				if (isProcessCycle) {
					processCycles.add(processCycle);
					isProcessCycle = false;
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
		boxs = new ArrayList<UC_Box>();
		processCycles = new ArrayList<UC_ProcessCycle>();
	}

	public void isNewOrEdit(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditActor(e, point);
		isNewOREditBox(e, point);
		isNewOREditProcess(e, point);
	}

	public void isNewOREditActor(MouseEvent e, Point2D point) {
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
						Button x = null;
						if (key.getClickCount() == 2) {
							// Edit Actor Label
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

	public void isNewOREditBox(MouseEvent e, Point2D point) {
		for (int i = 0; i < boxs.size(); i++) {
			int index = i;
			if (boxs.get(i).contains(point)) {
				isNew = false;
				boxs.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						boxs.get(index).setX(key.getX() - 100);
						boxs.get(index).setY(key.getY() - 100);
					}
				});

				boxs.get(i).setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							boxs.get(index).setWidth(boxs.get(index).getWidth() + 10);
						} else {
							boxs.get(index).setHeight(boxs.get(index).getHeight() + 10);
						}
					}
				});
				break;
			}
		}
	}

	public void isNewOREditProcess(MouseEvent e, Point2D point) {
		for (int i = 0; i < processCycles.size(); i++) {
			if (processCycles.get(i).contains(point)) {
				isNew = false;
				int index = i;
				processCycles.get(i).setEffect(shape);
				processCycles.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						processCycles.get(index).setCenterX(key.getX());
						processCycles.get(index).setCenterY(key.getY());
					}
				});

				processCycles.get(i).addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						Button x = null;
						if (key.getClickCount() == 2) {
							// Edit Actor Label
							TextField data = new TextField();
							data.layoutXProperty().bind(processCycles.get(index).centerXProperty().subtract(60));
							data.layoutYProperty().bind(processCycles.get(index).centerYProperty().add(60));
							getChildren().add(data);
							data.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									if (e.getCode() == KeyCode.ENTER) {
										if (!data.getText().equals("")) {
											processCycles.get(index).labelProperty().set(data.getText().trim());
											DoubleProperty w = new SimpleDoubleProperty();
											Text msg = new Text(data.getText().trim());
											w.set(msg.layoutBoundsProperty().getValue().getMaxX());
											processCycles.get(index).radiusXProperty().bind(w);
										}
										getChildren().remove(data);
									}
								}
							});
						}
					}
				});

			} else {
				processCycles.get(i).setEffect(null);
			}
			
			//Linked
			for(int k=0; k<actionLines.size(); k++){
				Point2D p=new Point2D(actionLines.get(k).getEndX(),actionLines.get(k).getEndY());
				if(processCycles.get(i).contains(p)){
					actionLines.get(k).endXProperty().bind(processCycles.get(i).centerXProperty());
					actionLines.get(k).endYProperty().bind(processCycles.get(i).centerYProperty());
				}
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
		label.layoutXProperty().bind(actor.centerXProperty().subtract(label.getLayoutBounds().getWidth() / 3));
		label.layoutYProperty().bind(actor.centerYProperty().add(80));

		getChildren().addAll(body, leg, leg2, leg3, leg4, label);

	}

	public void drawBoxLabel(UC_Box box) {
		Text label = new Text(box.labelProperty().getValue());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));

		label.textProperty().bind(box.labelProperty());
		System.out.println(" Y " + box.layoutXProperty().doubleValue());
		System.out.println(" X " + box.layoutYProperty().doubleValue());
		// label.layoutXProperty().bind(box.);
		// label.layoutYProperty().bind(box.layoutYProperty().subtract(40));
		getChildren().add(label);
	}

	public void drawProcessLabel(UC_ProcessCycle cycle) {
		Text plabel = new Text(cycle.labelProperty().getValue());
		plabel.textProperty().bind(cycle.labelProperty());

		plabel.layoutXProperty()
				.bind(cycle.centerXProperty().subtract(plabel.layoutBoundsProperty().getValue().getWidth()/2));
		plabel.layoutYProperty().bind(cycle.centerYProperty());

		getChildren().addAll(plabel);
	}
}
