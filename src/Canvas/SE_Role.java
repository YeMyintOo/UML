package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SE_Role extends Rectangle {

	private Color bgColor;
	private StringProperty label;
	private DoubleProperty life;

	public SE_Role(double x, double y, Color bgcolor) {
		super(x, y, 100, 40);
		setFill(bgcolor);
		label = new SimpleStringProperty(":Role");
		life=new SimpleDoubleProperty(100);
	}

	public Color getBgColor() {
		return bgColor;

	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public final StringProperty labelProperty() {
		return label;
	}
	public final DoubleProperty lifeProperty(){
		return life;
	}
}
