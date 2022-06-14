import sma.SmaSimulation;
import sma.io.Parser;

public class SmaSimulator {

    public static void main(String[] args) throws Exception {
        new Parser().parseArguments(args).forEach(SmaSimulation::run);
    }

}
