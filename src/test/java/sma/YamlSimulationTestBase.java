package sma;

import sma.io.InputData;
import sma.io.Loader;

public class YamlSimulationTestBase {

    protected String file = "teste_final.yml";

    protected InputData inputData;

    protected void load() throws Exception {
        InputData inputData = new Loader().inputLoader(file);
    }
}
