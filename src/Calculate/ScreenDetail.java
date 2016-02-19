package Calculate;

import java.awt.*;
import javax.swing.JFrame;

public class ScreenDetail {
	protected JFrame box;
	protected Dimension d;

	public ScreenDetail() {
		box = new JFrame();
		Toolkit kit = box.getToolkit();
		d = kit.getScreenSize();
	}

	public Dimension getFScreenSize() {
		return d;
	}

	public double getHeight() {
		return d.height;
	}

	public double getWidth() {
		return d.width;
	}
}
