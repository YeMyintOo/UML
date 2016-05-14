package Library;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Canvas.O_Link;
import Canvas.O_Object;
import Canvas.UC_ActionLine;
import Canvas.UC_Actor;
import Canvas.UC_Box;
import Canvas.UC_ExtendLine;
import Canvas.UC_IncludeLine;
import Canvas.UC_ProcessCycle;
import Canvas.UC_TypeOfLine;

public class SaveDiagramXML {

	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	protected Document doc;
	protected File path;
	protected XPath read;

	public SaveDiagramXML(File path) {
		this.path = path;
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(path);
			read = XPathFactory.newInstance().newXPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveUseCaseCanvaBox(ArrayList<UC_Actor> actors, ArrayList<UC_ActionLine> actions,
			ArrayList<UC_Box> boxs, ArrayList<UC_ProcessCycle> processCycles, ArrayList<UC_ExtendLine> extendLines,
			ArrayList<UC_IncludeLine> includeLines, ArrayList<UC_TypeOfLine> typeofLines) {

		// Actor
		Node actorNode = doc.getElementsByTagName("Actors").item(0);
		removeChilds(actorNode);
		for (int i = 0; i < actors.size(); i++) {
			Element data = doc.createElement("Actor");
			Element x = doc.createElement("x");
			Element y = doc.createElement("y");
			Element color = doc.createElement("color");

			x.appendChild(doc.createTextNode("" + actors.get(i).getCenterX()));
			y.appendChild(doc.createTextNode("" + actors.get(i).getCenterY()));
			color.appendChild(doc.createTextNode("" + actors.get(i).getFill()));

			data.appendChild(x);
			data.appendChild(y);
			data.appendChild(color);
			actorNode.appendChild(data);
		}

		// Action
		Node actionNode = doc.getElementsByTagName("Actions").item(0);
		removeChilds(actionNode);
		for (int i = 0; i < actions.size(); i++) {
			Element data = doc.createElement("Action");
			Element x1 = doc.createElement("x1");
			Element y1 = doc.createElement("y1");
			Element x2 = doc.createElement("x2");
			Element y2 = doc.createElement("y2");
			Element color = doc.createElement("color");

			x1.appendChild(doc.createTextNode("" + actions.get(i).getStartX()));
			y1.appendChild(doc.createTextNode("" + actions.get(i).getStartY()));
			x2.appendChild(doc.createTextNode("" + actions.get(i).getEndX()));
			y2.appendChild(doc.createTextNode("" + actions.get(i).getEndY()));
			color.appendChild(doc.createTextNode("" + actions.get(i).getStroke()));

			data.appendChild(x1);
			data.appendChild(y1);
			data.appendChild(x2);
			data.appendChild(y2);
			data.appendChild(color);
			actionNode.appendChild(data);
		}

		// Box
		Node boxNode = doc.getElementsByTagName("Boxs").item(0);
		removeChilds(boxNode);
		for (int i = 0; i < boxs.size(); i++) {
			Element data = doc.createElement("Box");
			Element x = doc.createElement("x");
			Element y = doc.createElement("y");
			Element width = doc.createElement("width");
			Element height = doc.createElement("height");
			Element color = doc.createElement("color");

			x.appendChild(doc.createTextNode("" + boxs.get(i).getX()));
			y.appendChild(doc.createTextNode("" + boxs.get(i).getY()));
			width.appendChild(doc.createTextNode("" + boxs.get(i).getWidth()));
			height.appendChild(doc.createTextNode("" + boxs.get(i).getHeight()));
			color.appendChild(doc.createTextNode("" + boxs.get(i).getFill()));

			data.appendChild(x);
			data.appendChild(y);
			data.appendChild(width);
			data.appendChild(height);
			data.appendChild(color);
			boxNode.appendChild(data);
		}

		// ProcessCycle
		Node processNode = doc.getElementsByTagName("Processes").item(0);
		removeChilds(processNode);
		for (int i = 0; i < processCycles.size(); i++) {
			Element data = doc.createElement("Process");
			Element x = doc.createElement("x");
			Element y = doc.createElement("y");
			Element color = doc.createElement("color");
			x.appendChild(doc.createTextNode("" + processCycles.get(i).getCenterX()));
			y.appendChild(doc.createTextNode("" + processCycles.get(i).getCenterY()));
			color.appendChild(doc.createTextNode("" + processCycles.get(i).getFill()));
			data.appendChild(x);
			data.appendChild(y);
			data.appendChild(color);
			processNode.appendChild(data);
		}
		
		//ExtendLines
		Node extNode = doc.getElementsByTagName("Extends").item(0);
		removeChilds(extNode);
		for (int i = 0; i < extendLines.size(); i++) {
			Element data = doc.createElement("Extend");
			Element x1 = doc.createElement("x1");
			Element y1 = doc.createElement("y1");
			Element x2 = doc.createElement("x2");
			Element y2 = doc.createElement("y2");
			Element color = doc.createElement("color");

			x1.appendChild(doc.createTextNode("" + extendLines.get(i).getStartX()));
			y1.appendChild(doc.createTextNode("" + extendLines.get(i).getStartY()));
			x2.appendChild(doc.createTextNode("" + extendLines.get(i).getEndX()));
			y2.appendChild(doc.createTextNode("" + extendLines.get(i).getEndY()));
			color.appendChild(doc.createTextNode("" + extendLines.get(i).getStroke()));

			data.appendChild(x1);
			data.appendChild(y1);
			data.appendChild(x2);
			data.appendChild(y2);
			data.appendChild(color);
			extNode.appendChild(data);
		}
		
		//IncludeLines
		Node incNode = doc.getElementsByTagName("Includes").item(0);
		removeChilds(incNode);
		for (int i = 0; i < includeLines.size(); i++) {
			Element data = doc.createElement("Include");
			Element x1 = doc.createElement("x1");
			Element y1 = doc.createElement("y1");
			Element x2 = doc.createElement("x2");
			Element y2 = doc.createElement("y2");
			Element color = doc.createElement("color");

			x1.appendChild(doc.createTextNode("" + includeLines.get(i).getStartX()));
			y1.appendChild(doc.createTextNode("" + includeLines.get(i).getStartY()));
			x2.appendChild(doc.createTextNode("" + includeLines.get(i).getEndX()));
			y2.appendChild(doc.createTextNode("" + includeLines.get(i).getEndY()));
			color.appendChild(doc.createTextNode("" + includeLines.get(i).getStroke()));

			data.appendChild(x1);
			data.appendChild(y1);
			data.appendChild(x2);
			data.appendChild(y2);
			data.appendChild(color);
			incNode.appendChild(data);
		}
		
		//TypeofLines
		Node typNode = doc.getElementsByTagName("Types").item(0);
		removeChilds(typNode);
		for (int i = 0; i < typeofLines.size(); i++) {
			Element data = doc.createElement("Type");
			Element x1 = doc.createElement("x1");
			Element y1 = doc.createElement("y1");
			Element x2 = doc.createElement("x2");
			Element y2 = doc.createElement("y2");
			Element color = doc.createElement("color");

			x1.appendChild(doc.createTextNode("" + typeofLines.get(i).getStartX()));
			y1.appendChild(doc.createTextNode("" + typeofLines.get(i).getStartY()));
			x2.appendChild(doc.createTextNode("" + typeofLines.get(i).getEndX()));
			y2.appendChild(doc.createTextNode("" + typeofLines.get(i).getEndY()));
			color.appendChild(doc.createTextNode("" + typeofLines.get(i).getStroke()));

			data.appendChild(x1);
			data.appendChild(y1);
			data.appendChild(x2);
			data.appendChild(y2);
			data.appendChild(color);
			incNode.appendChild(data);
		}
		
		save();
	}
	
	public void saveObjectCanvaBox(ArrayList<O_Object> objects,ArrayList<O_Link> links){

		// Object
		Node objNode = doc.getElementsByTagName("Objects").item(0);
		removeChilds(objNode);
		for (int i = 0; i < objects.size(); i++) {
			Element data = doc.createElement("Object");
			Element x = doc.createElement("x");
			Element y = doc.createElement("y");
			Element width = doc.createElement("width");
			Element height = doc.createElement("height");
			Element color = doc.createElement("color");

			x.appendChild(doc.createTextNode("" + objects.get(i).getX()));
			y.appendChild(doc.createTextNode("" + objects.get(i).getY()));
			width.appendChild(doc.createTextNode("" + objects.get(i).getWidth()));
			height.appendChild(doc.createTextNode("" + objects.get(i).getHeight()));
			color.appendChild(doc.createTextNode("" + objects.get(i).getFill()));

			data.appendChild(x);
			data.appendChild(y);
			data.appendChild(width);
			data.appendChild(height);
			data.appendChild(color);
			objNode.appendChild(data);
		}
		
		//Link
		Node linkNode = doc.getElementsByTagName("Links").item(0);
		removeChilds(linkNode);
		for (int i = 0; i < links.size(); i++) {
			Element data = doc.createElement("Link");
			Element x1 = doc.createElement("x1");
			Element y1 = doc.createElement("y1");
			Element x2 = doc.createElement("x2");
			Element y2 = doc.createElement("y2");
			Element color = doc.createElement("color");

			x1.appendChild(doc.createTextNode("" + links.get(i).getStartX()));
			y1.appendChild(doc.createTextNode("" + links.get(i).getStartY()));
			x2.appendChild(doc.createTextNode("" + links.get(i).getEndX()));
			y2.appendChild(doc.createTextNode("" + links.get(i).getEndY()));
			color.appendChild(doc.createTextNode("" + links.get(i).getStroke()));

			data.appendChild(x1);
			data.appendChild(y1);
			data.appendChild(x2);
			data.appendChild(y2);
			data.appendChild(color);
			linkNode.appendChild(data);
		}
		save();
	}
	
	
	public void save() {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(path);
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeChilds(Node node) {
		while (node.hasChildNodes())
			node.removeChild(node.getFirstChild());
	}
}
