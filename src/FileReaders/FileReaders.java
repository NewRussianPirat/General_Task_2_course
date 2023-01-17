package FileReaders;

import java.util.ArrayList;

public abstract class FileReaders {

    protected static String filename;

    protected static final String TXT_TYPE = ".txt";
    protected static final String XML_TYPE = ".xml";
    protected static final String JSON_TYPE = ".json";
    protected static final String ZIP_TYPE = ".zip";
    protected static final String RAR_TYPE = ".rar";
    protected static final String ENC_TYPE = ".enc";

    protected FileReaders() {
        filename = "";
    }

    protected FileReaders(String filename1) { filename = filename1; }

    public static ArrayList<String> readFile(String filename1) {
        FileReaders fileReaders = createFileReader(filename1);

        if (fileReaders == null) {
            throw new RuntimeException("Wrong file type");
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

    protected static String getFileType(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    public String getFilename() {
        return filename;
    }

    protected static FileReaders createFileReader(String filename1) {

        filename = filename1;
        String type = getFileType(filename1);
        switch (type) {
            case TXT_TYPE -> { return new FileReadersTXT(filename1); }
            case XML_TYPE -> { return new FileReadersXML(filename1); }
            case JSON_TYPE -> { return new FileReadersJSON(filename1); }
            case ZIP_TYPE -> { return new FileReadersZIP(filename1); }
//            case RAR_TYPE -> { return new FileReadersRAR(filename); }
            case ENC_TYPE -> { return new FileReadersENC(filename1); }
            default -> { return null; }
        }
    }
}
