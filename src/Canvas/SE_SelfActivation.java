package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SE_SelfActivation extends Rectangle {
	private Color bgColor;
	private DoubleProperty life;

	public SE_SelfActivation(Color bgcolor) {
		setFill(bgcolor);
		setStroke(Color.BLACK);
		setWidth(20);
		setHeight(100);
		life=new SimpleDoubleProperty();
	}

	public final DoubleProperty lifeProperty() {
		return life;
	}

	public Color getBgColor() {
		return bgColor;

	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}
}
