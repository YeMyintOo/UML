package Canvas;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UC_ExtendLine extends Line {

	private Path top;
	private Path bot;
	private Text label;
	private String msg;
	private Color color;

	public UC_ExtendLine(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		this.color = color;
		setStroke(color);
		getStrokeDashArray().addAll(10d, 10d);
		msg = "<<extend>>";
		label = new Text("");
		label.setFont(Font.font("Arial", FontWeight.BLACK, 13));
	}

	public Text getLabel(boolean isCreate) {
		if (isCreate) {
			double midx = (getStartX() + getEndX()) * 0.5;
			double midy = (getStartY() + getEndY()) * 0.5;

			if (getStartX() < getEndX() && getStartY() < getEndY()) {
				label = new Text(midx + 5, midy, msg);
			} else if (getStartX() > getEndX() && getStartY() > getEndY()) {
				label = new Text(midx + 5, midy, msg);
			} else if (getStartX() > getEndX() && getStartY() < getEndY()) {
				label = new Text(midx + 5, midy, msg);
			} else if (getStartX() < getEndX() && getStartY() > getEndY()) {
				label = new Text(midx + 5, midy, msg);
			} else if (getStartX() < getEndX() && getStartY() == getEndY()) {
				label = new Text(midx - 10, midy - 20, msg);
			} else if (getStartX() > getEndX() && getStartY() == getEndY()) {
				label = new Text(midx - 10, midy - 20, msg);
			} else if (getStartX() == getEndX() && getStartY() < getEndY()) {
				label = new Text(midx - 15, midy, msg);
			} else if (getStartX() == getEndX() && getStartY() > getEndY()) {
				label = new Text(midx - 15, midy, msg);
			}
		}

		return label;
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
