package sma;

public class LinearCongruentRandom implements IRandom {

    private final long a = 191;

    private final long c = Integer.MAX_VALUE;

    private final long m = Long.MAX_VALUE;

    private long x;

    public LinearCongruentRandom(long seed) {
        x = seed;
    }

    private void next() {
        long xi = (a * x + c) % m;
        x = xi & 0x7fffffffffffffffL;
    }

    public long nextInt() {
        next();
        return x;
    }

    @Override
    public double nextDouble() {
        next();
        return (double) x / m;
    }


    public static void warmupRandom(LinearCongruentRandom r) {
        for (int i = 0; i < 10; i++) {
            r.nextInt();
        }
    }
}
