package sma;

public class Route {

    private String origin;
    private String destination;
    private float probability;

    public Route(String origin, String destination, float probability) {
        this.origin = origin;
        this.destination = destination;
        this.probability = probability;
    }

    public Route(String origin, String destination) {
        this(origin, destination, 1.0f);
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "Route{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", probability=" + probability +
                '}';
    }
}
