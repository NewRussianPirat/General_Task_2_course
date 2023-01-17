package FileWriters;

import java.io.FileWriter;
import java.io.IOException;

public class FileWritersTXT extends FileWriters {

    FileWriter fileWriter;

    public FileWritersTXT() { super(); fileWriter = null; }
    public FileWritersTXT(String filename1) {
        try {
            fileWriter = new FileWriter(filename1, getOverwrite());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileWritersTXT(String filename1, boolean overwrite1) {
        setOverwrite(overwrite1);
        try {
            fileWriter = new FileWriter(filename1, getOverwrite());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFile(String string) {
        try {
            fileWriter.write(string + '\n');
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            fileWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isActive() {
        return fileWriter != null;
    }
}
