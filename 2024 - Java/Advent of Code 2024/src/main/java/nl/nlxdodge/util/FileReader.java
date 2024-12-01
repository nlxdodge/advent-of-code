package nl.nlxdodge.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    
    public static List<String> readLines(String resourceName) {
        List<String> lines = new ArrayList<>();
        
        try (InputStream stream = FileReader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (stream == null) {
                System.out.printf("File not found: %s", resourceName);
                return lines;
            }
            BufferedReader bf = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = bf.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }
}
