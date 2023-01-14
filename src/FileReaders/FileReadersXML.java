package FileReaders;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FileReadersXML extends FileReaders {

    public FileReadersXML() {
        super();
    }

    public FileReadersXML(String filename1) {
        super(filename1);
    }

    @Override
    protected ArrayList<String> getInfo() {
        try {
            ArrayList<String> arrayList = new ArrayList<>();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStream inputStream = new FileInputStream(this.getFilename());
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Expression")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        arrayList.add(xmlEvent.asCharacters().toString());
                    }
                }
            }
            inputStream.close();
            return arrayList;
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected FileReaders unpacking() {
        return this;
    }

    @Override
    protected FileReaders decrypting() {
        return this;
    }

    @Override
    protected boolean isPacked() {
        return false;
    }

    @Override
    protected boolean isEncrypted() {
        return false;
    }
}
