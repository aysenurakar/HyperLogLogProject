package hll;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== HyperLogLog Testi ===");

        int p = 10; // m = 1024
        HyperLogLog hll = new HyperLogLog(p);

        Set<String> exactSet = new HashSet<>();
        Random random = new Random(42);

        int totalData = 100000;

        for (int i = 0; i < totalData; i++) {
            String value = "user_" + random.nextInt(80000);
            hll.add(value);
            exactSet.add(value);
        }

        double estimate = hll.estimate();
        int exact = exactSet.size();

        System.out.println("Gerçek farklı eleman sayısı : " + exact);
        System.out.println("HLL tahmini                : " + (long) estimate);
        System.out.println("Mutlak hata                : " + Math.abs(exact - estimate));
        System.out.println("Bağıl hata (%)             : " + (100.0 * Math.abs(exact - estimate) / exact));
        System.out.println("Teorik hata sınırı (%)     : " + (100.0 * hll.getTheoreticalError()));

        System.out.println("\n=== Merge Testi ===");

        HyperLogLog hll1 = new HyperLogLog(p);
        HyperLogLog hll2 = new HyperLogLog(p);
        Set<String> mergedExact = new HashSet<>();

        for (int i = 0; i < 50000; i++) {
            String value = "A_" + random.nextInt(40000);
            hll1.add(value);
            mergedExact.add(value);
        }

        for (int i = 0; i < 50000; i++) {
            String value = "B_" + random.nextInt(40000);
            hll2.add(value);
            mergedExact.add(value);
        }

        hll1.merge(hll2);

        double mergedEstimate = hll1.estimate();

        System.out.println("Gerçek birleşik cardinality : " + mergedExact.size());
        System.out.println("Merge sonrası HLL tahmini   : " + (long) mergedEstimate);
        System.out.println("Bağıl hata (%)              : " +
                (100.0 * Math.abs(mergedExact.size() - mergedEstimate) / mergedExact.size()));
    }
}