package sma;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SmaSimulation {

    private SmaScheduler scheduler;

    private IRandom random;
    private int iterations;
    private float time;
    private Map<String, Queue> queues = new HashMap<>();
    private MultiValuedMap<String, Route> network = new ArrayListValuedHashMap<>();


    public SmaSimulation(Queue q, IRandom r, int i, float t1) {
        this.queues.put(q.getName(), q);
        this.iterations = i;
        this.time = 0;
        this.random = r;
        this.scheduler = new SmaScheduler();
        this.scheduler.init(new Event(Event.ARRIVAL, t1, q.getName()));
    }

    public SmaSimulation(Map<String, Queue> q, MultiValuedMap<String, Route> n, IRandom r, int i, List<Event> e1) {
        this.queues = q;
        this.network = n;
        this.iterations = i;
        this.time = 0;
        this.random = r;
        this.scheduler = new SmaScheduler(e1);
    }

    public void run() {
        System.out.println(String.format("Starting simulation: %n Queues=%s %n Iterations=%d %n", queues, iterations));
        try {
            while (iterations > 0) {
                Event e = scheduler.removeFirst();
                updateTime(e.getTime());
                System.out.println("Iterations: " + iterations + ", " + e);
                if (e.getType() == Event.ARRIVAL)
                    arrival(e, queues.get(e.getQueue()));
                else if (e.getType() == Event.DEPARTURE)
                    departure(e, queues.get(e.getQueue()));
                else if (e.getType() == Event.TRANSITION) {
                    transition(e, queues.get(e.getQueue()), queues.get(e.getDestinationQueue()));
                }
            }
        } catch (Exception e) {
            System.out.println("End of randoms");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println(String.format(Locale.ROOT, " End of Simulation: %n Scheduler=%s %n Time=%f", scheduler, time));
        queues.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> System.out.println(e.getValue().printStateTimes(time)));
    }


    public void updateTime(float t) {
        float delta = t - time;
        time = t;
        queues.forEach((s, queue) -> queue.updateTimes(delta));
//        System.out.println(String.format(Locale.ROOT, "Times: %n Time=%f %n Delta=%f %n Queue=%s", time, delta, queue.printStateTimes()));
    }


    public void arrival(Event e, Queue q) {
        innerArrival(e, q);
        scheduler.add(new Event(Event.ARRIVAL, e.getTime() + randomBetween(q.getMinArrival(), q.getMaxArrival()), q.getName()));
    }

    public void innerArrival(Event e, Queue q) {
        if (q.getCapacity() == 0 || q.getSize() < q.getCapacity()) {
            q.setSize(q.getSize() + 1);
            if (q.getSize() <= q.getServers()) {
                if (network != null) {
                    List<Route> routes = (List<Route>) network.get(q.getName());
                    if (routes != null && routes.size() > 0) {
                        Route rt = routes.size() == 1
                                ? routes.get(0)
                                : selectRoute(routes);
                        if (rt instanceof ExitRoute) {
                            scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
                        } else {
                            scheduler.add(new Event(Event.TRANSITION, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), rt.getOrigin(), rt.getDestination()));
                        }
                    } else {
                        scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
                    }
                } else {
                    scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
                }
            }
        } else {
            q.addLoss();
        }
    }

    public Route selectRoute(List<Route> routes) {
        routes.sort((o1, o2) -> Float.compare(o2.getProbability(), o1.getProbability()));
        float rnd = randomBetween(0, 1);
        float p = 0f;
        for (Route r : routes) {
            p += r.getProbability();
            if (rnd < p)
                return r;
        }
        return null;
    }


    public void departure(Event e, Queue q) throws Exception {
        if (q.getSize() == 0)
            throw new Exception("Queue is empty");
        q.setSize(q.getSize() - 1);
        if (q.getSize() >= q.getServers()) {
            if (network != null) {
                List<Route> routes = (List<Route>) network.get(q.getName());
                if (routes != null && routes.size() > 0) {
                    Route rt = routes.size() == 1
                            ? routes.get(0)
                            : selectRoute(routes);
                    if (rt instanceof ExitRoute) {
                        scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
                    } else {
                        scheduler.add(new Event(Event.TRANSITION, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), rt.getOrigin(), rt.getDestination()));
                    }
                } else {
                    scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
                }
            } else {
                scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
            }
        }
    }

//    private void transition(Event e, Queue src, Queue dst) throws Exception {
//        if (src.getSize() == 0)
//            throw new Exception("Queue is empty");
//        src.setSize(src.getSize() - 1);
//        if (src.getSize() >= src.getServers()) {
//            scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(dst.getMinDeparture(), dst.getMaxDeparture()), dst.getName()));
//        }
//        if (dst.getCapacity() == 0 || dst.getSize() < dst.getCapacity()) {
//            dst.setSize(dst.getSize() + 1);
//            if (dst.getSize() <= dst.getServers()) {
//                if (network != null) {
//                    List<Route> routes = (List<Route>) network.get(dst.getName());
//                    if (routes != null && routes.size() > 0) {
//                        Route rt = routes.size() == 1
//                                ? routes.get(0)
//                                : selectRoute(routes);
//                        if (rt instanceof ExitRoute) {
//                            scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(dst.getMinDeparture(), dst.getMaxDeparture()), dst.getName()));
//                        } else {
//                            scheduler.add(new Event(Event.TRANSITION, e.getTime() + randomBetween(dst.getMinDeparture(), dst.getMaxDeparture()), rt.getOrigin(), rt.getDestination()));
//                        }
//                    } else {
//                        scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(dst.getMinDeparture(), dst.getMaxDeparture()), dst.getName()));
//                    }
//                } else {
//                    scheduler.add(new Event(Event.DEPARTURE, e.getTime() + randomBetween(dst.getMinDeparture(), dst.getMaxDeparture()), dst.getName()));
//                }
//            }
//        } else {
//            dst.addLoss();
//        }
//    }

    private void transition(Event e, Queue src, Queue dst) throws Exception {
        departure(e, src);
        innerArrival(e, dst);
    }


    private float randomBetween(float a, float b) {
        iterations = iterations - 1;
        float rnd = (float) ((b - a) * random.nextDouble() + a);
//        System.out.println(String.format(Locale.ROOT, "Randoms: Next=%f Remaining=%d", rnd, iterations));
        return rnd;
    }
}
