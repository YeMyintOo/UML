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

import Canvas.UC_Actor;

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

	public void addActorCanva(ArrayList<UC_Actor> actors) {
		try {
			Node root = doc.getElementsByTagName("Data").item(0);

			String check = "/UseCase/Data";
			String isCheck = read.compile(check).evaluate(doc);
			if (isCheck.equals("")) {
				Element list = doc.createElement("Actors");
				root.appendChild(list);
				for (int i = 0; i < actors.size(); i++) {
					Element data = doc.createElement("Actor");
					Element x = doc.createElement("x");
					Element y = doc.createElement("y");
					Element color = doc.createElement("color");

					x.appendChild(doc.createTextNode(""+actors.get(i).getCenterX()));
					y.appendChild(doc.createTextNode(""+actors.get(i).getCenterY()));
					color.appendChild(doc.createTextNode(""+actors.get(i).getFill()));
					
					data.appendChild(x);
					data.appendChild(y);
					data.appendChild(color);
					list.appendChild(data);
				}
			} else {
				Node node = doc.getElementsByTagName("Actors").item(0);
				root.appendChild(node);
				removeChilds(node);
				for (int i = 0; i < actors.size(); i++) {
					Element data = doc.createElement("Actor");
					Element x = doc.createElement("x");
					Element y = doc.createElement("y");
					Element color = doc.createElement("color");

					x.appendChild(doc.createTextNode(""+actors.get(i).getCenterX()));
					y.appendChild(doc.createTextNode(""+actors.get(i).getCenterY()));
					color.appendChild(doc.createTextNode(""+actors.get(i).getFill()));
					
					data.appendChild(x);
					data.appendChild(y);
					data.appendChild(color);					
					node.appendChild(data);
				}
			}

			save();
			System.out.println("Succss Save Actor");

		} catch (

		Exception e)

		{
			e.printStackTrace();
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
