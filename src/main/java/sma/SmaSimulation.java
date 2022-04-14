package sma;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.*;

public class SmaSimulation {

    private SmaScheduler scheduler;

    private IRandom random;
    private int iterations;
    private float first;
    private float time;
    private Map<String, Queue> queues = new HashMap<>();
    private MultiValuedMap<String, Route> network = new ArrayListValuedHashMap<>();


    public SmaSimulation(Queue q, IRandom r, int i, float t1) {
        this.queues.put(q.getName(), q);
        this.iterations = i;
        this.first = t1;
        this.time = 0;

        this.random = r;

        this.scheduler = new SmaScheduler();
        this.scheduler.init(new Event(Event.ARRIVAL, t1, q.getName()));
    }

    public SmaSimulation(Map<String, Queue> q, MultiValuedMap<String, Route> n, IRandom r, int i, Event e1) {
        this.queues = q;
        this.network = n;
        this.iterations = i;
        this.time = 0;
        this.first = e1.getTime();

        this.random = r;

        this.scheduler = new SmaScheduler();
        this.scheduler.init(e1);
    }

    public void run() {
        System.out.println(String.format("Starting simulation: %n Queues=%s %n Iterations=%d %n", queues, iterations));
        try {
            while (iterations > 0) {
                Event e = scheduler.removeFirst();
//            System.out.println(e);
                if (e.getType() == Event.ARRIVAL)
                    arrival(queues.get(e.getQueue()), e.getTime());
                else if (e.getType() == Event.DEPARTURE)
                    departure(queues.get(e.getQueue()), e.getTime());
                else if (e.getType() == Event.TRANSITION) {
                    transition(queues.get(e.getQueue()), queues.get(e.getDestinationQueue()), e.getTime());
                }
            }
        } catch (Exception e) {
            System.out.println("End of randoms");
        }
        System.out.println(String.format(Locale.ROOT, " End of Simulation: %n Scheduler=%s %n Time=%f", scheduler, time));
        queues.forEach((s, queue) -> System.out.println(queue.printStateTimes(time)));
    }


    public void updateTime(float t) {
        float delta = t - time;
        time = t;
        queues.forEach((s, queue) -> queue.updateTimes(delta));
//        System.out.println(String.format(Locale.ROOT, "Times: %n Time=%f %n Delta=%f %n Queue=%s", time, delta, queue.printStateTimes()));
    }


    public void arrival(Queue q, float t) {
        updateTime(t);
        if (q.getSize() < q.getCapacity()) {
            q.setSize(q.getSize() + 1);
            if (q.getSize() <= q.getServers()) {
                if (network == null) {
                    scheduler.add(new Event(Event.DEPARTURE, t + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
                } else {
                    List<Route> routes = (List<Route>) network.get(q.getName());
                    Route rt;
                    if (routes.size() == 1) {
                        rt = routes.get(0);
                    } else {
                        rt = selectRoute(routes, randomBetween(0, 1));
                    }
                    scheduler.add(new Event(Event.TRANSITION, t + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), rt.getOrigin(), rt.getDestination()));
                }
            }
        } else {
            q.addLoss();
        }
        scheduler.add(new Event(Event.ARRIVAL, t + randomBetween(q.getMinArrival(), q.getMaxArrival()), q.getName()));
    }

    public Route selectRoute(List<Route> routes, float rnd) {
        return routes.stream()
                .sorted((o1, o2) -> Float.compare(o1.getProbability(), o2.getProbability()))
                .reduce((route, route2) -> rnd < route.getProbability() ? route : route2)
                .orElse(new Route("", "", 0));
    }


    public void departure(Queue q, float t) {
        updateTime(t);
        q.setSize(q.getSize() - 1);
        if (q.getSize() >= q.getServers()) {
            scheduler.add(new Event(Event.DEPARTURE, t + randomBetween(q.getMinDeparture(), q.getMaxDeparture()), q.getName()));
        }
    }

    private void transition(Queue src, Queue dst, float t) {
        updateTime(t);
        src.setSize(src.getSize() - 1);
        if (src.getSize() >= src.getServers()) {
            scheduler.add(new Event(Event.TRANSITION, t + randomBetween(src.getMinDeparture(), src.getMaxDeparture()), src.getName(), dst.getName()));
        }
        if (dst.getSize() < dst.getCapacity()) {
            dst.setSize(dst.getSize() + 1);
            if (dst.getSize() <= dst.getServers()) {
                scheduler.add(new Event(Event.DEPARTURE, t + randomBetween(dst.getMinDeparture(), dst.getMaxDeparture()), dst.getName()));
            }
        } else {
            dst.addLoss();
        }
    }


    private float randomBetween(float a, float b) {
        iterations = iterations - 1;
        float rnd = (float) ((b - a) * random.nextDouble() + a);
//        System.out.println(String.format(Locale.ROOT, "Randoms: Next=%f Remaining=%d", rnd, iterations));
        return rnd;
    }
}
