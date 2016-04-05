package Canvas;

import javafx.scene.shape.QuadCurve;

public class SCurve extends QuadCurve {
	private double startx;
	private double starty;
	private double endx;
	private double endy;
	private double anglex;
	private double angley;

	public SCurve() {
		this(0, 0, 0, 0, 0, 0);
	}

	public SCurve(int startx, int starty, int anglex, int angley, int endx, int endy) {
		super(startx, starty, anglex, angley, endx, endy);
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
	}

	public double getEndy() {
		return endy;
	}

	public void setEndy(double endy) {
		this.endy = endy;
	}

	public double getAnglex() {
		return anglex;
	}

	public void setAnglex(double anglex) {
		this.anglex = anglex;
	}

	public double getAngley() {
		return angley;
	}

	public void setAngley(double angley) {
		this.angley = angley;
	}

}
