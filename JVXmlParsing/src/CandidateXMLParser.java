import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CandidateXMLParser {

	static int count = 0;
	static DocumentBuilder db = null;

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	static void parseCandidate(Node node) throws DOMException, SAXException,
			IOException, ParserConfigurationException {
		NodeList candidateElements = (NodeList) node.getChildNodes();

		for (int i = 0; i < candidateElements.getLength(); i++) {

			Node nodeI = candidateElements.item(i);

			switch (nodeI.getNodeType()) {

			case Node.ELEMENT_NODE:
				//System.out.println("has child: " + nodeI.hasChildNodes());
				if (nodeI.getChildNodes().getLength()>=1) {
					NodeList childList = nodeI.getChildNodes();
					Node tempNode = null;
					// if (nodeI.getNodeType() == Node.ELEMENT_NODE)
					System.out.println(nodeI.getNodeName());

					for (int j = 0; j < childList.getLength(); j++) {
						// if (nodeI.getNodeType() == Node.ELEMENT_NODE)
						tempNode = childList.item(j);
						parseCandidate(tempNode); // recursive call for internal
													// child nodes
					}
				} else {
					//System.out.println("inside else has child: "
						//	+ nodeI.hasChildNodes());
					// if (nodeI.getNodeType() == Node.ELEMENT_NODE)
					System.out.println(nodeI.getNodeName() + " : "
							+ getCharacterDataFromElement((Element) nodeI));
				}

			}

		}

	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();
		File file = new File("C:/Work/CandRecordTest.xml");

		if (file.exists()) {
			Document doc = db.parse(file);
			Element docEle = doc.getDocumentElement();

			NodeList candidateList = docEle.getElementsByTagName("Candidate");

			if (candidateList.item(0).getNodeType() == Node.ELEMENT_NODE)

				parseCandidate(candidateList.item(0));
			System.out
					.println("================================================================================");
			// }

		} else
			System.out.println("File not Found");
	}
}
