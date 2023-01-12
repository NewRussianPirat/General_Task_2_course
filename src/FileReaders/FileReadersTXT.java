package FileReaders;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReadersTXT implements FileReaders {

    @Override
    public ArrayList<String> readFile(String filename) throws IOException {
        ArrayList<String> stringArrayList = new ArrayList<>();
        FileReader fileReader = new FileReader(filename);
        Scanner scanner = new Scanner(fileReader);
        while (scanner.hasNextLine()) {
            stringArrayList.add(scanner.nextLine());
        }
        fileReader.close();
        return stringArrayList;
    }
}