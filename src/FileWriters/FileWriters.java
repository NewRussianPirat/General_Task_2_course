package FileWriters;

public abstract class FileWriters {

    private boolean overwrite = false;

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
}
