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
						getChildren().addAll(anew);
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
					} else {
						getChildren().remove(anormal);
						System.out.println(" This Type of Activation is not allowed");
					}
					isActivation = false;

				}
				if (isNewActivation) {
					getChildren().addAll(anew.getTop(), anew.getBot(), anew.getNewOb(), anew.getNLine(),
							anew.getLabel(), anew.getLifeBox(), anew.getRLine(), anew.getRTop(), anew.getRBot(),
							anew.getText(false));
					anews.add(anew);
					isNewActivation = false;
				}
				if (isDestroyActivation) {
					getChildren().addAll(dnew.getTop(), dnew.getBot(), dnew.getC1(), dnew.getC2());
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
		isNewOREditDelActivation(e, point);
		isNewOREditSelfActivation(e, point);
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
							getChildren().removeAll(roles.get(index), roles.get(index).getLabel(),
									roles.get(index).getLife());
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
							snews.add(snew);
							getChildren().addAll(snew, snew.getArc1(), snew.getArc2(), snew.getTop1(), snew.getTop2(),
									snew.getBot1(), snew.getBot2());
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
			//////////////////////
			if (anews.get(i).getLifeBox().contains(point)) {
				isNew = false;

				anews.get(i).getLifeBox().setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							anews.get(index).lifeProperty().set(anews.get(index).lifeProperty().get() + 10);
						} else {
							anews.get(index).lifeProperty().set(anews.get(index).lifeProperty().get() - 10);
						}
					}
				});

				anews.get(i).getLifeBox().setEffect(shape);
			} else {
				anews.get(i).getLifeBox().setEffect(null);
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
							anews.get(index).lifepProperty().set(anews.get(index).lifepProperty().get() + 10);
						} else {
							anews.get(index).lifepProperty().set(anews.get(index).lifepProperty().get() - 10);
						}
					}
				});

				anews.get(i).getLabel().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						Button x = null;
						if (key.getClickCount() == 2) {
							// Edit Actor Label
							anews.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									DoubleProperty width = new SimpleDoubleProperty();
									width.set(anews.get(index).getLabel().layoutBoundsProperty().getValue().getWidth());
									anews.get(index).getLabel().xProperty()
											.bind(anews.get(index).getNewOb().xProperty()
													.add(anews.get(index).getNewOb().widthProperty().getValue() / 2)
													.subtract(anews.get(index).getLabel().layoutBoundsProperty()
															.getValue().getWidth() / 2));
									anews.get(index).getNewOb().widthProperty().bind(width.add(30));
									if (e.getCode() == KeyCode.ENTER) {
										anews.get(index).setTextInVisible();
									}
								}
							});
						}
					}
				});

				// Delete
				anews.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				anews.get(i).setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (anews.size() > 0) {
							getChildren().removeAll(anews.get(index), roles.get(index).getLabel());
							anews.remove(index);
						} else {
							System.out.println("No New Object to delete");
						}
					}
					// Print
					if (key.getCode() == KeyCode.PRINTSCREEN) {
						if (defaultprinter == null) {
							defaultprinter = Printer.getDefaultPrinter();
							pageLayout = defaultprinter.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
									Printer.MarginType.HARDWARE_MINIMUM);
						}
						anews.get(index).setEffect(null);
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

				anews.get(i).getNewOb().setEffect(shape);
			} else {
				anews.get(i).getNewOb().setEffect(null);
			}
		}
	}

	public void isNewOREditDelActivation(MouseEvent e, Point2D point) {
		for (int i = 0; i < dnews.size(); i++) {
			int index = i;
			boolean isclick = dnews.get(i).contains(point.getX(), point.getY())
					|| dnews.get(i).contains(point.getX() - 1, point.getY())
					|| dnews.get(i).contains(point.getX() - 2, point.getY())
					|| dnews.get(i).contains(point.getX() - 3, point.getY())
					|| dnews.get(i).contains(point.getX() - 4, point.getY())
					|| dnews.get(i).contains(point.getX() - 5, point.getY())
					|| dnews.get(i).contains(point.getX() + 1, point.getY())
					|| dnews.get(i).contains(point.getX() + 2, point.getY())
					|| dnews.get(i).contains(point.getX() + 3, point.getY())
					|| dnews.get(i).contains(point.getX() + 4, point.getY())
					|| dnews.get(i).contains(point.getX() + 5, point.getY())
					|| dnews.get(i).contains(point.getX(), point.getY() - 1)
					|| dnews.get(i).contains(point.getX(), point.getY() - 2)
					|| dnews.get(i).contains(point.getX(), point.getY() - 3)
					|| dnews.get(i).contains(point.getX(), point.getY() - 4)
					|| dnews.get(i).contains(point.getX(), point.getY() - 5)
					|| dnews.get(i).contains(point.getX(), point.getY() + 1)
					|| dnews.get(i).contains(point.getX(), point.getY() + 2)
					|| dnews.get(i).contains(point.getX(), point.getY() + 3)
					|| dnews.get(i).contains(point.getX(), point.getY() + 4)
					|| dnews.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;
				// Delete
				dnews.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				dnews.get(i).setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (dnews.size() > 0) {
							getChildren().removeAll(dnews.get(index), dnews.get(index).getTop(),
									dnews.get(index).getBot(), dnews.get(index).getC1(), dnews.get(index).getC2());
							dnews.remove(index);
						} else {
							System.out.println("No Delete Object to delete");
						}
					}
					// Print
					if (key.getCode() == KeyCode.PRINTSCREEN) {
						if (defaultprinter == null) {
							defaultprinter = Printer.getDefaultPrinter();
							pageLayout = defaultprinter.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
									Printer.MarginType.HARDWARE_MINIMUM);
						}
						dnews.get(index).setEffect(null);
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

				dnews.get(i).setEffect(shape);
			} else {
				dnews.get(i).setEffect(null);
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
