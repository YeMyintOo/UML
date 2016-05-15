package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import Canvas.C_AbstractClass;
import Canvas.C_Aggregation;
import Canvas.C_Association;
import Canvas.C_Class;
import Canvas.C_Interface;
import Database.ToolHandler;
import Library.SaveDiagramXML;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ClassCanvaBox extends CanvasPane {
	// Class
	private ArrayList<C_Class> cboxs;
	private C_Class cbox;
	private boolean isClass;

	// Abstract Class
	private ArrayList<C_AbstractClass> acboxs;
	private C_AbstractClass acbox;
	private boolean isAbstractClass;

	// Interface Class
	private ArrayList<C_Interface> icboxs;
	private C_Interface icbox;
	private boolean isInterFace;

	// Association
	private ArrayList<C_Association> assos;
	private C_Association asso;
	private boolean isAssociation;

	// Aggregation
	private ArrayList<C_Aggregation> aggs;
	private C_Aggregation agg;
	private boolean isAggregation;

	public ClassCanvaBox(Scene owner, File path, boolean isLoad) {
		setOwner(owner);
		setPath(path);
		cboxs = new ArrayList<C_Class>();
		assos = new ArrayList<C_Association>();
		acboxs = new ArrayList<C_AbstractClass>();
		icboxs = new ArrayList<C_Interface>();
		aggs = new ArrayList<C_Aggregation>();

		if (isLoad) {
			try {
				dbFactory = DocumentBuilderFactory.newInstance();
				dBuilder = dbFactory.newDocumentBuilder();
				doc = dBuilder.parse(path);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (toolHandler.getGrid().equals("Show")) {
			setGridLine();
		}

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				color = Color.web(toolHandler.getColor());

				if (toolHandler.getTool().equals("Class_Aggregation")) {
					agg = new C_Aggregation(e.getX(), e.getY(), e.getX(), e.getY(), Color.LIGHTGRAY);
					isAggregation = true;
					getChildren().addAll(agg);

				} else if (toolHandler.getTool().equals("Class_Association")) {
					asso = new C_Association(e.getX(), e.getY(), e.getX(), e.getY(), Color.LIGHTGRAY);
					isAssociation = true;
					getChildren().addAll(asso);
				} else {
					isNewOREdit(e);
					if (isNew) {
						switch (toolHandler.getTool()) {
						case "Class_Class":
							cbox = new C_Class(e.getX(), e.getY(), color, Color.LIGHTGRAY);
							isClass = true;
							getChildren().addAll(cbox, cbox.getdataBox(), cbox.getfunctionBox(), cbox.getLabel(),
									cbox.getText(false));
							break;
						case "Class_AbstractClass":
							acbox = new C_AbstractClass(e.getX(), e.getY(), 100, 100, color, Color.LIGHTGRAY);
							isAbstractClass = true;
							getChildren().addAll(acbox, acbox.getdataBox(), acbox.getfunctionBox(), acbox.getlabel(),
									acbox.getText(false));
							break;

						case "Class_InterfaceClass":
							icbox = new C_Interface(e.getX(), e.getY(), 100, 100, color, Color.LIGHTGRAY);
							isInterFace = true;
							getChildren().addAll(icbox, icbox.getdataBox(), icbox.getfunctionBox());
							drawInterfaceClassLabel(icbox);
							break;

						default:
							break;
						}
					}
				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isClass) {
					cbox.setX(e.getX());
					cbox.setY(e.getY());
				}
				if (isAbstractClass) {
					acbox.setX(e.getX());
					acbox.setY(e.getY());
				}
				if (isInterFace) {
					icbox.setX(e.getX());
					icbox.setY(e.getY());
				}
				if (isAssociation) {
					asso.setEndX(e.getX());
					asso.setEndY(e.getY());
				}
				if (isAggregation) {
					agg.setEndX(e.getX());
					agg.setEndY(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isClass) {
					cboxs.add(cbox);
					isClass = false;
				}
				if (isAbstractClass) {
					acboxs.add(acbox);
					isAbstractClass = false;
				}
				if (isInterFace) {
					icboxs.add(icbox);
					isInterFace = false;
				}
				if (isAssociation) {
					// getChildren().remove(asso);
					drawAssociationLine(asso);
					isAssociation = false;
				}
				if (isAggregation) {
					drawAggregationLine(agg);
					isAggregation = false;

				}

			}
		});

	}

	public void isNewOREdit(MouseEvent e) {
		Point2D point = new Point2D(e.getX(), e.getY());
		isNew = true;
		isNewOrEditClass(e, point);
		isNewOrEditAClass(e, point);
	}

	public void drawInterfaceClassLabel(C_Interface icbox) {
		Text head = new Text("<<interface>>");
		head.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		head.layoutXProperty()
				.bind(icbox.xProperty().add(head.layoutBoundsProperty().getValue().getWidth() / 3).subtract(7));
		head.layoutYProperty().bind(icbox.yProperty().add(20));

		Text label = new Text(icbox.labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(icbox.labelProperty());
		label.layoutXProperty().bind(icbox.xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 3));
		label.layoutYProperty().bind(icbox.yProperty().add(40));
		icbox.widthProperty()
				.bind(new SimpleDoubleProperty(label.layoutBoundsProperty().getValue().getWidth()).add(60));
		getChildren().addAll(label, head);
	}

	public void addDataLabel(int index) {
		Text data = new Text("data");
		int size = cboxs.get(index).getDatas().size();
		data.textProperty().bindBidirectional(cboxs.get(index).getDatas().get(--size));
		data.setFont(Font.font("Arial", FontWeight.BLACK, 12));
		data.setLayoutX(cboxs.get(index).getdataBox().getX() + 10);
		data.setLayoutY(cboxs.get(index).getdataBox().getY() + cboxs.get(index).getdataBox().getHeight());
		data.layoutXProperty().bind(cboxs.get(index).getdataBox().xProperty().add(10));
		data.layoutYProperty()
				.bind(cboxs.get(index).getdataBox().yProperty().add(cboxs.get(index).getdataBox().getHeight()));
		cboxs.get(index).getdataBox().setHeight(cboxs.get(index).getdataBox().getHeight() + 20);
		getChildren().add(data);

		System.out.println(" Height :" + cboxs.get(index).getdataBox().getHeight());

		// Label Listener
		data.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				data.setFill(Color.BLUE);
			}
		});
		data.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				data.setFill(Color.BLACK);
			}
		});

		data.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// Edit
				TextField text = new TextField();
				text.layoutXProperty().bind(data.layoutXProperty().subtract(15));
				text.layoutYProperty().bind(data.layoutYProperty().subtract(15));
				getChildren().add(text);

				text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent e) {
						if (e.getCode() == KeyCode.ENTER) {
							data.setText(text.getText().trim());
							if (cboxs.get(index).getdataBox().getWidth() < data.layoutBoundsProperty().getValue()
									.getWidth()) {
								System.out.println("Need to Repaint");
								cboxs.get(index).widthProperty().bind(
										new SimpleDoubleProperty(data.layoutBoundsProperty().getValue().getWidth())
												.add(20));
							}
							getChildren().remove(text);
						}
					}
				});
				//
			}
		});
	}

	public void addFunctionLabel(int index) {
		Text data = new Text("data");
		int size = cboxs.get(index).getFunctions().size();
		data.textProperty().bindBidirectional(cboxs.get(index).getFunctions().get(--size));
		data.setFont(Font.font("Arial", FontWeight.BLACK, 12));
		data.setLayoutX(cboxs.get(index).getfunctionBox().getX() + 10);
		data.setLayoutY(cboxs.get(index).getfunctionBox().getY() + cboxs.get(index).getfunctionBox().getHeight());
		data.layoutXProperty().bind(cboxs.get(index).getfunctionBox().xProperty().add(10));
		data.layoutYProperty()
				.bind(cboxs.get(index).getfunctionBox().yProperty().add(cboxs.get(index).getfunctionBox().getHeight()));
		cboxs.get(index).getfunctionBox().setHeight(cboxs.get(index).getfunctionBox().getHeight() + 20);
		getChildren().add(data);

		// Label Listener
		data.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				data.setFill(Color.BLUE);
			}
		});
		data.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				data.setFill(Color.BLACK);
			}
		});

		data.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// Edit
				System.out.println("Clikc");
				TextField text = new TextField();
				text.layoutXProperty().bind(data.layoutXProperty().subtract(15));
				text.layoutYProperty().bind(data.layoutYProperty().subtract(15));
				getChildren().add(text);

				text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent e) {
						if (e.getCode() == KeyCode.ENTER) {
							data.setText(text.getText().trim());
							if (cboxs.get(index).getfunctionBox().getWidth() < data.layoutBoundsProperty().getValue()
									.getWidth()) {
								cboxs.get(index).widthProperty().bind(
										new SimpleDoubleProperty(data.layoutBoundsProperty().getValue().getWidth())
												.add(20));
							}
							getChildren().remove(text);
						}
					}
				});
				//
			}
		});

	}

	public void drawAssociationLine(C_Association asso) {

		Text startL = new Text("Multiplicity");
		startL.textProperty().bindBidirectional(asso.startMultiplicity());

		Text endL = new Text("Multiplicity");
		endL.textProperty().bindBidirectional(asso.endMultiplicity());

		double startx = asso.getStartX();
		double starty = asso.getStartY();
		double endx = asso.getEndX();
		double endy = asso.getEndY();
		double slope = (starty - endy) / (startx - endx);

		// Distance
		double d = Math.sqrt((Math.pow(endx - startx, 2)) + (Math.pow(endy - starty, 2)));
		double mid = d / 2;

		if (startx < endx && starty < endy) {
			System.out.println(" Figure 1");
			if (slope < 2) {
				Line l1 = new Line(startx, starty, startx + mid, starty);
				l1.setStroke(color);
				Line l3 = new Line(endx, endy, endx - mid, endy);
				l3.setStroke(color);
				Line l2 = new Line(l1.getEndX(), l1.getEndY(), l3.getEndX(), l3.getEndY());
				l2.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx + mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx + mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				asso.startXProperty().bind(l1.startXProperty());
				asso.startYProperty().bind(l1.endYProperty());
				asso.endXProperty().bind(l3.endXProperty());
				asso.endYProperty().bind(l3.endYProperty());

				startL.xProperty().bind(l1.startXProperty().add(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().subtract(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});
				assos.add(asso);
				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}
			}

		} else if (startx > endx && starty > endy) {
			System.out.println(" Figure 2");

			if (slope < 1.5) {
				Line l1 = new Line(startx, starty, startx - mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx - mid, starty, startx - mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx - mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx - mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx - mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				asso.startXProperty().bind(l1.startXProperty());
				asso.startYProperty().bind(l1.endYProperty());
				asso.endXProperty().bind(l3.endXProperty());
				asso.endYProperty().bind(l3.endYProperty());

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});

				startL.xProperty().bind(l1.startXProperty().subtract(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().add(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}

			}

		} else if (startx > endx && starty < endy) {
			System.out.println(" Figure 3");
			System.out.println(" SLope : " + slope);

			if (slope > -1.5) {
				Line l1 = new Line(startx, starty, startx - mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx - mid, starty, startx - mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx - mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx - mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx - mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				asso.startXProperty().bind(l1.startXProperty());
				asso.startYProperty().bind(l1.endYProperty());
				asso.endXProperty().bind(l3.endXProperty());
				asso.endYProperty().bind(l3.endYProperty());

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});

				startL.xProperty().bind(l1.startXProperty().subtract(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().add(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}
			}

		} else if (startx < endx && starty > endy) {
			System.out.println(" Figure 4");

			System.out.println(" SLope : " + slope);

			if (slope > -1.5) {
				Line l1 = new Line(startx, starty, startx + mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx + mid, starty, startx + mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx + mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx + mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx + mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				asso.startXProperty().bind(l1.startXProperty());
				asso.startYProperty().bind(l1.endYProperty());
				asso.endXProperty().bind(l3.endXProperty());
				asso.endYProperty().bind(l3.endYProperty());

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});

				startL.xProperty().bind(l1.startXProperty().add(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().subtract(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}

			}

		} else if (startx < endx && starty == endy) {
			System.out.println(" Figure 5");
			System.out.println(" SLope : " + slope);

			if (slope == -0.0) {

				Line l1 = new Line(startx, starty, startx + mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx + mid, starty, startx + mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx + mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx + mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx + mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				asso.startXProperty().bind(l1.startXProperty());
				asso.startYProperty().bind(l1.endYProperty());
				asso.endXProperty().bind(l3.endXProperty());
				asso.endYProperty().bind(l3.endYProperty());

				startL.xProperty().bind(l1.startXProperty().add(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().subtract(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});
				assos.add(asso);
				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}

			}

		} else if (startx > endx && starty == endy) {
			System.out.println(" Figure 6");

		} else if (startx == endx && starty < endy) {
			System.out.println(" Figure 7");

		} else if (startx == endx && starty > endy) {
			System.out.println(" Figure 8");

		}

	}

	public void drawAggregationLine(C_Aggregation agg) {

		Text startL = new Text("Multiplicity");
		startL.textProperty().bindBidirectional(agg.startMultiplicity());

		Text endL = new Text("Multiplicity");
		endL.textProperty().bindBidirectional(agg.endMultiplicity());

		double startx = agg.getStartX();
		double starty = agg.getStartY();
		double endx = agg.getEndX();
		double endy = agg.getEndY();
		double slope = (starty - endy) / (startx - endx);

		// Distance
		double d = Math.sqrt((Math.pow(endx - startx, 2)) + (Math.pow(endy - starty, 2)));
		double mid = d / 2;

		if (startx < endx && starty < endy) {
			System.out.println(" Figure 1");
			if (slope < 2) {
				Line l1 = new Line(startx, starty, startx + mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx + mid, starty, startx + mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx + mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx + mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx + mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				// Aggregation Node
				Rectangle node3 = new Rectangle();
				node3.setFill(Color.LIGHTBLUE);
				node3.setRotate(45);
				node3.setX(l3.getEndX() - 20);
				node3.setY(l3.getEndY() - 10);
				node3.setWidth(20);
				node3.setHeight(20);

				node3.xProperty().bind(l3.endXProperty().subtract(20));
				node3.yProperty().bind(l3.endYProperty().subtract(10));

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				agg.startXProperty().bind(l1.startXProperty());
				agg.startYProperty().bind(l1.endYProperty());
				agg.endXProperty().bind(l3.endXProperty());
				agg.endYProperty().bind(l3.endYProperty());

				startL.xProperty().bind(l1.startXProperty().add(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().subtract(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAggregation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAggregation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});
				aggs.add(agg);
				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL, node3);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty());
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}
				}
			}

		} else if (startx > endx && starty > endy) {
			System.out.println(" Figure 2");

			if (slope < 1.5) {
				Line l1 = new Line(startx, starty, startx - mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx - mid, starty, startx - mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx - mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx - mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx - mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				// Aggregation Node
				Rectangle node3 = new Rectangle();
				node3.setFill(Color.LIGHTBLUE);
				node3.setRotate(45);
				node3.setX(l3.getEndX());
				node3.setY(l3.getEndY() - 10);
				node3.setWidth(20);
				node3.setHeight(20);

				node3.xProperty().bind(l3.endXProperty());
				node3.yProperty().bind(l3.endYProperty().subtract(10));

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				agg.startXProperty().bind(l1.startXProperty());
				agg.startYProperty().bind(l1.endYProperty());
				agg.endXProperty().bind(l3.endXProperty());
				agg.endYProperty().bind(l3.endYProperty());

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});

				startL.xProperty().bind(l1.startXProperty().subtract(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().add(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				getChildren().addAll(l1, l2, l3, node1, node2, node3, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(cboxs.get(i).getWidth()));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}

			}

		} else if (startx > endx && starty < endy) {
			System.out.println(" Figure 3");
			System.out.println(" SLope : " + slope);

			if (slope > -1.5) {
				Line l1 = new Line(startx, starty, startx - mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx - mid, starty, startx - mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx - mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx - mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx - mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				agg.startXProperty().bind(l1.startXProperty());
				agg.startYProperty().bind(l1.endYProperty());
				agg.endXProperty().bind(l3.endXProperty());
				agg.endYProperty().bind(l3.endYProperty());

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});

				startL.xProperty().bind(l1.startXProperty().subtract(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().add(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}
			}

		} else if (startx < endx && starty > endy) {
			System.out.println(" Figure 4");

			System.out.println(" SLope : " + slope);

			if (slope > -1.5) {
				Line l1 = new Line(startx, starty, startx + mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx + mid, starty, startx + mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx + mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx + mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx + mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				agg.startXProperty().bind(l1.startXProperty());
				agg.startYProperty().bind(l1.endYProperty());
				agg.endXProperty().bind(l3.endXProperty());
				agg.endYProperty().bind(l3.endYProperty());

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});

				startL.xProperty().bind(l1.startXProperty().add(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().subtract(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}

			}

		} else if (startx < endx && starty == endy) {
			System.out.println(" Figure 5");
			System.out.println(" SLope : " + slope);

			if (slope == -0.0) {

				Line l1 = new Line(startx, starty, startx + mid, starty);
				l1.setStroke(color);
				Line l2 = new Line(startx + mid, starty, startx + mid, endy);
				l2.setStroke(color);
				Line l3 = new Line(startx + mid, endy, endx, endy);
				l3.setStroke(color);

				Rectangle node1 = new Rectangle();
				node1.setFill(Color.LIGHTBLUE);
				node1.setX(startx + mid - 5);
				node1.setY(starty - 5);
				node1.setWidth(10);
				node1.setHeight(10);

				Rectangle node2 = new Rectangle();
				node2.setFill(Color.LIGHTBLUE);
				node2.setX(startx + mid - 5);
				node2.setY(endy - 5);
				node2.setWidth(10);
				node2.setHeight(10);

				l1.endXProperty().bind(node1.xProperty().add(5));
				l1.startYProperty().bind(node1.yProperty().add(5));
				l1.endYProperty().bind(node1.yProperty().add(5));
				l2.startYProperty().bind(node1.yProperty().add(5));
				l2.startXProperty().bind(node1.xProperty().add(5));
				l2.endXProperty().bind(node2.xProperty().add(5));
				l2.endYProperty().bind(node2.yProperty().add(5));
				l3.startXProperty().bind(node2.xProperty().add(5));
				l3.startYProperty().bind(node2.yProperty().add(5));
				l3.endYProperty().bind(node2.yProperty().add(5));

				agg.startXProperty().bind(l1.startXProperty());
				agg.startYProperty().bind(l1.endYProperty());
				agg.endXProperty().bind(l3.endXProperty());
				agg.endYProperty().bind(l3.endYProperty());

				startL.xProperty().bind(l1.startXProperty().add(20));
				startL.yProperty().bind(l1.startYProperty().subtract(10));

				endL.xProperty().bind(l3.endXProperty().subtract(20));
				endL.yProperty().bind(l3.endYProperty().subtract(10));

				node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node1.setX(e.getX() - 5);
						node1.setY(e.getY() - 5);
					}
				});
				node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						isAssociation = false;
						node2.setX(e.getX() - 5);
						node2.setY(e.getY() - 5);
					}
				});
				aggs.add(agg);
				getChildren().addAll(l1, l2, l3, node1, node2, startL, endL);

				// Linked
				for (int i = 0; i < cboxs.size(); i++) {
					Point2D start = new Point2D(l1.getStartX(), l1.getStartY());
					Point2D end = new Point2D(l3.getEndX(), l3.getEndY());
					if (cboxs.get(i).contains(start)) {
						double dx = start.getX() - cboxs.get(i).getX();
						double dy = start.getY() - cboxs.get(i).getY();
						l1.startXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l1.startYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l1.toBack();
					} else if (cboxs.get(i).contains(end)) {
						double dy = end.getY() - cboxs.get(i).getY();
						double dx = end.getX() - cboxs.get(i).getX();
						l3.endXProperty().bind(cboxs.get(i).xProperty().add(dx));
						l3.endYProperty().bind(cboxs.get(i).yProperty().add(dy));
						l3.toBack();
					}

				}

			}

		} else if (startx > endx && starty == endy) {
			System.out.println(" Figure 6");

		} else if (startx == endx && starty < endy) {
			System.out.println(" Figure 7");

		} else if (startx == endx && starty > endy) {
			System.out.println(" Figure 8");

		}

	}

	public void isNewOrEditClass(MouseEvent e, Point2D point) {

		for (int i = 0; i < cboxs.size(); i++) {
			if (cboxs.get(i).contains(point) || cboxs.get(i).getdataBox().contains(point)
					|| cboxs.get(i).getfunctionBox().contains(point)) {
				isNew = false;
				int index = i;

				cboxs.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						System.out.println("Edit Function Class MOuse Drag");
						if (isAssociation) {

						} else {
							cboxs.get(index).setX(key.getX());
							cboxs.get(index).setY(key.getY());
						}

					}
				});

				cboxs.get(i).getdataBox().addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent key) {
								Button add = new Button("+");
								getChildren().remove(add);
								add.layoutXProperty().bind(cboxs.get(index).getdataBox().xProperty().subtract(30));
								add.layoutYProperty().bind(cboxs.get(index).getdataBox().yProperty());
								getChildren().add(add);
								add.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										// Text
										cboxs.get(index).addData("data");
										addDataLabel(index);
										//
									}
								});
								add.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										getChildren().remove(add);
									}
								});
							}
						});
				cboxs.get(i).getfunctionBox().addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent key) {
								Button addf = new Button("+");
								getChildren().remove(addf);
								addf.layoutXProperty().bind(cboxs.get(index).getfunctionBox().xProperty().subtract(30));
								addf.layoutYProperty().bind(cboxs.get(index).getfunctionBox().yProperty());
								getChildren().add(addf);
								addf.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										cboxs.get(index).addFunction("data");
										addFunctionLabel(index);
									}
								});
								addf.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										getChildren().remove(addf);
									}
								});
							}
						});

				cboxs.get(i).getLabel().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						if (key.getClickCount() == 2) {
							// Edit Actor Label
							cboxs.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									DoubleProperty width = new SimpleDoubleProperty();
									width.set(cboxs.get(index).getLabel().layoutBoundsProperty().getValue().getWidth());
									cboxs.get(index).getLabel().xProperty()
											.bind(cboxs.get(index).xProperty()
													.add(cboxs.get(index).widthProperty().getValue() / 2)
													.subtract(cboxs.get(index).getLabel().layoutBoundsProperty()
															.getValue().getWidth() / 2));
									cboxs.get(index).widthProperty().bind(width.add(30));
									if (e.getCode() == KeyCode.ENTER) {
										cboxs.get(index).setTextInVisible();
									}
								}
							});
						}

					}
				});

				cboxs.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				cboxs.get(i).setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (cboxs.size() > 0) {

							cboxs.remove(index);
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
						cboxs.get(index).setEffect(null);
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
						save.saveClassCavaBox(cboxs);
						System.out.println("****Save*****");
					}
				});

				cboxs.get(i).setEffect(shape);
				cboxs.get(i).getfunctionBox().setEffect(shape);
				cboxs.get(i).getdataBox().setEffect(shape);

			} else {
				cboxs.get(i).setEffect(null);
				cboxs.get(i).getfunctionBox().setEffect(null);
				cboxs.get(i).getdataBox().setEffect(null);

			}

		}

	}

	public void isNewOrEditAClass(MouseEvent e, Point2D point) {

	}
}
