package CanvaBoxs;

import java.util.ArrayList;

import Canvas.UC_ActionLine;
import Canvas.UC_Actor;
import Canvas.UC_Box;
import Canvas.UC_ExtendLine;
import Canvas.UC_IncludeLine;
import Canvas.UC_ProcessCycle;
import Canvas.UC_TypeOfLine;
import Database.ToolHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UseCaseCanvaBox extends Pane {
	// Only Use Case Components Can Draw
	private ToolHandler toolHandler;
	private Color color;

	// Action
	private ArrayList<UC_ActionLine> actionLines;
	private UC_ActionLine actionLine;
	private boolean isActionLine;

	// Process
	private ArrayList<UC_ProcessCycle> processCycles;
	private UC_ProcessCycle processCycle;
	private boolean isProcessCycle;

	// Box
	private ArrayList<UC_Box> boxs;
	private UC_Box box;
	private boolean isBox;

	// IncludeLine
	private ArrayList<UC_IncludeLine> includeLines;
	private UC_IncludeLine includeLine;
	private boolean isIncludeLine;

	// ExtendLine
	private ArrayList<UC_ExtendLine> extendLines;
	private UC_ExtendLine extendLine;
	private boolean isExtendLine;

	// TypeOf
	private ArrayList<UC_TypeOfLine> typeofLines;
	private UC_TypeOfLine typeofLine;
	private boolean isTypeofLine;

	// Actor
	private ArrayList<UC_Actor> actors;
	private UC_Actor actor;
	private boolean isActor;

	private boolean isNew;

	public UseCaseCanvaBox() {
		actionLines = new ArrayList<UC_ActionLine>();
		processCycles = new ArrayList<UC_ProcessCycle>();
		boxs = new ArrayList<UC_Box>();
		includeLines = new ArrayList<UC_IncludeLine>();
		extendLines = new ArrayList<UC_ExtendLine>();
		typeofLines = new ArrayList<UC_TypeOfLine>();
		actors = new ArrayList<UC_Actor>();

		// InitState
		resetBooleans();

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();
				color = Color.web(colorS);
				// Check New Or Edit
				

				if (isNewOrEdit(e)) {
					switch (tool) {
					case "UseCase_Actor":
						actor = new UC_Actor(e.getX(), e.getY(), 20, color, Color.GRAY);
						isActor = true;
						getChildren().add(actor);
						break;

					case "UseCase_Action":
						actionLine = new UC_ActionLine(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isActionLine = true;
						getChildren().add(actionLine);
						break;

					case "UseCase_Box":
						box = new UC_Box(e.getX(), e.getY(), 100, 200, color, Color.BLACK);
						isBox = true;
						getChildren().add(box);
						break;

					case "UseCase_Process":
						processCycle = new UC_ProcessCycle(e.getX(), e.getY(), 60, 30, color, Color.BLACK);
						isProcessCycle = true;
						getChildren().add(processCycle);
						break;

					case "UseCase_Extend":
						extendLine = new UC_ExtendLine(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isExtendLine = true;
						getChildren().add(extendLine);
						break;

					case "UseCase_Include":
						includeLine = new UC_IncludeLine(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isIncludeLine = true;
						getChildren().add(includeLine);
						break;

					case "UseCase_Type":
						typeofLine = new UC_TypeOfLine(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isTypeofLine = true;
						getChildren().add(typeofLine);
						break;
					}// End switch
				} 
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isActionLine) {
					actionLine.setEndX(e.getX());
					actionLine.setEndY(e.getY());
				}
				if (isProcessCycle) {
					processCycle.setCenterX(e.getX());
					processCycle.setCenterY(e.getY());
				}
				if (isBox) {
					box.setX(e.getX());
					box.setY(e.getY());
				}
				if (isIncludeLine) {
					includeLine.setEndX(e.getX());
					includeLine.setEndY(e.getY());
				}
				if (isExtendLine) {
					extendLine.setEndX(e.getX());
					extendLine.setEndY(e.getY());
				}
				if (isTypeofLine) {
					typeofLine.setEndX(e.getX());
					typeofLine.setEndY(e.getY());
				}
				if (isActor) {
					actor.setCenterX(e.getX());
					actor.setCenterY(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isActionLine) {
					actionLines.add(actionLine);
					actionLine = null;
				}
				if (isProcessCycle) {
					processCycles.add(processCycle);
					processCycle = null;
				}
				if (isBox) {
					boxs.add(box);
					box = null;
				}
				if (isIncludeLine) {
					LineArrowHead(includeLine);
					Linelabel(includeLine, "include");

					includeLines.add(includeLine);
					includeLine = null;
				}
				if (isExtendLine) {
					LineArrowHead(extendLine);
					Linelabel(extendLine, "extend");
					extendLines.add(extendLine);
					extendLine = null;
				}
				if (isTypeofLine) {
					LineTriangleHead(typeofLine);
					typeofLines.add(typeofLine);
					typeofLine = null;
				}
				if (isActor) {
					drawBody(actor);
					actors.add(actor);
					actor = null;
				}

				resetBooleans();
			}
		});

	}

	public boolean isNewOrEdit(MouseEvent e) {
		isNew = true;
		removeShadow();
		resetBooleans();
		
		isNewOrEditAction(e);
		isNewOrEditActor(e);
		isNewOrEditProcessCycle(e);
		resetBooleans();

		return isNew;
	}

	// isEditOrNew Action
	public void isNewOrEditAction(MouseEvent e) {
		if (actionLines.size() > 0) {
			for (int i = 0; i < actionLines.size(); i++) {
				int index = i;
				Point2D point = new Point2D(e.getX(), e.getY());
				if (actionLines.get(i).contains(point)) {
					isNew=false;
					break;
				}
			}
		}
	}

	// isEditOrNew Actor
	public void isNewOrEditActor(MouseEvent e) {
		if (actors.size() > 0) {
			for (int i = 0; i < actors.size(); i++) {
				Point2D point = new Point2D(e.getX(), e.getY());
				if (actors.get(i).contains(point)) {
					isNew = false;
					int index = i;
					setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							actors.get(index).setCenterX(e.getX());
							actors.get(index).setCenterY(e.getY());
						}
					});

					// Label
					Text msg = new Text("Actor");
					msg.setFont(Font.font("Arial", FontWeight.BLACK, 16));
					double label_half = msg.layoutBoundsProperty().getValue().getWidth() / 2;
					msg.layoutXProperty().bind(actors.get(i).centerXProperty().subtract(label_half));
					msg.layoutYProperty().bind(actors.get(i).centerYProperty().add(80));
					getChildren().add(msg);

					break;
				}
			}
		}
	}

	// isEditOrNew Process Cycle
	public void isNewOrEditProcessCycle(MouseEvent e) {
		if (processCycles.size() > 0) {
			for (int i = 0; i < processCycles.size(); i++) {
				Point2D point = new Point2D(e.getX(), e.getY());

				// Label
				Text msg = new Text("Process");
				double label_half = msg.layoutBoundsProperty().getValue().getWidth() / 2;
				msg.layoutXProperty().bind(processCycles.get(i).centerXProperty().subtract(label_half));
				msg.layoutYProperty().bind(processCycles.get(i).centerYProperty());

				if (processCycles.get(i).contains(point)) {
					isNew = false;
					int index = i;
					// Label Null
					

					

					DropShadow dsEffect = new DropShadow();
					dsEffect.setOffsetX(5);
					dsEffect.setOffsetY(5);
					dsEffect.setRadius(15);
					processCycles.get(i).setEffect(dsEffect);

					setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							processCycles.get(index).setCenterX(e.getX());
							processCycles.get(index).setCenterY(e.getY());
							
						}
					});
					break;
				}
			}
		}
	}

	
	// RemoveShadow
	public void removeShadow() {
		for (int i = 0; i < processCycles.size(); i++) {
			processCycles.get(i).setEffect(null);
		}
		for (int i = 0; i < actionLines.size(); i++) {
			actionLines.get(i).setEffect(null);
		}

	}

	public void drawBody(Circle actor) {
		double centerx = actor.getCenterX();
		double centery = actor.getCenterY();

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

		getChildren().addAll(body, leg, leg2, leg3, leg4);
	}

	public void LineTriangleHead(Line line) {

		double startx = line.getStartX();
		double starty = line.getStartY();
		double endx = line.getEndX();
		double endy = line.getEndY();

		// Arrow Head
		double x, y, length;
		Point2D end = new Point2D(endx, endy);
		length = Math.sqrt((endx - startx) * (endx - startx) + (endy - starty) * (endy - starty));
		x = (endx - startx) / length;
		y = (endy - starty) / length;
		Point2D base = new Point2D(endx - x * 10, endy - y * 10);
		Point2D back_top = new Point2D(base.getX() - 10 * y, base.getY() + 10 * x);
		Point2D back_bottom = new Point2D(base.getX() + 10 * y, base.getY() - 10 * x);
		Path path = new Path();
		path.setStroke(color);
		path.getElements().add(new MoveTo(endx, endy));
		path.getElements().add(new LineTo(back_top.getX(), back_top.getY()));
		path.getElements().add(new LineTo(back_bottom.getX(), back_bottom.getY()));
		path.getElements().add(new LineTo(endx, endy));

		// Path rev=new Path();
		// rev.getElements().add(new MoveTo(endx, endy));
		// rev.getElements().add(new LineTo(base.getX(), base.getY()));

		getChildren().addAll(path);

	}

	public void LineArrowHead(Line line) {
		double startx = line.getStartX();
		double starty = line.getStartY();
		double endx = line.getEndX();
		double endy = line.getEndY();

		// Arrow Head
		double x, y, length;
		Point2D end = new Point2D(endx, endy);
		length = Math.sqrt((endx - startx) * (endx - startx) + (endy - starty) * (endy - starty));
		x = (endx - startx) / length;
		y = (endy - starty) / length;
		Point2D base = new Point2D(endx - x * 10, endy - y * 10);
		Point2D back_top = new Point2D(base.getX() - 10 * y, base.getY() + 10 * x);
		Point2D back_bottom = new Point2D(base.getX() + 10 * y, base.getY() - 10 * x);
		Path top = new Path();
		top.setStroke(color);
		top.getElements().add(new MoveTo(endx, endy));
		top.getElements().add(new LineTo(back_top.getX(), back_top.getY()));

		Path bot = new Path();
		bot.setStroke(color);
		bot.getElements().add(new MoveTo(endx, endy));
		bot.getElements().add(new LineTo(back_bottom.getX(), back_bottom.getY()));

		getChildren().addAll(top, bot);
	}

	public void Linelabel(Line line, String label) {
		double startx = line.getStartX();
		double starty = line.getStartY();
		double endx = line.getEndX();
		double endy = line.getEndY();

		double midx = (startx + endx) * 0.5;
		double midy = (starty + endy) * 0.5;
		double slope = (starty - endy) / (startx - endx);

		String msg = "<<" + label + ">>";

		if (startx < endx && starty < endy) {
			System.out.println(" Figure 1");

			getChildren().addAll(new Text(midx + 5, midy, msg));

		} else if (startx > endx && starty > endy) {
			System.out.println(" Figure 2");
			getChildren().addAll(new Text(midx + 5, midy, msg));

		} else if (startx > endx && starty < endy) {
			System.out.println(" Figure 3");
			getChildren().addAll(new Text(midx + 5, midy, msg));

		} else if (startx < endx && starty > endy) {
			System.out.println(" Figure 4");
			getChildren().addAll(new Text(midx + 5, midy, msg));

		} else if (startx < endx && starty == endy) {
			System.out.println(" Figure 5");
			getChildren().addAll(new Text(midx - 10, midy - 20, msg));

		} else if (startx > endx && starty == endy) {
			System.out.println(" Figure 6");
			getChildren().addAll(new Text(midx - 10, midy - 20, msg));

		} else if (startx == endx && starty < endy) {
			System.out.println(" Figure 7");
			getChildren().addAll(new Text(midx - 15, midy, msg));

		} else if (startx == endx && starty > endy) {
			System.out.println(" Figure 8");
			getChildren().addAll(new Text(midx - 15, midy, msg));
		} else {

		}

	}

	// initial State
	public void resetBooleans() {
		isActionLine = false;
		isProcessCycle = false;
		isBox = false;
		isIncludeLine = false;
		isExtendLine = false;
		isTypeofLine = false;
		isActor = false;

	}

}
