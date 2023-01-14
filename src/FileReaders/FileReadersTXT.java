package FileReaders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReadersTXT extends FileReaders {

    public FileReadersTXT(String filename1) {
        super(filename1);
    }

    @Override
    protected ArrayList<String> getInfo() {
        try {
            ArrayList<String> stringArrayList = new ArrayList<>();
            FileReader fileReader = new FileReader(this.getFilename());
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                stringArrayList.add(scanner.nextLine());
            }
            fileReader.close();
            return stringArrayList;
        }
        catch (FileNotFoundException e1) {
            throw new RuntimeException("File not found");
        }
        catch (IOException e2) {
            throw new RuntimeException("IOException");
        }
    }

    @Override
    protected FileReaders unpacking() {
        return this;
    }

    @Override
    protected FileReaders decrypting() {
        return this;
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
