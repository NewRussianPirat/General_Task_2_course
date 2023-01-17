package FileWriters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class FileWritersJSON extends FileWriters {

    FileWriter fileWriter;
    JSONArray jsonArray;

    public FileWritersJSON() {
        super();
        fileWriter = null;
        jsonArray = null;
    }
    public FileWritersJSON(String filename1) {
        try {
            fileWriter = new FileWriter(filename1, getOverwrite());
            jsonArray = new JSONArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeFile(String string) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", string);
        jsonArray.add(jsonObject);
    }

    @Override
    public void close() {
        try {
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
