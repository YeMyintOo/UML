package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SE_Activation extends Line {

	private Color color;
	private Line top;
	private Line bot;
	private DoubleProperty life;
	private Rectangle rect;
	private Line rLine; //Return Line
	private Line rtop;
	private Line rbot;
	private TextField text;

	public SE_Activation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		this.color = color;
		setFill(color);
		life = new SimpleDoubleProperty(100);
		
		rect = new Rectangle();
		rect.xProperty().bindBidirectional(endXProperty());
		rect.yProperty().bindBidirectional(endYProperty());
		rect.setWidth(20);
		rect.setFill(Color.LIGHTGRAY);
		rect.setStroke(Color.BLACK);
		rect.heightProperty().bind(lifeProperty());
		
		top=new Line();
		top.startXProperty().bind(endXProperty());
		top.startYProperty().bind(endYProperty());
		top.endXProperty().bind(endXProperty().subtract(10));
		top.endYProperty().bind(endYProperty().subtract(5));
		
		bot=new Line();
		bot.startXProperty().bind(endXProperty());
		bot.startYProperty().bind(endYProperty());
		bot.endXProperty().bind(endXProperty().subtract(10));
		bot.endYProperty().bind(endYProperty().add(5));
		
		rLine=new Line();
		rLine.getStrokeDashArray().addAll(10d, 5d);
		rLine.startXProperty().bind(getRect().xProperty());
		rLine.startYProperty().bind(getRect().yProperty().add(lifeProperty()));
		rLine.endYProperty().bind(getRect().yProperty().add(lifeProperty()));
		rLine.endXProperty().bind(startXProperty());
		
		rtop = new Line();
		rtop.startXProperty().bind(rLine.endXProperty());
		rtop.startYProperty().bind(rLine.endYProperty());
		rtop.endXProperty().bind(rLine.endXProperty().add(10));
		rtop.endYProperty().bind(rLine.endYProperty().subtract(5));
		
		rbot = new Line();
		rbot.startXProperty().bind(rLine.endXProperty());
		rbot.startYProperty().bind(rLine.endYProperty());
		rbot.endXProperty().bind(rLine.endXProperty().add(10));
		rbot.endYProperty().bind(rLine.endYProperty().add(5));
		
		startYProperty().bindBidirectional(endYProperty());
	}

	public final DoubleProperty lifeProperty() {
		return life;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public Line getTop() {
		return top;
	}

	public Line getBot() {
		return bot;
	}
	
	public Line getRLine(){
		return rLine;
	}

	public Line getRTop() {
		return rtop;
	}

	public Line getRBot() {
		return rbot;
	}
}
