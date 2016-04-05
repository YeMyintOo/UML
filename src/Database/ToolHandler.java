package Database;

import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

public class ToolHandler {
	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	protected Document doc;
	protected NodeList nList;
	public String tool;
	public String color;
	public String grid;

	public ToolHandler() {
		File xml = new File("ToolHandler.xml");
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Tool
	public String getTool() {
		nList = doc.getElementsByTagName("tool");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				color = nNode.getTextContent();
			}
		}
		return color;
	}

	public void setTool(String tool) {
		try {
			Node req = doc.getElementsByTagName("tool").item(0);
			req.setTextContent(tool);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File("ToolHandler.xml"));
			transformer.transform(domSource, streamResult);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//////////////////////////////////

	// Color
	public String getColor() {
		nList = doc.getElementsByTagName("color");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				tool = nNode.getTextContent();
			}
		}
		return tool;
	}

	public void setColor(String color) {
		try {
			Node req = doc.getElementsByTagName("color").item(0);
			req.setTextContent(color);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File("ToolHandler.xml"));
			transformer.transform(domSource, streamResult);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	///////////////////////////////////////////////////

	// Grid
	public String getGrid() {
		nList = doc.getElementsByTagName("grid");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				grid = nNode.getTextContent();
			}
		}
		return grid;
	}

	public void setGrid(String grid) {
		try {
			Node req = doc.getElementsByTagName("grid").item(0);
			req.setTextContent(grid);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File("ToolHandler.xml"));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
