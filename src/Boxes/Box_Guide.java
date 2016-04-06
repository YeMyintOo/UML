package Boxes;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Box_Guide extends Stage {
	// User Guide (Web browser)
	WebView webView;
	WebEngine webEngine;

	public Box_Guide(Stage owner) {
		super();
		initOwner(owner);
		webView = new WebView();
		webEngine = webView.getEngine();
		BorderPane pane = new BorderPane();

		String html = "<html><head><title>Test</title></head>" + "<body><h1>Help Guide using WebView</h1></body></html>";

		webEngine.loadContent(html);
		pane.setCenter(webView);
		Scene scene = new Scene(pane);
		setScene(scene);
		setTitle("User Guide");

	}
}
