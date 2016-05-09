package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class D_Protocal extends Line{
	public D_Protocal(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);
		
	}
}
