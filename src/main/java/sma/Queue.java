package sma;

import java.util.Arrays;
import java.util.Locale;

public class Queue {

    private int servers;
    private int capacity;
    private float minArrival;
    private float maxArrival;
    private float minDeparture;
    private float maxDeparture;

    private int size;
    private float[] states;
    private int loss = 0;


    public Queue(int servers, int capacity, float minArrival, float maxArrival, float minDeparture, float maxDeparture) {
        this.servers = servers;
        this.capacity = capacity;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minDeparture = minDeparture;
        this.maxDeparture = maxDeparture;

        this.size = 0;
        this.states = new float[capacity+1];
    }

    public int getServers() {
        return servers;
    }

    public void setServers(int servers) {
        this.servers = servers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getMinArrival() {
        return minArrival;
    }

    public void setMinArrival(float minArrival) {
        this.minArrival = minArrival;
    }

    public float getMaxArrival() {
        return maxArrival;
    }

    public void setMaxArrival(float maxArrival) {
        this.maxArrival = maxArrival;
    }

    public float getMinDeparture() {
        return minDeparture;
    }

    public void setMinDeparture(float minDeparture) {
        this.minDeparture = minDeparture;
    }

    public float getMaxDeparture() {
        return maxDeparture;
    }

    public void setMaxDeparture(float maxDeparture) {
        this.maxDeparture = maxDeparture;
    }

    public float[] getStates() {
        return states;
    }

    public void setStates(float[] states) {
        this.states = states;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "servers=" + servers +
                ", size=" + capacity +
                ", minArrival=" + minArrival +
                ", maxArrival=" + maxArrival +
                ", minExit=" + minDeparture +
                ", maxExit=" + maxDeparture +
                ", states=" + Arrays.toString(states) +
                '}';
    }


    public void updateTimes(float delta) {
        int i = size;
        states[i] = states[i] + delta;
    }

    public String printStateTimes(float time) {
        StringBuilder sb = new StringBuilder();
        sb.append(" Size=").append(size);
        sb.append("\n").append(" Loss=").append(loss);
        sb.append('\n').append(' ').append("States\t");
        for (int i = 0; i <= capacity; i++) {
            sb.append(String.format("%10s", i)).append('\t');
        }
        sb.append('\n').append(' ').append("Times\t");
        for (int i = 0; i <= capacity; i++) {
            sb.append(String.format("%10s", states[i])).append('\t');
        }
        sb.append('\n').append(' ').append("Prob.\t");
        for (int i = 0; i <= capacity; i++) {
            sb.append(String.format("%10s%%", String.format(Locale.ROOT, "%.2f", 100 * states[i]/time))).append('\t');
        }
        return sb.toString();
    }

    public void addLoss() {
        this.loss++;
    }

    public int getLoss() {
        return this.loss;
    }
}
