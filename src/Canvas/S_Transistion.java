package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.QuadCurve;

public class S_Transistion extends QuadCurve{
	
	public S_Transistion(double startx,double starty,double controlx,double controly,double endx,double endy,Color color){
		super(startx,starty,controlx,controly,endx,endy);
		setFill(Color.TRANSPARENT);
		setStroke(color);
	}
}
