package Main;

import java.util.ArrayList;

import Canvas.SLine;
import Canvas.SPoint;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CanvaBox extends Pane {
	private ToolHandler toolHandler;
	private Color color;

	private SLine sline;
	private ArrayList<SLine> lineHolder = new ArrayList<SLine>();

	public CanvaBox() {

		// Mouse Click Event
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				color = Color.web(colorS); // Dynamic color from ToolHander.xml
				//Check Tool From ToolHandler.xml
				
				sline = new SLine(color);
				sline.setStartX(e.getX());
				sline.setStartY(e.getY());
				sline.setEndX(e.getX());
				sline.setEndY(e.getY());

				SPoint point = new SPoint(e.getX(), e.getY(), 3);
				getChildren().addAll(sline, point);
			}
		});
		// Mouse Drag Event
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				sline.setEndX(e.getX());
				sline.setEndY(e.getY());

			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				lineHolder.add(sline);
				SPoint point = new SPoint(e.getX(), e.getY(), 3);
				getChildren().addAll(point);
				sline = null;
				// Reload Line holder
				for (int i = 0; i < lineHolder.size(); i++) {
					getChildren().addAll(lineHolder.get(i));
					//getChildrenUnmodifiable().addAll(lineHolder.get(i));
				}
			}
		});

	}

}
