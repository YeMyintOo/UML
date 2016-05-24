package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import Canvas.C_AbstractClass;
import Canvas.C_Aggregation;
import Canvas.C_Association;
import Canvas.C_Class;
import Canvas.C_Composition;
import Canvas.C_Inheritance;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
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

	// Composition
	private ArrayList<C_Composition> comps;
	private C_Composition comp;
	private boolean isComposition;

	// Inheritance
	private ArrayList<C_Inheritance> inhs;
	private C_Inheritance inh;
	private boolean isInheritance;

	public ClassCanvaBox(Scene owner, File path, boolean isLoad) {
		setOwner(owner);
		setPath(path);
		cboxs = new ArrayList<C_Class>();
		assos = new ArrayList<C_Association>();
		acboxs = new ArrayList<C_AbstractClass>();
		icboxs = new ArrayList<C_Interface>();
		aggs = new ArrayList<C_Aggregation>();
		comps = new ArrayList<C_Composition>();
		inhs = new ArrayList<C_Inheritance>();

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
						getChildren().addAll(acbox, acbox.getdataBox(), acbox.getfunctionBox(), acbox.getLabel(),
								acbox.getText(false));
						break;

					case "Class_InterfaceClass":
						icbox = new C_Interface(e.getX(), e.getY(), 100, 100, color, Color.LIGHTGRAY);
						isInterFace = true;
						getChildren().addAll(icbox, icbox.getdataBox(), icbox.getfunctionBox(), icbox.getLabel(),
								icbox.getiLabel(), icbox.getText(false));
						break;

					case "Class_Association":
						asso = new C_Association(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isAssociation = true;
						getChildren().addAll(asso);
						break;
					case "Class_Aggregation":
						agg = new C_Aggregation(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isAggregation = true;
						getChildren().addAll(agg);
						break;
					case "Class_Composition":
						comp = new C_Composition(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isComposition = true;
						getChildren().addAll(comp);
						break;
					case "Class_Inheritance":
						inh = new C_Inheritance(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isInheritance = true;
						getChildren().addAll(inh);
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
				if (isComposition) {
					comp.setEndX(e.getX());
					comp.setEndY(e.getY());
				}
				if (isInheritance) {
					inh.setEndX(e.getX());
					inh.setEndY(e.getY());
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
					getChildren().remove(asso);
					if (asso.filterLine()) {
						getChildren().addAll(asso.getL1(), asso.getL2(), asso.getL3(), asso.getNode1(), asso.getNode2(),
								asso.getStartNode(), asso.getEndNode());
						assos.add(asso);
					}
					isAssociation = false;
				}
				if (isAggregation) {
					getChildren().remove(agg);
					if (agg.filterLine()) {
						getChildren().addAll(agg.getL1(), agg.getL2(), agg.getL3(), agg.getNode1(), agg.getNode2(),
								agg.getStartNode(), agg.getBox());
						aggs.add(agg);
					}
					isAggregation = false;
				}

				if (isComposition) {
					getChildren().remove(comp);
					if (comp.filterLine()) {
						getChildren().addAll(comp.getL1(), comp.getL2(), comp.getL3(), comp.getNode1(), comp.getNode2(),
								comp.getStartNode(), comp.getBox());
						comps.add(comp);
					}
					isComposition = false;
				}

				if (isInheritance) {
					getChildren().remove(inh);
					if (inh.filterLine()) {
						getChildren().addAll(inh.getL1(), inh.getL2(), inh.getL3(), inh.getNode1(), inh.getNode2(),
								inh.getStartNode(),inh.getTri());
						inhs.add(inh);
					}
					isInheritance = false;
				}
			}
		});

	}

	public void isNewOREdit(MouseEvent e) {
		Point2D point = new Point2D(e.getX(), e.getY());
		isNew = true;
		isNewOrEditClass(e, point);
		isNewOrEditAClass(e, point);
		isNewOrEditIClass(e, point);
		isNewOrEditAssociation(e, point);
		isNewOrEditAggregation(e, point);
		isNewOrEditComposition(e, point);
		isNewOrEditInheritance(e,point);
	}

	public void addClassDataLabel(int index) {
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

	public void addClassFunctionLabel(int index) {
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

	public void addAClassDataLabel(int index) {

		Text data = new Text("data");
		int size = acboxs.get(index).getDatas().size();
		data.textProperty().bindBidirectional(acboxs.get(index).getDatas().get(--size));
		data.setFont(Font.font("Arial", FontWeight.BLACK, 12));
		data.setLayoutX(acboxs.get(index).getdataBox().getX() + 10);
		data.setLayoutY(acboxs.get(index).getdataBox().getY() + acboxs.get(index).getdataBox().getHeight());
		data.layoutXProperty().bind(acboxs.get(index).getdataBox().xProperty().add(10));
		data.layoutYProperty()
				.bind(acboxs.get(index).getdataBox().yProperty().add(acboxs.get(index).getdataBox().getHeight()));
		acboxs.get(index).getdataBox().setHeight(acboxs.get(index).getdataBox().getHeight() + 20);
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
				TextField text = new TextField();
				text.layoutXProperty().bind(data.layoutXProperty().subtract(15));
				text.layoutYProperty().bind(data.layoutYProperty().subtract(15));
				getChildren().add(text);

				text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent e) {
						if (e.getCode() == KeyCode.ENTER) {
							data.setText(text.getText().trim());
							if (acboxs.get(index).getdataBox().getWidth() < data.layoutBoundsProperty().getValue()
									.getWidth()) {
								acboxs.get(index).widthProperty().bind(
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

	public void addAClassFunctionLabel(int index) {

		Text data = new Text("data");
		int size = acboxs.get(index).getFunctions().size();
		data.textProperty().bindBidirectional(acboxs.get(index).getFunctions().get(--size));
		data.setFont(Font.font("Arial", FontWeight.BLACK, 12));
		data.setLayoutX(acboxs.get(index).getfunctionBox().getX() + 10);
		data.setLayoutY(acboxs.get(index).getfunctionBox().getY() + acboxs.get(index).getfunctionBox().getHeight());
		data.layoutXProperty().bind(acboxs.get(index).getfunctionBox().xProperty().add(10));
		data.layoutYProperty().bind(
				acboxs.get(index).getfunctionBox().yProperty().add(acboxs.get(index).getfunctionBox().getHeight()));
		acboxs.get(index).getfunctionBox().setHeight(acboxs.get(index).getfunctionBox().getHeight() + 20);
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
							if (acboxs.get(index).getfunctionBox().getWidth() < data.layoutBoundsProperty().getValue()
									.getWidth()) {
								acboxs.get(index).widthProperty().bind(
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

	public void addIClassDataLabel(int index) {
		Text data = new Text("data");
		int size = icboxs.get(index).getDatas().size();
		data.textProperty().bindBidirectional(icboxs.get(index).getDatas().get(--size));
		data.setFont(Font.font("Arial", FontWeight.BLACK, 12));
		data.setLayoutX(icboxs.get(index).getdataBox().getX() + 10);
		data.setLayoutY(icboxs.get(index).getdataBox().getY() + icboxs.get(index).getdataBox().getHeight());
		data.layoutXProperty().bind(icboxs.get(index).getdataBox().xProperty().add(10));
		data.layoutYProperty()
				.bind(icboxs.get(index).getdataBox().yProperty().add(icboxs.get(index).getdataBox().getHeight()));
		icboxs.get(index).getdataBox().setHeight(icboxs.get(index).getdataBox().getHeight() + 20);
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
				TextField text = new TextField();
				text.layoutXProperty().bind(data.layoutXProperty().subtract(15));
				text.layoutYProperty().bind(data.layoutYProperty().subtract(15));
				getChildren().add(text);

				text.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent e) {
						if (e.getCode() == KeyCode.ENTER) {
							data.setText(text.getText().trim());
							if (icboxs.get(index).getdataBox().getWidth() < data.layoutBoundsProperty().getValue()
									.getWidth()) {
								icboxs.get(index).widthProperty().bind(
										new SimpleDoubleProperty(data.layoutBoundsProperty().getValue().getWidth())
												.add(20));
								icboxs.get(index).getLabel().xProperty()
										.bind(icboxs.get(index).xProperty()
												.add(icboxs.get(index).widthProperty().getValue() / 2)
												.subtract(icboxs.get(index).getLabel().layoutBoundsProperty().getValue()
														.getWidth() / 2));
								icboxs.get(index).getiLabel().xProperty()
										.bind(icboxs.get(index).xProperty()
												.add(icboxs.get(index).widthProperty().getValue() / 2)
												.subtract(icboxs.get(index).getiLabel().layoutBoundsProperty()
														.getValue().getWidth() / 2));
							}
							getChildren().remove(text);
						}
					}
				});
				//
			}
		});

	}

	public void addIClassfunctionLabel(int index) {

		Text data = new Text("data");
		int size = icboxs.get(index).getFunctions().size();
		data.textProperty().bindBidirectional(icboxs.get(index).getFunctions().get(--size));
		data.setFont(Font.font("Arial", FontWeight.BLACK, 12));
		data.setLayoutX(icboxs.get(index).getfunctionBox().getX() + 10);
		data.setLayoutY(icboxs.get(index).getfunctionBox().getY() + icboxs.get(index).getfunctionBox().getHeight());
		data.layoutXProperty().bind(icboxs.get(index).getfunctionBox().xProperty().add(10));
		data.layoutYProperty().bind(
				icboxs.get(index).getfunctionBox().yProperty().add(icboxs.get(index).getfunctionBox().getHeight()));
		icboxs.get(index).getfunctionBox().setHeight(icboxs.get(index).getfunctionBox().getHeight() + 20);
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
							if (icboxs.get(index).getfunctionBox().getWidth() < data.layoutBoundsProperty().getValue()
									.getWidth()) {
								icboxs.get(index).widthProperty().bind(
										new SimpleDoubleProperty(data.layoutBoundsProperty().getValue().getWidth())
												.add(20));
								icboxs.get(index).getLabel().xProperty()
										.bind(icboxs.get(index).xProperty()
												.add(icboxs.get(index).widthProperty().getValue() / 2)
												.subtract(icboxs.get(index).getLabel().layoutBoundsProperty().getValue()
														.getWidth() / 2));
								icboxs.get(index).getiLabel().xProperty()
										.bind(icboxs.get(index).xProperty()
												.add(icboxs.get(index).widthProperty().getValue() / 2)
												.subtract(icboxs.get(index).getiLabel().layoutBoundsProperty()
														.getValue().getWidth() / 2));
							}
							getChildren().remove(text);
						}
					}
				});
				//
			}
		});

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
										addClassDataLabel(index);
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
										addClassFunctionLabel(index);
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
							cboxs.get(index).getText(true).toFront();
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

			// Link
			for (int k = 0; k < assos.size(); k++) {

				// StartNodeLink
				Shape start = Shape.intersect(assos.get(k).getStartNode(), cboxs.get(i));
				boolean isStart = start.getBoundsInLocal().getWidth() >= 0 || start.getBoundsInLocal().getHeight() >= 0;
				if (isStart) {
					// Find x,y difference
					double dx = cboxs.get(i).getX() - assos.get(k).getStartNode().getX();
					double dy = cboxs.get(i).getY() - assos.get(k).getStartNode().getY();

					if (dy <= 0 && dx < 0) {
						// Right link
						assos.get(k).getStartNode().xProperty()
								.bind(cboxs.get(i).xProperty().add(cboxs.get(i).widthProperty()).subtract(5));
					}
					if (dy <= 0 && dx > 0) {
						// Left Link
						assos.get(k).getStartNode().xProperty().bind(cboxs.get(i).xProperty().subtract(5));
						assos.get(k).getStartNode().toFront();
					}

					if (dy > 0 && dx <= 0) {
						// Top Link
						assos.get(k).getStartNode().xProperty().bind(cboxs.get(i).xProperty().subtract(dx));
					}

				}

				// EndNodeLink

				Shape end = Shape.intersect(assos.get(k).getEndNode(), cboxs.get(i));
				boolean isEnd = end.getBoundsInLocal().getWidth() >= 0 || end.getBoundsInLocal().getHeight() >= 0;
				if (isEnd) {
					// Find x,y difference
					double dx = cboxs.get(i).getX() - assos.get(k).getEndNode().getX();
					double dy = cboxs.get(i).getY() - assos.get(k).getEndNode().getY();

					if (dy <= 0 && dx < 0) {
						// Right link
						assos.get(k).getEndNode().xProperty()
								.bind(cboxs.get(i).xProperty().add(cboxs.get(i).widthProperty()).subtract(5));
					}
					if (dy <= 0 && dx > 0) {
						// Left Link
						assos.get(k).getEndNode().xProperty().bind(cboxs.get(i).xProperty().subtract(5));
						assos.get(k).getEndNode().toFront();
					}
					if (dy > 0 && dx <= 0) {
						// Top Link
						assos.get(k).getEndNode().xProperty().bind(cboxs.get(i).xProperty().subtract(dx));
					}

				}
			}

		}
	}

	public void isNewOrEditAClass(MouseEvent e, Point2D point) {
		for (int i = 0; i < acboxs.size(); i++) {
			if (acboxs.get(i).contains(point) || acboxs.get(i).getdataBox().contains(point)
					|| acboxs.get(i).getfunctionBox().contains(point)) {
				isNew = false;
				int index = i;
				acboxs.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						acboxs.get(index).setX(e.getX());
						acboxs.get(index).setY(e.getY());
					}
				});

				acboxs.get(i).getdataBox().addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								Button add = new Button("+");
								getChildren().remove(add);
								add.layoutXProperty().bind(acboxs.get(index).getdataBox().xProperty().subtract(30));
								add.layoutYProperty().bind(acboxs.get(index).getdataBox().yProperty());
								getChildren().add(add);
								add.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										// Text
										acboxs.get(index).addData("data");
										addAClassDataLabel(index);
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

				acboxs.get(i).getfunctionBox().addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								Button addf = new Button("+");
								getChildren().remove(addf);
								addf.layoutXProperty()
										.bind(acboxs.get(index).getfunctionBox().xProperty().subtract(30));
								addf.layoutYProperty().bind(acboxs.get(index).getfunctionBox().yProperty());
								getChildren().add(addf);
								addf.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										acboxs.get(index).addFunction("data");
										addAClassFunctionLabel(index);
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

				acboxs.get(i).getLabel().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						if (key.getClickCount() == 2) {
							// Edit Actor Label
							acboxs.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									DoubleProperty width = new SimpleDoubleProperty();
									width.set(
											acboxs.get(index).getLabel().layoutBoundsProperty().getValue().getWidth());
									acboxs.get(index).getLabel().xProperty()
											.bind(acboxs.get(index).xProperty()
													.add(acboxs.get(index).widthProperty().getValue() / 2)
													.subtract(acboxs.get(index).getLabel().layoutBoundsProperty()
															.getValue().getWidth() / 2));
									acboxs.get(index).widthProperty().bind(width.add(30));
									if (e.getCode() == KeyCode.ENTER) {
										acboxs.get(index).setTextInVisible();
									}
								}
							});
						}

					}
				});
				acboxs.get(i).setEffect(shape);
			} else {
				acboxs.get(i).setEffect(null);
			}
		}
	}

	public void isNewOrEditIClass(MouseEvent e, Point2D point) {
		for (int i = 0; i < icboxs.size(); i++) {
			if (icboxs.get(i).contains(point) || icboxs.get(i).getdataBox().contains(point)
					|| icboxs.get(i).getfunctionBox().contains(point)) {
				isNew = false;
				int index = i;
				icboxs.get(i).addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						icboxs.get(index).setX(e.getX());
						icboxs.get(index).setY(e.getY());
					}
				});

				icboxs.get(i).getLabel().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						if (e.getClickCount() == 2) {
							icboxs.get(index).getText(true).addEventHandler(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									DoubleProperty width = new SimpleDoubleProperty();
									width.set(
											icboxs.get(index).getLabel().layoutBoundsProperty().getValue().getWidth());
									icboxs.get(index).getLabel().xProperty()
											.bind(icboxs.get(index).xProperty()
													.add(icboxs.get(index).widthProperty().getValue() / 2)
													.subtract(icboxs.get(index).getLabel().layoutBoundsProperty()
															.getValue().getWidth() / 2));
									icboxs.get(index).widthProperty().bind(width.add(30));
									icboxs.get(index).getiLabel().xProperty()
											.bind(icboxs.get(index).xProperty()
													.add(icboxs.get(index).widthProperty().getValue() / 2)
													.subtract(icboxs.get(index).getiLabel().layoutBoundsProperty()
															.getValue().getWidth() / 2));
									if (e.getCode() == KeyCode.ENTER) {
										icboxs.get(index).setTextInVisible();
									}
								}
							});
						}
					}
				});

				icboxs.get(i).getdataBox().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						Button addf = new Button("+");
						getChildren().remove(addf);
						addf.layoutXProperty().bind(icboxs.get(index).getdataBox().xProperty().subtract(30));
						addf.layoutYProperty().bind(icboxs.get(index).getdataBox().yProperty());
						getChildren().add(addf);
						addf.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								icboxs.get(index).addData("data");
								addIClassDataLabel(index);
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

				icboxs.get(i).getfunctionBox().addEventHandler(MouseEvent.MOUSE_ENTERED,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								Button addf = new Button("+");
								getChildren().remove(addf);
								addf.layoutXProperty()
										.bind(icboxs.get(index).getfunctionBox().xProperty().subtract(30));
								addf.layoutYProperty().bind(icboxs.get(index).getfunctionBox().yProperty());
								getChildren().add(addf);
								addf.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										icboxs.get(index).addFunction("data");
										addIClassfunctionLabel(index);
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

				icboxs.get(i).setEffect(shape);
				icboxs.get(i).getdataBox().setEffect(shape);
				icboxs.get(i).getfunctionBox().setEffect(shape);
			} else {
				icboxs.get(i).setEffect(null);
				icboxs.get(i).getdataBox().setEffect(null);
				icboxs.get(i).getfunctionBox().setEffect(null);
			}
		}
	}

	public void isNewOrEditAssociation(MouseEvent e, Point2D point) {
		for (int i = 0; i < assos.size(); i++) {
			if (assos.get(i).getNode1().contains(point) || assos.get(i).getNode2().contains(point)
					|| assos.get(i).getStartNode().contains(point) || assos.get(i).getEndNode().contains(point)) {
				isNew = false;
				assos.get(i).getStartNode().xProperty().unbind();
			}
		}
	}

	public void isNewOrEditAggregation(MouseEvent e, Point2D point) {
		for (int i = 0; i < aggs.size(); i++) {
			if (aggs.get(i).getNode1().contains(point) || aggs.get(i).getNode2().contains(point)
					|| aggs.get(i).getStartNode().contains(point) || aggs.get(i).getEndNode().contains(point)
					|| aggs.get(i).getBox().contains(point)) {
				isNew = false;
				aggs.get(i).getStartNode().xProperty().unbind();
			}
		}
	}

	public void isNewOrEditComposition(MouseEvent e, Point2D point) {
		for (int i = 0; i < comps.size(); i++) {
			if (comps.get(i).getNode1().contains(point) || comps.get(i).getNode2().contains(point)
					|| comps.get(i).getStartNode().contains(point) || comps.get(i).getEndNode().contains(point)
					|| comps.get(i).getBox().contains(point)) {
				isNew = false;
				comps.get(i).getStartNode().xProperty().unbind();
			}
		}
	}
	
	public void isNewOrEditInheritance(MouseEvent e,Point2D point){
		for (int i = 0; i < inhs.size(); i++) {
			int index=i;
			if (inhs.get(i).getNode1().contains(point) || inhs.get(i).getNode2().contains(point)
					|| inhs.get(i).getStartNode().contains(point) || inhs.get(i).getEndNode().contains(point)
					|| inhs.get(i).getTri().contains(point)) {
				isNew = false;
				inhs.get(i).getStartNode().xProperty().unbind();
			}
			inhs.get(i).getTri().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					getChildren().remove(inhs.get(index).getTri());
					getChildren().add(inhs.get(index).getEndNode());
				}
			});
			inhs.get(i).getEndNode().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					getChildren().add(inhs.get(index).getTri());
					getChildren().remove(inhs.get(index).getEndNode());
				}
			});
		}
	}
}
