package Canvas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UC_Box extends Rectangle {
	private StringProperty data;
	private Text label;
	private TextField text;

	public UC_Box(double x, double y, double width, double height, Color bgcolor, Color scolor) {
		super(x, y, width, height);
		setX(x);
		setY(y);
		setFill(bgcolor);
		setOpacity(0.3);
		setStroke(scolor);
		setArcWidth(10);
		setArcHeight(10);
		data = new SimpleStringProperty("Box Label");

		label = new Text();
		label.textProperty().bindBidirectional(labelProperty());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 16));
		label.layoutXProperty().bind(xProperty());
		label.layoutYProperty().bind(yProperty().subtract(15));

		text = new TextField(data.get());
		text.layoutXProperty().bind(xProperty());
		text.layoutYProperty().bind(yProperty().subtract(10));
		
		labelProperty().bindBidirectional(getTextData().textProperty());

	}

	public final StringProperty labelProperty() {
		return data;
	}

	public Text getLabel() {
		return label;
	}
	
	public TextField getTextData(){
		return text;
	}
	public TextField getText(boolean isShow) {
		text.setText(labelProperty().get());
		if(isShow){
			text.setVisible(isShow);
		}else{
			text.setVisible(false);
		}
		return text;
	}
	public void setTextInVisible(){
		text.setVisible(false);
	}
}
