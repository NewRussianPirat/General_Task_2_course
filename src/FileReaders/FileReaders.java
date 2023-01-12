package FileReaders;

import java.io.IOException;
import java.util.ArrayList;

public interface FileReaders {
    ArrayList<String> readFile(String filename) throws IOException;
}
