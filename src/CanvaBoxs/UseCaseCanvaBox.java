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
import Library.Types;
import SaveMe.SaveUseCase;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class UseCaseCanvaBox extends CanvasPane {
	private SaveUseCase saveMe;

	private ArrayList<UC_Actor> actors;
	private UC_Actor actor;
	private boolean isActor;
	private ArrayList<UC_ActionLine> aLines;
	private UC_ActionLine aLine;
	private boolean isaLine;
	private ArrayList<UC_Box> boxs;
	private UC_Box box;
	private boolean isBox;
	private ArrayList<UC_Process> processes;
	private UC_Process pC;
	private boolean isProcess;
	private ArrayList<UC_ExtendLine> eLines;
	private UC_ExtendLine eLine;
	private boolean iseLine;
	private ArrayList<UC_IncludeLine> iLines;
	private UC_IncludeLine iLine;
	private boolean isiLine;
	private ArrayList<UC_TypeOfLine> tLines;
	private UC_TypeOfLine tLine;
	private boolean istLine;

	public UseCaseCanvaBox(Scene owner, File path, boolean isLoad) {
		this.isLoad = isLoad;
		setOwner(owner);
		setPath(path);
		menu.getChildren().addAll(getLabel(Types.Usecase), saveB, printB);

		actors = new ArrayList<UC_Actor>();
		aLines = new ArrayList<UC_ActionLine>();
		boxs = new ArrayList<UC_Box>();
		processes = new ArrayList<UC_Process>();
		eLines = new ArrayList<UC_ExtendLine>();
		iLines = new ArrayList<UC_IncludeLine>();
		tLines = new ArrayList<UC_TypeOfLine>();
		if (isLoad) {
			loadXMLData();
		}
		if (toolHandler.getGrid().equals("Show")) {
			setGridLine();
		}

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
						aLine = new UC_ActionLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isaLine = true;
						getChildren().addAll(aLine);

						break;
					case "UseCase_Box":
						box = new UC_Box(key.getX(), key.getY(), 300, 400, color, Color.GRAY);
						isBox = true;
						getChildren().addAll(box, box.getLabel());
						break;
					case "UseCase_Process":
						pC = new UC_Process(key.getX(), key.getY(), 60, 30, color, Color.BLACK);
						isProcess = true;
						getChildren().addAll(pC, pC.getLabel(), pC.getText(false));
						break;
					case "UseCase_Extend":
						eLine = new UC_ExtendLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						iseLine = true;
						getChildren().addAll(eLine);
						break;
					case "UseCase_Include":
						iLine = new UC_IncludeLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						isiLine = true;
						getChildren().add(iLine);
						break;
					case "UseCase_Type":
						tLine = new UC_TypeOfLine(key.getX(), key.getY(), key.getX(), key.getY(), color);
						istLine = true;
						getChildren().add(tLine);
						break;
					default:
						break;
					}
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
				} else if (isaLine) {
					aLine.setEndX(e.getX());
					aLine.setEndY(e.getY());
				} else if (isBox) {
					box.setX(e.getX() - 100);
					box.setY(e.getY() - 100);
				} else if (isProcess) {
					pC.setCenterX(e.getX());
					pC.setCenterY(e.getY());
				} else if (iseLine) {
					eLine.setEndX(e.getX());
					eLine.setEndY(e.getY());
				} else if (isiLine) {
					iLine.setEndX(e.getX());
					iLine.setEndY(e.getY());
				} else if (istLine) {
					tLine.setEndX(e.getX());
					tLine.setEndY(e.getY());
				}
			}
		});
		setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (isActor) {
					actors.add(actor);
					isActor = false;
				} else if (isaLine) {
					aLines.add(aLine);
					isaLine = false;
				} else if (isBox) {
					getChildren().add(box.getText(false));
					boxs.add(box);
					isBox = false;
					requestFocus();
				} else if (isProcess) {
					processes.add(pC);
					isProcess = false;
				} else if (iseLine) {
					eLine.recalculatePoint();
					getChildren().addAll(eLine.getLabel(true), eLine.getTop(), eLine.getBot());
					eLines.add(eLine);
					iseLine = false;
				} else if (isiLine) {
					iLine.recalculatePoint();
					getChildren().addAll(iLine.getLabel(true), iLine.getTop(), iLine.getBot());
					iLines.add(iLine);
					isiLine = false;
				} else if (istLine) {
					tLine.calculateTri();
					getChildren().add(tLine.getTri());
					tLines.add(tLine);
					istLine = false;
				}
			}
		});

		saveB.setOnAction(e -> {
			if (saveMe == null) {
				saveMe = new SaveUseCase(path);
			}
			saveMe.setDatas(actors, aLines, boxs, processes, eLines, iLines, tLines);
			saveMe.save();
		});
		printB.setOnAction(e -> {
			getChildren().removeAll(gridLine, menu);
			new Library.PrintNodes(this);
			getChildren().add(gridLine);
			gridLine.toBack();
		});
	}

	public void isNewOrEdit(MouseEvent e) {
		isNew = true;
		Point2D point = new Point2D(e.getX(), e.getY());
		isNewOREditAction(e, point);
		isNewOREditTypeOf(e, point);
		isNewOREditInclude(e, point);
		isNewOREditExtend(e, point);
		DeleteNode();
	}

	public void isNewOREditAction(MouseEvent e, Point2D point) {
		for (int i = 0; i < aLines.size(); i++) {
			int index = i;
			boolean isclick = aLines.get(i).contains(point.getX(), point.getY())
					|| aLines.get(i).contains(point.getX() - 1, point.getY())
					|| aLines.get(i).contains(point.getX() - 2, point.getY())
					|| aLines.get(i).contains(point.getX() - 3, point.getY())
					|| aLines.get(i).contains(point.getX() - 4, point.getY())
					|| aLines.get(i).contains(point.getX() - 5, point.getY())
					|| aLines.get(i).contains(point.getX() + 1, point.getY())
					|| aLines.get(i).contains(point.getX() + 2, point.getY())
					|| aLines.get(i).contains(point.getX() + 3, point.getY())
					|| aLines.get(i).contains(point.getX() + 4, point.getY())
					|| aLines.get(i).contains(point.getX() + 5, point.getY())
					|| aLines.get(i).contains(point.getX(), point.getY() - 1)
					|| aLines.get(i).contains(point.getX(), point.getY() - 2)
					|| aLines.get(i).contains(point.getX(), point.getY() - 3)
					|| aLines.get(i).contains(point.getX(), point.getY() - 4)
					|| aLines.get(i).contains(point.getX(), point.getY() - 5)
					|| aLines.get(i).contains(point.getX(), point.getY() + 1)
					|| aLines.get(i).contains(point.getX(), point.getY() + 2)
					|| aLines.get(i).contains(point.getX(), point.getY() + 3)
					|| aLines.get(i).contains(point.getX(), point.getY() + 4)
					|| aLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				aLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				aLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (aLines.size() > 0) {
							getChildren().removeAll(aLines.get(index));
							aLines.remove(index);
						} else {
							System.out.println("No ActionLine to delete");
						}
					}
				});
				aLines.get(i).setEffect(shape);
			} else {
				aLines.get(i).setEffect(null);
			}

		}
	}

	public void isNewOREditTypeOf(MouseEvent e, Point2D point) {
		for (int i = 0; i < tLines.size(); i++) {

			int index = i;
			boolean isclick = tLines.get(i).contains(point.getX(), point.getY())
					|| tLines.get(i).contains(point.getX() - 1, point.getY())
					|| tLines.get(i).contains(point.getX() - 2, point.getY())
					|| tLines.get(i).contains(point.getX() - 3, point.getY())
					|| tLines.get(i).contains(point.getX() - 4, point.getY())
					|| tLines.get(i).contains(point.getX() - 5, point.getY())
					|| tLines.get(i).contains(point.getX() + 1, point.getY())
					|| tLines.get(i).contains(point.getX() + 2, point.getY())
					|| tLines.get(i).contains(point.getX() + 3, point.getY())
					|| tLines.get(i).contains(point.getX() + 4, point.getY())
					|| tLines.get(i).contains(point.getX() + 5, point.getY())
					|| tLines.get(i).contains(point.getX(), point.getY() - 1)
					|| tLines.get(i).contains(point.getX(), point.getY() - 2)
					|| tLines.get(i).contains(point.getX(), point.getY() - 3)
					|| tLines.get(i).contains(point.getX(), point.getY() - 4)
					|| tLines.get(i).contains(point.getX(), point.getY() - 5)
					|| tLines.get(i).contains(point.getX(), point.getY() + 1)
					|| tLines.get(i).contains(point.getX(), point.getY() + 2)
					|| tLines.get(i).contains(point.getX(), point.getY() + 3)
					|| tLines.get(i).contains(point.getX(), point.getY() + 4)
					|| tLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;
				// Delete
				tLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				tLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (tLines.size() > 0) {
							getChildren().removeAll(tLines.get(index), tLines.get(index).getTri());
							tLines.remove(index);
						} else {
							System.out.println("No TypeOfLine to delete");
						}
					}
				});
				tLines.get(i).setEffect(shape);
				tLines.get(i).getTri().setEffect(shape);
				// getChildren().addAll(x, y);
			} else {
				tLines.get(i).setEffect(null);
				tLines.get(i).getTri().setEffect(null);
			}

		}
	}

	public void isNewOREditInclude(MouseEvent e, Point2D point) {
		for (int i = 0; i < iLines.size(); i++) {

			int index = i;
			boolean isclick = iLines.get(i).contains(point.getX(), point.getY())
					|| iLines.get(i).contains(point.getX() - 1, point.getY())
					|| iLines.get(i).contains(point.getX() - 2, point.getY())
					|| iLines.get(i).contains(point.getX() - 3, point.getY())
					|| iLines.get(i).contains(point.getX() - 4, point.getY())
					|| iLines.get(i).contains(point.getX() - 5, point.getY())
					|| iLines.get(i).contains(point.getX() + 1, point.getY())
					|| iLines.get(i).contains(point.getX() + 2, point.getY())
					|| iLines.get(i).contains(point.getX() + 3, point.getY())
					|| iLines.get(i).contains(point.getX() + 4, point.getY())
					|| iLines.get(i).contains(point.getX() + 5, point.getY())
					|| iLines.get(i).contains(point.getX(), point.getY() - 1)
					|| iLines.get(i).contains(point.getX(), point.getY() - 2)
					|| iLines.get(i).contains(point.getX(), point.getY() - 3)
					|| iLines.get(i).contains(point.getX(), point.getY() - 4)
					|| iLines.get(i).contains(point.getX(), point.getY() - 5)
					|| iLines.get(i).contains(point.getX(), point.getY() + 1)
					|| iLines.get(i).contains(point.getX(), point.getY() + 2)
					|| iLines.get(i).contains(point.getX(), point.getY() + 3)
					|| iLines.get(i).contains(point.getX(), point.getY() + 4)
					|| iLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				iLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				iLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (iLines.size() > 0) {
							getChildren().removeAll(iLines.get(index), iLines.get(index).getTop(),
									iLines.get(index).getBot(), iLines.get(index).getLabel(false));
							iLines.remove(index);
						} else {
							System.out.println("No Include Line to delete");
						}
					}
				});
				iLines.get(i).setEffect(shape);
				iLines.get(i).getTop().setEffect(shape);
				iLines.get(i).getBot().setEffect(shape);
				// getChildren().addAll(x, y);
			} else {
				iLines.get(i).setEffect(null);
				iLines.get(i).getTop().setEffect(null);
				iLines.get(i).getBot().setEffect(null);
			}

		}
	}

	public void isNewOREditExtend(MouseEvent e, Point2D point) {

		for (int i = 0; i < eLines.size(); i++) {

			int index = i;
			boolean isclick = eLines.get(i).contains(point.getX(), point.getY())
					|| eLines.get(i).contains(point.getX() - 1, point.getY())
					|| eLines.get(i).contains(point.getX() - 2, point.getY())
					|| eLines.get(i).contains(point.getX() - 3, point.getY())
					|| eLines.get(i).contains(point.getX() - 4, point.getY())
					|| eLines.get(i).contains(point.getX() - 5, point.getY())
					|| eLines.get(i).contains(point.getX() + 1, point.getY())
					|| eLines.get(i).contains(point.getX() + 2, point.getY())
					|| eLines.get(i).contains(point.getX() + 3, point.getY())
					|| eLines.get(i).contains(point.getX() + 4, point.getY())
					|| eLines.get(i).contains(point.getX() + 5, point.getY())
					|| eLines.get(i).contains(point.getX(), point.getY() - 1)
					|| eLines.get(i).contains(point.getX(), point.getY() - 2)
					|| eLines.get(i).contains(point.getX(), point.getY() - 3)
					|| eLines.get(i).contains(point.getX(), point.getY() - 4)
					|| eLines.get(i).contains(point.getX(), point.getY() - 5)
					|| eLines.get(i).contains(point.getX(), point.getY() + 1)
					|| eLines.get(i).contains(point.getX(), point.getY() + 2)
					|| eLines.get(i).contains(point.getX(), point.getY() + 3)
					|| eLines.get(i).contains(point.getX(), point.getY() + 4)
					|| eLines.get(i).contains(point.getX(), point.getY() + 5);
			if (isclick) {
				isNew = false;

				// Delete
				eLines.get(i).onKeyPressedProperty().bindBidirectional(getOwner().onKeyPressedProperty());
				eLines.get(i).setOnKeyPressed(key -> {
					if (key.getCode() == KeyCode.DELETE) {
						if (eLines.size() > 0) {
							getChildren().removeAll(eLines.get(index), eLines.get(index).getTop(),
									eLines.get(index).getBot(), eLines.get(index).getLabel(false));
							eLines.remove(index);
						} else {
							System.out.println("No Extend Line to delete");
						}
					}
				});
				eLines.get(i).setEffect(shape);
				eLines.get(i).getTop().setEffect(shape);
				eLines.get(i).getBot().setEffect(shape);
				// getChildren().addAll(x, y);
			} else {
				eLines.get(i).setEffect(null);
				eLines.get(i).getTop().setEffect(null);
				eLines.get(i).getBot().setEffect(null);
			}

		}

	}

	private void loadXMLData() {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(path);

			org.w3c.dom.Node node = doc.getElementsByTagName("Actors").item(0);
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				org.w3c.dom.Node data = nodes.item(i);
				NodeList datas = data.getChildNodes();
				double x = 0, y = 0;
				Color color = null;
				String label = null;
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
					if ("label".equals(element.getNodeName())) {
						label = element.getTextContent();
					}

				}
				UC_Actor actor = new UC_Actor(x, y, 20, color, Color.LIGHTGRAY);
				actor.labelProperty().set(label);
				actors.add(actor);
				getChildren().addAll(actor, actor.getBody(), actor.getLeg(), actor.getLeg2(), actor.getLeg3(),
						actor.getLeg4(), actor.getLabel());
			}

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
				aLines.add(actionLine);
				getChildren().addAll(actionLine);
			}

			org.w3c.dom.Node ubox = doc.getElementsByTagName("Boxs").item(0);
			NodeList uboxs = ubox.getChildNodes();
			for (int i = 0; i < uboxs.getLength(); i++) {
				org.w3c.dom.Node data = uboxs.item(i);
				NodeList datas = data.getChildNodes();
				double x = 0, y = 0, h = 0, w = 0;
				Color color = null;
				String label = null;
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
					if ("label".equals(element.getNodeName())) {
						label = element.getTextContent();
					}

				}
				UC_Box uboxd = new UC_Box(x, y, w, h, color, Color.LIGHTGRAY);
				uboxd.labelProperty().set(label);
				boxs.add(uboxd);
				getChildren().addAll(uboxd, uboxd.getLabel());
			}

			org.w3c.dom.Node uprocess = doc.getElementsByTagName("Processes").item(0);
			NodeList uprocesses = uprocess.getChildNodes();
			for (int i = 0; i < uprocesses.getLength(); i++) {
				org.w3c.dom.Node data = uprocesses.item(i);
				NodeList datas = data.getChildNodes();
				double x = 0, y = 0, h = 0, w = 0;
				Color color = null;
				String label = null;
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
					if ("label".equals(element.getNodeName())) {
						label = element.getTextContent();
					}

				}
				UC_Process up = new UC_Process(x, y, 60, 30, color, Color.LIGHTGRAY);
				up.labelProperty().set(label);
				processes.add(up);
				getChildren().addAll(up, up.getLabel());
			}

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
				eLines.add(up);
				up.recalculatePoint();
				getChildren().addAll(up, up.getLabel(true), up.getTop(), up.getBot());
			}

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
				iLines.add(up);
				up.recalculatePoint();
				getChildren().addAll(up, up.getLabel(true), up.getTop(), up.getBot());
			}

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
				tLines.add(up);
				up.calculateTri();
				getChildren().addAll(up, up.getTri());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DeleteNode() {
		if (actors.size() > 0) {
			for (int i = 0; i < actors.size(); i++) {
				int index = i;
				if (actors.get(i).isPressed()) {
					actors.get(i).onKeyPressedProperty().bindBidirectional(owner.onKeyPressedProperty());
					actors.get(i).setOnKeyPressed(e -> {
						if (e.getCode() == KeyCode.DELETE) {
							getChildren().removeAll(actors.get(index), actors.get(index).getBody(),
									actors.get(index).getLeg(), actors.get(index).getLeg2(),
									actors.get(index).getLeg3(), actors.get(index).getLeg4(),
									actors.get(index).getLabel(), actors.get(index).getText(false));
							actors.remove(index);
						}
					});
					break;
				}
			}
		}

		if (boxs.size() > 0) {
			for (int i = 0; i < boxs.size(); i++) {
				int index = i;
				if (boxs.get(i).isPressed()) {
					boxs.get(i).onKeyPressedProperty().bindBidirectional(owner.onKeyPressedProperty());
					boxs.get(i).setOnKeyPressed(e -> {
						if (e.getCode() == KeyCode.DELETE) {
							getChildren().removeAll(boxs.get(index), boxs.get(index).getLabel());
							boxs.remove(index);

						}
					});
					break;
				}
			}
		}

		if (processes.size() > 0) {
			for (int i = 0; i < processes.size(); i++) {
				int index = i;
				if (processes.get(i).isPressed()) {
					processes.get(i).onKeyPressedProperty().bindBidirectional(owner.onKeyPressedProperty());
					processes.get(i).setOnKeyPressed(e -> {
						if (e.getCode() == KeyCode.DELETE) {
							getChildren().removeAll(processes.get(index), processes.get(index).getLabel(),
									processes.get(index).getText(false));
							processes.remove(index);
						}
					});
					break;
				}
			}
		}

	}
}
