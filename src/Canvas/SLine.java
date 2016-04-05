package Canvas;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SLine extends Line {
	private int startx;
	private int starty;
	private int endx;
	private int endy;
	

	// Constructor	
	public SLine(Color color) {
		super(0, 0, 0, 0);
		setStroke(color);
	}
	

	public SLine(int starx, int starty, int endx, int endy) {
		super(starx, starty, endx, endy);
		
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

	
}
