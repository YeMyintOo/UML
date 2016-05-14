package Canvas;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class O_Object extends Rectangle {

	private Rectangle dataBox; // Data area box
	private StringProperty oname; // Object name
	private Text label;
	private TextField text;

	private ArrayList<StringProperty> datas;

	public O_Object(double x, double y, double width, double height, Color bgcolor, Color scolor) {
		super(x, y, width, height);
		setFill(bgcolor);
		setStroke(scolor);

		datas = new ArrayList<StringProperty>();
		oname = new SimpleStringProperty("Oject :Class ");

		dataBox = new Rectangle(x, y + getHeight(), 100, 40);
		dataBox.setFill(Color.WHITE);
		dataBox.setStroke(scolor);
		dataBox.xProperty().bind(this.xProperty());
		dataBox.yProperty().bind(this.yProperty().add(getHeight()));

		label = new Text(labelProperty().get());
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(20));

		text = new TextField(oname.get());
		text.layoutXProperty().bind(xProperty().subtract(25));
		text.layoutYProperty().bind(yProperty().add(10));
		text.textProperty().bindBidirectional(labelProperty());

		widthProperty().bindBidirectional(dataBox.widthProperty());
		labelProperty().bindBidirectional(getTextData().textProperty());

	}

	public Text getLabel() {
		return label;
	}

	public TextField getTextData() {
		return text;
	}

	public TextField getText(boolean isShow) {
		text.setText(labelProperty().get());
		if (isShow) {
			text.setVisible(isShow);
		} else {
			text.setVisible(false);
		}
		return text;
	}

	public void setTextInVisible() {
		text.setVisible(false);
	}

	public final StringProperty labelProperty() {
		return oname;
	}

	public void addData(String data) {
		StringProperty d = new SimpleStringProperty("var=value");
		datas.add(d);
	}

	public ArrayList<StringProperty> getDatas() {
		return datas;
	}

	public Rectangle getdataBox() {
		return dataBox;
	}
}
