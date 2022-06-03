import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import sma.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmaSimulator {


    public static void main(String[] args) {
        try {
            Input input = new Loader().loadFromFile(args[0]);
//            printInput(input);

            long seed = 0;
            int iterations = 0;
            IRandom random;

            if (input.seeds != null) {
                seed = input.seeds.get(0);
                iterations = Math.toIntExact(input.rndnumbersPerSeed);
                random = new LinearCongruentRandom(seed);
            } else if (input.rndnumbers != null) {
                random = new MockedRandom(convertRandoms(input.rndnumbers));
                iterations = input.rndnumbers.size();
            } else {
                throw new Exception("Randoms unespecified");
            }

            Map<String, Queue> queueMap = new HashMap<>();
            input.queues.forEach((s, q) -> queueMap.put(s, convertQueue(s, q)));

            MultiValuedMap<String, Route> network = null;
            if (input.network != null) {
                network = new ArrayListValuedHashMap<>();
                MultiValuedMap<String, Route> finalNetwork = network;
                input.network.forEach((n) -> {
                    finalNetwork.put(n.source, convertRoute(n.source, n));
                });
                queueMap.forEach((s, q) -> addMissingRoutes(q, (List<Route>) finalNetwork.get(s)));
            }
            List<Event> arrivals = new ArrayList<>();
            input.arrivals.forEach((s, t) -> arrivals.add(new Event(Event.ARRIVAL, t, s)));

            SmaSimulation simulation = new SmaSimulation(queueMap, network, random, iterations, arrivals);
            simulation.run();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printInput(Input input) {
        System.out.println("Print Input");

        if (input.rndnumbers != null) {
            System.out.println("Randoms");
            input.rndnumbers.forEach(System.out::println);
        }

        if (input.seeds != null) {
            System.out.println("Seeds");
            input.seeds.forEach(System.out::println);

            System.out.println("RandomsPerSeed");
            System.out.println(input.rndnumbersPerSeed);
        }

        System.out.println("Queues");
        input.queues.forEach((s, q) -> {
            System.out.println(convertQueue(s, q));
        });

        System.out.println("Arrivals");
        input.arrivals.forEach((s, t) -> {
            System.out.println(new Event(Event.ARRIVAL, t, s));
        });

        System.out.println("Network");
        if (input.network != null) {
            input.network.forEach((n) -> {
                System.out.println(convertRoute(n.source, n));
            });
        }
    }

    public static Queue convertQueue(String name, Input.InputQueue in) {
        return new Queue(name, in.servers, in.capacity == 0 ? 100 : in.capacity, in.minArrival, in.maxArrival, in.minService, in.maxService);
    }

    public static Route convertRoute(String name, Input.Network in) {
        return new Route(in.source, in.target, in.probability);
    }

    private static void addMissingRoutes(Queue q, List<Route> routes) {
        float sum = routes.stream().reduce(0f, (aFloat, route) -> aFloat + route.getProbability(), (aFloat, aFloat2) -> aFloat2);

        assert sum <= 1.0f;

        if (sum < 1.0f) {
            BigDecimal p = new BigDecimal(1.0 - sum).setScale(4, RoundingMode.HALF_UP);
            routes.add(new ExitRoute(q.getName(), p.floatValue()));
        }
    }

    private static float[] convertRandoms(List<Float> rndnumbers) {
        float[] floats = new float[rndnumbers.size()];
        for (int i = 0; i < rndnumbers.size(); i++) {
            floats[i] = rndnumbers.get(i);
        }
        return floats;
    }


}
