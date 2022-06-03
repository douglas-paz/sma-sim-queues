package sma;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SelectRouteProbabilityMissingTest {

    private ArrayList<Route> routes;

    @BeforeEach
    public void setup() {
        routes = new ArrayList<>();
        routes.add(new Route("Q1", "Q2", 0.3f));
        routes.add(new Route("Q1", "Q3", 0.5f));

        addMissingRoutes(routes);
    }

    private void addMissingRoutes(List<Route> routes) {
        float sum = routes.stream().reduce(0f, (aFloat, route) -> aFloat + route.getProbability(), (aFloat, aFloat2) -> aFloat2);

        assert sum <= 1.0f;

        if (sum < 1.0f) {
            BigDecimal p = new BigDecimal(1.0 - sum).setScale(4, RoundingMode.UP);
            routes.add(new ExitRoute("Q1", p.floatValue()));
        }
    }

    @Test
    public void testMissing1() {
        Assertions.assertEquals(routes.get(2).getProbability(), selectRoute(0.0000f).getProbability());
    }

    @Test
    public void testMissing2() {
        Assertions.assertEquals(routes.get(2).getProbability(), selectRoute(0.19999999f).getProbability());
    }

    @Test
    public void testMissing3() {
        Assertions.assertEquals(routes.get(0).getProbability(), selectRoute(0.2000f).getProbability());
    }

    @Test
    public void testMissing4() {
        Assertions.assertEquals(routes.get(0).getProbability(), selectRoute(0.4999f).getProbability());
    }

    @Test
    public void testMissing5() {
        Assertions.assertEquals(routes.get(1).getProbability(), selectRoute(0.5000f).getProbability());
    }


    public Route selectRoute(float rnd) {
        routes.sort((o1, o2) -> Float.compare(o1.getProbability(), o2.getProbability()));
        float p = 0f;
        for (Route r : routes) {
            p += r.getProbability();
            if (rnd < p) return r;
        }
        return routes.get(0);
    }
}
