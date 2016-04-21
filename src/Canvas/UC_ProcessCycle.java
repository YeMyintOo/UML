package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class UC_ProcessCycle extends Ellipse {

	private double centerx;
	private double centery;
	private double radiusx;
	private double radiusy;
	private Color bgcolor; // Background Color;
	private Color scolor; // Stroke Color;

	public UC_ProcessCycle() {
		super(0, 0, 0, 0);
	}

	public UC_ProcessCycle(double centerX, double centerY, double radiusX, double radiusY) {
		super(centerX, centerY, radiusX, radiusY);

	}

	public UC_ProcessCycle(double centerX, double centerY, double radiusX, double radiusY, Color bgcolor,
			Color scolor) {
		super(centerX, centerY, radiusX, radiusY);
		setBgcolor(bgcolor);
		setScolor(scolor);
	}

	public double getCenterx() {
		return centerx;
	}

	public void setCenterx(double centerx) {
		this.centerx = centerx;
		setCenterX(centerx);
	}

	public double getCentery() {
		return centery;
	}

	public void setCentery(double centery) {
		this.centery = centery;
		setCenterY(centery);
	}

	public double getRadiusx() {
		return radiusx;
	}

	public void setRadiusx(double radiusx) {
		this.radiusx = radiusx;
		setRadiusX(radiusx);
	}

	public double getRadiusy() {
		return radiusy;
	}

	public void setRadiusy(double radiusy) {
		this.radiusy = radiusy;
		setRadiusY(radiusy);
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
