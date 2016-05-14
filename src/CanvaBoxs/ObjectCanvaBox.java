package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;

import Canvas.O_Link;
import Canvas.O_Object;
import Database.ToolHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
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
						object = new O_Object(e.getX(), e.getY(), 100, 30, color, Color.LIGHTGRAY);
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
			System.out.println(" Key is Press");
		});
	}

	public void isEditOrNew(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditObject(e, point);
	}

	public void isNewOREditObject(MouseEvent e, Point2D point) {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).contains(point)) {
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

			}
		}
	}

}
