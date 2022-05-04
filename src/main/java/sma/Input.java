package sma;

import java.util.List;
import java.util.Map;

public class Input {

    public List<Double> rndnumbers;
    public List<Long> seeds;
    public Map<String, InputQueue> queues;
    public Map<String, Double> arrivals;
    public List<Network> network;

    public Long rndnumbersPerSeed = 100000L;

    public static class InputQueue {
        public int capacity;
        public int servers;
        public double minArrival;
        public double maxArrival;
        public double minService;
        public double maxService;
    }
    public static class Network {
        public String source;
        public String target;
        public double probability;

        public Network() {
        }
    }
}
