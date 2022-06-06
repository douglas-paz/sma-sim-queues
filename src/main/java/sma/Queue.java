package sma;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Queue {

    private String name;
    private int servers;
    private int capacity;
    private float minArrival;
    private float maxArrival;
    private float minDeparture;
    private float maxDeparture;

    private int size;
    private HashMap<Integer, Float> states;
    private int loss = 0;


    public Queue(String name, int servers, int capacity, float minArrival, float maxArrival, float minDeparture, float maxDeparture) {
        this.name = name;
        this.servers = servers;
        this.capacity = capacity;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minDeparture = minDeparture;
        this.maxDeparture = maxDeparture;

        this.size = 0;
        this.states = new HashMap<>();
        this.states.put(0, 0f);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addLoss() {
        if (capacity > 0)
            this.loss++;
    }

    public int getLoss() {
        return this.loss;
    }

    @Override
    public String toString() {
        return "Queue{" +
                "name=" + name +
                ", size=" + size +
                ", servers=" + servers +
                ", capacity=" + capacity +
                ", minArrival=" + minArrival +
                ", maxArrival=" + maxArrival +
                ", minExit=" + minDeparture +
                ", maxExit=" + maxDeparture +
                ", states=" + this.states.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList()) +
                '}';
    }

    public void updateTimes(float delta) {
        Float val = this.states.getOrDefault(size, 0f);
        this.states.put(size, val + delta);
    }

    public String printStateTimes(float time) {
        StringBuilder sb = new StringBuilder();
        sb.append("Queue: ").append(name).append("\n")
                .append("Size: ").append(size).append("\n")
                .append("Loss: ").append(loss).append("\n\n")
                .append("State     ").append("\t")
                .append("Time      ").append("\t")
                .append("Prob.     ").append("\n");


        this.states.forEach((i, v) -> {
            sb.append(String.format("%-10s", i)).append('\t')
                    .append(String.format("%10s", String.format(Locale.ROOT, "%.4f", v))).append('\t')
                    .append(String.format("%10s%%", String.format(Locale.ROOT, "%.2f", 100 * v / time))).append('\n');
        });

        return sb.toString();
    }
}
