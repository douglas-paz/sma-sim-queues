package sma;

import java.util.Locale;

public class Event {

    public static final int ARRIVAL = 0;
    public static final int DEPARTURE = 1;

    private int type;
    private float time;


    public Event(int type, float time) {
        this.type = type;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    @Override
    public String toString() {
        String t = type == 0 ? "ARRIVAL" : "DEPARTURE";
        return String.format(Locale.ROOT, "Event: %s at %f", t, time);
    }
}
