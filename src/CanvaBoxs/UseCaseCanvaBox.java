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
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
				System.out.println("Mouse Pressed");
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();
				color = Color.web(colorS);
				// Check New Or Edit

				if (isNewOrEdit(e)) {
					switch (tool) {
					case "UseCase_Actor":
						actor = new UC_Actor(e.getX(), e.getY(), 20);
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
				} // isNew end
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				System.out.println("Mouse Dragged");
				if (isActionLine) {
					actionLine.setEndx(e.getX());
					actionLine.setEndy(e.getY());
				}
				if (isProcessCycle) {
					processCycle.setCenterx(e.getX());
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
				System.out.println("Mouse Released");
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
		boolean isNew = true;
		if (actors.size() > 0) { // Actor
			for (int i = 0; i < actors.size(); i++) {
				Point2D point = new Point2D(e.getX(), e.getY());
				if (actors.get(i).contains(point)) {
					isNew = false;
					//////////////
					openActorEditBox();
					//////////////
					break;
				}
			}
		} // end of Actor

		if (processCycles.size() > 0) { // Process Cycle
			for (int i = 0; i < processCycles.size(); i++) {
				Point2D point = new Point2D(e.getX(), e.getY());
				if (processCycles.get(i).contains(point)) {
					isNew = false;
					int index=i;
					//////////////
					setOnMouseDragged(new EventHandler<MouseEvent>(){
						@Override
						public void handle(MouseEvent e) {
							processCycles.get(index).setCenterX(e.getX());
							processCycles.get(index).setCenterY(e.getY());
						}
					});
					//////////////
					break;
				}
			}
		} // end of Process Cycle

		return isNew;
	}
	public void openActorEditBox(){
		
	}
	

	public void drawBody(Circle actor) {
		double centerx = actor.getCenterX();
		double centery = actor.getCenterY();
		Path body = new Path();
		body.getElements().add(new MoveTo(centerx, centery + 20));
		body.getElements().add(new LineTo(centerx, centery + 40));

		Path leg = new Path();
		leg.getElements().add(new MoveTo(centerx, centery + 20));
		leg.getElements().add(new LineTo(centerx + 10, centery + 20 + 10));

		Path leg2 = new Path();
		leg2.getElements().add(new MoveTo(centerx, centery + 20));
		leg2.getElements().add(new LineTo(centerx - 10, centery + 20 + 10));

		Path leg3 = new Path();
		leg3.getElements().add(new MoveTo(centerx, centery + 40));
		leg3.getElements().add(new LineTo(centerx + 10, centery + 40 + 10));

		Path leg4 = new Path();
		leg4.getElements().add(new MoveTo(centerx, centery + 40));
		leg4.getElements().add(new LineTo(centerx - 10, centery + 40 + 10));

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
