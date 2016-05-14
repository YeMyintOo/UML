package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class SE_SelfActivation extends Rectangle {
	private Color bgColor;
	private DoubleProperty life;
	private Arc arc1;
	private Arc arc2;
	
	private Line top1;
	private Line top2;
	
	private Line bot1;
	private Line bot2;

	public SE_SelfActivation(Color bgcolor) {
		setFill(bgcolor);
		setStroke(Color.BLACK);
		setWidth(20);
		setHeight(100);
		life = new SimpleDoubleProperty();

		arc1 = new Arc(getX() + 10, getY() - 10, 60, 10, 270, 180);
		arc1.setFill(Color.TRANSPARENT);
		arc1.setStroke(Color.BLACK);
		
		top1=new Line();
		top1.startXProperty().bind(arc1.centerXProperty().add(10));
		top1.startYProperty().bind(arc1.centerYProperty().add(10));
		top1.endXProperty().bind(arc1.centerXProperty().add(25));
		top1.endYProperty().bind(arc1.centerYProperty());
		
		top2=new Line();
		top2.startXProperty().bind(arc1.centerXProperty().add(10));
		top2.startYProperty().bind(arc1.centerYProperty().add(10));
		top2.endXProperty().bind(arc1.centerXProperty().add(25));
		top2.endYProperty().bind(arc1.centerYProperty().add(20));
		

		arc2 = new Arc(getX() + 10, getY() + getHeight() + 10, 60, 10, 270, 180);
		arc2.getStrokeDashArray().addAll(5d, 5d);
		arc2.setFill(Color.TRANSPARENT);
		arc2.setStroke(Color.BLACK);
		
		bot1=new Line();
		bot1.startXProperty().bind(arc2.centerXProperty());
		bot1.startYProperty().bind(arc2.centerYProperty().add(10));
		bot1.endXProperty().bind(arc2.centerXProperty().add(15));
		bot1.endYProperty().bind(arc2.centerYProperty());
		
		bot2=new Line();
		bot2.startXProperty().bind(arc2.centerXProperty());
		bot2.startYProperty().bind(arc2.centerYProperty().add(10));
		bot2.endXProperty().bind(arc2.centerXProperty().add(15));
		bot2.endYProperty().bind(arc2.centerYProperty().add(20));
		

		arc1.centerYProperty().bind(yProperty().subtract(10));
		arc1.centerXProperty().bind(xProperty().add(10));
		
		arc2.centerYProperty().bind(yProperty().add(10).add(heightProperty()));
		arc2.centerXProperty().bind(xProperty().add(10));

		yProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				arc2.centerYProperty().bind(yProperty().add(getHeight()).add(10));
			}
		});
		heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				arc2.centerYProperty().bind(heightProperty().add(getY()).add(10));
			}
		});
	}
	
	public Line getBot2(){
		return bot2;
	}
	
	public Line getBot1(){
		return bot1;
	}
	
	public Line getTop2(){
		return top2;
	}
	
	public Line getTop1(){
		return top1;
	}
	
	public final DoubleProperty lifeProperty() {
		return life;
	}

	public Arc getArc1() {
		return arc1;
	}

	public Arc getArc2() {
		return arc2;
	}

}
