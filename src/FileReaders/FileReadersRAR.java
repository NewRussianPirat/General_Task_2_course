package FileReaders;

import java.util.ArrayList;

public class FileReadersRAR extends FileReaders {
    FileReadersRAR(String filename1) {
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
        return null;
    }

    @Override
    protected boolean isPacked() {
        return false;
    }

    @Override
    protected boolean isEncrypted() {
        return false;
    }
}
