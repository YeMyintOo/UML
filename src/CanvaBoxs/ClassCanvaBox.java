package CanvaBoxs;

import java.util.ArrayList;

import Canvas.C_Class;
import Database.ToolHandler;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ClassCanvaBox extends Pane {
	// Only Class Components Can Draw
	private ToolHandler toolHandler;
	private Color color;
	private DropShadow shape;
	private boolean isNew;

	// Class
	private ArrayList<C_Class> cboxs;
	private C_Class cbox;
	private boolean isClass;

	public ClassCanvaBox() {
		init();
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isNewOREdit(e);
				if (isNew) {
					toolHandler = new ToolHandler();
					color = Color.web(toolHandler.getColor());
					switch (toolHandler.getTool()) {
					case "Class_Class":
						cbox = new C_Class(e.getX(), e.getY(), 100, 100, color, Color.LIGHTGRAY);
						isClass = true;
						getChildren().addAll(cbox, cbox.getdataBox(), cbox.getfunctionBox());
						drawClassLabel(cbox);
						break;
					default:
						break;
					}
				} else {

				}

			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isClass) {
					cbox.setX(e.getX());
					cbox.setY(e.getY());
				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isClass) {
					cboxs.add(cbox);
					isClass = false;
				}
			}
		});

	}

	public void init() {
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);

		cboxs = new ArrayList<C_Class>();
	}

	public void isNewOREdit(MouseEvent e) {
		Point2D point = new Point2D(e.getX(), e.getY());
		isNew = true;
		isNewOrEditClass(e, point);
	}

	public void drawClassLabel(C_Class cbox) {
		Text label = new Text(cbox.labelProperty().get());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(cbox.labelProperty());
		label.layoutXProperty().bind(cbox.xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 3));
		label.layoutYProperty().bind(cbox.yProperty().add(20));

		cbox.widthProperty().bind(new SimpleDoubleProperty(label.layoutBoundsProperty().getValue().getWidth()).add(60));

		getChildren().add(label);
	}

	public void isNewOrEditClass(MouseEvent e, Point2D point) {
		for (int i = 0; i < cboxs.size(); i++) {
			if (cboxs.get(i).contains(point) || cboxs.get(i).getdataBox().contains(point)
					|| cboxs.get(i).getfunctionBox().contains(point)) {
				isNew = false;
				int index = i;
				// Mouse Class Diagram
				cboxs.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						cboxs.get(index).setX(key.getX());
						cboxs.get(index).setY(key.getY());
					}
				});

				// Data/////////////////////////////////////////////////
				
				cboxs.get(i).getdataBox().addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent key) {
								Button add = new Button("+");
								getChildren().remove(add);
								add.layoutXProperty().bind(cboxs.get(index).getdataBox().xProperty()
										.add(cboxs.get(index).getdataBox().getWidth()));
								add.layoutYProperty().bind(cboxs.get(index).getdataBox().yProperty());
								getChildren().add(add);
								add.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										cboxs.get(index).getdataBox()
												.setHeight(cboxs.get(index).getdataBox().getHeight() + 20);
									}
								});
								add.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										getChildren().remove(add);
									}
								});
							}
						});
						//////////////////////////////////////////////////////

				// Function////////////////////////////////////////////
				
				cboxs.get(i).getfunctionBox().addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent key) {
								Button addf = new Button("+");
								getChildren().remove(addf);
								addf.layoutXProperty().bind(cboxs.get(index).getfunctionBox().xProperty()
										.add(cboxs.get(index).getdataBox().getWidth()));
								addf.layoutYProperty().bind(cboxs.get(index).getfunctionBox().yProperty());
								getChildren().add(addf);
								addf.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										cboxs.get(index).getfunctionBox()
												.setHeight(cboxs.get(index).getfunctionBox().getHeight() + 20);
									}
								});
								addf.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent e) {
										getChildren().remove(addf);
									}
								});
							}
						});
				//////////////////////////////////////////////////////

				cboxs.get(i).setEffect(shape);
			} else {
				cboxs.get(i).setEffect(null);

			}
		}
	}
}
