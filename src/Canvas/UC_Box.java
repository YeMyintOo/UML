package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UC_Box extends Rectangle {
	private double x;
	private double y;
	private double width;
	private double height;
	private double arcWidth;
	private double arcHeight;
	private Color bgcolor; //Background Color
	private Color scolor;//Stroke Color

	public UC_Box() {
		super(0, 0, 0, 0);
	}

	public UC_Box(double x, double y, double width, double height) {
		super(x,y,width,height);
		setBgcolor(Color.WHITE);
		setScolor(Color.BLACK);
	}

	public UC_Box(double x, double y, double width, double height,Color bgcolor,Color scolor) {
		super(x,y,width,height);
		setBgcolor(bgcolor);
		setScolor(scolor);
	}
	
	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;
		setFill(bgcolor);
	}

	public Color getScolor() {
		return scolor;
	}

	public void setScolor(Color scolor) {
		this.scolor = scolor;
		setStroke(scolor);
	}

}
