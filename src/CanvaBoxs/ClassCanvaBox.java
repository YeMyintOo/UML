package CanvaBoxs;

import java.util.ArrayList;

import Canvas.C_Association;
import Canvas.C_Class;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ClassCanvaBox extends Pane {
	// Only Class Components Can Draw
	private ToolHandler toolHandler;
	private Color color;
	private DropShadow shape;
	private boolean isNew;

	// Class
	private ArrayList<C_Class> cboxs;
	private C_Class cbox;
	private boolean isClass;

	// Association
	private ArrayList<C_Association> assos;
	private C_Association asso;
	private boolean isAssociation;

	public ClassCanvaBox() {
		init();

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				color = Color.web(toolHandler.getColor());

				// Association Link
				if (toolHandler.getTool().equals("Class_Association")) {
					isClass = false;
					asso = new C_Association(e.getX(), e.getY(), e.getX(), e.getY(), Color.LIGHTGRAY);
					isAssociation = true;
					getChildren().addAll(asso);
				} else {
					isNewOREdit(e);

					if (isNew) {
						switch (toolHandler.getTool()) {
						case "Class_Class":
							cbox = new C_Class(e.getX(), e.getY(), 100, 100, color, Color.LIGHTGRAY);
							isClass = true;
							getChildren().addAll(cbox, cbox.getdataBox(), cbox.getfunctionBox());
							drawClassLabel(cbox);
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
					System.out.println("Initial Class MOuse Drag");
					cbox.setX(e.getX());
					cbox.setY(e.getY());
				}
				if (isAssociation) {
					asso.setEndX(e.getX());
					asso.setEndY(e.getY());
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
				if (isAssociation) {
					getChildren().remove(asso);
					drawAssociationLine(asso);
					isAssociation = false;

				}
			}
		});

	}

	public void init() {
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);

		cboxs = new ArrayList<C_Class>();
		assos = new ArrayList<C_Association>();
	}

	public void isNewOREdit(MouseEvent e) {
		Point2D point = new Point2D(e.getX(), e.getY());
		isNew = true;
		isNewOrEditClass(e, point);
	}

	public void drawClassLabel(C_Class cbox) {
		Text label = new Text(cbox.labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(cbox.labelProperty());
		label.layoutXProperty().bind(cbox.xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 3));
		label.layoutYProperty().bind(cbox.yProperty().add(20));
		cbox.widthProperty().bind(new SimpleDoubleProperty(label.layoutBoundsProperty().getValue().getWidth()).add(60));
		getChildren().add(label);

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

		} else if (startx > endx && starty > endy) {
			System.out.println(" Figure 2");

		} else if (startx > endx && starty < endy) {
			System.out.println(" Figure 3");

		} else if (startx < endx && starty > endy) {
			System.out.println(" Figure 4");

		} else if (startx < endx && starty == endy) {
			System.out.println(" Figure 5");

		} else if (startx > endx && starty == endy) {
			System.out.println(" Figure 6");

		} else if (startx == endx && starty < endy) {
			System.out.println(" Figure 7");

		} else if (startx == endx && starty > endy) {
			System.out.println(" Figure 8");

		} else {

		}

	}

	public void isNewOrEditClass(MouseEvent e, Point2D point) {

		for (int i = 0; i < cboxs.size(); i++) {
			if (cboxs.get(i).contains(point) || cboxs.get(i).getdataBox().contains(point)
					|| cboxs.get(i).getfunctionBox().contains(point)) {
				isNew = false;
				int index = i;
				// Mouse Class Diagram
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

				// Data/////////////////////////////////////////////////

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

				//////////////////////////////////////////////////////

				// Function////////////////////////////////////////////

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
				//////////////////////////////////////////////////////

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
}
