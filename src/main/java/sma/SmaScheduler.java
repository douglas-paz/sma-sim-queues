package sma;

import java.util.ArrayList;
import java.util.List;

public class SmaScheduler {

    List<Event> queue;

    public SmaScheduler() {
        this.queue = new ArrayList<>();
    }

    public void init(Event e) {
        this.queue.add(e);
    }

    public void add(Event e) {
        this.queue.add(e);
    }

    public Event removeFirst() {
        queue.sort((o1, o2) -> Float.compare(o1.getTime(), o2.getTime()));
        return queue.remove(0);
    }

    @Override
    public String toString() {
        return "SmaScheduler{" +
                "queue=" + queue +
                '}';
    }
}
