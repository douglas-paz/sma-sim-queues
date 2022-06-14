import sma.random.LinearCongruentRandom;

import java.util.Locale;

public class SmaRandom {

    public static void main(String[] args) {
        long seed = 0;
        long iterations = 1;

        LinearCongruentRandom random;

        if (args.length == 2) {
            iterations = Long.parseUnsignedLong(args[0]);
            seed = Long.parseUnsignedLong(args[1]);
            random = new LinearCongruentRandom(seed);
        } else if (args.length < 2) {
            if (args.length == 1)
                iterations = Long.parseUnsignedLong(args[0]);
            random = new LinearCongruentRandom(System.currentTimeMillis());
        } else {
            return;
        }

        for (int i = 0; i < iterations; i++) {
            System.out.printf(Locale.ROOT, "%20.20f%n", random.nextDouble());
        }
    }
}
