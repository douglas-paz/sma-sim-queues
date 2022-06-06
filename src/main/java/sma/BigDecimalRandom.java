package sma;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalRandom implements IRandom {

    private BigInteger a = BigInteger.valueOf(25214903917L);
    private BigInteger c = BigInteger.valueOf(11L);
    private BigInteger M = BigInteger.valueOf(281474976710656L);
    private BigInteger x;

    public BigDecimalRandom(Long semente) {
        this.x = BigInteger.valueOf(semente);
    }

    @Override
    public double nextDouble() {
        this.x = this.a.multiply(this.x).add(this.c).mod(this.M);
        return BigDecimal.valueOf(this.x.longValue()).divide(BigDecimal.valueOf(this.M.longValue())).doubleValue();
    }

    @Override
    public long nextInt() {
        return 0;
    }
}
