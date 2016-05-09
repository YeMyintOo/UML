package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class D_File extends Rectangle {

	private Text label;
	private Text data;
	private Rectangle file;

	public D_File(double x, double y, Color color) {
		super(x, y, 140, 60);
		setTranslateZ(10);
		setStroke(Color.LIGHTGRAY);
		setFill(color);

		label = new Text("<<Artifact>>");
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 3)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 3));
		label.yProperty().bind(yProperty().add(20));

		data = new Text("fileList.jar");
		data.xProperty().bind(xProperty().add(widthProperty().getValue() / 3)
				.subtract(data.layoutBoundsProperty().getValue().getWidth() / 3));
		data.yProperty().bind(yProperty().add(35));

		file = new Rectangle();
		file.setWidth(20);
		file.setHeight(20);
		file.setFill(Color.WHITE);
		file.setStroke(Color.LIGHTGRAY);
		file.xProperty().bind(xProperty().add(widthProperty().getValue()).subtract(25));
		file.yProperty().bind(yProperty().add(10));
	}

	public Text getLabel() {
		return label;
	}

	public Text getData() {
		return data;
	}

	public Rectangle getFile(){
		return file;
	}
}
