package Main;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class WorkSpace2 extends BorderPane {
	
	private BorderPane boderPane; //Drawing Area
	

	public WorkSpace2(int type, File file) {
		ToolBar toolbar = new ToolBar(type);
		addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getX() < 10) {
					toolbar.slideShow();
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

		setCenter(boderPane);
	}

}
