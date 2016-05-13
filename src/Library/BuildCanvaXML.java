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
	private Element root;

	public BuildCanvaXML(File path, String name, int type) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException parserException) {
			parserException.printStackTrace();
		}

		root = document.createElement("Diagram");

		Element nameNode = document.createElement("Name"); // Canvas Name
		nameNode.appendChild(document.createTextNode(name));
		Element dataNode = document.createElement("Data");

		switch (type) {
		case 1: // UseCase
			root.setAttribute("type", "UseCase");
			dataNode.appendChild(document.createElement("Actors"));
			dataNode.appendChild(document.createElement("Actions"));
			dataNode.appendChild(document.createElement("Boxs"));
			dataNode.appendChild(document.createElement("Processes"));
			dataNode.appendChild(document.createElement("Extends"));
			dataNode.appendChild(document.createElement("Includes"));
			dataNode.appendChild(document.createElement("Types"));
			break;
		case 2:// Object
			root.setAttribute("type", "Object");
			dataNode.appendChild(document.createElement("Objects"));
			dataNode.appendChild(document.createElement("Links"));
			break;
		case 3:// Sequence
			root.setAttribute("type", "Sequence");
			dataNode.appendChild(document.createElement("Roles"));
			dataNode.appendChild(document.createElement("ANormals"));
			dataNode.appendChild(document.createElement("ANObjects"));
			dataNode.appendChild(document.createElement("ADObjects"));
			dataNode.appendChild(document.createElement("ASLoops"));
			break;
		case 4:// Collaboration
			root.setAttribute("type", "Collaboration");
			dataNode.appendChild(document.createElement("Objects"));
			dataNode.appendChild(document.createElement("Links"));
			break;
		case 5:// Class
			root.setAttribute("type", "Class");
			dataNode.appendChild(document.createElement("Class"));
			dataNode.appendChild(document.createElement("AbstractClass"));
			dataNode.appendChild(document.createElement("InterfaceClass"));
			dataNode.appendChild(document.createElement("Association"));
			dataNode.appendChild(document.createElement("Aggregation"));
			break;
		case 6:// State Chart
			root.setAttribute("type", "StateChart");
			dataNode.appendChild(document.createElement("InitStates"));
			dataNode.appendChild(document.createElement("FinalStates"));
			dataNode.appendChild(document.createElement("SubStates"));
			dataNode.appendChild(document.createElement("HistoryStates"));
			dataNode.appendChild(document.createElement("States"));
			dataNode.appendChild(document.createElement("Transitions"));
			break;
		case 7:// Activity
			root.setAttribute("type", "Activity");
			dataNode.appendChild(document.createElement("InitNodes"));
			dataNode.appendChild(document.createElement("EndNodes"));
			dataNode.appendChild(document.createElement("Actions"));
			dataNode.appendChild(document.createElement("Edges"));
			dataNode.appendChild(document.createElement("Merges"));
			dataNode.appendChild(document.createElement("Times"));
			dataNode.appendChild(document.createElement("Regions"));
			break;
		case 8:// Component
			root.setAttribute("type", "Component");
			dataNode.appendChild(document.createElement("Components"));
			dataNode.appendChild(document.createElement("Dependences"));
			dataNode.appendChild(document.createElement("Artefacts"));
			dataNode.appendChild(document.createElement("SComponents"));
			dataNode.appendChild(document.createElement("Packages"));
			dataNode.appendChild(document.createElement("Libraries"));
			break;
		case 9:// Deployment
			root.setAttribute("type", "Deployment");
			dataNode.appendChild(document.createElement("Hardwares"));
			dataNode.appendChild(document.createElement("Softwares"));
			dataNode.appendChild(document.createElement("Databases"));
			dataNode.appendChild(document.createElement("Protocols"));
			dataNode.appendChild(document.createElement("Files"));
			dataNode.appendChild(document.createElement("Components"));
			dataNode.appendChild(document.createElement("Systems"));
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
