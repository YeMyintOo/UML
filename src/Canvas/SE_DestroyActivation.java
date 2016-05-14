package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SE_DestroyActivation extends Line {
	private Color color;
	private Line top;
	private Line bot;
	private Line c1;
	private Line c2;

	public SE_DestroyActivation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);

		top = new Line();
		top.setStroke(color);
		top.startXProperty().bind(endXProperty());
		top.startYProperty().bind(endYProperty());
		top.endXProperty().bind(endXProperty().subtract(10));
		top.endYProperty().bind(endYProperty().subtract(5));

		bot = new Line();
		bot.setStroke(color);
		bot.startXProperty().bind(endXProperty());
		bot.startYProperty().bind(endYProperty());
		bot.endXProperty().bind(endXProperty().subtract(10));
		bot.endYProperty().bind(endYProperty().add(5));

		c1 = new Line();
		c1.startXProperty().bind(endXProperty().subtract(15));
		c1.startYProperty().bind(endYProperty().subtract(15));
		c1.endXProperty().bind(endXProperty().add(15));
		c1.endYProperty().bind(endYProperty().add(15));
		c1.setStrokeWidth(3);
		
		c2 = new Line(getEndX() - 15, getEndY() + 15, getEndX() + 15, getEndY() - 15);
		c2.startXProperty().bind(endXProperty().subtract(15));
		c2.startYProperty().bind(endYProperty().add(15));
		c2.endXProperty().bind(endXProperty().add(15));
		c2.endYProperty().bind(endYProperty().subtract(15));
		c2.setStrokeWidth(3);
		
		startYProperty().bindBidirectional(endYProperty());
	}
	
	public Line getC1(){
		return c1;
	}
	
	public Line getC2(){
		return c2;
	}

	public Line getTop() {
		return top;
	}

	public Line getBot() {
		return bot;
	}

}
