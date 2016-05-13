package Library;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildCanvaXML {
	private Document document;

	public BuildCanvaXML(File path, String name, int type) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException parserException) {
			parserException.printStackTrace();
		}

		Element root = document.createElement("UseCase");

		Element nameNode = document.createElement("Name"); // Canvas Name
		nameNode.appendChild(document.createTextNode(name));

		Element dataNode = document.createElement("Data"); // Draw Data

		switch (type) {
		case 1: // UseCase
			Element actors = document.createElement("Actors");
			Element actions = document.createElement("Actions");
			Element box = document.createElement("Boxs");
			Element process = document.createElement("Processes");
			Element ext = document.createElement("Extends");
			Element inc = document.createElement("Includes");
			Element typ = document.createElement("Types");
			dataNode.appendChild(actors);
			dataNode.appendChild(actions);
			dataNode.appendChild(box);
			dataNode.appendChild(process);
			dataNode.appendChild(ext);
			dataNode.appendChild(inc);
			dataNode.appendChild(typ);
			break;
		}

		document.appendChild(root);
		root.appendChild(nameNode);
		root.appendChild(dataNode);

		try {
			Source xmlSource = new DOMSource(document);
			Result result = new StreamResult(new FileOutputStream(path));
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("indent", "yes");
			transformer.transform(xmlSource, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
