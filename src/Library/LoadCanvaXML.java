package Library;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoadCanvaXML {
	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	protected Document doc;
	protected NodeList nList;

	public LoadCanvaXML(File path) {
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDiagram() {
		String name = null;
		Element node = doc.getDocumentElement();
		name = node.getTagName();
		return name;
	}

	public String getName() {
		String name = null;
		nList = doc.getElementsByTagName("Name");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				name = nNode.getTextContent();
			}
		}
		return name;
	}

}
