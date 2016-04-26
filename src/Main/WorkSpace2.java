package Main;

import java.io.File;

import Calculate.ScreenDetail;
import CanvaBoxs.ActivityCanvaBox;
import CanvaBoxs.ClassCanvaBox;
import CanvaBoxs.CollaborationCanvaBox;
import CanvaBoxs.ComponentCanvaBox;
import CanvaBoxs.DeploymentCanvaBox;
import CanvaBoxs.ObjectCanvaBox;
import CanvaBoxs.SequenceCanvaBox;
import CanvaBoxs.StatechartCanvaBox;
import CanvaBoxs.UseCaseCanvaBox2;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class WorkSpace2 extends BorderPane {
	private BorderPane boderPane; // Drawing Area

	public WorkSpace2(int type, File file) {
		ToolBar toolbar = new ToolBar(type);

		addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getX() < 10) {
					toolbar.slideShow();
					toolbar.toFront();
				} else {
					toolbar.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							toolbar.slideHide();
						}
					});

				}

			}
		});

		boderPane = new BorderPane();
		boderPane.setStyle("-fx-background-color:white;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		boderPane.setLeft(toolbar);
		RenderTool(type);
		setCenter(boderPane);
	}

	public void RenderTool(int type) {

		switch (type) {
		case 1: // Use_Case
			boderPane.setCenter(new UseCaseCanvaBox2());
			break;
		case 2:// Object
			boderPane.setCenter(new ObjectCanvaBox());
			break;
		case 3:// Sequence
			boderPane.setCenter(new SequenceCanvaBox());
			break;
		case 4:// Collaboration
			boderPane.setCenter(new CollaborationCanvaBox());
			break;
		case 5:// Class
			boderPane.setCenter(new ClassCanvaBox());
			break;
		case 6:// State Chart
			boderPane.setCenter(new StatechartCanvaBox());
			break;
		case 7:// Activity
			boderPane.setCenter(new ActivityCanvaBox());
			break;
		case 8:// Component
			boderPane.setCenter(new ComponentCanvaBox());
			break;
		case 9:// Deployment
			boderPane.setCenter(new DeploymentCanvaBox());
			break;
		}

	}

}
