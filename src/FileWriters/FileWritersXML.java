package FileWriters;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;

public class FileWritersXML extends FileWriters {

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder;
    Document document;
    Element mainElement;
    String filename;

    public FileWritersXML() {
        super();
        filename = null;
        documentBuilder = null;
        document = null;
        mainElement = null;
    }

    public FileWritersXML(String filename1) {
        try {
            filename = filename1;
            setOverwrite(true);
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            mainElement = document.createElement("results");
            document.appendChild(mainElement);
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public FileWritersXML(String filename1, boolean overwrite1) throws ParserConfigurationException {
        filename = filename1;
        setOverwrite(overwrite1);
        try {
            if (!getOverwrite()) {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(filename1);
                document.getDocumentElement().normalize();
                NodeList nodeList = document.getElementsByTagName("results");
                mainElement = (Element) nodeList.item(0);
            }
            else {
                documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.newDocument();
                mainElement = document.createElement("results");
                document.appendChild(mainElement);
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            mainElement = document.createElement("results");
            document.appendChild(mainElement);
//            id = 1;
        }
    }


    @Override
    public void writeFile(String string) {
        Element result = document.createElement("result");
        result.appendChild(document.createTextNode(string));
        mainElement.appendChild(result);
    }

    @Override
    public void close() {
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filename));
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isActive() {
        return filename != null;
    }
}
