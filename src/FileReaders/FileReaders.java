package FileReaders;

import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;

public abstract class FileReaders {

    protected final String filename;
    protected boolean encrypted;
    protected SecretKeySpec key;

    protected FileReaders() {
        filename = "";
    }

    protected FileReaders(String filename1) {
        filename = filename1;
    }

    public ArrayList<String> readFile(String filename) {
        FileReaders fileReaders;
        String type = getFileType(filename);

        switch (type) {
            case "txt" -> fileReaders = new FileReadersTXT(filename);
            case "xml" -> fileReaders = new FileReadersXML(filename);
            case "json" -> fileReaders = new FileReadersJSON(filename);
            case "zip" -> fileReaders = new FileReadersZIP(filename);
//            case "rar" -> fileReaders = new FileReadersRAR(filename);
            default -> throw new RuntimeException("Wrong file type");
        }
        
        while (fileReaders.isPacked() || fileReaders.isEncrypted()) {
            fileReaders = fileReaders.unpacking();
            fileReaders = fileReaders.decrypting();
        }
        
        return fileReaders.getInfo();
    }

    abstract protected ArrayList<String> getInfo();
    abstract protected FileReaders unpacking();
    abstract protected FileReaders decrypting();
    abstract protected boolean isPacked();
    protected boolean isEncrypted() { return encrypted; }
    
    String getFileType(String filename) {
        if (filename.contains(".txt")) {
            return "txt";
        } else if (filename.contains(".xml")) {
            return  "xml";
        } else if (filename.contains(".json")) {
            return  "json";
        } else if (filename.contains(".zip")) {
            return  "zip";
        } /*else if (filename.contains(".rar")) {
            return  "rar";
        }*/ else {
            return  "";
        }
    }

    public String getFilename() {
        return filename;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public void setKey(SecretKeySpec key) {
        this.key = key;
    }
}
