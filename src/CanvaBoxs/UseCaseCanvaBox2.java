package CanvaBoxs;

import java.util.ArrayList;

import Calculate.ScreenDetail;
import Canvas.UC_ActionLine;
import Canvas.UC_Actor;
import Canvas.UC_Box;
import Canvas.UC_ExtendLine;
import Canvas.UC_IncludeLine;
import Canvas.UC_ProcessCycle;
import Canvas.UC_TypeOfLine;
import Database.ToolHandler;
import Library.MyGridLine;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UseCaseCanvaBox2 extends Pane {

	private boolean isNew;
	protected ScreenDetail screen;

	private ToolHandler toolHandler;
	private Color color;
	private DropShadow shape;
	private Scene owner;

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

	// ExtendLine
	private ArrayList<UC_ExtendLine> extendLines;
	private UC_ExtendLine extendLine;
	private boolean isExtendLine;

	// IncludeLine
	private ArrayList<UC_IncludeLine> includeLines;
	private UC_IncludeLine includeLine;
	private boolean isIncludeLine;

	// TypeOf
	private ArrayList<UC_TypeOfLine> typeofLines;
	private UC_TypeOfLine typeofLine;
	private boolean isTypeofLine;

	public UseCaseCanvaBox2(Scene owner) {
		this.owner = owner;
		toolHandler = new ToolHandler();
		if (toolHandler.getGrid().equals("Show")) {
			drawGridLines();
		}
		init();
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				// Is New or Edit///////////////
				isNewOrEdit(key);
				if (isNew) {
					/// Get Color and Tool//////////
					toolHandler = new ToolHandler();
					color = Color.web(toolHandler.getColor());
					//////////////////////////////
					switch (toolHandler.getTool()) {
					case "UseCase_Actor":
						actor = new UC_Actor(key.getX(), key.getY(), 20, color, Color.GRAY);
						isActor = true;
						getChildren().addAll(actor, actor.getBody(), actor.getLeg(), actor.getLeg2(), actor.getLeg3(),
								actor.getLeg4(), actor.getLabel());
						break;
					case "UseCase_Action":
						actionLine = new UC_ActionLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isActionLine = true;
						getChildren().addAll(actionLine);
						break;
					case "UseCase_Box":
						box = new UC_Box(key.getX(), key.getY(), 300, 400, color, Color.GRAY);
						isBox = true;
						getChildren().addAll(box, box.getLabel());
						break;
					case "UseCase_Process":
						processCycle = new UC_ProcessCycle(key.getX(), key.getY(), 60, 30, color, Color.BLACK);
						isProcessCycle = true;
						getChildren().addAll(processCycle, processCycle.getLabel());
						break;
					case "UseCase_Extend":
						extendLine = new UC_ExtendLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isExtendLine = true;
						getChildren().add(extendLine);
						break;
					case "UseCase_Include":
						includeLine = new UC_IncludeLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isIncludeLine = true;
						getChildren().add(includeLine);
						break;
					case "UseCase_Type":
						typeofLine = new UC_TypeOfLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isTypeofLine = true;
						getChildren().add(typeofLine);
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
				if (isExtendLine) {
					extendLine.setEndX(e.getX());
					extendLine.setEndY(e.getY());
				}
				if (isIncludeLine) {
					includeLine.setEndX(e.getX());
					includeLine.setEndY(e.getY());
				}
				if (isTypeofLine) {
					typeofLine.setEndX(e.getX());
					typeofLine.setEndY(e.getY());
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
					getChildren().add(box.getText(false));
					boxs.add(box);
					isBox = false;
				}
				if (isProcessCycle) {
					getChildren().add(processCycle.getText(false));
					processCycles.add(processCycle);
					isProcessCycle = false;
				}
				if (isExtendLine) {
					LineArrowHead(extendLine);
					Linelabel(extendLine, "extend");
					extendLines.add(extendLine);
					isExtendLine = false;
				}
				if (isIncludeLine) {
					LineArrowHead(includeLine);
					Linelabel(includeLine, "include");
					includeLines.add(includeLine);
					isIncludeLine = false;
				}
				if (isTypeofLine) {
					LineTriangleHead(typeofLine);
					typeofLines.add(typeofLine);
					isTypeofLine = false;
				}
			}
		});

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
		extendLines = new ArrayList<UC_ExtendLine>();
		includeLines = new ArrayList<UC_IncludeLine>();
		typeofLines = new ArrayList<UC_TypeOfLine>();
	}

	public void isNewOrEdit(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditActor(e, point);
		isNewOREditAction(e, point);
		isNewOREditBox(e, point);
		isNewOREditProcess(e, point);
	}

	public void isNewOREditActor(MouseEvent e, Point2D point) {
		// Actor
		int i;
		for (i = 0; i < actors.size(); i++) {
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

				// Delete
				actors.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				actors.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (actors.size() > 0) {
							getChildren().removeAll(actors.get(index), actors.get(index).getBody(),
									actors.get(index).getLeg(), actors.get(index).getLeg2(),
									actors.get(index).getLeg3(), actors.get(index).getLeg4(),
									actors.get(index).getLabel());
							actors.remove(index);
						} else {
							System.out.println("No Actor to delete");
						}

					}
				});

			} else {
				actors.get(i).setEffect(null);
			}

			// Linked
			for (int k = 0; k < actionLines.size(); k++) {
				Rectangle rc = new Rectangle(actors.get(i).getCenterX() - 30, actors.get(i).getCenterY() - 30, 60, 100);
				Point2D p = new Point2D(actionLines.get(k).getStartX(), actionLines.get(k).getStartY());
				if (rc.contains(p)) {
					double difx = actors.get(i).getCenterX() - actionLines.get(k).getStartX();
					double dify = actors.get(i).getCenterY() - actionLines.get(k).getStartY();
					actionLines.get(k).startXProperty().bind(actors.get(i).centerXProperty().add(difx + 30));
					actionLines.get(k).startYProperty().bind(actors.get(i).centerYProperty());
					actors.get(i).toFront();
				}

			}

		}
	}

	public void isNewOREditAction(MouseEvent e, Point2D point) {
		for (int i = 0; i < actionLines.size(); i++) {
			// Line x = new Line(point.getX() - 5, point.getY(), point.getX() +
			// 5, point.getY());
			// Line y = new Line(point.getX(), point.getY() - 5, point.getX(),
			// point.getY() + 5);
			int index = i;
			boolean isclick = actionLines.get(i).contains(point.getX(), point.getY())
					|| actionLines.get(i).contains(point.getX() - 1, point.getY())
					|| actionLines.get(i).contains(point.getX() - 2, point.getY())
					|| actionLines.get(i).contains(point.getX() - 3, point.getY())
					|| actionLines.get(i).contains(point.getX() - 4, point.getY())
					|| actionLines.get(i).contains(point.getX() - 5, point.getY())
					|| actionLines.get(i).contains(point.getX() + 1, point.getY())
					|| actionLines.get(i).contains(point.getX() + 2, point.getY())
					|| actionLines.get(i).contains(point.getX() + 3, point.getY())
					|| actionLines.get(i).contains(point.getX() + 4, point.getY())
					|| actionLines.get(i).contains(point.getX() + 5, point.getY())
					|| actionLines.get(i).contains(point.getX(), point.getY() - 1)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 2)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 3)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 4)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 5)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 1)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 2)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 3)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 4)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				actionLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				actionLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (actionLines.size() > 0) {
							getChildren().removeAll(actionLines.get(index));
							actionLines.remove(index);
						} else {
							System.out.println("No ActionLine to delete");
						}
					}
				});
				actionLines.get(i).setEffect(shape);
				// getChildren().addAll(x, y);
			} else {
				actionLines.get(i).setEffect(null);
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
				boxs.get(i).addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						if (key.getClickCount() == 2) {
							boxs.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									if (e.getCode() == KeyCode.ENTER) {
										boxs.get(index).setTextInVisible();
									}
								}
							});
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
							processCycles.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									if (e.getCode() == KeyCode.ENTER) {
										DoubleProperty width = new SimpleDoubleProperty();
										width.set(processCycles.get(index).getLabel().layoutBoundsProperty().getValue()
												.getWidth());
										processCycles.get(index).radiusXProperty().bind(width);
										processCycles.get(index).getLabel()
												.layoutXProperty().bind(
														processCycles.get(index).centerXProperty()
																.subtract(processCycles.get(index).getLabel()
																		.layoutBoundsProperty().getValue().getWidth()
																		/ 2));
										processCycles.get(index).setTextInVisible();
									}
								}
							});
						}
					}
				});

				// Delete
				processCycles.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				processCycles.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (processCycles.size() > 0) {
							getChildren().removeAll(processCycles.get(index), processCycles.get(index).getLabel(),
									processCycles.get(index).getText(false));
							processCycles.remove(index);
						} else {
							System.out.println("No ProcessCycle to delete");
						}
					}
				});

			} else {
				processCycles.get(i).setEffect(null);
			}

			// Linked
			if (actionLines.size() > 0) {
				for (int k = 0; k < actionLines.size(); k++) {
					Point2D p = new Point2D(actionLines.get(k).getEndX(), actionLines.get(k).getEndY());
					if (processCycles.get(i).contains(p)) {
						actionLines.get(k).endXProperty().bind(processCycles.get(i).centerXProperty());
						actionLines.get(k).endYProperty().bind(processCycles.get(i).centerYProperty());
						processCycles.get(i).toFront();
						processCycles.get(i).getLabel().toFront();
					}
				}
			}
		}
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

	public void LineArrowHead(Line line) {
		double startx = line.getStartX();
		double starty = line.getStartY();
		double endx = line.getEndX();
		double endy = line.getEndY();

		// Arrow Head
		double x, y, length;
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

	public void LineTriangleHead(UC_TypeOfLine line) {

		double startx = line.getStartX();
		double starty = line.getStartY();
		double endx = line.getEndX();
		double endy = line.getEndY();

		// Arrow Head
		double x, y, length;
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
		getChildren().addAll(path);

	}

	public void drawGridLines() {
		int i = 10;
		while (i < 800) {
			MyGridLine l = new MyGridLine(10, i, 1340, i);
			getChildren().add(l);
			i = i + 20;
		}
		int k = 10;
		while (k < 1340) {
			MyGridLine l = new MyGridLine(k, 10, k, 690);
			getChildren().add(l);
			k = k + 20;
		}
	}

	public Scene getOwner() {
		return owner;
	}

}
