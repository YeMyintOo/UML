package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class UC_Actor extends Circle{
	private double centerx;
	private double centery;
	private double radius;
	private Color bgcolor; // Background Color;
	private Color scolor; // Stroke Color;
	
	//body
	private double bstarx;//body star x
	private double bstary;//body star y
	private double body;//body size
	private double legs;
	

	public UC_Actor() {
		super(0, 0, 0);
	}

	public UC_Actor(double centerX, double centerY, double radius) {
		super(centerX, centerY, radius);
		setFill(Color.WHITE);
		setStroke(Color.BLACK);
	}

	public UC_Actor(double centerX, double centerY, double radius,Color bgcolor,
			Color scolor) {
		super(centerX, centerY,radius);
		setBgcolor(bgcolor);
		setScolor(scolor);
		setLegs(5);
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

	public Color getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(Color bgcolor) {
		this.bgcolor = bgcolor;
	}

	public Color getScolor() {
		return scolor;
	}

	public void setScolor(Color scolor) {
		this.scolor = scolor;
	}

	public double getLegs() {
		return legs;
	}

	public void setLegs(double legs) {
		this.legs = legs;
	}

	public double getBstarx() {
		return bstarx;
	}

	public void setBstarx(double bstarx) {
		this.bstarx = bstarx;
	}

	public double getBstary() {
		return bstary;
	}

	public void setBstary(double bstary) {
		this.bstary = bstary;
	}

	public double getBody() {
		return body;
	}

	public void setBody(double body) {
		this.body = body;
	}
}
