package sma;

import java.util.List;
import java.util.Map;

public class Input {

    public List<Float> rndnumbers;
    public List<Long> seeds;
    public Long rndnumbersPerSeed;
    public Map<String, InputQueue> queues;
    public Map<String, Float> arrivals;
    public List<Network> network;


    public static class InputQueue {
        public int capacity;
        public int servers;
        public float minArrival;
        public float maxArrival;
        public float minService;
        public float maxService;
    }

    public static class Network {
        public String source;
        public String target;
        public float probability;
        public Network() {}
    }
}
