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

public class BuildPropertiesXML {

	private Document document;
	
	public BuildPropertiesXML(File path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException parserException) {
			parserException.printStackTrace();
		}

		// Root Node
		Element root = document.createElement("Root");
		document.appendChild(root);

		// Element Node
		Element firstName = document.createElement("Properties");
		// Character Data
		firstName.appendChild(document.createTextNode("HelloWorld"));

		// Append
		root.appendChild(firstName);

		try {

			// create DOMSource for source XML document
			Source xmlSource = new DOMSource(document);

			// create StreamResult for transformation result
			Result result = new StreamResult(new FileOutputStream(path));

			// create TransformerFactory
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			// create Transformer for transformation
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty("indent", "yes");

			// transform and deliver content to client
			transformer.transform(xmlSource, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
