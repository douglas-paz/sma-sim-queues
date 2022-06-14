package sma.random;

import java.math.BigInteger;

public class BigDecimalRandom implements IRandom {

    private BigInteger a = BigInteger.valueOf(2).pow(64);
    private BigInteger c = BigInteger.valueOf(6364136223846793005L);
    private BigInteger m = BigInteger.valueOf(1442695040888963407L);
    private BigInteger x;

    public BigDecimalRandom(Long seed) {
        this.x = BigInteger.valueOf(seed);
    }

    @Override
    public double nextDouble() {
        x = a.multiply(x).add(c).mod(m);
        return x.divide(m).doubleValue();
    }

    @Override
    public long nextInt() {
        return 0;
    }
}
