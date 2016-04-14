package Main;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class WorkSpaceList extends BorderPane {
	// For New Project Menu Action
	public WorkSpaceList() {
		// Load XML data
		setCenter(new Label("This is WorkSpace List"));
	}

	public WorkSpaceList(String wsname) {
		// New Work Space and Add wsname to XML data
		
	}
}
