package sma;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;

public class Loader {

    public Input inputLoader(String fileLocation) {
        Yaml yaml = new Yaml(new Constructor(Input.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileLocation);
        return yaml.load(inputStream);
    }
}
