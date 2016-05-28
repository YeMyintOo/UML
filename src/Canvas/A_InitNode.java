package Canvas;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class A_InitNode extends Circle {

	public A_InitNode(double x, double y, double r, Color color) {
		super(x, y, r);
		setFill(color);
		setStroke(Color.LIGHTGRAY);
		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setCenterX(e.getX());
				setCenterY(e.getY());
			}
		});
	}

}
