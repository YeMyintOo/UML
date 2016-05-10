package Canvas;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class UC_TypeOfLine extends Line {
	private Color color;

	private Path tri;

	public UC_TypeOfLine(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		this.color = color;
		setStroke(color);

	}

	public Path getTri() {
		return tri;
	}

	public void calculateTri() {
		double startx = getStartX();
		double starty = getStartY();
		double endx = getEndX();
		double endy = getEndY();

		// Arrow Head
		double x, y, length;
		length = Math.sqrt((endx - startx) * (endx - startx) + (endy - starty) * (endy - starty));
		x = (endx - startx) / length;
		y = (endy - starty) / length;
		Point2D base = new Point2D(endx - x * 10, endy - y * 10);
		Point2D back_top = new Point2D(base.getX() - 10 * y, base.getY() + 10 * x);
		Point2D back_bottom = new Point2D(base.getX() + 10 * y, base.getY() - 10 * x);
		tri = new Path();
		tri.setStroke(Color.LIGHTGRAY);
		tri.setFill(color);
		tri.getElements().add(new MoveTo(endx, endy));
		tri.getElements().add(new LineTo(back_top.getX(), back_top.getY()));
		tri.getElements().add(new LineTo(back_bottom.getX(), back_bottom.getY()));
		tri.getElements().add(new LineTo(endx, endy));
	}
}
