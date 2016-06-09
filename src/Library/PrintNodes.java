package Library;

import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;

public class PrintNodes {
	private Node node;
	private PageLayout pageLayout;
	public Printer printer;

	public PrintNodes(Node node) {

		printer = Printer.getDefaultPrinter();
		pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);

		PrinterJob job = PrinterJob.createPrinterJob();
		JobSettings setting = job.getJobSettings();
		setting.setPageLayout(pageLayout);
		boolean isPrint = job.showPrintDialog(node.getScene().getWindow());
		if (isPrint) {
			// Scale
			double scaleX = pageLayout.getPrintableWidth() / node.getLayoutBounds().getWidth();
			double scaleY = pageLayout.getPrintableHeight() / node.getLayoutBounds().getHeight();
			double min = Math.min(scaleX, scaleY);
			Scale scale = new Scale(min, min);
			try {
				node.getTransforms().add(scale);
				job.printPage(node);
				job.endJob();
				node.getTransforms().remove(scale);
				System.out.println("***Print Success***");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
