import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParseXMLForCandidates {

	static void parseRecNodes(NodeList childList)
			throws ParserConfigurationException, SAXException, IOException {

		for (int i = 0; i < childList.getLength(); i++) {
			recNode(childList.item(i));
		}

	}

	static void parseEvaluation(String stringData)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder evalDB = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(stringData));

		Document doc = evalDB.parse(is);

	NodeList evalChilds=	doc.getChildNodes();
		parseRecNodes(evalChilds);

	}

	static void recNode(Node node) throws ParserConfigurationException,
			SAXException, IOException {
		if (node.getChildNodes().getLength() > 0 && node.getNodeType() == 1) {
			NodeList list = node.getChildNodes();
			if ("Evaluation" != node.getNodeName())
				System.out.println(node.getNodeName() + ":"
						+ getCharacterDataFromElement((Element) node));
			if ("Evaluation" == node.getNodeName()) {
				// System.out.println(getCharacterDataFromElement((Element) node));
				parseEvaluation(getCharacterDataFromElement((Element) node)
						.trim()); // string to XML Parsing

			}
			parseRecNodes(list);
		} else {
			if (node.getNodeType() == 1 && node.getLocalName() != null) {
				System.out.println(node.getLocalName() + ":"
						+ getCharacterDataFromElement((Element) node));
			}
		}
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
		DocumentBuilder Doc = DBF.newDocumentBuilder();

		File CandidateFile = new File("C:/Work/CandRecordTest.xml");

		if (CandidateFile.exists()) {
			Document doc = Doc.parse(CandidateFile);
			Element docEle = doc.getDocumentElement();

			NodeList candidateList = docEle.getElementsByTagName("Candidate");

			System.out.println(docEle.getNodeName());
			recNode(candidateList.item(0));

		}
	}

}
