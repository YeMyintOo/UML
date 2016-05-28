package Canvas;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class A_Time extends Rectangle {

	private Line l0;
	private Line l1;
	private Line l2;
	private Line l3;
	

	public A_Time(double startx, double starty, Color color) {
		super(startx, starty, 30, 40);
		setFill(Color.WHITE);
		
		l0=new Line();
		l0.setStroke(color);
		l0.startXProperty().bind(xProperty());
		l0.startYProperty().bind(yProperty());
		l0.endXProperty().bind(xProperty().add(30));
		l0.endYProperty().bind(yProperty().add(40));

		l1 = new Line();
		l1.setStroke(color);
		l1.startXProperty().bind(xProperty());
		l1.startYProperty().bind(yProperty());
		l1.endXProperty().bind(xProperty().add(30));
		l1.endYProperty().bind(yProperty());

		l2 = new Line();
		l2.setStroke(color);
		l2.startXProperty().bind(l1.endXProperty());
		l2.startYProperty().bind(l1.endYProperty());
		l2.endXProperty().bind(xProperty());
		l2.endYProperty().bind(yProperty().add(40));

		l3 = new Line();
		l3.setStroke(color);
		l3.startXProperty().bind(xProperty());
		l3.startYProperty().bind(yProperty().add(40));
		l3.endXProperty().bind(xProperty().add(30));
		l3.endYProperty().bind(yProperty().add(40));
		
		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setX(e.getX());
				setY(e.getY());
			}
		});
	}
	public Line getLine0(){
		return l0;
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
