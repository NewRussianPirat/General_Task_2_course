package FileWriters;

public abstract class FileWriters {

    private boolean overwrite = false;
    private String filename = null;

    protected FileWriters() {}

    abstract public void writeFile(String string);
    abstract public void close();
    abstract public boolean isActive();

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public boolean getOverwrite() {
        return overwrite;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String string) {
        this.filename = string;
    }
}
