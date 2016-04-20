package Canvas;


import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class UC_IncludeLine extends Line {
	private double startx;
	private double starty;
	private double endx;
	private double endy;
	private Color color;

	public UC_IncludeLine() {
		super(0, 0,0, 0);

	}

	public UC_IncludeLine(double startx, double starty, double endx, double endy) {
		super(startx, starty, endx, endy);
		setStartx(startx);
		setStarty(starty);
		setEndx(endx);
		setEndy(endy);
		setColor(Color.BLACK);

	}

	public UC_IncludeLine(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStartx(startx);
		setStarty(starty);
		setEndx(endx);
		setEndy(endy);
		setColor(color);

	}

	public double getStartx() {
		return startx;
	}

	public void setStartx(double startx) {
		this.startx = startx;
	}

	public double getStarty() {
		return starty;
	}

	public void setStarty(double starty) {
		this.starty = starty;
	}

	public double getEndx() {
		return endx;
	}

	public void setEndx(double endx) {
		this.endx = endx;
		setEndX(endx);
	}

	public double getEndy() {
		return endy;
	}

	public void setEndy(double endy) {
		this.endy = endy;
		setEndY(endy);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
		setFill(color);
	}

}
