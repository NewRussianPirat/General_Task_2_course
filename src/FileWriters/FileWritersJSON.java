package FileWriters;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWritersJSON extends FileWriters {

    FileWriter fileWriter;
    FileReader fileReader;
    JSONObject results;
    JSONArray jsonArray;

    public FileWritersJSON() {
        super();
        fileWriter = null;
        fileReader = null;
        results = null;
        jsonArray = null;
    }
    public FileWritersJSON(String filename1) {
        try {
            setFilename(filename1);
            fileWriter = new FileWriter(filename1, getOverwrite());
            results = new JSONObject();
            jsonArray = new JSONArray();
            fileReader = null;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileWritersJSON(String filename1, boolean overwrite1) throws IOException {
        setFilename(filename1);
        setOverwrite(overwrite1);
        try {
            if (getOverwrite()) {
                fileWriter = new FileWriter(filename1);
                results = new JSONObject();
                jsonArray = new JSONArray();
                fileReader = null;
            } else {
                fileReader = new FileReader(filename1);
                results = (JSONObject) new JSONParser().parse(fileReader);
                jsonArray = (JSONArray) results.get("results");
                fileReader.close();
                fileWriter = new FileWriter(filename1);
            }
        }
        catch (IOException | ParseException e) {
            fileWriter = new FileWriter(filename1);
            results = new JSONObject();
            jsonArray = new JSONArray();
            fileReader = null;
        }
    }

    @Override
    public void writeFile(String string) {
        jsonArray.add(string);
    }

    @Override
    public void close() {
        try {
            results.put("results", jsonArray);
            if (fileReader != null) {
                fileReader.close();
            }
            fileWriter.write(results.toJSONString());
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
