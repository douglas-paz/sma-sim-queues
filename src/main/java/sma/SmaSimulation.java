package sma;

import java.util.Locale;

public class SmaSimulation {

    private SmaScheduler scheduler;

    private IRandom random;
    private Queue queue;
    private int iterations;
    private float first;
    private float time;


    public SmaSimulation(Queue queue, int randoms, float first) {
        this.queue = queue;
        this.iterations = randoms;
        this.first = first;
        this.time = 0;

        this.random = new LinearCongruentRandom(0);
        warmupRandom();

        this.scheduler = new SmaScheduler();
        this.scheduler.init(new Event(Event.ARRIVAL, first));
    }

    public SmaSimulation(Queue q1, float[] list, float first) {
        this.queue = q1;
        this.iterations = list.length;
        this.first = first;
        this.time = 0;

        this.random = new MockedRandom(list);

        this.scheduler = new SmaScheduler();
        this.scheduler.init(new Event(Event.ARRIVAL, first));
    }

    private void warmupRandom() {
        for (int i = 0; i < 10; i++) {
            random.nextInt();
        }
    }


    public void run() {
        while (iterations > 0) {
            Event e = scheduler.removeFirst();
//            System.out.println(e);
            if (e.getType() == Event.ARRIVAL)
                arrival(e.getTime());
            if (e.getType() == Event.DEPARTURE)
                departure(e.getTime());
        }
        System.out.println(String.format(Locale.ROOT, "End of Simulation: %n Scheduler=%s %n Queue=%s %n Time=%f", scheduler, queue.printStateTimes(time), time));
    }


    public void updateTime(float t) {
        float delta = t - time;
        time = t;
        queue.updateTimes(delta);
//        System.out.println(String.format(Locale.ROOT, "Times: %n Time=%f %n Delta=%f %n Queue=%s", time, delta, queue.printStateTimes()));
    }


    public void arrival(float t) {
        updateTime(t);
        if (queue.getSize() < queue.getCapacity()) {
            queue.setSize(queue.getSize() + 1);
            if (queue.getSize() <= queue.getServers()) {
                scheduler.add(new Event(Event.DEPARTURE, t + randomBetween(queue.getMinDeparture(), queue.getMaxDeparture())));
            }
        } else {
            queue.addLoss();
        }
        scheduler.add(new Event(Event.ARRIVAL, t + randomBetween(queue.getMinArrival(), queue.getMaxArrival())));
    }


    public void departure(float t) {
        updateTime(t);
        queue.setSize(queue.getSize() - 1);
        if (queue.getSize() >= queue.getServers()) {
            scheduler.add(new Event(Event.DEPARTURE, t + randomBetween(queue.getMinDeparture(), queue.getMaxDeparture())));
        }
    }


    private float randomBetween(float a, float b) {
        iterations = iterations - 1;
        float rnd = (float) ((b - a) * random.nextDouble() + a);
//        System.out.println(String.format(Locale.ROOT, "Randoms: Next=%f Remaining=%d", rnd, iterations));
        return rnd;
    }
}
