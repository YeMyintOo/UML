package Canvas;

import Library.LRectangle;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;

public class S_Transistion extends QuadCurve {

	private Rectangle node;
	private Path top;
	private Path bot;
	private Color color;

	public S_Transistion(double startx, double starty, double controlx, double controly, double endx, double endy,
			Color color) {
		super(startx, starty, controlx, controly, endx, endy);
		this.color = color;
		setFill(Color.TRANSPARENT);
		setStroke(color);

		node = new LRectangle();
		node.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				node.setX(e.getX());
				node.setY(e.getY());

			}
		});
		node.xProperty().bindBidirectional(controlXProperty());
		node.yProperty().bindBidirectional(controlYProperty());
	}

	public Path getTop() {
		return top;
	}

	public Path getBot() {
		return bot;
	}

	public Rectangle getNode() {
		return node;
	}

	public void recalculatePoint() {
		double x, y, length;
		length = Math.sqrt((endXProperty().get() - startXProperty().get())
				* (endXProperty().get() - startXProperty().get())
				+ (endYProperty().get() - startYProperty().get()) * (endXProperty().get() - startXProperty().get()));
		x = (endXProperty().get() - startXProperty().get()) / length;
		y = (endYProperty().get() - startYProperty().get()) / length;
		Point2D base = new Point2D(endXProperty().get() - x * 10, endYProperty().get() - y * 10);
		Point2D back_top = new Point2D(base.getX() - 10 * y, base.getY() + 10 * x);
		Point2D back_bottom = new Point2D(base.getX() + 10 * y, base.getY() - 10 * x);

		top = new Path();
		top.setStroke(color);
		top.getElements().add(new MoveTo(endXProperty().get(), endYProperty().get()));
		top.getElements().add(new LineTo(back_top.getX(), back_top.getY()));

		bot = new Path();
		bot.setStroke(color);
		bot.getElements().add(new MoveTo(endXProperty().get(), endYProperty().get()));
		bot.getElements().add(new LineTo(back_bottom.getX(), back_bottom.getY()));
	}
}
