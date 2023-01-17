package FileWriters;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileWritersTest {

    @Test
    void writeFile() throws IOException {
        FileWriters fileWriters = new FileWritersTXT("outputTestFiles\\output.txt");
        fileWriters.writeFile("TestTestTestTXT");
        fileWriters.close();
        FileInputStream fileInputStream1 = new FileInputStream("outputTestFiles\\output.txt");
        FileInputStream fileInputStream2 = new FileInputStream("outputTestFiles\\expected_output.txt");
        assertArrayEquals(fileInputStream1.readAllBytes(), fileInputStream2.readAllBytes());

        fileWriters = new FileWritersXML("outputTestFiles\\output.xml");
        fileWriters.writeFile("TestTestTestXML");
        fileWriters.close();
        fileInputStream1 = new FileInputStream("outputTestFiles\\output.xml");
        fileInputStream2 = new FileInputStream("outputTestFiles\\expected_output.xml");
        assertArrayEquals(fileInputStream1.readAllBytes(), fileInputStream2.readAllBytes());

        fileWriters = new FileWritersJSON("outputTestFiles\\output.json");
        fileWriters.writeFile("TestTestTestJSON");
        fileWriters.close();
        fileInputStream1 = new FileInputStream("outputTestFiles\\output.json");
        fileInputStream2 = new FileInputStream("outputTestFiles\\expected_output.json");
        assertArrayEquals(fileInputStream1.readAllBytes(), fileInputStream2.readAllBytes());

        fileWriters = new FileWritersZIP("outputTestFiles\\output.zip");
        fileWriters.writeFile("outputTestFiles\\output.txt");
        fileWriters.close();
        fileInputStream1 = new FileInputStream("outputTestFiles\\output.zip");
        assertNotNull(fileInputStream1.readAllBytes());
    }
}