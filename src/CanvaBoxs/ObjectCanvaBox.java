package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import Canvas.O_Link;
import Canvas.O_Object;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ObjectCanvaBox extends CanvasPane {

	// Object
	private ArrayList<O_Object> objects;
	private O_Object object;
	private boolean isObject;

	// Link
	private ArrayList<O_Link> links;
	private O_Link link;
	private boolean isLink;

	public ObjectCanvaBox(Scene owner, File path, boolean isLoad) {
		setOwner(owner);
		setPath(path);
		objects = new ArrayList<O_Object>();
		links = new ArrayList<O_Link>();

		if (isLoad) {
			System.out.println(" Load Object data From XML");
			loadXMLData("Objects");
		}

		if (toolHandler.getGrid().equals("Show")) {
			setGridLine();
		}

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isEditOrNew(e);
				if (isNew) {
					toolHandler = new ToolHandler();
					String colorS = toolHandler.getColor();
					String tool = toolHandler.getTool();
					color = Color.web(colorS);
					switch (tool) {
					case "ObjectD_Object":
						object = new O_Object(e.getX(), e.getY(), 100, 40, color, Color.LIGHTGRAY);
						isObject = true;
						getChildren().addAll(object, object.getdataBox(), object.getLabel(), object.getText(false));
						break;
					case "ObjectD_link":
						link = new O_Link(e.getX(), e.getY(), e.getX(), e.getY(), color);
						isLink = true;
						getChildren().addAll(link);
						break;
					}
				}
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isObject) {
					object.setX(e.getX());
					object.setY(e.getY());
				}
				if (isLink) {
					link.setEndX(e.getX());
					link.setEndY(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isObject) {
					objects.add(object);
					isObject = false;
				}
				if (isLink) {
					link.recalculatePoint();
					getChildren().addAll(link.getTop(), link.getBot());
					links.add(link);
					isLink = false;
				}
			}
		});

		this.onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
		this.setOnKeyPressed(e -> {

			// Print
			if (e.getCode() == KeyCode.PRINTSCREEN) {
				if (defaultprinter == null) {
					defaultprinter = Printer.getDefaultPrinter();
					pageLayout = defaultprinter.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
							Printer.MarginType.HARDWARE_MINIMUM);
				}
				getChildren().remove(gridLine);
				PrintNode(this, pageLayout);
				getChildren().add(gridLine);
				gridLine.toBack();
			}
			// Save
			if (e.getCode() == KeyCode.F1) {
				if (save == null) {
					save = new SaveDiagramXML(path);
				}
				save.saveObjectCanvaBox(objects, links);
				System.out.println("****Save*****");
			}
		});
	}

	public void isEditOrNew(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditObject(e, point);
	}

	public void isNewOREditObject(MouseEvent e, Point2D point) {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).contains(point) || objects.get(i).getdataBox().contains(point)) {
				int index = i;
				isNew = false;
				objects.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						objects.get(index).setX(e.getX());
						objects.get(index).setY(e.getY());
					}
				});

				objects.get(i).getLabel().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						if (e.getClickCount() == 2) {
							objects.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
									new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									DoubleProperty width = new SimpleDoubleProperty();
									width.set(
											objects.get(index).getLabel().layoutBoundsProperty().getValue().getWidth());
									objects.get(index).widthProperty().bind(width.add(40));
									if (e.getCode() == KeyCode.ENTER) {
										objects.get(index).setTextInVisible();
									}
								}
							});
						}
					}
				});

				objects.get(i).getdataBox().addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						Button add = new Button("+");
						getChildren().remove(add);
						add.layoutXProperty().bind(objects.get(index).getdataBox().xProperty().subtract(30));
						add.layoutYProperty().bind(objects.get(index).getdataBox().yProperty());
						getChildren().add(add);
						add.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								// Text
								objects.get(index).addData("data");
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

				objects.get(i).setEffect(shape);
			} else {
				objects.get(i).setEffect(null);
			}
		}
	}

	public void addDataLabel(int index) {

		Text data = new Text("data");
		int size = objects.get(index).getDatas().size();
		data.textProperty().bindBidirectional(objects.get(index).getDatas().get(--size));
		data.setFont(Font.font("Arial", FontWeight.BLACK, 12));
		data.setLayoutX(objects.get(index).getdataBox().getX() + 10);
		data.setLayoutY(objects.get(index).getdataBox().getY() + objects.get(index).getdataBox().getHeight());
		data.layoutXProperty().bind(objects.get(index).getdataBox().xProperty().add(10));
		data.layoutYProperty()
				.bind(objects.get(index).getdataBox().yProperty().add(objects.get(index).getdataBox().getHeight()));
		objects.get(index).getdataBox().setHeight(objects.get(index).getdataBox().getHeight() + 20);
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
							if (objects.get(index).getdataBox().getWidth() < data.layoutBoundsProperty().getValue()
									.getWidth()) {
								objects.get(index).widthProperty().bind(
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

	private void loadXMLData(String tagName) {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(path);

			switch (tagName) {
			case "Links":
				org.w3c.dom.Node action = doc.getElementsByTagName("Links").item(0);
				NodeList actions = action.getChildNodes();
				for (int i = 0; i < actions.getLength(); i++) {
					org.w3c.dom.Node data = actions.item(i);
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
					O_Link link = new O_Link(x1, y1, x2, y2, color);
					links.add(link);
					getChildren().addAll(link);
				}
				break;

			case "Objects":
				org.w3c.dom.Node ubox = doc.getElementsByTagName("Objects").item(0);
				NodeList uboxs = ubox.getChildNodes();
				for (int i = 0; i < uboxs.getLength(); i++) {
					org.w3c.dom.Node data = uboxs.item(i);
					NodeList datas = data.getChildNodes();
					double x = 0, y = 0, h = 0, w = 0;
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
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					O_Object obj = new O_Object(x, y, w, h, color, Color.LIGHTGRAY);
					objects.add(obj);
					getChildren().addAll(obj, obj.getLabel(), obj.getdataBox());
				}
				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
