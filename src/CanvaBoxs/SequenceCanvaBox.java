package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;

import Canvas.SE_Activation;
import Canvas.SE_DestroyActivation;
import Canvas.SE_NewActivation;
import Canvas.SE_Role;
import Canvas.SE_SelfActivation;
import Database.ToolHandler;
import Library.SaveDiagramXML;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
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
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SequenceCanvaBox extends CanvasPane {

	// Role
	private ArrayList<SE_Role> roles;
	private SE_Role role;
	private boolean isRole;

	// Normal Activation
	private ArrayList<SE_Activation> anormals;
	private SE_Activation anormal;
	private boolean isActivation;

	// New Object Activation
	private ArrayList<SE_NewActivation> anews;
	private SE_NewActivation anew;
	private boolean isNewActivation;

	// Object Destroy Activation
	private ArrayList<SE_DestroyActivation> dnews;
	private SE_DestroyActivation dnew;
	private boolean isDestroyActivation;

	// Self Activation
	private ArrayList<SE_SelfActivation> snews;
	private SE_SelfActivation snew;
	private boolean isSelfActivation;
	private boolean callSelf;

	public SequenceCanvaBox(Scene owner, File path, boolean isLoad) {
		setOwner(owner);
		setPath(path);
		roles = new ArrayList<SE_Role>();
		anormals = new ArrayList<SE_Activation>();
		dnews = new ArrayList<SE_DestroyActivation>();
		anews = new ArrayList<SE_NewActivation>();
		snews = new ArrayList<SE_SelfActivation>();

		if (isLoad) {

		}

		if (toolHandler.getGrid().equals("Show")) {
			setGridLine();
		}

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isNewOrEdit(e);

				if (isNew) {
					toolHandler = new ToolHandler();
					color = Color.web(toolHandler.getColor());

					switch (toolHandler.getTool()) {

					case "Sequence_Role":
						role = new SE_Role(e.getX(), e.getY(), color);
						isRole = true;
						getChildren().addAll(role, role.getLabel(), role.getLife(), role.getText(false));
						break;
					case "Sequence_ANormal":
						anormal = new SE_Activation(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isActivation = true;
						getChildren().addAll(anormal);
						break;
					case "Sequence_ANObject":
						anew = new SE_NewActivation(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isNewActivation = true;
						getChildren().add(anew);
						break;

					case "Sequence_ADObject":
						dnew = new SE_DestroyActivation(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isDestroyActivation = true;
						getChildren().add(dnew);
						break;

					default:
						break;
					}
				}
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				if (isRole) {
					role.setX(e.getX());
					role.setY(e.getY());
				}
				if (isActivation) {
					anormal.setEndX(e.getX());
					anormal.setEndY(e.getY());
				}
				if (isNewActivation) {
					anew.setEndX(e.getX());
					anew.setEndY(e.getY());
				}
				if (isDestroyActivation) {
					dnew.setEndX(e.getX());
					dnew.setEndY(e.getY());
				}

			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isRole) {
					roles.add(role);
					isRole = false;
				}
				if (isActivation) {
					if (anormal.getStartX() < anormal.getEndX()) {
						getChildren().addAll(anormal.getTop(), anormal.getBot(), anormal.getRect(), anormal.getRLine(),
								anormal.getRTop(), anormal.getRBot());
						anormals.add(anormal);
					}else{
						getChildren().remove(anormal);
						System.out.println(" This Type of Activation is not allowed");
					}
					isActivation = false;

				}
				if (isNewActivation) {
					drawNewActivation(anew);
					anews.add(anew);
					isNewActivation = false;
				}
				if (isDestroyActivation) {
					drawDestroyActivation(dnew);
					dnews.add(dnew);
					isDestroyActivation = false;
				}

			}
		});

	}

	public void isNewOrEdit(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditRole(e, point);
		isNewOREditNormalActivation(e, point);
		isNewOREditNewActivation(e, point);
		isNewOREditSelfActivation(e, point);
	}

	public void drawNewActivation(SE_NewActivation anew) {
		Line top = new Line();
		top.startXProperty().bind(anew.endXProperty());
		top.startYProperty().bind(anew.endYProperty());
		top.endXProperty().bind(anew.endXProperty().subtract(10));
		top.endYProperty().bind(anew.endYProperty().subtract(5));
		Line bot = new Line();
		bot.startXProperty().bind(anew.endXProperty());
		bot.startYProperty().bind(anew.endYProperty());
		bot.endXProperty().bind(anew.endXProperty().subtract(10));
		bot.endYProperty().bind(anew.endYProperty().add(5));

		// Role
		Rectangle newOb = anew.getNewOb();
		newOb.setX(anew.getEndX());
		newOb.setY(anew.getEndY() - 20);
		newOb.setWidth(100);
		newOb.setHeight(40);

		anew.endXProperty().bind(newOb.xProperty());
		anew.endYProperty().bind(newOb.yProperty().add(20));

		// Role Label
		Text label = new Text(anew.labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(anew.labelProperty());
		label.layoutXProperty()
				.bind(anew.getNewOb().xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.layoutYProperty().bind(anew.getNewOb().yProperty().add(20));

		// Life
		Rectangle life = anew.getRect();
		life.setX(anew.getEndX() + (newOb.getWidth() / 2) - 10);
		life.setY(anew.getEndY() + 20);
		life.setWidth(20);
		life.setHeight(100);

		life.xProperty().bind(newOb.xProperty().add(newOb.getWidth() / 2).subtract(10));
		life.yProperty().bind(newOb.yProperty().add(newOb.getHeight()));
		life.heightProperty().bind(anew.lifeProperty());

		// Life Line

		DoubleProperty length = new SimpleDoubleProperty();
		length.bind(anew.newlifeProperty().add(10));
		Line rline = new Line(anew.getNewOb().getX() + (anew.getNewOb().getWidth() / 2),
				anew.getNewOb().getY() + anew.getNewOb().getHeight(),
				anew.getNewOb().getX() + (anew.getNewOb().getWidth() / 2),
				anew.getNewOb().getY() + length.doubleValue());
		rline.setStroke(color);
		rline.getStrokeDashArray().addAll(10d, 10d);
		rline.startXProperty().bind(anew.getNewOb().xProperty().add(anew.getNewOb().getWidth() / 2));
		rline.startYProperty().bind(anew.getNewOb().yProperty().add(anew.getNewOb().getHeight()));
		rline.endXProperty().bind(anew.getNewOb().xProperty().add(anew.getNewOb().getWidth() / 2));
		rline.endYProperty().bind(length.add(anew.getNewOb().yProperty()));

		// Return line
		Line line = new Line(anew.getEndX(), anew.getEndY() + life.getHeight(), anew.getStartX(),
				anew.getStartY() + life.getHeight());
		line.getStrokeDashArray().addAll(10d, 5d);
		line.startXProperty().bind(anew.getRect().xProperty());
		line.startYProperty().bind(anew.getRect().yProperty().add(anew.lifeProperty()));
		line.endYProperty().bind(anew.getRect().yProperty().add(anew.lifeProperty()));
		Line rtop = new Line();
		rtop.startXProperty().bind(line.endXProperty());
		rtop.startYProperty().bind(line.endYProperty());
		rtop.endXProperty().bind(line.endXProperty().add(10));
		rtop.endYProperty().bind(line.endYProperty().subtract(5));
		Line rbot = new Line();
		rbot.startXProperty().bind(line.endXProperty());
		rbot.startYProperty().bind(line.endYProperty());
		rbot.endXProperty().bind(line.endXProperty().add(10));
		rbot.endYProperty().bind(line.endYProperty().add(5));

		getChildren().addAll(top, bot, newOb, rline, life, label, line, rtop, rbot);
	}

	public void drawDestroyActivation(SE_DestroyActivation dnew) {
		double endx = dnew.getEndX();
		double endy = dnew.getEndY();

		// Arrow Head
		Line top = new Line();
		top.setStroke(color);
		top.startXProperty().bind(dnew.endXProperty());
		top.startYProperty().bind(dnew.endYProperty());
		top.endXProperty().bind(dnew.endXProperty().subtract(10));
		top.endYProperty().bind(dnew.endYProperty().subtract(5));
		Line bot = new Line();
		bot.setStroke(color);
		bot.startXProperty().bind(dnew.endXProperty());
		bot.startYProperty().bind(dnew.endYProperty());
		bot.endXProperty().bind(dnew.endXProperty().subtract(10));
		bot.endYProperty().bind(dnew.endYProperty().add(5));

		Line c1 = new Line(endx - 20, endy - 20, endx + 20, endy + 20);
		c1.setStrokeWidth(3);
		Line c2 = new Line(endx - 20, endy + 20, endx + 20, endy - 20);
		c2.setStrokeWidth(3);

		getChildren().addAll(top, bot, c1, c2);
	}

	public void drawSelfActivation(SE_SelfActivation snew) {
		double startx = snew.getX();
		double centery = snew.getY();

		Arc arc1 = new Arc(startx + 10, centery - 10, 60, 10, 270, 180);
		arc1.setFill(Color.TRANSPARENT);
		arc1.setStroke(Color.BLACK);

		Arc arc2 = new Arc(startx + 10, centery + snew.getHeight() + 10, 60, 10, 270, 180);
		arc2.getStrokeDashArray().addAll(5d, 5d);
		arc2.setFill(Color.TRANSPARENT);
		arc2.setStroke(Color.BLACK);

		arc1.centerYProperty().bind(snew.yProperty().subtract(10));

		snew.yProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				arc2.centerYProperty().bind(snew.yProperty().add(snew.getHeight()).add(10));
			}
		});
		snew.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				System.out.println("Change due to Height Property");
				arc2.centerYProperty().bind(snew.heightProperty().add(snew.getY()).add(10));
			}
		});

		getChildren().addAll(arc1, arc2);
	}

	public void isNewOREditRole(MouseEvent e, Point2D point) {
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).contains(point)) {
				isNew = false;
				int index = i;
				roles.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						roles.get(index).setX(key.getX());
						roles.get(index).setY(key.getY());

					}
				});

				roles.get(i).setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							roles.get(index).lifeProperty().set(roles.get(index).lifeProperty().getValue() + 10);
						} else {
							roles.get(index).lifeProperty().set(roles.get(index).lifeProperty().getValue() - 10);
						}
					}
				});

				roles.get(i).getLabel().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						Button x = null;
						if (key.getClickCount() == 2) {
							// Edit Actor Label
							roles.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									DoubleProperty width = new SimpleDoubleProperty();
									width.set(roles.get(index).getLabel().layoutBoundsProperty().getValue().getWidth());
									roles.get(index).getLabel().xProperty()
											.bind(roles.get(index).xProperty()
													.add(roles.get(index).widthProperty().getValue() / 2)
													.subtract(roles.get(index).getLabel().layoutBoundsProperty()
															.getValue().getWidth() / 2));
									roles.get(index).widthProperty().bind(width.add(30));
									if (e.getCode() == KeyCode.ENTER) {
										roles.get(index).setTextInVisible();
									}
								}
							});
						}
					}
				});

				// Delete
				roles.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				roles.get(i).setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (roles.size() > 0) {
							getChildren().removeAll(roles.get(index), roles.get(index).getLabel());
							roles.remove(index);
						} else {
							System.out.println("No Object to delete");
						}
					}
					// Print
					if (key.getCode() == KeyCode.PRINTSCREEN) {
						if (defaultprinter == null) {
							defaultprinter = Printer.getDefaultPrinter();
							pageLayout = defaultprinter.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
									Printer.MarginType.HARDWARE_MINIMUM);
						}
						roles.get(index).setEffect(null);
						getChildren().remove(gridLine);
						PrintNode(this, pageLayout);
						getChildren().add(gridLine);
						gridLine.toBack();
					}
					// Save
					if (key.getCode() == KeyCode.F1) {
						if (save == null) {
							save = new SaveDiagramXML(path);
						}
						save.saveSequenceCavaBox(roles, anormals, anews, snews, dnews);
						System.out.println("****Save*****");
					}
				});

				roles.get(i).setEffect(shape);
			} else {
				roles.get(i).setEffect(null);
			}
		}
	}

	public void isNewOREditNormalActivation(MouseEvent e, Point2D point) {
		for (int i = 0; i < anormals.size(); i++) {
			if (anormals.get(i).getRect().contains(point) || anormals.get(i).contains(point)) {
				isNew = false;
				int index = i;

				// is Self Loop
				anormals.get(i).getRect().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						toolHandler = new ToolHandler();
						if (toolHandler.getTool().equals("Sequence_ASLoop")) {
							toolHandler.setTool("");
							snew = new SE_SelfActivation(Color.LIGHTGRAY);
							snew.setY(e.getY());
							snew.xProperty().bind(anormals.get(index).getRect().xProperty().add(10));
							drawSelfActivation(snew);
							snews.add(snew);
							getChildren().add(snew);
						}

					}
				});

				anormals.get(i).getRect().addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						anormals.get(index).getRect().setX(e.getX());
						anormals.get(index).getRect().setY(e.getY());
					}
				});

				anormals.get(i).getRect().setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							anormals.get(index).lifeProperty().set(anormals.get(index).lifeProperty().get() + 10);
						} else {
							anormals.get(index).lifeProperty().set(anormals.get(index).lifeProperty().get() - 10);
						}
					}
				});

				anormals.get(i).getRect().setEffect(shape);
			} else {
				anormals.get(i).getRect().setEffect(null);
			}
		}
	}

	public void isNewOREditNewActivation(MouseEvent e, Point2D point) {
		for (int i = 0; i < anews.size(); i++) {
			int index = i;
			if (anews.get(i).getRect().contains(point)) {
				isNew = false;

				anews.get(i).getRect().setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							anews.get(index).lifeProperty().set(anews.get(index).lifeProperty().get() + 10);
						} else {
							anews.get(index).lifeProperty().set(anews.get(index).lifeProperty().get() - 10);
						}
					}
				});

				anews.get(i).getRect().setEffect(shape);
			} else {
				anews.get(i).getRect().setEffect(null);
			}

			if (anews.get(i).getNewOb().contains(point)) {
				isNew = false;

				anews.get(i).getNewOb().addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						anews.get(index).getNewOb().setX(e.getX());
						anews.get(index).getNewOb().setY(e.getY());
					}
				});

				anews.get(i).getNewOb().setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							anews.get(index).newlifeProperty().set(anews.get(index).newlifeProperty().get() + 10);
						} else {
							anews.get(index).newlifeProperty().set(anews.get(index).newlifeProperty().get() - 10);
						}
					}
				});

				anews.get(i).getNewOb().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						Button x = null;
						if (key.getClickCount() == 2) {
							// Edit Actor Label
							TextField data = new TextField();
							data.layoutXProperty().bind(anews.get(index).getNewOb().xProperty().add(60));
							data.layoutYProperty().bind(anews.get(index).getNewOb().yProperty().add(60));
							getChildren().add(data);
							data.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									if (e.getCode() == KeyCode.ENTER) {
										if (!data.getText().equals("")) {
											anews.get(index).labelProperty().set(data.getText().trim());
											Text w = new Text(data.getText().trim());
											anews.get(index).getNewOb().widthProperty().bind(new SimpleDoubleProperty(
													w.layoutBoundsProperty().getValue().getWidth()).add(60));
										}
										data.setVisible(!data.isVisible());
									}
								}
							});
						}
					}
				});

				anews.get(i).getNewOb().setEffect(shape);
			} else {
				anews.get(i).getNewOb().setEffect(null);
			}
		}
	}

	public void isNewOREditSelfActivation(MouseEvent e, Point2D point) {
		for (int i = 0; i < snews.size(); i++) {
			if (snews.get(i).contains(point)) {
				int index = i;
				// Listener

				snews.get(i).setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							snews.get(index).setHeight(snews.get(index).getHeight() + 10);
						} else {
							snews.get(index).setHeight(snews.get(index).getHeight() - 10);
						}
					}
				});

				snews.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						snews.get(index).setY(e.getY());
					}
				});

				snews.get(i).setEffect(shape);
			} else {
				snews.get(i).setEffect(null);
			}
		}
	}
}
