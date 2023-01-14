package FileReaders;

import java.util.ArrayList;

public class FileReadersRAR extends FileReaders {
    public FileReadersRAR(String filename1) {
        super(filename1);
    }

    @Override
    protected ArrayList<String> getInfo() {
        return null;
    }

    @Override
    protected FileReaders unpacking() {
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
