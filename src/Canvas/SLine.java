package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SLine extends Line {
	private int startx;
	private int starty;
	private int endx;
	private int endy;
	private Color start;
	private Color end;

	// Constructor
	public SLine() {
		super(0, 0, 0, 0);
	}

	public SLine(int starx, int starty, int endx, int endy) {
		super(starx, starty, endx, endy);
		setStart(Color.RED);
		setEnd(Color.RED);
	}

	public int getStartx() {
		return startx;
	}

	public void setStartx(int startx) {
		this.startx = startx;
	}

	public int getStarty() {
		return starty;
	}

	public void setStarty(int starty) {
		this.starty = starty;
	}

	public int getEndx() {
		return endx;
	}

	public void setEndx(int endx) {
		this.endx = endx;
	}

	public int getEndy() {
		return endy;
	}

	public void setEndy(int endy) {
		this.endy = endy;
	}

	public Color getStart() {
		return start;
	}

	public void setStart(Color start) {
		this.start = start;
	}

	public Color getEnd() {
		return end;
	}

	public void setEnd(Color end) {
		this.end = end;
	}

}
