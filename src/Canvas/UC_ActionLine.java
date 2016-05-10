package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class UC_ActionLine extends Line {

	private Rectangle snode;// Start Node
	private Rectangle enode;// End Node

	public UC_ActionLine(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);
		
		snode=new Rectangle();
		snode.setWidth(10);
		snode.setHeight(10);
		snode.xProperty().bind(startXProperty().subtract(5));
		snode.yProperty().bind(startYProperty().subtract(5));
		
		enode=new Rectangle();
		enode.setWidth(10);
		enode.setHeight(10);
		enode.xProperty().bind(endXProperty().subtract(5));
		enode.yProperty().bind(endYProperty().subtract(5));
	}

	public Rectangle getSnode() {
		return snode;
	}

	public Rectangle getEnode() {
		return enode;
	}

}
