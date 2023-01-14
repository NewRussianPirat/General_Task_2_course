package FileReaders;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileReadersZIP extends FileReaders {
    public FileReadersZIP(String filename1) {
        super(filename1);
    }

    @Override
    protected  ArrayList<String> getInfo() {
        return null;
    }

    @Override
    protected FileReaders unpacking() {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(this.getFilename()));
            ZipEntry zipEntry;
            String filename = "";
            if ((zipEntry = zipInputStream.getNextEntry()) != null) {
                filename = "intermediateFiles/" + zipEntry.getName();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            fileOutputStream.write(zipInputStream.readAllBytes());
            fileOutputStream.close();
            zipInputStream.close();

            String type = getFileType(filename);
            switch (type) {
                case "txt" -> { return new FileReadersTXT(filename); }
                case "xml" -> { return new FileReadersXML(filename); }
                case "json" -> { return new FileReadersJSON(filename); }
                case "zip" -> { return new FileReadersZIP(filename); }
                case "rar" -> { return new FileReadersRAR(filename); }
                default -> { return null; }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected FileReaders decrypting() {
        return this;
    }

    @Override
    protected boolean isPacked() {
        return true;
    }

    @Override
    protected boolean isEncrypted() {
        return false;
    }
}
