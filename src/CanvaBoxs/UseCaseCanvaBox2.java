package CanvaBoxs;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import Canvas.UC_ActionLine;
import Canvas.UC_Actor;
import Canvas.UC_Box;
import Canvas.UC_ExtendLine;
import Canvas.UC_IncludeLine;
import Canvas.UC_Process;
import Canvas.UC_TypeOfLine;
import Database.ToolHandler;
import Library.MyGridLine;
import Library.SaveDiagramXML;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class UseCaseCanvaBox2 extends CanvasPane {
	
	//All In One
	
	// Actor
	private ArrayList<UC_Actor> actors;
	private UC_Actor actor;
	private boolean isActor;

	// Action
	private ArrayList<UC_ActionLine> actionLines;
	private UC_ActionLine actionLine;
	private boolean isActionLine;

	// Box
	private ArrayList<UC_Box> boxs;
	private UC_Box box;
	private boolean isBox;

	// Process
	private ArrayList<UC_Process> processCycles;
	private UC_Process pC;
	private boolean isProcessCycle;

	// ExtendLine
	private ArrayList<UC_ExtendLine> extendLines;
	private UC_ExtendLine extendLine;
	private boolean isExtendLine;

	// IncludeLine
	private ArrayList<UC_IncludeLine> includeLines;
	private UC_IncludeLine includeLine;
	private boolean isIncludeLine;

	// TypeOf
	private ArrayList<UC_TypeOfLine> typeofLines;
	private UC_TypeOfLine typeofLine;
	private boolean isTypeofLine;

	public UseCaseCanvaBox2(Scene owner, File path, boolean isLoad) {
		this.isLoad = isLoad;
		setOwner(owner);
		setPath(path);
		if (toolHandler.getGrid().equals("Show")) {
			setGridLines();
		}
		actors = new ArrayList<UC_Actor>();
		actionLines = new ArrayList<UC_ActionLine>();
		boxs = new ArrayList<UC_Box>();
		processCycles = new ArrayList<UC_Process>();
		extendLines = new ArrayList<UC_ExtendLine>();
		includeLines = new ArrayList<UC_IncludeLine>();
		typeofLines = new ArrayList<UC_TypeOfLine>();

		setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent key) {
				isNewOrEdit(key);
				if (isNew) {
					toolHandler = new ToolHandler();
					color = Color.web(toolHandler.getColor());
					switch (toolHandler.getTool()) {
					case "UseCase_Actor":
						actor = new UC_Actor(key.getX(), key.getY(), 20, color, Color.GRAY);
						isActor = true;
						getChildren().addAll(actor, actor.getBody(), actor.getLeg(), actor.getLeg2(), actor.getLeg3(),
								actor.getLeg4(), actor.getLabel(), actor.getText(false));
						break;
					case "UseCase_Action":
						actionLine = new UC_ActionLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isActionLine = true;
						getChildren().addAll(actionLine);

						break;
					case "UseCase_Box":
						box = new UC_Box(key.getX(), key.getY(), 300, 400, color, Color.GRAY);
						isBox = true;
						getChildren().addAll(box, box.getLabel());
						break;
					case "UseCase_Process":
						pC = new UC_Process(key.getX(), key.getY(), 60, 30, color, Color.BLACK);
						isProcessCycle = true;
						getChildren().addAll(pC, pC.getLabel(), pC.getText(false));
						break;
					case "UseCase_Extend":
						extendLine = new UC_ExtendLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isExtendLine = true;
						getChildren().addAll(extendLine);
						break;
					case "UseCase_Include":
						includeLine = new UC_IncludeLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isIncludeLine = true;
						getChildren().add(includeLine);
						break;
					case "UseCase_Type":
						typeofLine = new UC_TypeOfLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isTypeofLine = true;
						getChildren().add(typeofLine);
						break;
					default:
						break;
					}
					requestFocus();
					toolHandler.setTool("");
				}
			}
		});
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (isActor) {
					actor.setCenterX(e.getX());
					actor.setCenterY(e.getY());
				} else if (isActionLine) {
					actionLine.setEndX(e.getX());
					actionLine.setEndY(e.getY());
				} else if (isBox) {
					box.setX(e.getX() - 100);
					box.setY(e.getY() - 100);
				} else if (isProcessCycle) {
					pC.setCenterX(e.getX());
					pC.setCenterY(e.getY());
				} else if (isExtendLine) {
					extendLine.setEndX(e.getX());
					extendLine.setEndY(e.getY());
				} else if (isIncludeLine) {
					includeLine.setEndX(e.getX());
					includeLine.setEndY(e.getY());
				} else if (isTypeofLine) {
					typeofLine.setEndX(e.getX());
					typeofLine.setEndY(e.getY());
				}
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (isActor) {
					actors.add(actor);
					isActor = false;
				} else if (isActionLine) {
					actionLines.add(actionLine);
					isActionLine = false;
				} else if (isBox) {
					getChildren().add(box.getText(false));
					boxs.add(box);
					isBox = false;
					requestFocus();
				} else if (isProcessCycle) {
					processCycles.add(pC);
					isProcessCycle = false;
				} else if (isExtendLine) {
					extendLine.recalculatePoint();
					getChildren().addAll(extendLine.getLabel(true), extendLine.getTop(), extendLine.getBot());
					extendLines.add(extendLine);
					isExtendLine = false;
				} else if (isIncludeLine) {
					includeLine.recalculatePoint();
					getChildren().addAll(includeLine.getLabel(true), includeLine.getTop(), includeLine.getBot());
					includeLines.add(includeLine);
					isIncludeLine = false;
				} else if (isTypeofLine) {
					typeofLine.calculateTri();
					getChildren().add(typeofLine.getTri());
					typeofLines.add(typeofLine);
					isTypeofLine = false;
				}
				requestFocus();
			}
		});

		saveB.setOnAction(e -> {
			if (save == null) {
				save = new SaveDiagramXML(path);
			}
			save.saveUseCaseCanvaBox(actors, actionLines, boxs, processCycles, extendLines, includeLines, typeofLines);
		});
		printB.setOnAction(e -> {
			getChildren().removeAll(gridLine, menu);
			new Library.PrintNode(this);
			getChildren().add(gridLine);
			gridLine.toBack();
		});
	}

	public void init() {
		if (isLoad) {
			System.out.println(" Load Actor data From XML");
			loadXMLData("Actors");
			loadXMLData("Actions");
			loadXMLData("Boxs");
			loadXMLData("Processes");
			loadXMLData("Extends");
			loadXMLData("Includes");
			loadXMLData("Types");

		}
	}

	public void isNewOrEdit(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditAction(e, point);
		isNewOREditTypeOf(e, point);
		isNewOREditInclude(e, point);
		isNewOREditExtend(e, point);
	}

	public void isNewOREditAction(MouseEvent e, Point2D point) {
		for (int i = 0; i < actionLines.size(); i++) {
			int index = i;
			boolean isclick = actionLines.get(i).contains(point.getX(), point.getY())
					|| actionLines.get(i).contains(point.getX() - 1, point.getY())
					|| actionLines.get(i).contains(point.getX() - 2, point.getY())
					|| actionLines.get(i).contains(point.getX() - 3, point.getY())
					|| actionLines.get(i).contains(point.getX() - 4, point.getY())
					|| actionLines.get(i).contains(point.getX() - 5, point.getY())
					|| actionLines.get(i).contains(point.getX() + 1, point.getY())
					|| actionLines.get(i).contains(point.getX() + 2, point.getY())
					|| actionLines.get(i).contains(point.getX() + 3, point.getY())
					|| actionLines.get(i).contains(point.getX() + 4, point.getY())
					|| actionLines.get(i).contains(point.getX() + 5, point.getY())
					|| actionLines.get(i).contains(point.getX(), point.getY() - 1)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 2)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 3)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 4)
					|| actionLines.get(i).contains(point.getX(), point.getY() - 5)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 1)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 2)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 3)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 4)
					|| actionLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				actionLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				actionLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (actionLines.size() > 0) {
							getChildren().removeAll(actionLines.get(index));
							actionLines.remove(index);
						} else {
							System.out.println("No ActionLine to delete");
						}
					}
				});
				actionLines.get(i).setEffect(shape);
			} else {
				actionLines.get(i).setEffect(null);
			}

		}
	}

	public void isNewOREditTypeOf(MouseEvent e, Point2D point) {
		for (int i = 0; i < typeofLines.size(); i++) {

			int index = i;
			boolean isclick = typeofLines.get(i).contains(point.getX(), point.getY())
					|| typeofLines.get(i).contains(point.getX() - 1, point.getY())
					|| typeofLines.get(i).contains(point.getX() - 2, point.getY())
					|| typeofLines.get(i).contains(point.getX() - 3, point.getY())
					|| typeofLines.get(i).contains(point.getX() - 4, point.getY())
					|| typeofLines.get(i).contains(point.getX() - 5, point.getY())
					|| typeofLines.get(i).contains(point.getX() + 1, point.getY())
					|| typeofLines.get(i).contains(point.getX() + 2, point.getY())
					|| typeofLines.get(i).contains(point.getX() + 3, point.getY())
					|| typeofLines.get(i).contains(point.getX() + 4, point.getY())
					|| typeofLines.get(i).contains(point.getX() + 5, point.getY())
					|| typeofLines.get(i).contains(point.getX(), point.getY() - 1)
					|| typeofLines.get(i).contains(point.getX(), point.getY() - 2)
					|| typeofLines.get(i).contains(point.getX(), point.getY() - 3)
					|| typeofLines.get(i).contains(point.getX(), point.getY() - 4)
					|| typeofLines.get(i).contains(point.getX(), point.getY() - 5)
					|| typeofLines.get(i).contains(point.getX(), point.getY() + 1)
					|| typeofLines.get(i).contains(point.getX(), point.getY() + 2)
					|| typeofLines.get(i).contains(point.getX(), point.getY() + 3)
					|| typeofLines.get(i).contains(point.getX(), point.getY() + 4)
					|| typeofLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;
				// Delete
				typeofLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				typeofLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (typeofLines.size() > 0) {
							getChildren().removeAll(typeofLines.get(index), typeofLines.get(index).getTri());
							typeofLines.remove(index);
						} else {
							System.out.println("No TypeOfLine to delete");
						}
					}
				});
				typeofLines.get(i).setEffect(shape);
				typeofLines.get(i).getTri().setEffect(shape);
				// getChildren().addAll(x, y);
			} else {
				typeofLines.get(i).setEffect(null);
				typeofLines.get(i).getTri().setEffect(null);
			}

		}
	}

	public void isNewOREditInclude(MouseEvent e, Point2D point) {
		for (int i = 0; i < includeLines.size(); i++) {

			int index = i;
			boolean isclick = includeLines.get(i).contains(point.getX(), point.getY())
					|| includeLines.get(i).contains(point.getX() - 1, point.getY())
					|| includeLines.get(i).contains(point.getX() - 2, point.getY())
					|| includeLines.get(i).contains(point.getX() - 3, point.getY())
					|| includeLines.get(i).contains(point.getX() - 4, point.getY())
					|| includeLines.get(i).contains(point.getX() - 5, point.getY())
					|| includeLines.get(i).contains(point.getX() + 1, point.getY())
					|| includeLines.get(i).contains(point.getX() + 2, point.getY())
					|| includeLines.get(i).contains(point.getX() + 3, point.getY())
					|| includeLines.get(i).contains(point.getX() + 4, point.getY())
					|| includeLines.get(i).contains(point.getX() + 5, point.getY())
					|| includeLines.get(i).contains(point.getX(), point.getY() - 1)
					|| includeLines.get(i).contains(point.getX(), point.getY() - 2)
					|| includeLines.get(i).contains(point.getX(), point.getY() - 3)
					|| includeLines.get(i).contains(point.getX(), point.getY() - 4)
					|| includeLines.get(i).contains(point.getX(), point.getY() - 5)
					|| includeLines.get(i).contains(point.getX(), point.getY() + 1)
					|| includeLines.get(i).contains(point.getX(), point.getY() + 2)
					|| includeLines.get(i).contains(point.getX(), point.getY() + 3)
					|| includeLines.get(i).contains(point.getX(), point.getY() + 4)
					|| includeLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				includeLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				includeLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (includeLines.size() > 0) {
							getChildren().removeAll(includeLines.get(index), includeLines.get(index).getTop(),
									includeLines.get(index).getBot(), includeLines.get(index).getLabel(false));
							includeLines.remove(index);
						} else {
							System.out.println("No Include Line to delete");
						}
					}
				});
				includeLines.get(i).setEffect(shape);
				includeLines.get(i).getTop().setEffect(shape);
				includeLines.get(i).getBot().setEffect(shape);
				// getChildren().addAll(x, y);
			} else {
				includeLines.get(i).setEffect(null);
				includeLines.get(i).getTop().setEffect(null);
				includeLines.get(i).getBot().setEffect(null);
			}

		}
	}

	public void isNewOREditExtend(MouseEvent e, Point2D point) {

		for (int i = 0; i < extendLines.size(); i++) {

			int index = i;
			boolean isclick = extendLines.get(i).contains(point.getX(), point.getY())
					|| extendLines.get(i).contains(point.getX() - 1, point.getY())
					|| extendLines.get(i).contains(point.getX() - 2, point.getY())
					|| extendLines.get(i).contains(point.getX() - 3, point.getY())
					|| extendLines.get(i).contains(point.getX() - 4, point.getY())
					|| extendLines.get(i).contains(point.getX() - 5, point.getY())
					|| extendLines.get(i).contains(point.getX() + 1, point.getY())
					|| extendLines.get(i).contains(point.getX() + 2, point.getY())
					|| extendLines.get(i).contains(point.getX() + 3, point.getY())
					|| extendLines.get(i).contains(point.getX() + 4, point.getY())
					|| extendLines.get(i).contains(point.getX() + 5, point.getY())
					|| extendLines.get(i).contains(point.getX(), point.getY() - 1)
					|| extendLines.get(i).contains(point.getX(), point.getY() - 2)
					|| extendLines.get(i).contains(point.getX(), point.getY() - 3)
					|| extendLines.get(i).contains(point.getX(), point.getY() - 4)
					|| extendLines.get(i).contains(point.getX(), point.getY() - 5)
					|| extendLines.get(i).contains(point.getX(), point.getY() + 1)
					|| extendLines.get(i).contains(point.getX(), point.getY() + 2)
					|| extendLines.get(i).contains(point.getX(), point.getY() + 3)
					|| extendLines.get(i).contains(point.getX(), point.getY() + 4)
					|| extendLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				extendLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				extendLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (extendLines.size() > 0) {
							getChildren().removeAll(extendLines.get(index), extendLines.get(index).getTop(),
									extendLines.get(index).getBot(), extendLines.get(index).getLabel(false));
							extendLines.remove(index);
						} else {
							System.out.println("No Extend Line to delete");
						}
					}
				});
				extendLines.get(i).setEffect(shape);
				extendLines.get(i).getTop().setEffect(shape);
				extendLines.get(i).getBot().setEffect(shape);
				// getChildren().addAll(x, y);
			} else {
				extendLines.get(i).setEffect(null);
				extendLines.get(i).getTop().setEffect(null);
				extendLines.get(i).getBot().setEffect(null);
			}

		}

	}

	public void setGridLines() {
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

	private void loadXMLData(String tagName) {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(path);

			switch (tagName) {
			case "Actors":
				org.w3c.dom.Node node = doc.getElementsByTagName("Actors").item(0);
				NodeList nodes = node.getChildNodes();
				for (int i = 0; i < nodes.getLength(); i++) {
					org.w3c.dom.Node data = nodes.item(i);
					NodeList datas = data.getChildNodes();
					double x = 0, y = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x".equals(element.getNodeName())) {
							System.out.println(" Center X Value : " + element.getTextContent());
							x = Double.parseDouble(element.getTextContent());
						}
						if ("y".equals(element.getNodeName())) {
							System.out.println(" Center Y Value : " + element.getTextContent());
							y = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							System.out.println(" color : " + element.getTextContent());
							color = Color.web(element.getTextContent());
						}

					}
					UC_Actor actor = new UC_Actor(x, y, 20, color, Color.LIGHTGRAY);
					actors.add(actor);
					getChildren().addAll(actor, actor.getBody(), actor.getLeg(), actor.getLeg2(), actor.getLeg3(),
							actor.getLeg4(), actor.getLabel());
				}

				break;
			case "Actions":
				org.w3c.dom.Node action = doc.getElementsByTagName("Actions").item(0);
				NodeList actions = action.getChildNodes();
				for (int i = 0; i < actions.getLength(); i++) {
					org.w3c.dom.Node data = actions.item(i);
					NodeList datas = data.getChildNodes();
					double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x1".equals(element.getNodeName())) {
							x1 = Double.parseDouble(element.getTextContent());
						}
						if ("y1".equals(element.getNodeName())) {
							y1 = Double.parseDouble(element.getTextContent());
						}
						if ("x2".equals(element.getNodeName())) {
							x2 = Double.parseDouble(element.getTextContent());
						}
						if ("y2".equals(element.getNodeName())) {
							y2 = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}
					}
					UC_ActionLine actionLine = new UC_ActionLine(x1, y1, x2, y2, color);
					actionLines.add(actionLine);
					getChildren().addAll(actionLine);
				}
				break;

			case "Boxs":
				org.w3c.dom.Node ubox = doc.getElementsByTagName("Boxs").item(0);
				NodeList uboxs = ubox.getChildNodes();
				for (int i = 0; i < uboxs.getLength(); i++) {
					org.w3c.dom.Node data = uboxs.item(i);
					NodeList datas = data.getChildNodes();
					double x = 0, y = 0, h = 0, w = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x".equals(element.getNodeName())) {
							x = Double.parseDouble(element.getTextContent());
						}
						if ("y".equals(element.getNodeName())) {
							y = Double.parseDouble(element.getTextContent());
						}
						if ("width".equals(element.getNodeName())) {
							w = Double.parseDouble(element.getTextContent());
						}
						if ("height".equals(element.getNodeName())) {
							h = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					UC_Box uboxd = new UC_Box(x, y, w, h, color, Color.LIGHTGRAY);
					boxs.add(uboxd);
					getChildren().addAll(uboxd, uboxd.getLabel());
				}
				break;

			case "Processes":
				org.w3c.dom.Node uprocess = doc.getElementsByTagName("Processes").item(0);
				NodeList uprocesses = uprocess.getChildNodes();
				for (int i = 0; i < uprocesses.getLength(); i++) {
					org.w3c.dom.Node data = uprocesses.item(i);
					NodeList datas = data.getChildNodes();
					double x = 0, y = 0, h = 0, w = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x".equals(element.getNodeName())) {
							x = Double.parseDouble(element.getTextContent());
						}
						if ("y".equals(element.getNodeName())) {
							y = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					UC_Process up = new UC_Process(x, y, 60, 30, color, Color.LIGHTGRAY);
					processCycles.add(up);
					getChildren().addAll(up, up.getLabel());
				}
				break;

			case "Extends":
				org.w3c.dom.Node ext = doc.getElementsByTagName("Extends").item(0);
				NodeList exts = ext.getChildNodes();
				for (int i = 0; i < exts.getLength(); i++) {
					org.w3c.dom.Node data = exts.item(i);
					NodeList datas = data.getChildNodes();
					double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x1".equals(element.getNodeName())) {
							x1 = Double.parseDouble(element.getTextContent());
						}
						if ("y1".equals(element.getNodeName())) {
							y1 = Double.parseDouble(element.getTextContent());
						}
						if ("x2".equals(element.getNodeName())) {
							x2 = Double.parseDouble(element.getTextContent());
						}
						if ("y2".equals(element.getNodeName())) {
							y2 = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					UC_ExtendLine up = new UC_ExtendLine(x1, y1, x2, y2, color);
					extendLines.add(up);
					up.recalculatePoint();
					getChildren().addAll(up, up.getLabel(true), up.getTop(), up.getBot());
				}
				break;

			case "Includes":
				org.w3c.dom.Node inc = doc.getElementsByTagName("Includes").item(0);
				NodeList incs = inc.getChildNodes();
				for (int i = 0; i < incs.getLength(); i++) {
					org.w3c.dom.Node data = incs.item(i);
					NodeList datas = data.getChildNodes();
					double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x1".equals(element.getNodeName())) {
							x1 = Double.parseDouble(element.getTextContent());
						}
						if ("y1".equals(element.getNodeName())) {
							y1 = Double.parseDouble(element.getTextContent());
						}
						if ("x2".equals(element.getNodeName())) {
							x2 = Double.parseDouble(element.getTextContent());
						}
						if ("y2".equals(element.getNodeName())) {
							y2 = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					UC_IncludeLine up = new UC_IncludeLine(x1, y1, x2, y2, color);
					includeLines.add(up);
					up.recalculatePoint();
					getChildren().addAll(up, up.getLabel(true), up.getTop(), up.getBot());
				}
				break;

			case "Types":
				org.w3c.dom.Node typ = doc.getElementsByTagName("Types").item(0);
				NodeList typs = typ.getChildNodes();
				for (int i = 0; i < typs.getLength(); i++) {
					org.w3c.dom.Node data = typs.item(i);
					NodeList datas = data.getChildNodes();
					double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
					Color color = null;
					for (int k = 0; k < datas.getLength(); k++) {
						org.w3c.dom.Node element = datas.item(k);
						if ("x1".equals(element.getNodeName())) {
							x1 = Double.parseDouble(element.getTextContent());
						}
						if ("y1".equals(element.getNodeName())) {
							y1 = Double.parseDouble(element.getTextContent());
						}
						if ("x2".equals(element.getNodeName())) {
							x2 = Double.parseDouble(element.getTextContent());
						}
						if ("y2".equals(element.getNodeName())) {
							y2 = Double.parseDouble(element.getTextContent());
						}
						if ("color".equals(element.getNodeName())) {
							color = Color.web(element.getTextContent());
						}

					}
					UC_TypeOfLine up = new UC_TypeOfLine(x1, y1, x2, y2, color);
					typeofLines.add(up);
					up.calculateTri();
					getChildren().addAll(up, up.getTri());
				}
				break;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public Scene getOwner() {
		return owner;
	}

}
