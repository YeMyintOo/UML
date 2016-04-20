package CanvaBoxs;

import java.util.ArrayList;

import Canvas.UC_ActionLine;
import Canvas.UC_Box;
import Canvas.UC_IncludeLine;
import Canvas.UC_ProcessCycle;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
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

	public UseCaseCanvaBox() {
		actionLines = new ArrayList<UC_ActionLine>();
		processCycles = new ArrayList<UC_ProcessCycle>();
		boxs = new ArrayList<UC_Box>();
		includeLines = new ArrayList<UC_IncludeLine>();
		// InitState
		resetBooleans();

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();
				color = Color.web(colorS);

				switch (tool) {
				case "UseCase_Actor":
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
					processCycle = new UC_ProcessCycle(e.getX(), e.getY(), 60, 30);
					isProcessCycle = true;
					getChildren().add(processCycle);
					break;

				case "UseCase_Extend":
					break;

				case "UseCase_Include":
					includeLine = new UC_IncludeLine(e.getX(), e.getY(), e.getX(), e.getY(), color);
					isIncludeLine = true;
					getChildren().add(includeLine);
					System.out.println(" Include Line is Pressed");
					break;

				case "UseCase_Type":
					break;
				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
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
					System.out.println(" Include Line is Dragged");
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
					Linelabel(includeLine);

					includeLines.add(includeLine);
					includeLine = null;
				}
				resetBooleans();
			}
		});

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

	public void Linelabel(Line line) {
		double startx = line.getStartX();
		double starty = line.getStartY();
		double endx = line.getEndX();
		double endy = line.getEndY();

		double midx = (startx + endx) * 0.5;
		double midy = (starty + endy) * 0.5;
		double slope = (starty - endy) / (startx - endx);

		if (startx < endx && starty < endy) {
			System.out.println(" Figure 1");

			getChildren().addAll(new Text(midx + 5, midy, "<<include>>"));

		} else if (startx > endx && starty > endy) {
			System.out.println(" Figure 2");
			getChildren().addAll(new Text(midx + 5, midy, "<<include>>"));

		} else if (startx > endx && starty < endy) {
			System.out.println(" Figure 3");
			getChildren().addAll(new Text(midx + 5, midy, "<<include>>"));

		} else if (startx < endx && starty > endy) {
			System.out.println(" Figure 4");
			getChildren().addAll(new Text(midx + 5, midy, "<<include>>"));

		} else if (startx < endx && starty == endy) {
			System.out.println(" Figure 5");
			getChildren().addAll(new Text(midx - 10, midy - 20, "<<include>>"));

		} else if (startx > endx && starty == endy) {
			System.out.println(" Figure 6");
			getChildren().addAll(new Text(midx - 10, midy - 20, "<<include>>"));

		} else if (startx == endx && starty < endy) {
			System.out.println(" Figure 7");
			getChildren().addAll(new Text(midx - 15, midy, "<<include>>"));

		} else if (startx == endx && starty > endy) {
			System.out.println(" Figure 8");
			getChildren().addAll(new Text(midx - 15, midy, "<<include>>"));
		} else {

		}

	}

	// initial State
	public void resetBooleans() {
		isActionLine = false;
		isProcessCycle = false;
		isBox = false;
		isIncludeLine = false;
	}

}
