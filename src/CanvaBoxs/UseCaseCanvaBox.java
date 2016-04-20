package CanvaBoxs;

import java.util.ArrayList;

import Canvas.UC_ActoionLine;
import Database.ToolHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class UseCaseCanvaBox extends Pane {
	// Only Use Case Components Can Draw
	private ToolHandler toolHandler;
	private Color color;

	private ArrayList<UC_ActoionLine> actionLines;
	private UC_ActoionLine actionLine;
	private boolean isActionLine;

	public UseCaseCanvaBox() {
		actionLines = new ArrayList<UC_ActoionLine>();
		// InitState
		isActionLine = false;

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				toolHandler = new ToolHandler();
				String colorS = toolHandler.getColor();
				String tool = toolHandler.getTool();
				color = Color.web(colorS);

				switch (tool) {
				case "UseCase_Actor":
					break;
				case "UseCase_Action":
					actionLine = new UC_ActoionLine(e.getX(), e.getY(), e.getX(), e.getY());
					isActionLine = true;
					getChildren().add(actionLine);
					break;
				case "UseCase_Box":
					break;
				case "UseCase_Process":
					break;
				case "UseCase_Extend":
					break;
				case "UseCase_Include":
					break;
				case "UseCase_Type":
					break;
				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isActionLine) {
					actionLine.setEndx(e.getX());
					actionLine.setEndy(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isActionLine) {
					actionLines.add(actionLine);
					actionLine = null;
				}

			}
		});

	}
	
}
