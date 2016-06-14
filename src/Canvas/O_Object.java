package Canvas;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class O_Object extends Rectangle {

	private Rectangle dataBox; // Data area box
	private StringProperty name; // Class name
	private ArrayList<StringProperty> datas;
	private ArrayList<Text> dataGs;
	private Text label;
	private TextField field;
	private Rectangle bound;
	private Button addB;

	public O_Object(double x, double y, Color bgcolor, Color scolor) {
		super(x, y, 120, 40);
		setFill(bgcolor);
		setStroke(scolor);
		name = new SimpleStringProperty("Name:Class");

		bound = new Rectangle();

		dataBox = new Rectangle(x, y + getHeight(), 100, 20);
		dataBox.setFill(Color.WHITE);
		dataBox.setStroke(scolor);
		dataBox.xProperty().bind(this.xProperty());
		dataBox.yProperty().bind(this.yProperty().add(getHeight()));

		label = new Text(labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(labelProperty());
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(20));

		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(xProperty().subtract(25));
		field.layoutYProperty().bind(yProperty().add(10));
		field.textProperty().bindBidirectional(labelProperty());

		dataBox.widthProperty().bindBidirectional(widthProperty());
		datas = new ArrayList<StringProperty>();
		dataGs = new ArrayList<Text>();

		
		

	}

	public Rectangle getLinkBound() {
		Bounds d = getBoundsInParent();
		bound.setX(d.getMinX() - 10);
		bound.setY(d.getMinY() - 10);
		bound.setHeight(d.getHeight() + 10);
		bound.setWidth(d.getWidth() + 20);
		return bound;
	}

	public TextField getText(boolean isShow) {
		field.setText(labelProperty().get());
		if (isShow) {
			field.setVisible(isShow);
		} else {
			field.setVisible(false);
		}
		return field;
	}

	public void setTextInVisible() {
		field.setVisible(false);
	}

	public Text getLabel() {
		return label;
	}

	public Rectangle getdataBox() {
		return dataBox;
	}

	public void addData(String data) {
		StringProperty d = new SimpleStringProperty(data);
		Text dg = new Text(data);
		dataGs.add(dg);
		datas.add(d);
	}

	public ArrayList<StringProperty> getDatas() {
		return datas;
	}

	public ArrayList<Text> getDataGs() {
		return dataGs;
	}

	public final StringProperty labelProperty() {
		return name;
	}

	public Button getAddB(){
		addB = new Button("+");
		addB.layoutXProperty().bind(dataBox.xProperty().subtract(30));
		addB.layoutYProperty().bind(dataBox.yProperty());
		return addB;
	}

	public void setAddBInVisible() {
		if(addB!=null){
			addB.setVisible(false);
		}
	}
	
}
