package sma;

public class ExitRoute extends Route {

    public ExitRoute(String origin, float probability) {
        super(origin, null, probability);
    }

    public ExitRoute(String origin) {
        super(origin, null);
    }
}
