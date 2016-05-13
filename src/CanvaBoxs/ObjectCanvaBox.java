package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;
import Canvas.O_Link;
import Canvas.O_Object;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();
				color = Color.web(colorS);
				switch (tool) {
				case "ObjectD_Object":
					object = new O_Object(e.getX(), e.getY(), 100, 30, color, Color.LIGHTGRAY);
					isObject = true;
					getChildren().addAll(object,object.getdataBox(),object.getText());
					break;
				case "ObjectD_link":
					link=new O_Link(e.getX(),e.getY(),e.getX(),e.getY(),color);
					isLink=true;
					getChildren().addAll(link);
					break;
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
				if(isLink){
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
				if(isLink){
					link.recalculatePoint();
					getChildren().addAll(link.getTop(),link.getBot());
					links.add(link);
					isLink=false;
				}
			}
		});

		this.onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
		this.setOnKeyPressed(e -> {
			System.out.println(" Key is Press");
		});
	}
	
	

}
