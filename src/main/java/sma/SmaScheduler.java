package sma;

import java.util.ArrayList;
import java.util.List;

public class SmaScheduler {

    List<Event> queue;

    public SmaScheduler() {
        this.queue = new ArrayList<>();
    }

    public SmaScheduler(List<Event> list) {
        this.queue = list;
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
        StringBuilder builder = new StringBuilder();
        for (Event event : queue) {
            builder.append(event);
        }
        return builder.toString();
    }
}
