package Canvas;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class A_Edge extends Line {

	// private Color color;
	private Path top;
	private Path bot;
	private Color color;

	public A_Edge(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);
		this.color=color;
		recalculatePoint();
	}

	public Path getTop() {
		return top;
	}

	public Path getBot() {
		return bot;
	}

	public void recalculatePoint() {
		double x, y, length;
		length = Math.sqrt((getEndX() - getStartX()) * (getEndX() - getStartX())
				+ (getEndY() - getStartY()) * (getEndY() - getStartY()));
		x = (getEndX() - getStartX()) / length;
		y = (getEndY() - getStartY()) / length;
		Point2D base = new Point2D(getEndX() - x * 10, getEndY() - y * 10);
		Point2D back_top = new Point2D(base.getX() - 10 * y, base.getY() + 10 * x);
		Point2D back_bottom = new Point2D(base.getX() + 10 * y, base.getY() - 10 * x);

		top = new Path();
		top.setStroke(color);
		top.getElements().add(new MoveTo(getEndX(), getEndY()));
		top.getElements().add(new LineTo(back_top.getX(), back_top.getY()));

		bot = new Path();
		bot.setStroke(color);
		bot.getElements().add(new MoveTo(getEndX(), getEndY()));
		bot.getElements().add(new LineTo(back_bottom.getX(), back_bottom.getY()));
	}
}
