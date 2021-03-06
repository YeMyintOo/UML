package CanvaBoxs;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import Calculate.ScreenDetail;
import Database.ToolHandler;
import Library.CodeGenerate;
import Library.MyGridLine;
import Library.Types;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CanvasPane extends Pane {

	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	protected Document doc;
	public ToolHandler toolHandler;
	public Color color;
	public Scene owner;
	public Stage parent;
	public File path;
	public BorderPane gridLine;
	public DropShadow shape;
	public boolean isNew;
	public boolean isLoad;
	public CodeGenerate code;
	public HBox menu;
	public Button printB;
	public Button saveB;
	public Label nLabel;
	protected ScreenDetail screen;
	

	public CanvasPane() {
		toolHandler = new ToolHandler();
		

		// GridLine
		gridLine = new BorderPane();

		// Shape
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);

		// Menu
		menu = new HBox();
		screen = new ScreenDetail();
		menu.setStyle("-fx-padding:5 1 5 5;" + "-fx-background-color:white;" + "-fx-border-style: solid inside;"
				+ "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;"
				+ "-fx-border-color: gray;");

		menu.setSpacing(10);
		nLabel = new Label("");
		nLabel.setStyle(
				"-fx-font-size: 16px;" + " -fx-font-family: 'Segoe UI Semibold';" + "-fx-text-fill:rgb(142,56,142);");

		saveB = new Button("");
		saveB.setStyle("-fx-background-color:#FFFFFF");
		printB = new Button("");
		printB.setStyle("-fx-background-color:#FFFFFF");
		Image sIMG, pIMG;
		try {
			sIMG = new Image(new FileInputStream("Resources/Image/Save.png"));
			pIMG = new Image(new FileInputStream("Resources/Image/Print.png"));
			saveB.setGraphic(new ImageView(sIMG));
			printB.setGraphic(new ImageView(pIMG));
		} catch (Exception e) {
			e.printStackTrace();
		}

		getChildren().add(menu);
	}

	public Label getLabel(Types nodes) {
		switch (nodes) {
		case Usecase:
			menu.setLayoutX(screen.getWidth() - 210);
			nLabel.setText("Use Case");
			break;
		case Object:
			menu.setLayoutX(screen.getWidth() - 190);
			nLabel.setText("Object");
			break;
		case Sequence:
			menu.setLayoutX(screen.getWidth() - 150);
			nLabel.setText("Sequence");
			break;
		case Collaboration:
			menu.setLayoutX(screen.getWidth() - 150);
			nLabel.setText("Colloaboration");
			break;
		case Class:
			menu.setLayoutX(screen.getWidth() - 150);
			nLabel.setText("Class");
			break;
		case Statechart:
			menu.setLayoutX(screen.getWidth() - 150);
			nLabel.setText("State Chart");
			break;
		case Activity:
			menu.setLayoutX(screen.getWidth() - 150);
			nLabel.setText("Activity");
			break;
		case Component:
			menu.setLayoutX(screen.getWidth() - 150);
			nLabel.setText("Component");
			break;
		case Deployment:
			menu.setLayoutX(screen.getWidth() - 150);
			nLabel.setText("Deployment");
			break;
		}

		return nLabel;
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

	public Stage getStage() {
		return parent;
	}

	public void setStage(Stage parent) {
		this.parent = parent;
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
}
