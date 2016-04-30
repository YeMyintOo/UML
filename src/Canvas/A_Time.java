package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class A_Time extends Line {

	private Line l1;
	private Line l2;
	private Line l3;

	public A_Time(double startx, double starty, Color color) {
		super(startx, starty, startx + 20, starty + 30);
		setStroke(color);
		endXProperty().bind(startXProperty().add(20));
		endYProperty().bind(startYProperty().add(30));

		l1 = new Line();
		l1.setStroke(color);
		l1.startXProperty().bind(startXProperty());
		l1.startYProperty().bind(startYProperty());
		l1.endXProperty().bind(startXProperty().add(20));
		l1.endYProperty().bind(startYProperty());

		l2 = new Line();
		l2.setStroke(color);
		l2.startXProperty().bind(l1.endXProperty());
		l2.startYProperty().bind(l1.endYProperty());
		l2.endXProperty().bind(startXProperty());
		l2.endYProperty().bind(startYProperty().add(30));

		l3 = new Line();
		l3.setStroke(color);
		l3.startXProperty().bind(startXProperty());
		l3.startYProperty().bind(startYProperty().add(30));
		l3.endXProperty().bind(startXProperty().add(20));
		l3.endYProperty().bind(startYProperty().add(30));

	}

	public Line getLine1() {
		return l1;
	}

	public Line getLine2() {
		return l2;
	}

	public Line getLine3() {
		return l3;
	}
}
