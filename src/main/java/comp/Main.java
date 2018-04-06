package comp;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    private static final String OUTPUT_FILE_NAME = "output.txt";
    private static final String INPUT_FILE_NAME = "input.txt";

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Starting competition!");
        Competition competition = new Competition();
        competition.start(INPUT_FILE_NAME, OUTPUT_FILE_NAME);
    }
}
