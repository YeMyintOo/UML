package Main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

import Canvas.*;

public class CanvaBox extends Pane {
	SLine sline;
	private ArrayList<SLine> lineHolder = new ArrayList<SLine>();

	public CanvaBox() {

		// Mouse Click Event
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				sline = new SLine();
				sline.setStartX(e.getX());
				sline.setStartY(e.getY());
				sline.setEndX(e.getX());
				sline.setEndY(e.getY());
				getChildren().add(sline);
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
				sline = null;
				// Reload holder
				for (int i = 0; i < lineHolder.size(); i++) {
					getChildren().add(lineHolder.get(i));
				}
			}
		});

	}

}
