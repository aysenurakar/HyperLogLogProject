package hll;

import java.nio.charset.StandardCharsets;

public class MurmurHash3 {

    public static long hash64(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        return hash64(bytes, bytes.length, 0xe17a1465);
    }

    public static long hash64(byte[] data, int length, int seed) {
        final long m = 0xc6a4a7935bd1e995L;
        final int r = 47;

        long h = (seed & 0xffffffffL) ^ (length * m);

        int length8 = length / 8;

        for (int i = 0; i < length8; i++) {
            int i8 = i * 8;
            long k = ((long) data[i8] & 0xff)
                    | (((long) data[i8 + 1] & 0xff) << 8)
                    | (((long) data[i8 + 2] & 0xff) << 16)
                    | (((long) data[i8 + 3] & 0xff) << 24)
                    | (((long) data[i8 + 4] & 0xff) << 32)
                    | (((long) data[i8 + 5] & 0xff) << 40)
                    | (((long) data[i8 + 6] & 0xff) << 48)
                    | (((long) data[i8 + 7] & 0xff) << 56);

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        int remaining = length & 7;
        int offset = length8 * 8;

        switch (remaining) {
            case 7:
                h ^= (long) (data[offset + 6] & 0xff) << 48;
            case 6:
                h ^= (long) (data[offset + 5] & 0xff) << 40;
            case 5:
                h ^= (long) (data[offset + 4] & 0xff) << 32;
            case 4:
                h ^= (long) (data[offset + 3] & 0xff) << 24;
            case 3:
                h ^= (long) (data[offset + 2] & 0xff) << 16;
            case 2:
                h ^= (long) (data[offset + 1] & 0xff) << 8;
            case 1:
                h ^= (long) (data[offset] & 0xff);
                h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        return h;
    }
}