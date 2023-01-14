package FileWriters;

public abstract class FileWriters {

    private final String filename;

    protected FileWriters() { filename = ""; }
    protected FileWriters(String filename1) { filename = filename1; }

    abstract public void writeFile(String string);
    abstract public void close();

    public String getFilename() {
        return filename;
    }
}
