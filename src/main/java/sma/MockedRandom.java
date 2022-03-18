package sma;

public class MockedRandom implements IRandom {

    float[] list;
    int i = 0;

    public MockedRandom(float[] list) {
        this.list = list;
    }

    @Override
    public double nextDouble() {
        float r = list[i];
        i++;
        return r;
    }

    @Override
    public long nextInt() {
        return 0;
    }
}
