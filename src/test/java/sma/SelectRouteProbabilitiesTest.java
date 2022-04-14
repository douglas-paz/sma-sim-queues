package sma;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SelectRouteProbabilitiesTest {

    private ArrayList<Route> routes;

    @BeforeEach
    public void setup() {
        routes = new ArrayList<>();
        routes.add(new Route("Q1", "Q2", 0.1f));
        routes.add(new Route("Q1", "Q3", 0.3f));
        routes.add(new Route("Q1", "Q4", 0.6f));
    }

    @Test
    public void testProbabilities_1() {
        Assertions.assertEquals(routes.get(0).getProbability(), selectRoute(0.0000f).getProbability());
    }

    @Test
    public void testProbabilities_2() {
        Assertions.assertEquals(routes.get(0).getProbability(), selectRoute(0.0999f).getProbability());
    }

    @Test
    public void testProbabilities_3() {
        Assertions.assertEquals(routes.get(1).getProbability(), selectRoute(0.1000f).getProbability());
    }

    @Test
    public void testProbabilities_4() {
        Assertions.assertEquals(routes.get(1).getProbability(), selectRoute(0.2999f).getProbability());
    }

    @Test
    public void testProbabilities_5() {
        Assertions.assertEquals(routes.get(2).getProbability(), selectRoute(0.3000f).getProbability());
    }

    @Test
    public void testProbabilities_6() {
        Assertions.assertEquals(routes.get(2).getProbability(), selectRoute(0.9999f).getProbability());
    }

    public Route selectRoute(float rnd) {
        return routes.stream()
                .sorted((o1, o2) -> Float.compare(o1.getProbability(), o2.getProbability()))
                .reduce((route, route2) -> rnd < route.getProbability() ? route : route2)
                .orElse(new Route("VOID", "VOID", 0));
    }
}
