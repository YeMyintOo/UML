package CanvaBoxs;

import java.util.ArrayList;

import Canvas.SE_Role;
import Database.ToolHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SequenceCanvaBox extends Pane {
	// Only Sequence Components Can Draw
	private boolean isNew;

	private ToolHandler toolHandler;
	private Color color;
	private DropShadow shape;

	// Role
	private ArrayList<SE_Role> roles;
	private SE_Role role;
	private boolean isRole;
	

	public SequenceCanvaBox() {
		init();
		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				isNewOrEdit(e);

				if (isNew) {
					toolHandler = new ToolHandler();
					color = Color.web(toolHandler.getColor());

					switch (toolHandler.getTool()) {

					case "Sequence_Role":
						role = new SE_Role(e.getX(), e.getY(), color);
						isRole = true;
						getChildren().add(role);
						break;
					}
				}
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isRole) {
					role.setX(e.getX());
					role.setY(e.getY());

				}
			}
		});

		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isRole) {
					drawRoleLabel(role);
					roles.add(role);
					isRole = false;
				}
			}
		});

	}

	public void init() {
		shape = new DropShadow();
		shape.setOffsetX(5);
		shape.setOffsetY(5);
		shape.setRadius(15);
		roles = new ArrayList<SE_Role>();
	}

	public void isNewOrEdit(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditRole(e, point);

	}

	public void drawRoleLabel(SE_Role role) {
		Text label = new Text(role.labelProperty().getValue());
		label.setFont(Font.font("Arial", FontWeight.BLACK, 14));
		label.textProperty().bind(role.labelProperty());
		
		DoubleProperty length = new SimpleDoubleProperty(); 
		length.bind(role.lifeProperty().add(10));
		System.out.println(" Length :"+ length.doubleValue());
		
		Line line = new Line(role.getX() + (role.getWidth() / 2), role.getY() + role.getHeight(),
				role.getX() + (role.getWidth() / 2), role.getY() + length.doubleValue());
		line.setStroke(color);
		line.startXProperty().bind(role.xProperty().add(role.getWidth() / 2));
		line.startYProperty().bind(role.yProperty().add(role.getHeight()));
		line.endXProperty().bind(role.xProperty().add(role.getWidth() / 2));
	//	line.endYProperty().bind(role.yProperty().add(length.doubleValue()));
		line.endYProperty().bind(length);

		label.layoutXProperty().bind(role.xProperty().add(label.layoutBoundsProperty().getValue().getWidth() / 2));
		label.layoutYProperty().bind(role.yProperty().add(role.heightProperty().divide(2)));
		getChildren().addAll(label, line);
	}

	public void isNewOREditRole(MouseEvent e, Point2D point) {
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).contains(point)) {
				isNew = false;
				int index = i;
				roles.get(i).addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						roles.get(index).setX(key.getX());
						roles.get(index).setY(key.getY());

					}
				});

				roles.get(i).setOnScroll(new EventHandler<ScrollEvent>() {
					@Override
					public void handle(ScrollEvent s) {
						if (s.getDeltaY() == 40) {
							roles.get(index).lifeProperty().set(roles.get(index).lifeProperty().getValue() + 10);
							System.out.println(" Role Up :"+ roles.get(index).lifeProperty().getValue());
							System.out.println(" Length Up :"+ length.doubleValue());

						} else {
							roles.get(index).lifeProperty().set(roles.get(index).lifeProperty().getValue() - 10);
							System.out.println(" Length Down :"+ roles.get(index).lifeProperty().getValue());
							System.out.println(" Length Down :"+ length.doubleValue());
						}
					}
				});

				roles.get(i).addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent key) {
						Button x = null;
						if (key.getClickCount() == 2) {
							// Edit Actor Label
							TextField data = new TextField();
							data.layoutXProperty().bind(roles.get(index).xProperty().add(60));
							data.layoutYProperty().bind(roles.get(index).yProperty().add(60));
							getChildren().add(data);
							data.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
								@Override
								public void handle(KeyEvent e) {
									if (e.getCode() == KeyCode.ENTER) {
										if (!data.getText().equals("")) {
											roles.get(index).labelProperty().set(data.getText().trim());
											Text w = new Text(data.getText().trim());
											roles.get(index).widthProperty()
													.bind(new SimpleDoubleProperty(
															w.layoutBoundsProperty().getValue().getWidth()).multiply(2)
																	.subtract(10));
										}
										data.setVisible(!data.isVisible());
									}
								}
							});
						}
					}
				});

				roles.get(i).setEffect(shape);
			} else {
				roles.get(i).setEffect(null);
			}
		}
	}
}
