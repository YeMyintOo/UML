package Canvas;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SE_NewActivation extends Line {
	private Color color;

	private DoubleProperty life;

	private DoubleProperty lifeP;
	private StringProperty data;

	private Line top;
	private Line bot;
	private Line nLine;
	private Rectangle newOb;
	private Text label;
	private Rectangle lifeB;
	private Line rLine;
	private Line rtop;
	private Line rbot;
	private TextField field;

	public SE_NewActivation(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);
		life = new SimpleDoubleProperty(100);
		lifeP = new SimpleDoubleProperty(150);
		data = new SimpleStringProperty(":Role");

		newOb = new Rectangle();
		newOb.setWidth(100);
		newOb.setFill(color);
		newOb.setStroke(Color.LIGHTGRAY);
		newOb.setHeight(40);
		newOb.xProperty().bindBidirectional(endXProperty());
		DoubleProperty y = new SimpleDoubleProperty();
		y.set(endYProperty().subtract(20).doubleValue());
		newOb.yProperty().bindBidirectional(y);
		newOb.yProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				endYProperty().bind(newOb.yProperty().add(20));
			}
		});

		nLine = new Line();
		nLine.getStrokeDashArray().addAll(10d, 10d);
		nLine.startXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
		nLine.startYProperty().bind(newOb.yProperty().add(newOb.getHeight()));
		nLine.endXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
		nLine.endYProperty().bind(newOb.yProperty().add(newOb.getHeight()).add(lifepProperty()));

		label = new Text(data.get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bindBidirectional(labelProperty());
		label.xProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.yProperty().bind(newOb.yProperty().add(20));

		lifeB = new Rectangle();
		lifeB.setFill(Color.LIGHTGRAY);
		lifeB.setStroke(Color.BLACK);
		lifeB.setHeight(100);
		lifeB.setWidth(20);
		lifeB.xProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2).subtract(10));
		lifeB.yProperty().bind(newOb.yProperty().add(newOb.getHeight()));
		lifeB.heightProperty().bind(lifeProperty());

		top = new Line();
		top.startXProperty().bind(endXProperty());
		top.startYProperty().bind(endYProperty());
		top.endXProperty().bind(endXProperty().subtract(10));
		top.endYProperty().bind(endYProperty().subtract(5));

		bot = new Line();
		bot.startXProperty().bind(endXProperty());
		bot.startYProperty().bind(endYProperty());
		bot.endXProperty().bind(endXProperty().subtract(10));
		bot.endYProperty().bind(endYProperty().add(5));

		rLine = new Line();
		rLine.getStrokeDashArray().addAll(10d, 5d);
		rLine.startXProperty().bind(getLifeBox().xProperty());
		rLine.startYProperty().bind(getLifeBox().yProperty().add(lifeProperty()));
		rLine.endYProperty().bind(getLifeBox().yProperty().add(lifeProperty()));
		rLine.endXProperty().bind(startXProperty());

		rtop = new Line();
		rtop.startXProperty().bind(rLine.endXProperty());
		rtop.startYProperty().bind(rLine.endYProperty());
		rtop.endXProperty().bind(rLine.endXProperty().add(10));
		rtop.endYProperty().bind(rLine.endYProperty().subtract(5));

		rbot = new Line();
		rbot.startXProperty().bind(rLine.endXProperty());
		rbot.startYProperty().bind(rLine.endYProperty());
		rbot.endXProperty().bind(rLine.endXProperty().add(10));
		rbot.endYProperty().bind(rLine.endYProperty().add(5));
		
		field = new TextField(labelProperty().get());
		field.layoutXProperty().bind(newOb.xProperty().subtract(25));
		field.layoutYProperty().bind(newOb.yProperty().add(10));
		field.textProperty().bindBidirectional(labelProperty());


		startYProperty().bindBidirectional(endYProperty());
		newOb.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				nLine.startXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
				nLine.startYProperty().bind(newOb.yProperty().add(newOb.getHeight()));
				nLine.endXProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2));
				nLine.endYProperty().bind(newOb.yProperty().add(newOb.getHeight()).add(lifepProperty()));
				lifeB.xProperty().bind(newOb.xProperty().add(newOb.widthProperty().getValue() / 2).subtract(10));
				lifeB.yProperty().bind(newOb.yProperty().add(newOb.getHeight()));
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

	public void setTextInVisible() {
		field.setVisible(false);
	}
	
	public Line getRTop() {
		return rtop;
	}

	public Line getRBot() {
		return rbot;
	}

	public Line getRLine() {
		return rLine;
	}

	public Line getNLine() {
		return nLine;
	}

	public Rectangle getLifeBox() {
		return lifeB;
	}

	public Text getLabel() {
		return label;
	}

	public StringProperty labelProperty() {
		return data;
	}

	public DoubleProperty lifepProperty() {
		return lifeP;
	}

	public DoubleProperty lifeProperty() {
		return life;
	}

	public Rectangle getNewOb() {

		return newOb;
	}

	public void setNewOb(Rectangle newOb) {
		this.newOb = newOb;
	}

	public Line getTop() {
		return top;
	}

	public Line getBot() {
		return bot;
	}

}
