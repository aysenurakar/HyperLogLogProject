package hll;

public class HyperLogLog {

    private final int p;          // bucket bit sayısı
    private final int m;          // bucket sayısı = 2^p
    private final byte[] registers;

    public HyperLogLog(int p) {
        if (p < 4 || p > 20) {
            throw new IllegalArgumentException("p değeri 4 ile 20 arasında olmalı.");
        }
        this.p = p;
        this.m = 1 << p;
        this.registers = new byte[m];
    }

    public void add(String value) {
        long hash = MurmurHash3.hash64(value);

        int bucket = (int) (hash >>> (64 - p));

        long remaining = hash << p;

        int rank = getLeadingZeros(remaining, 64 - p) + 1;

        if (rank > registers[bucket]) {
            registers[bucket] = (byte) rank;
        }
    }

    private int getLeadingZeros(long value, int maxBits) {
        int count = 0;
        for (int i = 63; i >= 64 - maxBits; i--) {
            if (((value >>> i) & 1L) == 0) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public double estimate() {
        double alpha = getAlpha(m);

        double sum = 0.0;
        int zeroCount = 0;

        for (byte reg : registers) {
            sum += 1.0 / (1L << reg);
            if (reg == 0) {
                zeroCount++;
            }
        }

        double rawEstimate = alpha * m * m / sum;

        // Küçük aralık düzeltmesi (Linear Counting)
        if (rawEstimate <= 2.5 * m) {
            if (zeroCount != 0) {
                return m * Math.log((double) m / zeroCount);
            }
            return rawEstimate;
        }

        // Büyük aralık düzeltmesi
        double twoTo32 = Math.pow(2, 32);
        if (rawEstimate > (1.0 / 30.0) * twoTo32) {
            return -twoTo32 * Math.log(1.0 - (rawEstimate / twoTo32));
        }

        return rawEstimate;
    }

    public void merge(HyperLogLog other) {
        if (this.p != other.p) {
            throw new IllegalArgumentException("Sadece aynı p değerine sahip HLL yapıları birleştirilebilir.");
        }

        for (int i = 0; i < m; i++) {
            this.registers[i] = (byte) Math.max(this.registers[i], other.registers[i]);
        }
    }

    public byte[] getRegisters() {
        return registers.clone();
    }

    public int getBucketCount() {
        return m;
    }

    public int getP() {
        return p;
    }

    public double getTheoreticalError() {
        return 1.04 / Math.sqrt(m);
    }

    private double getAlpha(int m) {
        switch (m) {
            case 16:
                return 0.673;
            case 32:
                return 0.697;
            case 64:
                return 0.709;
            default:
                return 0.7213 / (1 + 1.079 / m);
        }
    }
}