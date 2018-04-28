package comp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import static comp.Constants.*;

public class Competition {

    void start(String inputFileName, String outputFileName) throws URISyntaxException, IOException {
        List<String> data = readData(inputFileName);
        List<String> result = processInputData(data);
        writeData(result, outputFileName);
    }

    private void writeData(List<String> data, String outputFileName) throws URISyntaxException, IOException {
        writeData(data, outputFileName, true);
    }

    private void writeData(List<String> data, String outputFileName, boolean newLineAfterEachEntry) throws URISyntaxException, IOException {
        Path path = Paths.get(getUri(outputFileName));
        clearFile(path);
        data.forEach(e -> {
            try {
                Files.write(path, getDataWithSpace(e, newLineAfterEachEntry).getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        System.out.println("Data written to " + path.toUri().getPath());
    }

    private String getDataWithSpace(String data, boolean newLineAfterEachEntry) {
        return newLineAfterEachEntry? data + "\n" : data;
    }

    private void clearFile(Path path) throws IOException {
        Files.write(path, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private List<String> readData(String inputFileName) throws URISyntaxException, IOException {
        Path uri = Paths.get(getUri(inputFileName));

        List<String> data = new ArrayList<>();
        Stream<String> lines = Files.lines(uri);
        lines.forEach(data::add);
        lines.close();
        return data;
    }

    private URI getUri(String inputFileName) throws URISyntaxException {
        return getClass().getClassLoader().getResource(inputFileName).toURI();
    }

    private List<String> processInputData(List<String> data) {
        String[] metaInfoLineSplit =  data.get(META_INFO_INDEX).split(" ");
        long startTimestamp = Long.parseLong(metaInfoLineSplit[0]);
        long endTimestamp = Long.parseLong(metaInfoLineSplit[1]);
        Context.endTimestamp = endTimestamp;
        Context.startTimestamp = startTimestamp;
        long imageCount = Long.parseLong(metaInfoLineSplit[2]);

        List<Images> imagesList = readImages(imageCount, data);
        return processImages(imagesList);
    }

    private List<String> processImages(List<Images> imagesList) {
        List<String> result = new ArrayList<>();
        Collections.sort(result);

        for (Images image : imagesList) {
            image.processShape();
//            long occurence = processImage(image);
//            if (occurence != -1)
//            {
//                result.add(String.valueOf(occurence));
//            }
        }

        long firstOccurence = -1;
        long lastOccurence = -1;
        long match = 0;
        List<String> triplets = new ArrayList<>();
        for (int i = 0; i < imagesList.size(); i++) {
            match = 0;
            firstOccurence = -1;
            lastOccurence = -1;
            for (int i1 = i; i1 < imagesList.size(); i1++) {
                if (imagesList.get(i).equalsImage(imagesList.get(i1)))
                {
                    if (i != i1)
                    {
                        imagesList.get(i1).shape.processed = true;
                    }
                    match++;
//                    if (firstOccurence == -1)
//                    {
//                        firstOccurence = imagesList.get(i).getTimestamp();
//                    }
//
//                    lastOccurence = imagesList.get(i1).getTimestamp();
                    long timestamp1 = imagesList.get(i).getTimestamp();
                    long timestamp2 = imagesList.get(i1).getTimestamp();
                    long diff = timestamp2 - timestamp1;

                    if (diff != 0) {

                    if (getByTimestamp(imagesList, timestamp2 + diff).equalsImage(imagesList.get(i1)))
                        if (getByTimestamp(imagesList, timestamp2 + 2 * diff).equalsImage(imagesList.get(i1))) {
//                            System.out.println((timestamp2 - diff) + " " + (timestamp2 + 2 * diff) + " " + (match + 1));
                            triplets.add("" + (timestamp2 - diff) + " " + (timestamp2 + 2 * diff) + " " + (match + 1));
                        }
                    }
                }

            }

//            if (firstOccurence != -1 && lastOccurence != -1){
//                triplets.add("" + firstOccurence + " " + lastOccurence + " " + match);
//
//            }

            imagesList.get(i).shape.processed = true;
        }

         return triplets;
    }

    private Images getByTimestamp(List<Images> imagesList, long timestamp) {
        for (Images images : imagesList) {
            if (images.getTimestamp() == timestamp)
            {
                return images;
            }
        }

        return new Images(0, 0, 0);
    }

    private long processImage(Images image) {
        if (image.containsAsteroid() && image.inBetween(Context.startTimestamp, Context.endTimestamp))
        {
         return image.getTimestamp();
        }
        return -1;
    }

    private List<Images> readImages(long imageCount, List<String> data) {
        List<Images> images = new ArrayList<>();
        long offset = 0;
        for (long imageIndex = 0; imageIndex < imageCount; imageIndex++) {

            String[] imageMetaData = data.get((int) (IMAGE_META_INFO + offset)).split(" ");
            Images image = new Images(Long.parseLong(imageMetaData[TIMESTAMP_INDEX]), Long.parseLong(imageMetaData[ROW_INDEX]),
                    Long.parseLong(imageMetaData[COLUMN_INDEX]));
            for (int i = 0; i < Integer.parseInt(imageMetaData[ROW_INDEX]); i++) {
                image.addPixels(data.get((int) (ACTUAL_DATA_INDEX + i + offset)), i);
            }
            offset += image.getRowLines() + 1;
            images.add(image);
        }

        return images;
    }

    public static class Context{
        public static long startTimestamp;
        public static long endTimestamp;

    }
}
