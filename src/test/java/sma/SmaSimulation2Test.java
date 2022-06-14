package sma;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;
import sma.io.Logger;
import sma.random.IRandom;
import sma.random.LinearCongruentRandom;
import sma.random.MockedRandom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmaSimulation2Test {

    public static final Logger log = Logger.init(Logger.LOG_MODE.CONSOLE);

    @Test
    public void testTandem_RandomList() {
        System.out.println("Test Tandem " +
                "\nQueue1=G/G/2/3 Arrivals=2-3 Departure=2-5 " +
                "\nQueue2=G/G/1/3 Arrivals=TandemQ1 Departures=3-5");


        Map<String, Queue> queues = new HashMap<>();
        queues.put("Q1", new Queue("Q1", 2, 3, 2, 3, 2, 5));
        queues.put("Q2", new Queue("Q2", 1, 3, 0, 0, 3, 5));

        MultiValuedMap<String, Route> network = new ArrayListValuedHashMap<>();
        network.put("Q1", new Route("Q1", "Q2", 1.0f));

        float[] randomsList = new float[] {
                0.9921f,
                0.0004f,
                0.5534f,
                0.2761f,
                0.3398f,
                0.8963f,
                0.9023f,
                0.0132f,
                0.4569f,
                0.5121f,
                0.9208f,
                0.0171f,
                0.2299f,
                0.8545f,
                0.6001f,
                0.2921f
        };

        IRandom random = new MockedRandom(randomsList);
        List<Event> arrivals = new ArrayList<>();
        arrivals.add(new Event(Event.ARRIVAL, 2.5f, "Q1"));
        SmaSimulation sim = new SmaSimulation(log, queues, network, random, randomsList.length, arrivals);
        sim.run();
    }

    @Test
    public void testTandem_RandomAmount() {
        System.out.println("Test Tandem " +
                "\nQueue1=G/G/2/3 Arrivals=2-3 Departure=2-5 " +
                "\nQueue2=G/G/1/3 Arrivals=TandemQ1 Departures=3-5");

        Map<String, Queue> queues = new HashMap<>();
        queues.put("Q1", new Queue("Q1", 2, 3, 2, 3, 2, 5));
        queues.put("Q2", new Queue("Q2", 1, 3, 0, 0, 3, 5));

        MultiValuedMap<String, Route> network = new ArrayListValuedHashMap<>();
        network.put("Q1", new Route("Q1", "Q2", 1.0f));

        IRandom random = new LinearCongruentRandom(10);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        int iterations = 100_000;

        List<Event> arrivals = new ArrayList<>();
        arrivals.add(new Event(Event.ARRIVAL, 2.5f, "Q1"));
        SmaSimulation sim = new SmaSimulation(log, queues, network, random, iterations, arrivals);
        sim.run();
    }
}
