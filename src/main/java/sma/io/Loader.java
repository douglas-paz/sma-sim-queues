package sma.io;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Loader {

    public InputData inputLoader(String fileLocation) {
        Yaml yaml = new Yaml(new Constructor(InputData.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileLocation);
        return yaml.load(inputStream);
    }

    public InputData loadFromFile(String file) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(InputData.class));
        InputStream inputStream = new FileInputStream(file);
        InputData data = yaml.load(inputStream);
        data.originalPath = file;
        return data;
    }

    public static void writeOnPath(String path, String content) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path + "_" + System.currentTimeMillis() + ".out");
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
