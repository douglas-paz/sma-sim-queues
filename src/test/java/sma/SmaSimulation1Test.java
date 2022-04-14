package sma;

import org.junit.jupiter.api.Test;

class SmaSimulation1Test {


    @Test
    public void test1Server() {
        System.out.println("Test 1 \nQueue=G/G/1/5 \nArrivals=2-4 \nDeparture=3-5");
        Queue q1;
        int randoms = 100_000;
        float first = 3f;
        IRandom random;
        SmaSimulation sim;

        System.out.println("\nRun 1: \n Randoms=100_000 \n Seed=1640995200000");
        q1 = new Queue("Q1", 1, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1640995200000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();


        System.out.println("\nRun 2: \n Randoms=100_000 \n Seed=1577836800000");
        q1 = new Queue("Q1",1, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1577836800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();

        System.out.println("\nRun 3: \n Randoms=100_000 \n Seed=1546300800000");
        q1 = new Queue("Q1",1, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1546300800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();

        System.out.println("\nRun 4: \n Randoms=100_000 \n Seed=1514764800000");
        q1 = new Queue("Q1",1, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1514764800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();

        System.out.println("\nnRun 5: \n Randoms=100_000 \n Seed=1483228800000");
        q1 = new Queue("Q1",1, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1483228800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();
    }


    @Test
    public void test2Servers() {
        System.out.println("\nTest 2 \nQueue=G/G/2/5 \nArrivals=2-4 \nDeparture=3-5");
        Queue q1;
        int randoms = 100_000;
        float first = 3f;
        IRandom random;
        SmaSimulation sim;

        System.out.println("\nRun 1: \n Randoms=100_000 \n Seed=1640995200000");
        q1 = new Queue("Q1",2, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1640995200000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();


        System.out.println("\nRun 2: \n Randoms=100_000 \n Seed=1577836800000");
        q1 = new Queue("Q1",2, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1577836800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();

        System.out.println("\nRun 3: \n Randoms=100_000 \n Seed=1546300800000");
        q1 = new Queue("Q1",2, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1546300800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();

        System.out.println("\nRun 4: \n Randoms=100_000 \n Seed=1514764800000");
        q1 = new Queue("Q1",2, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1514764800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();

        System.out.println("\nRun 5: \n Randoms=100_000 \n Seed=1483228800000");
        q1 = new Queue("Q1",2, 5, 2f, 4f, 3f, 5f);
        random = new LinearCongruentRandom(1483228800000L);
        LinearCongruentRandom.warmupRandom((LinearCongruentRandom) random);
        sim = new SmaSimulation(q1, random, randoms, first);
        sim.run();
    }
}