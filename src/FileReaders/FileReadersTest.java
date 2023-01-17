package FileReaders;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class FileReadersTest {

    @Test
    void readFile() {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(
                "1 + 3 / 2",
                "-9 + 5 * 4",
                "2 + 2"
        ));
        assertEquals(arrayList, FileReaders.readFile("inputTestFiles\\input.txt"));
        assertEquals(arrayList, FileReaders.readFile("inputTestFiles\\input.xml"));
        assertEquals(arrayList, FileReaders.readFile("inputTestFiles\\input.json"));
        assertEquals(arrayList, FileReaders.readFile("inputTestFiles\\input.zip"));
        assertEquals(arrayList, FileReaders.readFile("inputTestFiles\\input.txt.enc"));
    }

    @Test
    void getInfo() {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(
                "1 + 3 / 2",
                "-9 + 5 * 4",
                "2 + 2"
        ));
        FileReaders fileReaders = new FileReadersTXT("inputTestFiles\\input.txt");
        assertEquals(arrayList, fileReaders.getInfo());
        fileReaders = new FileReadersXML("inputTestFiles\\input.xml");
        assertEquals(arrayList, fileReaders.getInfo());
        fileReaders = new FileReadersJSON("inputTestFiles\\input.json");
        assertEquals(arrayList, fileReaders.getInfo());
        fileReaders = new FileReadersZIP("inputTestFiles\\input_txt.zip");
        assertNull(fileReaders.getInfo());
        fileReaders = new FileReadersENC("inputTestFiles\\input.txt.enc");
        assertNull(fileReaders.getInfo());
    }

    @Test
    void unpacking() {
        FileReaders fileReaders = new FileReadersZIP("inputTestFiles\\input_txt.zip");
        assertEquals(FileReadersTXT.class, fileReaders.unpacking().getClass());
        fileReaders = new FileReadersZIP("inputTestFiles\\input_xml.zip");
        assertEquals(FileReadersXML.class, fileReaders.unpacking().getClass());
        fileReaders = new FileReadersZIP("inputTestFiles\\input_json.zip");
        assertEquals(FileReadersJSON.class, fileReaders.unpacking().getClass());
        fileReaders = new FileReadersZIP("inputTestFiles\\input_enc.zip");
        assertEquals(FileReadersENC.class, fileReaders.unpacking().getClass());
    }

    @Test
    void getFileType() {
        assertEquals(".zip", FileReaders.getFileType("input.zip"));
        assertEquals(".txt", FileReaders.getFileType("input.txt"));
        assertEquals(".json", FileReaders.getFileType("input.json"));
        assertEquals(".xml", FileReaders.getFileType("input.xml"));
        assertEquals(".enc", FileReaders.getFileType("input.enc"));
        assertEquals(".rar", FileReaders.getFileType("input.rar"));
        assertEquals(".rar", FileReaders.getFileType("input.txt.enc.zip.rar"));
    }

    @Test
    void createFileReader() {
        assertEquals(FileReadersTXT.class, Objects.requireNonNull(FileReaders.createFileReader("input.txt")).getClass());
        assertEquals(FileReadersXML.class, Objects.requireNonNull(FileReaders.createFileReader("input.xml")).getClass());
        assertEquals(FileReadersJSON.class, Objects.requireNonNull(FileReaders.createFileReader("input.json")).getClass());
        assertEquals(FileReadersZIP.class, Objects.requireNonNull(FileReaders.createFileReader("input.zip")).getClass());
//        assertEquals(FileReadersRAR.class, fileReaders.createFileReader("input.rar").getClass());
        assertEquals(FileReadersENC.class, Objects.requireNonNull(FileReaders.createFileReader("input.enc")).getClass());
        assertNull(FileReaders.createFileReader("input.wer"));
    }
}