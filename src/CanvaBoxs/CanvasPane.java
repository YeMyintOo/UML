package CanvaBoxs;

import java.io.File;

import Database.ToolHandler;
import Library.MyGridLine;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

public class CanvasPane extends Pane {
	public ToolHandler toolHandler;
	public Color color;
	public Scene owner;
	public File path;
	public BorderPane gridLine;
	public DropShadow shape;
	public Printer defaultprinter;
	public PageLayout pageLayout;
	public boolean isNew;

	public CanvasPane() {
		toolHandler = new ToolHandler();

		// GridLine
		gridLine = new BorderPane();

		// Shape
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);
	}

	public ToolHandler getToolHandler() {
		return toolHandler;
	}

	public void setToolHandler(ToolHandler toolHandler) {
		this.toolHandler = toolHandler;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Scene getOwner() {
		return owner;
	}

	public void setOwner(Scene owner) {
		this.owner = owner;
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public BorderPane getGridLine() {
		return gridLine;
	}

	public void setGridLine(BorderPane gridLine) {
		this.gridLine = gridLine;
	}

	public DropShadow getMyShape() {
		return shape;
	}

	public void setShape(DropShadow shape) {
		this.shape = shape;
	}

	public void setGridLine() {
		int i = 10;
		while (i < 800) {
			MyGridLine l = new MyGridLine(10, i, 1340, i);
			gridLine.getChildren().add(l);
			i = i + 20;
		}
		int k = 10;
		while (k < 1340) {
			MyGridLine l = new MyGridLine(k, 10, k, 690);
			gridLine.getChildren().add(l);
			k = k + 20;
		}
		getChildren().add(gridLine);
		gridLine.toBack();
	}

	public void removeGridLine() {
		getChildren().remove(gridLine);
	}
	
	public void PrintNode(Node node, PageLayout pageLayout) {
		PrinterJob job = PrinterJob.createPrinterJob();
		JobSettings jobSettings = job.getJobSettings();
		jobSettings.setPageLayout(pageLayout);

		boolean proceed = job.showPrintDialog(null);
		if (proceed) {
			double scaleX = pageLayout.getPrintableWidth() / node.getLayoutBounds().getWidth();
			double scaleY = pageLayout.getPrintableHeight() / node.getLayoutBounds().getHeight();
			double minimumScale = Math.min(scaleX, scaleY);
			Scale scale = new Scale(minimumScale, minimumScale);
			try {
				node.getTransforms().add(scale);
			} catch (Exception e) {
				e.printStackTrace();
			}

			job.printPage(node);
			job.endJob();
			node.getTransforms().remove(scale);
			System.out.println("***Print Success");

		}

	}
}
