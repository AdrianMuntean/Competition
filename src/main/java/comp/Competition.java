package comp;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Competition {

    void start(String inputFileName, String outputFileName) throws URISyntaxException, IOException {
        List<String> data = readData(inputFileName);
        List<String> result = processInputData(data);
        writeData(result, outputFileName);
    }

    private void writeData(List<String> data, String outputFileName) throws URISyntaxException, IOException {
        Path path = Paths.get(getUri(outputFileName));
        clearFile(path);
        data.forEach(e -> {
            try {
                Files.write(path, e.getBytes(), StandardOpenOption.APPEND);
                Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        System.out.println("Data written to " + path.toUri().getPath());
    }

    private void clearFile(Path path) throws IOException {
        Files.write(path, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    @NotNull
    private List<String> readData(String inputFileName) throws URISyntaxException, IOException {
        Path uri = Paths.get(getUri(inputFileName));

        List<String> data = new ArrayList<>();
        Stream<String> lines = Files.lines(uri);
        lines.forEach(data::add);
        lines.close();
        return data;
    }

    @NotNull
    private URI getUri(String inputFileName) throws URISyntaxException {
        return getClass().getClassLoader().getResource(inputFileName).toURI();
    }

    private List<String> processInputData(List<String> data) {
        return new ArrayList<>(data);
    }
}
