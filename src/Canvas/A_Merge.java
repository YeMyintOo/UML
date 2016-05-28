package Canvas;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class A_Merge extends Rectangle {
	public A_Merge(double x, double y, Color color) {
		super(x, y, 30, 30);
		setFill(color);
		setRotate(45);
		setStroke(Color.LIGHTGRAY);
		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setX(e.getX());
				setY(e.getY());
			}
		});
	}
}
