package FileWriters;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileWritersXML extends FileWriters {

    XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
    XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
    OutputStream outputStream;
    XMLEventWriter xmlEventWriter;

    public FileWritersXML() {
        super();
        outputStream = null;
        xmlEventWriter = null;
    }
    public FileWritersXML(String filename1) {
        try {
            outputStream = new FileOutputStream(filename1, getOverwrite());
            xmlEventWriter = xmlOutputFactory.createXMLEventWriter(outputStream);
            xmlEventWriter.add(xmlEventFactory.createStartDocument());
            xmlEventWriter.add(xmlEventFactory.createStartElement("", "", "results"));
        }
        catch (FileNotFoundException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void writeFile(String string) {
        try {
            xmlEventWriter.add(xmlEventFactory.createStartElement("", "", "result"));
            xmlEventWriter.add(xmlEventFactory.createCharacters(string));
            xmlEventWriter.add(xmlEventFactory.createEndElement("", "", "result"));
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            xmlEventWriter.add(xmlEventFactory.createEndElement("", "", "results"));
            xmlEventWriter.add(xmlEventFactory.createEndDocument());
            xmlEventWriter.flush();
            xmlEventWriter.close();
            outputStream.close();
        } catch (XMLStreamException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
