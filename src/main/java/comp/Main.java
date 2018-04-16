package comp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Starting competition!");
        Main main = new Main();
        long amount = main.readValueFromFile();
        long exactPart = findExactPart(amount);
        System.out.println("For amount " + amount + " part " + exactPart + " is made.");
    }

    private static long findExactPart(long amount) {
        long clearValue = clearValue(amount);
        for (int i = 1; i <= clearValue; i++) {
            long divisorSum = getDivisorSum(i);
            if (divisorSum == clearValue) {
                clearValue = i;
                break;
            }
        }
        return clearValue;
    }

    private static long getDivisorSum(int i) {
        long divisorSum = 0;
        for (int i1 = 2; i1 <= i; i1++) {
            if (i % i1 == 0) {
                divisorSum += i1;
            }
        }
        return divisorSum;
    }

    private static long clearValue(long amount) {
        if (amount < 0 && amount % 10 != 0) {
            return -1;
        }

        return amount / 10 - 1;
    }

    private URI getUri() throws URISyntaxException {
        return getClass().getClassLoader().getResource("input.txt").toURI();
    }

    private long readValueFromFile() throws URISyntaxException, IOException {
        final long[] value = new long[1];
        Path uri = Paths.get(getUri());

        Stream<String> lines = Files.lines(uri);
        lines.forEach(e -> value[0] = Long.parseLong(e));
        lines.close();
        return value[0];
    }
}
