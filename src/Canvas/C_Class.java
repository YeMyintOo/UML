package Canvas;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class C_Class extends Rectangle {

	private Rectangle dataBox; // Data area box

	private Rectangle funBox; // Function area box
	private StringProperty name; // Class name
	private ArrayList<StringProperty> datas;
	private ArrayList<StringProperty> functions;

	public C_Class(double x, double y, double width, double height, Color bgcolor, Color scolor) {
		super(x, y, 100, 40);
		setFill(bgcolor);
		setStroke(scolor);
		name = new SimpleStringProperty("Class Name");

		dataBox = new Rectangle(x, y, width, height);
		funBox = new Rectangle(x, y, width, height);

		dataBox = new Rectangle(x, y + getHeight(), 100, 20);
		dataBox.setFill(Color.WHITE);
		dataBox.setStroke(scolor);
		dataBox.xProperty().bind(this.xProperty());
		dataBox.yProperty().bind(this.yProperty().add(getHeight()));

		funBox = new Rectangle(dataBox.getX(), dataBox.getY() + dataBox.getHeight(), 100, 20);
		funBox.setFill(Color.WHITE);
		funBox.setStroke(scolor);
		funBox.xProperty().bind(dataBox.xProperty());
		funBox.yProperty().bind(dataBox.yProperty().add(dataBox.getHeight()));

		dataBox.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				funBox.yProperty().bind(dataBox.heightProperty().add(dataBox.yProperty()));
			}
		});

		widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				funBox.widthProperty().bind(widthProperty());
				dataBox.widthProperty().bind(widthProperty());
			}
		});

		widthProperty().bind(dataBox.widthProperty());
		dataBox.widthProperty().bind(funBox.widthProperty());

		datas = new ArrayList<StringProperty>();
		functions = new ArrayList<StringProperty>();
	}

	public Rectangle getdataBox() {
		return dataBox;
	}

	public Rectangle getfunctionBox() {
		return funBox;
	}

	public void addData(String data) {
		StringProperty d = new SimpleStringProperty("variable:data-type");
		datas.add(d);
	}

	public void addFunction(String data) {
		StringProperty d = new SimpleStringProperty("name:return-type");
		functions.add(d);
	}

	public ArrayList<StringProperty> getDatas() {
		return datas;
	}

	public ArrayList<StringProperty> getFunctions() {
		return functions;
	}

	public final StringProperty labelProperty() {
		return name;
	}

}
