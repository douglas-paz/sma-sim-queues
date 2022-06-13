package sma.io;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import sma.Event;
import sma.ExitRoute;
import sma.Queue;
import sma.Route;
import sma.SmaSimulation;
import sma.random.BigDecimalRandom;
import sma.random.IRandom;
import sma.random.LinearCongruentRandom;
import sma.random.MockedRandom;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    public List<SmaSimulation> parseArguments(String... args) throws Exception {
        if (args.length == 0) {
            System.out.println("=================================================================================================");
            System.out.println("usage: simulator                                                                                 ");
            System.out.println("[filename.yml]                        | path for a single file                                   ");
            System.out.println("-f, --files <file1.yml, file2.yml...> | path for files to be executed                            ");
            System.out.println("-b, --big                             | uses a big decimal random instead of a linear congruent  ");
            System.out.println("-w, --write                           | write mode, write the output to a file                   ");
            System.out.println("-d, --debug                           | log extra debug information                              ");
            System.out.println("=================================================================================================");
            System.exit(0);
            return null;
        }

        boolean isWriteActive = false;
        boolean isBigActive = false;
        boolean isDebugEnabled = false;
        List<String> files = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "-f":
                case "--files":
                    break;
                case "-b":
                case "--big":
                    isBigActive = true;
                    break;
                case "-w":
                case "--write":
                    isWriteActive = true;
                    break;
                case "-d":
                case "--debug":
                    isDebugEnabled = true;
                    break;
                default:
                    files.add(arg);
            }
        }
        List<SmaSimulation> list = new ArrayList<>();
        for (String file : files) {
            Logger logger = Logger.init(file, isWriteActive ? Logger.LOG_MODE.FILE : Logger.LOG_MODE.CONSOLE, isDebugEnabled);
            SmaSimulation parse = parse(file, logger, isBigActive);
            list.add(parse);
        }
        return list;
    }

    public SmaSimulation parse(String filename, Logger logger, boolean isBigActive) throws Exception {
        InputData input = new Loader().loadFromFile(filename);

        long seed;
        int iterations;
        IRandom random;

        if (input.seeds != null) {
            seed = input.seeds.get(0);
            iterations = Math.toIntExact(input.rndnumbersPerSeed);
            random = isBigActive ? new BigDecimalRandom(seed) : new LinearCongruentRandom(seed);
        } else if (input.rndnumbers != null) {
            random = new MockedRandom(input.rndnumbers);
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
            input.network.forEach((n) -> finalNetwork.put(n.source, convertRoute(n.source, n)));
            queueMap.forEach((s, q) -> addMissingRoutes(q, (List<Route>) finalNetwork.get(s)));
        }
        List<Event> arrivals = new ArrayList<>();
        input.arrivals.forEach((s, t) -> arrivals.add(new Event(Event.ARRIVAL, t, s)));

        return new SmaSimulation(logger, queueMap, network, random, iterations, arrivals);
    }

    public static Queue convertQueue(String name, InputData.InputQueue in) {
        return new Queue(name, in.servers, in.capacity, in.minArrival, in.maxArrival, in.minService, in.maxService);
    }

    public static Route convertRoute(String name, InputData.Network in) {
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

}
