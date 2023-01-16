package FileReaders;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileReadersTest {

    @Test
    void readFile() {
        FileReaders fileReaders = new FileReadersTXT();
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(
                "1 + 3 / 2",
                "-9 + 5 * 4",
                "2 + 2"
        ));
        assertEquals(arrayList, fileReaders.readFile("inputTestFiles/input.txt"));
        assertEquals(arrayList, fileReaders.readFile("inputTestFiles/input.xml"));
        assertEquals(arrayList, fileReaders.readFile("inputTestFiles/input.json"));
        assertEquals(arrayList, fileReaders.readFile("inputTestFiles/input_txt.zip"));
        assertEquals(arrayList, fileReaders.readFile("inputTestFiles/input.txt.enc"));
    }

    @Test
    void getInfo() {
        FileReaders fileReaders = new FileReadersTXT("inputTestFiles/input.txt");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(
                "1 + 3 / 2",
                "-9 + 5 * 4",
                "2 + 2"
        ));
        assertEquals(arrayList, fileReaders.getInfo());
        fileReaders = new FileReadersXML("inputTestFiles/input.xml");
        assertEquals(arrayList, fileReaders.getInfo());
        fileReaders = new FileReadersJSON("inputTestFiles/input.json");
        assertEquals(arrayList, fileReaders.getInfo());
        fileReaders = new FileReadersZIP("inputTestFiles/input_txt.zip");
        assertNull(fileReaders.getInfo());
        fileReaders = new FileReadersENC("inputTestFiles/input.txt.enc");
        assertNull(fileReaders.getInfo());
    }

    @Test
    void unpacking() {
        FileReaders fileReaders = new FileReadersZIP("inputTestFiles/input_txt.zip");
        assertEquals(FileReadersTXT.class, fileReaders.unpacking().getClass());
        fileReaders = new FileReadersZIP("inputTestFiles/input_xml.zip");
        assertEquals(FileReadersXML.class, fileReaders.unpacking().getClass());
        fileReaders = new FileReadersZIP("inputTestFiles/input_json.zip");
        assertEquals(FileReadersJSON.class, fileReaders.unpacking().getClass());
        fileReaders = new FileReadersZIP("inputTestFiles/input_enc.zip");
        assertEquals(FileReadersENC.class, fileReaders.unpacking().getClass());
    }

    @Test
    void getFileType() {
        FileReaders fileReaders = new FileReadersTXT();
        assertEquals(".zip", fileReaders.getFileType("input.zip"));
        assertEquals(".txt", fileReaders.getFileType("input.txt"));
        assertEquals(".json", fileReaders.getFileType("input.json"));
        assertEquals(".xml", fileReaders.getFileType("input.xml"));
        assertEquals(".enc", fileReaders.getFileType("input.enc"));
        assertEquals(".rar", fileReaders.getFileType("input.rar"));
        assertEquals(".rar", fileReaders.getFileType("input.txt.enc.zip.rar"));
    }

    @Test
    void createFileReader() {
        FileReaders fileReaders = new FileReadersTXT();
        assertEquals(FileReadersTXT.class, fileReaders.createFileReader("input.txt").getClass());
        assertEquals(FileReadersXML.class, fileReaders.createFileReader("input.xml").getClass());
        assertEquals(FileReadersJSON.class, fileReaders.createFileReader("input.json").getClass());
        assertEquals(FileReadersZIP.class, fileReaders.createFileReader("input.zip").getClass());
//        assertEquals(FileReadersRAR.class, fileReaders.createFileReader("input.rar").getClass());
        assertEquals(FileReadersENC.class, fileReaders.createFileReader("input.enc").getClass());
        assertNull(fileReaders.createFileReader("input.wer"));
    }
}