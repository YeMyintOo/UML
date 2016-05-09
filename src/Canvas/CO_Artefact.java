package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CO_Artefact extends Rectangle {

	private Text label;

	public CO_Artefact(double x, double y, Color color) {
		super(x, y, 110, 45);
		setStroke(Color.LIGHTGRAY);
		setFill(color);		

		label = new Text("<<source>>");
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2).subtract(label.layoutBoundsProperty().getValue().getWidth()/2));
		label.yProperty().bind(yProperty().add(15));
	}

	public Text getLabel() {
		return label;
	}

	

}
