package util;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * User: M.Belski@sam-solutions.com
 * Date: 28.01.2020
 */


public class StaticDataLoader {

    public static final HashMap<String, String> getSchemas() throws IOException {
        File file = new StaticDataLoader().getFileFromResources("lineups.txt");
        HashMap<String, String> lines = new HashMap<>();
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] x = line.split("=");
                lines.put(x[0], x[1]);
            }
        }
        return lines;
    }

    private File getFileFromResources(String fileName) {
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }

}
