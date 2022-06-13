package sma.random;

import java.util.List;

public class MockedRandom implements IRandom {

    float[] list;
    int i = 0;

    public MockedRandom(List<Float> list) {
        float[] floats = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            floats[i] = list.get(i);
        }
        this.list = floats;
    }


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
