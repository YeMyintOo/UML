package Canvas;

import Library.LRectangle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class C_Inheritance extends Line {

	private StringProperty data1; // Start Point Multiplicity
	private StringProperty data2; // End Point Multiplicity

	private Line l1;
	private Line l2;
	private Line l3;

	private Rectangle node1;
	private Rectangle node2;

	private Rectangle startNode;
	private Rectangle endNode;

	private Polygon tri;

	private boolean isdrawable;

	public C_Inheritance(double startx, double starty, double endx, double endy, Color color) {
		super(startx, starty, endx, endy);
		setStroke(color);

		data1 = new SimpleStringProperty("1");
		data2 = new SimpleStringProperty("1");

		node1 = new LRectangle();
		node2 = new LRectangle();
		startNode = new LRectangle();
		endNode = new LRectangle();
		tri = new Polygon();
		tri.setFill(Color.WHITE);
		tri.setStroke(Color.LIGHTGRAY);

		l1 = new Line();
		l1.setStroke(color);

		l2 = new Line();
		l2.setStroke(color);

		l3 = new Line();
		l3.setStroke(color);

		node1.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				node1.setX(e.getX() - 5);
				node1.setY(e.getY() - 5);
				startNode.setY(e.getY() - 5);
			}
		});

		node2.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				node2.setX(e.getX() - 5);
				node2.setY(e.getY() - 5);
				endNode.setY(e.getY() - 5);
			}
		});
		startNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				startNode.setX(e.getX() - 5);
			}
		});
		endNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				endNode.setX(e.getX() - 5);
			}
		});

	}

	public Rectangle getStartNode() {
		return startNode;
	}

	public Rectangle getEndNode() {
		return endNode;
	}

	public Rectangle getNode1() {
		return node1;
	}

	public Rectangle getNode2() {
		return node2;
	}

	public boolean filterLine() {
		isdrawable = true;
		double d = Math.sqrt((Math.pow(getEndX() - getStartX(), 2)) + (Math.pow(getEndY() - getStartY(), 2)));
		double mid = d / 2;
		double slope = (getStartY() - getEndY()) / (getStartX() - getEndX());

		if (getStartX() < getEndX() && getStartY() < getEndY()) {
			if (slope < 2) {
				node1.setX(getStartX() + mid - 5);
				node1.setY(getStartY() - 5);

				node2.setX(getStartX() + mid - 5);
				node2.setY(getEndY() - 5);

				startNode.setX(getStartX() - 5);
				startNode.setY(getStartY() - 5);

				endNode.setX(getEndX() - 5);
				endNode.setY(getEndY() - 5);

				tri.getPoints().addAll(endXProperty().subtract(20).get(), endYProperty().subtract(10).get(),
						endXProperty().get(), endYProperty().get(), endXProperty().subtract(20).get(),
						endYProperty().add(10).get());

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() + mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() + mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() + mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() + mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());

			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() > getEndX() && getStartY() > getEndY()) {
			if (slope < 2) {
				node1.setX(getStartX() - mid - 5);
				node1.setY(getStartY() - 5);

				node2.setX(getStartX() - mid - 5);
				node2.setY(getEndY() - 5);

				startNode.setX(getStartX() - 5);
				startNode.setY(getStartY() - 5);

				endNode.setX(getEndX() - 5);
				endNode.setY(getEndY() - 5);

				tri.getPoints().addAll(endXProperty().add(20).get(), endYProperty().subtract(10).get(),
						endXProperty().get(), endYProperty().get(), endXProperty().add(20).get(),
						endYProperty().add(10).get());

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() - mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() - mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() - mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() - mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());

			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() > getEndX() && getStartY() < getEndY()) {
			if (slope > -1.5) {
				node1.setX(getStartX() - mid - 5);
				node1.setY(getStartY() - 5);

				node2.setX(getStartX() - mid - 5);
				node2.setY(getEndY() - 5);

				startNode.setX(getStartX() - 5);
				startNode.setY(getStartY() - 5);

				endNode.setX(getEndX() - 5);
				endNode.setY(getEndY() - 5);

				tri.getPoints().addAll(endXProperty().add(20).get(), endYProperty().subtract(10).get(),
						endXProperty().get(), endYProperty().get(), endXProperty().add(20).get(),
						endYProperty().add(10).get());

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() - mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() - mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() - mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() - mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());
			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() < getEndX() && getStartY() > getEndY()) {
			if (slope > -1.5) {
				node1.setX(getStartX() + mid - 5);
				node1.setY(getStartY() - 5);

				node2.setX(getStartX() + mid - 5);
				node2.setY(getEndY() - 5);

				startNode.setX(getStartX() - 5);
				startNode.setY(getStartY() - 5);

				endNode.setX(getEndX() - 5);
				endNode.setY(getEndY() - 5);

				tri.getPoints().addAll(endXProperty().subtract(20).get(), endYProperty().subtract(10).get(),
						endXProperty().get(), endYProperty().get(), endXProperty().subtract(20).get(),
						endYProperty().add(10).get());

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() + mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() + mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() + mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() + mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());
			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() < getEndX() && getStartY() == getEndY()) {
			if (slope == -0.0) {
				node1.setX(getStartX() + mid - 5);
				node1.setY(getStartY() - 5);

				node2.setX(getStartX() + mid - 5);
				node2.setY(getEndY() - 5);

				startNode.setX(getStartX() - 5);
				startNode.setY(getStartY() - 5);

				endNode.setX(getEndX() - 5);
				endNode.setY(getEndY() - 5);

				tri.getPoints().addAll(endXProperty().subtract(20).get(), endYProperty().subtract(10).get(),
						endXProperty().get(), endYProperty().get(), endXProperty().subtract(20).get(),
						endYProperty().add(10).get());

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX() + mid);
				l1.setEndY(getStartY());

				l2.setStartX(getStartX() + mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() + mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() + mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());
			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() > getEndX() && getStartY() == getEndY()) {
			if (slope == -0.0) {
				node1.setX(getStartX() - 5);
				node1.setY(getStartY() + mid - 5);

				node2.setX(getStartX() - 5);
				node2.setY(getEndY() + mid - 5);

				startNode.setX(getStartX() - 5);
				startNode.setY(getStartY() - 5);

				endNode.setX(getEndX() - 5);
				endNode.setY(getEndY() - 5);

				tri.getPoints().addAll(endXProperty().add(20).get(), endYProperty().subtract(10).get(),
						endXProperty().get(), endYProperty().get(), endXProperty().add(20).get(),
						endYProperty().add(10).get());

				l1.setStartX(getStartX());
				l1.setStartY(getStartY());
				l1.setEndX(getStartX());
				l1.setEndY(getStartY() + mid);

				l2.setStartX(getStartX() - mid);
				l2.setStartY(getStartY());
				l2.setEndX(getStartX() - mid);
				l2.setEndY(getEndY());

				l3.setStartX(getStartX() - mid);
				l3.setStartY(getEndY());
				l3.setEndX(getEndX());
				l3.setEndY(getEndY());

			} else {
				isdrawable = false;
				System.out.println("You cann't daraw This Line");
			}
		}

		if (getStartX() == getEndX() && getStartY() < getEndY()) {
		}

		if (getStartX() == getEndX() && getStartY() > getEndY()) {
		}

		l1.startXProperty().bind(startNode.xProperty().add(5));
		l3.endXProperty().bind(endNode.xProperty().add(5));
		l1.endXProperty().bind(node1.xProperty().add(5));
		l1.startYProperty().bind(node1.yProperty().add(5));
		l1.endYProperty().bind(node1.yProperty().add(5));
		l2.startYProperty().bind(node1.yProperty().add(5));
		l2.startXProperty().bind(node1.xProperty().add(5));
		l2.endXProperty().bind(node2.xProperty().add(5));
		l2.endYProperty().bind(node2.yProperty().add(5));
		l3.startXProperty().bind(node2.xProperty().add(5));
		l3.startYProperty().bind(node2.yProperty().add(5));
		l3.endYProperty().bind(node2.yProperty().add(5));

		startXProperty().bind(l1.startXProperty());
		startYProperty().bind(l1.endYProperty());
		endXProperty().bind(l3.endXProperty());
		endYProperty().bind(l3.endYProperty());
		return isdrawable;
	}

	public StringProperty startMultiplicity() {
		return data1;
	}

	public StringProperty endMultiplicity() {
		return data2;
	}

	public Line getL1() {
		return l1;
	}

	public Line getL2() {
		return l2;
	}

	public Line getL3() {
		return l3;
	}

	public Polygon getTri() {
		return tri;
	}

}
