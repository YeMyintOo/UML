package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class S_FinalState extends Circle {

	private Circle outer;

	public S_FinalState(double x, double y, double r, Color color) {
		super(x, y, r);
		setFill(color);
		setStroke(Color.LIGHTGRAY);

		outer = new Circle(getCenterX(), getCenterY(), 15);
		outer.setFill(Color.WHITE);
		outer.setStroke(Color.LIGHTGRAY);

		outer.centerXProperty().bindBidirectional(centerXProperty());
		outer.centerYProperty().bindBidirectional(centerYProperty());
	}

	public Circle getOuter() {
		return outer;
	}

}
