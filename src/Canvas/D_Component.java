package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class D_Component extends Rectangle{

	private Text label;
	private Text data;
	private StringProperty dataV;
	private TextField field;
	
	private Rectangle file;
	private Rectangle node1;
	private Rectangle node2;

	public D_Component(double x, double y, Color color) {
		super(x, y, 160, 50);
		setTranslateZ(10);
		setStroke(Color.LIGHTGRAY);
		setFill(color);

		label = new Text("<<Component>>");
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(yProperty().add(20));
		
		dataV = new SimpleStringProperty("file.jar");
		data = new Text();
		data.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		data.textProperty().bind(labelProperty());
		data.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
				.subtract(data.layoutBoundsProperty().getValue().getWidth() / 2));
		data.yProperty().bind(yProperty().add(35));

		
		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(xProperty().subtract(25));
		field.layoutYProperty().bind(yProperty().add(30));
		field.textProperty().bindBidirectional(labelProperty());
		
		file = new Rectangle();
		file.setWidth(30);
		file.setHeight(20);
		file.setFill(Color.WHITE);
		file.setStroke(Color.LIGHTGRAY);
		file.xProperty().bind(xProperty().add(widthProperty().getValue()).subtract(35));
		file.yProperty().bind(yProperty().add(10));
		
		node1 = new Rectangle();
		node1.setWidth(10);
		node1.setHeight(5);
		node1.setFill(Color.WHITE);
		node1.setStroke(Color.LIGHTGRAY);
		node1.xProperty().bind(file.xProperty().subtract(5));
		node1.yProperty().bind(file.yProperty().add(4));		
		
		node2 = new Rectangle();
		node2.setWidth(10);
		node2.setHeight(5);
		node2.setFill(Color.WHITE);
		node2.setStroke(Color.LIGHTGRAY);
		node2.xProperty().bind(file.xProperty().subtract(5));
		node2.yProperty().bind(file.yProperty().add(12));
		
		addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setX(e.getX());
				setY(e.getY());
			}
		});
		
		data.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				field.setVisible(true);
			}
		});
		
		DoubleProperty w = new SimpleDoubleProperty();
		DoubleProperty min = new SimpleDoubleProperty();
		field.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				min.set(label.layoutBoundsProperty().getValue().getWidth());
				w.set(data.layoutBoundsProperty().getValue().getWidth());
				data.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(data.layoutBoundsProperty().getValue().getWidth() / 2));
				label.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
						.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
				widthProperty().bind(w.add(20));
				if (w.get() < min.get()) {
					widthProperty().unbind();
					setWidth(160);
					data.xProperty().bind(xProperty().add(widthProperty().getValue() / 2)
							.subtract(data.layoutBoundsProperty().getValue().getWidth() / 2));
				}
				if (e.getCode() == KeyCode.ENTER) {
					field.setVisible(false);
				}
			}
		});
		
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

	public StringProperty labelProperty() {
		return dataV;
	}

	public void setLabelProperty(String label) {
		dataV.set(label);
	}
	
	public Text getLabel() {
		return label;
	}

	public Text getData() {
		return data;
	}

	public Rectangle getComponent(){
		return file;
	}
	
	public Rectangle getNode1(){
		return node1;
	}
	public Rectangle getNode2(){
		return node2;
	}

}
