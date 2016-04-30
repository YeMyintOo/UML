package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class A_Region extends Rectangle {

	private StringProperty data;
	private Text label;

	public A_Region(double x, double y, Color color) {
		super(x, y, 300, 200);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("Region Label");
		setFill(color);
		setStroke(Color.LIGHTGRAY);
		
		label=new Text(labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(this.xProperty().add(20));
		label.yProperty().bind(this.yProperty().add(20));
	}

	public final StringProperty labelProperty() {
		return data;
	}
	public Text getLabel(){
		return label;
	}
	
}
