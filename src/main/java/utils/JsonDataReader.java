package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonDataReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> readSingle(String filePath) {
        try {
            return mapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read test data file: " + filePath, e);
        }
    }

    public static List<Map<String, Object>> readList(String filePath) {
        try {
            return mapper.readValue(new File(filePath), List.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read test data file: " + filePath, e);
        }
    }
}
