package FileReaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileReadersJSON extends FileReaders {

    public FileReadersJSON(String filename1) {
        super(filename1);
    }

    @Override
    protected ArrayList<String> getInfo() {
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(this.getFilename()));
            ArrayList<String> arrayList = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) jsonObject.get("expressions");
            for (Object o : jsonArray) {
                arrayList.add(o.toString());
            }
            return arrayList;
        }
        catch (FileNotFoundException e1) {
            throw new RuntimeException("File not found");
        }
        catch (ParseException e2) {
            throw new RuntimeException("ParseException");
        }
        catch (IOException e3) {
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
