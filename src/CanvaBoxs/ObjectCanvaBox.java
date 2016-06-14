package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;

import Canvas.O_Link;
import Canvas.O_Object;
import Database.ToolHandler;
import Library.SaveDiagramXML;
import Library.Types;
import SaveMe.SaveObject;
import SaveMe.SaveUseCase;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ObjectCanvaBox extends CanvasPane {
	private SaveObject saveMe;
	private ArrayList<O_Object> objects;
	private O_Object object;
	private boolean isObject;
	private ArrayList<O_Link> links;
	private O_Link link;
	private boolean isLink;

	public ObjectCanvaBox(Scene owner, File path, boolean isLoad) {
		setOwner(owner);
		setPath(path);
		objects = new ArrayList<O_Object>();
		links = new ArrayList<O_Link>();
		menu.getChildren().addAll(getLabel(Types.Object), saveB, printB);

		if (isLoad) {
			loadXMLData();
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
						object = new O_Object(e.getX(), e.getY(), color, Color.LIGHTGRAY);
						isObject = true;
						getChildren().addAll(object, object.getLabel(), object.getdataBox(), object.getText(false));
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
				requestFocus();
				toolHandler.setTool("");
			}
		});

		saveB.setOnAction(e -> {
			if (saveMe == null) {
				saveMe = new SaveObject(path);
			}
			saveMe.setDatas(objects, links);
			saveMe.save();
		});
		printB.setOnAction(e -> {
			getChildren().removeAll(gridLine, menu);
			new Library.PrintNodes(this);
			getChildren().add(gridLine);
			gridLine.toBack();
		});

	}

	public void isEditOrNew(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditObject(e, point);
		isNewOREditLink(e, point);
	}

	public void isNewOREditObject(MouseEvent e, Point2D point) {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).contains(point) || objects.get(i).getdataBox().contains(point)) {
				isNew = false;
				int index = i;

				objects.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						objects.get(index).setX(key.getX());
						objects.get(index).setY(key.getY());
					}
				});

				objects.get(i).getdataBox().addEventFilter(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						Button add = objects.get(index).getAddB();
						getChildren().add(add);
						add.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								if(e.getButton()==MouseButton.SECONDARY){
									getChildren().remove(add);
									add.setVisible(false);
								}else{
									objects.get(index).addData("data");
									addDataLabel(index);
								}
								
							}
						});
					}
				});

				objects.get(i).getLabel().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						objects.get(index).getText(true).toFront();
						objects.get(index).getText(true).addEventFilter(KeyEvent.KEY_PRESSED,
								new EventHandler<KeyEvent>() {
							@Override
							public void handle(KeyEvent e) {
								DoubleProperty width = new SimpleDoubleProperty();
								width.set(objects.get(index).getLabel().layoutBoundsProperty().getValue().getWidth());
								objects.get(index).getLabel().xProperty()
										.bind(objects.get(index).xProperty()
												.add(objects.get(index).widthProperty().getValue() / 2)
												.subtract(objects.get(index).getLabel().layoutBoundsProperty()
														.getValue().getWidth() / 2));
								objects.get(index).widthProperty().bind(width.add(30));
								if (e.getCode() == KeyCode.ENTER) {
									objects.get(index).setTextInVisible();
								}
							}
						});
					}
				});

				objects.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				objects.get(i).setOnKeyPressed(key -> {
					// Delete
					if (key.getCode() == KeyCode.DELETE) {
						if (objects.size() > 0) {
							getChildren().removeAll(objects.get(index), objects.get(index).getLabel(),
									objects.get(index).getdataBox(), objects.get(index).getText(false));
							for (int k = 0; k < objects.get(index).getDataGs().size(); k++) {
								getChildren().removeAll(objects.get(index).getDataGs().get(k));
							}

							objects.remove(index);
						} else {
							System.out.println("No Self Activation to delete");
						}
					}
				});

				objects.get(i).setEffect(shape);
				objects.get(i).getdataBox().setEffect(shape);

			} else {
				objects.get(i).setEffect(null);
				objects.get(i).getdataBox().setEffect(null);

			}

		}
	}

	public void isNewOREditLink(MouseEvent e, Point2D point) {

		for (int i = 0; i < links.size(); i++) {
			int index = i;
			boolean isclick = links.get(i).contains(point.getX(), point.getY())
					|| links.get(i).contains(point.getX() - 1, point.getY())
					|| links.get(i).contains(point.getX() - 2, point.getY())
					|| links.get(i).contains(point.getX() - 3, point.getY())
					|| links.get(i).contains(point.getX() - 4, point.getY())
					|| links.get(i).contains(point.getX() - 5, point.getY())
					|| links.get(i).contains(point.getX() + 1, point.getY())
					|| links.get(i).contains(point.getX() + 2, point.getY())
					|| links.get(i).contains(point.getX() + 3, point.getY())
					|| links.get(i).contains(point.getX() + 4, point.getY())
					|| links.get(i).contains(point.getX() + 5, point.getY())
					|| links.get(i).contains(point.getX(), point.getY() - 1)
					|| links.get(i).contains(point.getX(), point.getY() - 2)
					|| links.get(i).contains(point.getX(), point.getY() - 3)
					|| links.get(i).contains(point.getX(), point.getY() - 4)
					|| links.get(i).contains(point.getX(), point.getY() - 5)
					|| links.get(i).contains(point.getX(), point.getY() + 1)
					|| links.get(i).contains(point.getX(), point.getY() + 2)
					|| links.get(i).contains(point.getX(), point.getY() + 3)
					|| links.get(i).contains(point.getX(), point.getY() + 4)
					|| links.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				links.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				links.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (links.size() > 0) {
							getChildren().removeAll(links.get(index), links.get(index).getTop(),
									links.get(index).getBot());
							links.remove(index);
						} else {
							System.out.println("No ActionLine to delete");
						}
					}
				});
				links.get(i).setEffect(shape);
			} else {
				links.get(i).setEffect(null);
			}

		}

	}

	public void addDataLabel(int index) {
		int gsize = objects.get(index).getDataGs().size();
		int size = objects.get(index).getDatas().size();
		Text data = objects.get(index).getDataGs().get(--gsize);
		data.textProperty().bindBidirectional(objects.get(index).getDatas().get(--size));
		data.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
		data.setLayoutX(objects.get(index).getdataBox().getX() + 10);
		data.setLayoutY(objects.get(index).getdataBox().getY() + objects.get(index).getdataBox().getHeight());
		data.layoutXProperty().bind(objects.get(index).getdataBox().xProperty().add(10));
		data.layoutYProperty()
				.bind(objects.get(index).getdataBox().yProperty().add(objects.get(index).getdataBox().getHeight()));
		objects.get(index).getdataBox().setHeight(objects.get(index).getdataBox().getHeight() + 20);
		getChildren().add(data);

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
								System.out.println("Need to Repaint");
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

	private void loadXMLData() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(path);

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
				link.recalculatePoint();
				getChildren().addAll(link, link.getTop(), link.getBot());
			}

			org.w3c.dom.Node ubox = doc.getElementsByTagName("Objects").item(0);
			NodeList uboxs = ubox.getChildNodes();
			for (int i = 0; i < uboxs.getLength(); i++) {
				org.w3c.dom.Node data = uboxs.item(i);
				NodeList datas = data.getChildNodes();
				double x = 0, y = 0, h = 0, w = 0;
				Color color = null;
				String label = null;
				String[] vars = null;
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
					if ("label".equals(element.getNodeName())) {
						label = element.getTextContent();
					}

					if ("variables".equals(element.getNodeName())) {
						String var = element.getTextContent();
						vars = var.split("@@@");
					}
				}
				O_Object obj = new O_Object(x, y, color, Color.LIGHTGRAY);
				obj.setWidth(w);
				obj.setHeight(h);
				obj.labelProperty().set(label);
				for (int k = 0; k < vars.length; k++) {
					obj.addData(vars[k]);
				}
				objects.add(obj);
				getChildren().addAll(obj, obj.getLabel(), obj.getdataBox(), obj.getText(false));
				loadDataLabel(vars, i);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// End Loop

	public void loadDataLabel(String[] vars, int index) {
		System.out.println("Index :" + index);
		for (int i = 0; i < vars.length; i++) {
			Text data = objects.get(index).getDataGs().get(i);
			data.textProperty().bindBidirectional(objects.get(index).getDatas().get(i));
			data.setLayoutX(objects.get(index).getdataBox().getX() + 10);
			data.setLayoutY(objects.get(index).getdataBox().getY() + objects.get(index).getdataBox().getHeight());
			data.layoutXProperty().bind(objects.get(index).getdataBox().xProperty().add(10));
			data.layoutYProperty()
					.bind(objects.get(index).getdataBox().yProperty().add(objects.get(index).getdataBox().getHeight()));
			objects.get(index).getdataBox().setHeight(objects.get(index).getdataBox().getHeight() + 20);
			getChildren().add(data);

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
									System.out.println("Need to Repaint");
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
	}

}// End Function
