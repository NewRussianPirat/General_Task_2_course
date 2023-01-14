package FileReaders;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.*;
import java.util.ArrayList;

public class FileReadersRAR extends FileReaders {

    public FileReadersRAR() {
        super();
    }

    public FileReadersRAR(String filename1) {
        super(filename1);
    }

    @Override
    protected ArrayList<String> getInfo() {
        return null;
    }

    @Override
    protected FileReaders unpacking() {
/*        try {
            Archive archive = new Archive(new File(this.getFilename()));
            FileHeader fileHeader = archive.nextFileHeader();
            String filename = fileHeader.getFileName();
            OutputStream outputStream = new FileOutputStream(filename);
            archive.extractFile(fileHeader, outputStream);

            String type = getFileType(filename);
            archive.close();
            outputStream.close();

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
        catch (RarException e) {
            throw new RuntimeException("RarException");
        }
        catch (IOException e) {
            throw new RuntimeException("IOException");
        }*/
        return null;
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
