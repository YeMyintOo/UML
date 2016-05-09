package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CO_Component extends Rectangle {

	private Rectangle node1;
	private Rectangle node2;

	public CO_Component(double x, double y, Color color) {
		super(x, y, 100, 45);
		setStroke(Color.LIGHTGRAY);
		setFill(color);
		
		node1=new Rectangle();
		node1.setStroke(Color.LIGHTGRAY);
		node1.setFill(Color.WHITE);
		node1.setWidth(30);
		node1.setHeight(15);
		node1.xProperty().bind(xProperty().subtract(15));
		node1.yProperty().bind(yProperty().add(5));
		node1.toFront();
		
		node2=new Rectangle();
		node2.setStroke(Color.LIGHTGRAY);
		node2.setFill(Color.WHITE);
		node2.setWidth(30);
		node2.setHeight(15);
		node2.xProperty().bind(xProperty().subtract(15));
		node2.yProperty().bind(yProperty().add(25));
		node2.toFront();
	}

	public Rectangle getNode1() {
		return node1;
	}

	public Rectangle getNode2() {
		return node2;
	}

}
