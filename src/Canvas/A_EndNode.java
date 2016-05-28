package Canvas;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class A_EndNode extends Circle {

	private Circle outer;

	public A_EndNode(double x, double y, double r, Color color) {
		super(x, y, r);
		setFill(color);
		setStroke(Color.LIGHTGRAY);

		outer = new Circle(getCenterX(), getCenterY(), 15);
		outer.setFill(Color.WHITE);
		outer.setStroke(Color.LIGHTGRAY);

		outer.centerXProperty().bindBidirectional(centerXProperty());
		outer.centerYProperty().bindBidirectional(centerYProperty());
		
		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setCenterX(e.getX());
				setCenterY(e.getY());
			}
		});
	}

	public Circle getOuter() {
		return outer;
	}

}
