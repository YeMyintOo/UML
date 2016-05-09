package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class D_Component extends Rectangle{

	private Text label;
	private Text data;
	private Rectangle file;
	private Rectangle node1;
	private Rectangle node2;

	public D_Component(double x, double y, Color color) {
		super(x, y, 160, 60);
		setTranslateZ(10);
		setStroke(Color.LIGHTGRAY);
		setFill(color);

		label = new Text("<<Component>>");
		label.xProperty().bind(xProperty().add(widthProperty().getValue() / 3)
				.subtract(label.layoutBoundsProperty().getValue().getWidth() / 3));
		label.yProperty().bind(yProperty().add(20));

		data = new Text("fileList.jar");
		data.xProperty().bind(xProperty().add(widthProperty().getValue() / 3)
				.subtract(data.layoutBoundsProperty().getValue().getWidth() / 3));
		data.yProperty().bind(yProperty().add(35));

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
