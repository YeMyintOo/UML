package CanvaBoxs;

import java.util.ArrayList;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ObjectCanvaBox extends Pane {
	// Only Object Components Can Draw
	private ToolHandler toolHandler;
	private Color color;

	

	public ObjectCanvaBox() {
	
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();
				color = Color.web(colorS);
				switch (tool) {
				case "ObjectD_Object":
				
					break;
				case "ObjectD_link":
				
					break;
				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			}
		});

	}

}
