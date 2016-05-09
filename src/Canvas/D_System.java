package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class D_System extends Rectangle {
	private Text label;

	public D_System(double x, double y,Color bgcolor) {
		super(x, y, 300, 400);
		setX(x);
		setY(y);
		setFill(bgcolor);
		setStroke(Color.LIGHTGRAY);
		

		label = new Text("Sample System");
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));
		label.xProperty().bind(xProperty().add(20));
		label.yProperty().bind(yProperty().add(20));
	}
	
	public Text getLabel(){
		return label;
	}

}
