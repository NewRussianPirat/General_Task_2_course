package FileReaders;

import java.util.ArrayList;

public abstract class FileReaders {

    protected String filename;

    protected final String TXT_TYPE = ".txt";
    protected final String XML_TYPE = ".xml";
    protected final String JSON_TYPE = ".json";
    protected final String ZIP_TYPE = ".zip";
    protected final String RAR_TYPE = ".rar";
    protected final String ENC_TYPE = ".enc";

    protected FileReaders() {
        filename = "";
    }

    protected FileReaders(String filename1) { filename = filename1; }

    public ArrayList<String> readFile(String filename) {
        FileReaders fileReaders;
        String type = getFileType(filename);

        switch (type) {
            case TXT_TYPE -> fileReaders = new FileReadersTXT(filename);
            case XML_TYPE -> fileReaders = new FileReadersXML(filename);
            case JSON_TYPE -> fileReaders = new FileReadersJSON(filename);
            case ZIP_TYPE -> fileReaders = new FileReadersZIP(filename);
//            case RAR_TYPE -> fileReaders = new FileReadersRAR(filename);
            case ENC_TYPE -> fileReaders = new FileReadersENC(filename);
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
    abstract protected boolean isEncrypted();

    String getFileType(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    public String getFilename() {
        return filename;
    }

    protected FileReaders createFileReader(String filename) {
        String type = getFileType(filename);
        switch (type) {
            case TXT_TYPE -> { return new FileReadersTXT(filename); }
            case XML_TYPE -> { return new FileReadersXML(filename); }
            case JSON_TYPE -> { return new FileReadersJSON(filename); }
            case ZIP_TYPE -> { return new FileReadersZIP(filename); }
//                case "rar" -> { return new FileReadersRAR(filename); }
            case ENC_TYPE -> { return new FileReadersENC(filename); }
            default -> { return null; }
        }
    }
}
