package SaveMe;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;

import Canvas.O_Link;
import Canvas.O_Object;

public class SaveObject {
	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	protected Document doc;
	protected File path;
	protected XPath read;

	public SaveObject(File path) {
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

	public void setDatas(ArrayList<O_Object> objects, ArrayList<O_Link> links) {
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
			Element label=doc.createElement("label");
			Element var=doc.createElement("variables");

			x.appendChild(doc.createTextNode("" + objects.get(i).getX()));
			y.appendChild(doc.createTextNode("" + objects.get(i).getY()));
			width.appendChild(doc.createTextNode("" + objects.get(i).getWidth()));
			height.appendChild(doc.createTextNode("" + objects.get(i).getHeight()));
			color.appendChild(doc.createTextNode("" + objects.get(i).getFill()));
			label.appendChild(doc.createTextNode(""+objects.get(i).labelProperty().get()));
			
			//Data
			String varD = "";
			for(int d=0;d<objects.get(i).getDatas().size(); d++){
				if(varD.equals("")){
					varD=objects.get(i).getDatas().get(d).get();
				}else{
					varD=varD+"@@@"+objects.get(i).getDatas().get(d).get();
				}
				
			}
			var.appendChild(doc.createTextNode(varD));

			data.appendChild(x);
			data.appendChild(y);
			data.appendChild(width);
			data.appendChild(height);
			data.appendChild(color);
			data.appendChild(label);
			data.appendChild(var);
			objNode.appendChild(data);
		}

		// Link
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
