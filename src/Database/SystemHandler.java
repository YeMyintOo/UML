package Database;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SystemHandler {
	protected DocumentBuilderFactory dbFactory;
	protected DocumentBuilder dBuilder;
	protected Document doc;
	protected NodeList nList;

	public SystemHandler() {
		File xml = new File("System.xml");
		try {
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Set Default WorkSpace
	public void setDefaultWorkspace(String workspace) {
		try {
			Node req = doc.getElementsByTagName("Default-workspace").item(0);
			req.setTextContent(workspace);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File("System.xml"));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Set Default Project
	public void setDefaultProject(String project) {
		try {
			Node req = doc.getElementsByTagName("Default-project").item(0);
			req.setTextContent(project);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(new File("System.xml"));
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Get Default Workspace
	public String getDefaultWorkspace() {
		String ws = null;
		nList = doc.getElementsByTagName("Default-workspace");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				ws = nNode.getTextContent();
			}
		}
		return ws;
	}

	// Get Default Project
	public String getDefaultProject() {
		String pro = null;
		nList = doc.getElementsByTagName("Default-project");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				pro = nNode.getTextContent();
			}
		}
		return pro;
	}

}
