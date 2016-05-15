package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import Canvas.SE_Activation;
import Canvas.SE_DestroyActivation;
import Canvas.SE_NewActivation;
import Canvas.SE_Role;
import Canvas.SE_SelfActivation;
import Canvas.UC_ActionLine;
import Canvas.UC_Actor;
import Canvas.UC_Box;
import Canvas.UC_ExtendLine;
import Canvas.UC_IncludeLine;
import Canvas.UC_ProcessCycle;
import Canvas.UC_TypeOfLine;
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
			try {
				dbFactory = DocumentBuilderFactory.newInstance();
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
			loadXMLData("Roles");
			loadXMLData("ANormals");
			loadXMLData("ANObjects");
			loadXMLData("ADObjects");
			loadXMLData("ASLoops");
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
					if (anew.getStartX() < anew.getEndX()) {
						getChildren().addAll(anew.getTop(), anew.getBot(), anew.getNewOb(), anew.getNLine(),
								anew.getLabel(), anew.getLifeBox(), anew.getRLine(), anew.getRTop(), anew.getRBot(),
								anew.getText(false));
						anews.add(anew);
					} else {
						getChildren().remove(anew);
						System.out.println(" This Type of New Object Activation is not allowed");
					}
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

				anormals.get(i).getRect().onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				anormals.get(i).getRect().setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (anormals.size() > 0) {
							getChildren().removeAll(anormals.get(index), anormals.get(index).getTop(),
									anormals.get(index).getBot(), anormals.get(index).getRect(),
									anormals.get(index).getRLine(), anormals.get(index).getRTop(),
									anormals.get(index).getRBot());
							anormals.remove(index);
						} else {
							System.out.println("No Normal Activation to delete");
						}
					}
					// Print
					if (key.getCode() == KeyCode.PRINTSCREEN) {
						if (defaultprinter == null) {
							defaultprinter = Printer.getDefaultPrinter();
							pageLayout = defaultprinter.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
									Printer.MarginType.HARDWARE_MINIMUM);
						}
						anormals.get(index).setEffect(null);
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

				anews.get(i).getLifeBox().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						toolHandler = new ToolHandler();
						if (toolHandler.getTool().equals("Sequence_ASLoop")) {
							toolHandler.setTool("");
							snew = new SE_SelfActivation(Color.LIGHTGRAY);
							snew.setY(e.getY());
							snew.xProperty().bind(anews.get(index).getLifeBox().xProperty().add(10));
							snews.add(snew);
							getChildren().addAll(snew, snew.getArc1(), snew.getArc2(), snew.getTop1(), snew.getTop2(),
									snew.getBot1(), snew.getBot2());
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
				anews.get(i).getNewOb().onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				anews.get(i).getNewOb().setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (anews.size() > 0) {
							getChildren().removeAll(anews.get(index), anews.get(index).getTop(),
									anews.get(index).getBot(), anews.get(index).getNewOb(), anews.get(index).getNLine(),
									anews.get(index).getLabel(), anews.get(index).getLifeBox(),
									anews.get(index).getRLine(), anews.get(index).getRTop(), anews.get(index).getRBot(),
									anews.get(index).getText(false));
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

				snews.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				snews.get(i).setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (snews.size() > 0) {
							getChildren().removeAll(snews.get(index), snews.get(index).getArc1(),
									snews.get(index).getArc2(), snews.get(index).getTop1(), snews.get(index).getTop2(),
									snews.get(index).getBot1(), snews.get(index).getBot2());
							snews.remove(index);
						} else {
							System.out.println("No Self Activation to delete");
						}
					}
					// Print
					if (key.getCode() == KeyCode.PRINTSCREEN) {
						if (defaultprinter == null) {
							defaultprinter = Printer.getDefaultPrinter();
							pageLayout = defaultprinter.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
									Printer.MarginType.HARDWARE_MINIMUM);
						}
						snews.get(index).setEffect(null);
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

				snews.get(i).setEffect(shape);
			} else {
				snews.get(i).setEffect(null);
			}
		}
	}

	public void loadXMLData(String tagName) {
		try {
			switch (tagName) {
			case "Roles":
				org.w3c.dom.Node node = doc.getElementsByTagName("Roles").item(0);
				NodeList nodes = node.getChildNodes();
				for (int i = 0; i < nodes.getLength(); i++) {
					org.w3c.dom.Node data = nodes.item(i);
					NodeList datas = data.getChildNodes();
					double x = 0, y = 0, w = 0, h = 0, life = 0;
					String label = null;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x".equals(element.getNodeName())) {
							x = Double.parseDouble(element.getTextContent());
						}
						if ("y".equals(element.getNodeName())) {
							y = Double.parseDouble(element.getTextContent());
						}
						if ("width".equals(element.getNodeName())) {
							w = Double.parseDouble(element.getTextContent());
						}
						if ("height".equals(element.getNodeName())) {
							h = Double.parseDouble(element.getTextContent());
						}
						if ("label".equals(element.getNodeName())) {
							label = element.getTextContent();
						}
						if ("life".equals(element.getNodeName())) {
							life = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					SE_Role role = new SE_Role(x, y, color);
					role.setWidth(w);
					role.setHeight(h);
					role.labelProperty().set(label);
					role.lifeProperty().set(life);
					roles.add(role);
					DoubleProperty width = new SimpleDoubleProperty();
					width.set(role.getLabel().layoutBoundsProperty().getValue().getWidth());
					role.getLabel().xProperty().bind(role.xProperty().add(role.widthProperty().getValue() / 2)
							.subtract(role.getLabel().layoutBoundsProperty().getValue().getWidth() / 2));
					role.widthProperty().bind(width.add(30));
					getChildren().addAll(role, role.getLabel(), role.getLife(), role.getText(false));
				}
				break;

			case "ANormals":
				org.w3c.dom.Node anNode = doc.getElementsByTagName("ANormals").item(0);
				NodeList anNodes = anNode.getChildNodes();
				for (int i = 0; i < anNodes.getLength(); i++) {
					org.w3c.dom.Node data = anNodes.item(i);
					NodeList datas = data.getChildNodes();
					double x1 = 0, y1 = 0, x2 = 0, y2 = 0, life = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x1".equals(element.getNodeName())) {
							x1 = Double.parseDouble(element.getTextContent());
						}
						if ("y1".equals(element.getNodeName())) {
							y1 = Double.parseDouble(element.getTextContent());
						}
						if ("x2".equals(element.getNodeName())) {
							x2 = Double.parseDouble(element.getTextContent());
						}
						if ("y2".equals(element.getNodeName())) {
							y2 = Double.parseDouble(element.getTextContent());
						}
						if ("life".equals(element.getNodeName())) {
							life = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					SE_Activation nactive = new SE_Activation(x1, y1, x2, y2, color);
					nactive.lifeProperty().set(life);
					anormals.add(nactive);
					getChildren().addAll(nactive, nactive.getTop(), nactive.getBot(), nactive.getRect(),
							nactive.getRLine(), nactive.getRTop(), nactive.getRBot());

				}
				break;

			case "ANObjects":
				org.w3c.dom.Node newNode = doc.getElementsByTagName("ANObjects").item(0);
				NodeList newNodes = newNode.getChildNodes();
				for (int i = 0; i < newNodes.getLength(); i++) {
					org.w3c.dom.Node data = newNodes.item(i);
					NodeList datas = data.getChildNodes();
					double x1 = 0, y1 = 0, x2 = 0, y2 = 0, life = 0, lifep = 0;
					String label = null;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x1".equals(element.getNodeName())) {
							x1 = Double.parseDouble(element.getTextContent());
						}
						if ("y1".equals(element.getNodeName())) {
							y1 = Double.parseDouble(element.getTextContent());
						}
						if ("x2".equals(element.getNodeName())) {
							x2 = Double.parseDouble(element.getTextContent());
						}
						if ("y2".equals(element.getNodeName())) {
							y2 = Double.parseDouble(element.getTextContent());
						}
						if ("label".equals(element.getNodeName())) {
							label = element.getTextContent();
						}

						if ("life".equals(element.getNodeName())) {
							life = Double.parseDouble(element.getTextContent());
						}
						if ("lifep".equals(element.getNodeName())) {
							lifep = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					SE_NewActivation newsAC = new SE_NewActivation(x1, y1, x2, y2, color);
					newsAC.lifeProperty().set(life);
					newsAC.lifeProperty().set(lifep);
					newsAC.labelProperty().set(label);
					anews.add(newsAC);
					DoubleProperty width = new SimpleDoubleProperty();
					width.set(newsAC.getLabel().layoutBoundsProperty().getValue().getWidth());
					newsAC.getNewOb().widthProperty().bind(width.add(30));
					newsAC.getLabel().xProperty().bind(newsAC.getNewOb().xProperty().add(15));

					getChildren().addAll(newsAC, newsAC.getTop(), newsAC.getBot(), newsAC.getNewOb(), newsAC.getNLine(),
							newsAC.getLabel(), newsAC.getLifeBox(), newsAC.getRLine(), newsAC.getRTop(),
							newsAC.getRBot(), newsAC.getText(false));

				}
				break;

			case "ADObjects":
				org.w3c.dom.Node dNode = doc.getElementsByTagName("ADObjects").item(0);
				NodeList dNodes = dNode.getChildNodes();
				for (int i = 0; i < dNodes.getLength(); i++) {
					org.w3c.dom.Node data = dNodes.item(i);
					NodeList datas = data.getChildNodes();
					double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x1".equals(element.getNodeName())) {
							x1 = Double.parseDouble(element.getTextContent());
						}
						if ("y1".equals(element.getNodeName())) {
							y1 = Double.parseDouble(element.getTextContent());
						}
						if ("x2".equals(element.getNodeName())) {
							x2 = Double.parseDouble(element.getTextContent());
						}
						if ("y2".equals(element.getNodeName())) {
							y2 = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					SE_DestroyActivation dAC = new SE_DestroyActivation(x1, y1, x2, y2, color);
					getChildren().addAll(dAC, dAC.getTop(), dAC.getBot(), dAC.getC1(), dAC.getC2());
					dnews.add(dAC);

				}
				break;

			case "ASLoops":
				org.w3c.dom.Node sNode = doc.getElementsByTagName("ASLoops").item(0);
				NodeList sNodes = sNode.getChildNodes();
				for (int i = 0; i < sNodes.getLength(); i++) {
					org.w3c.dom.Node data = sNodes.item(i);
					NodeList datas = data.getChildNodes();
					double x = 0, y = 0, h = 0;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x".equals(element.getNodeName())) {
							x = Double.parseDouble(element.getTextContent());
						}
						if ("y".equals(element.getNodeName())) {
							y = Double.parseDouble(element.getTextContent());
						}
						if ("height".equals(element.getNodeName())) {
							h = Double.parseDouble(element.getTextContent());
						}
					}
					SE_SelfActivation sAC = new SE_SelfActivation(color);
					sAC.setX(x);
					sAC.setY(y);
					snews.add(sAC);
					getChildren().addAll(sAC, sAC.getArc1(), sAC.getArc2(), sAC.getTop1(), sAC.getTop2(),
							sAC.getBot1(), sAC.getBot2());

				}
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
