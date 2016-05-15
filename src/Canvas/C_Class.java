package Canvas;

import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class C_Class extends Rectangle {

	private Rectangle dataBox; // Data area box

	private Rectangle funBox; // Function area box
	private StringProperty name; // Class name
	private ArrayList<StringProperty> datas;
	private ArrayList<StringProperty> functions;
	private Text label;
	private TextField field;

	public C_Class(double x, double y,Color bgcolor, Color scolor) {
		super(x, y, 120, 40);
		setFill(bgcolor);
		setStroke(scolor);
		name = new SimpleStringProperty("Class Name");

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
		
		
		funBox.widthProperty().bind(widthProperty());
		dataBox.widthProperty().bind(widthProperty());
		
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
		
		dataBox.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				widthProperty().bind(dataBox.widthProperty());
				funBox.widthProperty().bind(dataBox.widthProperty());
			}
		});
		
		funBox.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				widthProperty().bind(funBox.widthProperty());
				dataBox.widthProperty().bind(funBox.widthProperty());
			}
		});

		datas = new ArrayList<StringProperty>();
		functions = new ArrayList<StringProperty>();
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
