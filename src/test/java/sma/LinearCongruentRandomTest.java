package sma;

import org.junit.jupiter.api.Test;
import sma.random.LinearCongruentRandom;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

class LinearCongruentRandomTest {

    private final int iterations = 1_000;

    @Test
    public void randomTestX() {
        System.out.println("Starting test X");
        LinearCongruentRandom r = new LinearCongruentRandom(0);

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            System.out.println(r.nextInt());
        }
        System.out.println("-----");
    }

    @Test
    public void randomTestU() {
        System.out.println("Starting test U");
        LinearCongruentRandom r = new LinearCongruentRandom(0);

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            printFormatted(r);
        }
        System.out.println("-----");
    }

    @Test
    public void randomTestSeed0() {
        System.out.println("Starting test Seed 0");
        LinearCongruentRandom r = new LinearCongruentRandom(0);

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            printFormatted(r);
        }
        System.out.println("-----");
    }

    @Test
    public void randomTestSeed1() {
        System.out.println("Starting test Seed 1");
        LinearCongruentRandom r = new LinearCongruentRandom(1);

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            printFormatted(r);
        }
        System.out.println("-----");
    }

    @Test
    public void randomTestSeedMin() {
        System.out.println("Starting test Seed MIN");
        LinearCongruentRandom r = new LinearCongruentRandom(Long.MIN_VALUE);

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            printFormatted(r);
        }
        System.out.println("-----");
    }

    @Test
    public void randomTestSeedMax() {
        System.out.println("Starting test Seed MAX");
        LinearCongruentRandom r = new LinearCongruentRandom(Long.MAX_VALUE);

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            printFormatted(r);
        }
        System.out.println("-----");
    }

    @Test
    public void randomTestSeedTimestamp1() {
        System.out.println("Starting test Seed Timestamp");
        LocalDateTime time = LocalDateTime.of(1999, 12, 31, 23, 59);
        LinearCongruentRandom r = new LinearCongruentRandom(time.toInstant(ZoneOffset.UTC).toEpochMilli());

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            printFormatted(r);
        }
        System.out.println("-----");
    }


    @Test
    public void randomTestSeedTimestampNow() {
        System.out.println("Starting test Seed NOW");
        LocalDateTime time = LocalDateTime.now();
        LinearCongruentRandom r = new LinearCongruentRandom(time.toInstant(ZoneOffset.UTC).toEpochMilli());

        System.out.println("-----");
        for (int i = 0; i < iterations; i++) {
            printFormatted(r);
        }
        System.out.println("-----");
    }

    private void printFormatted(LinearCongruentRandom r) {
        System.out.println(String.format(Locale.ROOT, "%.20f", r.nextDouble()));
    }

}
