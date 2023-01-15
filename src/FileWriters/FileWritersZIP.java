package FileWriters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileWritersZIP extends FileWriters {
    ZipOutputStream zipOutputStream;
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    ZipEntry zipEntry;

    public FileWritersZIP() { super();
        zipOutputStream = null;
        fileOutputStream = null;
        fileInputStream = null;
        zipEntry = null;
    }
    public FileWritersZIP(String filename1) {
        super(filename1);
        try {
            fileOutputStream = new FileOutputStream(filename1);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            fileInputStream = null;
            zipEntry = null;
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFile(String filename) {
        try {
            fileInputStream = new FileInputStream(filename);
            zipEntry = new ZipEntry(filename.substring(filename.lastIndexOf('/')));
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(fileInputStream.readAllBytes());
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            zipOutputStream.close();
            fileOutputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
