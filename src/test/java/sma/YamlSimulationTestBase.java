package sma;

public class YamlSimulationTestBase {

    protected String file = "teste_final.yml";

    protected Input input;

    protected void load() throws Exception {
        Input input = new Loader().inputLoader(file);
    }
}
