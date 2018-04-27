package comp;

import java.util.List;

public class Images implements Comparable{
    private long timestamp;
    private long rowLines;
    private long columnLines;
    private long[][] pixels;


    public Images(long timestamp, long rowLines, long columnLines) {
        this.timestamp = timestamp;
        this.rowLines = rowLines;
        this.columnLines = columnLines;
        pixels = new long[999][999];
        initPixels();
    }

    private void initPixels() {
    }

    public void addPixels(String data, int line)//separator is space
    {
        String[] dataSplit = data.split(" ");
        int column = 0;
        for (String s : dataSplit) {
            pixels[line][column++] = Integer.parseInt(s);
        }
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getRowLines() {
        return rowLines;
    }

    public void setRowLines(long rowLines) {
        this.rowLines = rowLines;
    }

    public long getColumnLines() {
        return columnLines;
    }

    public void setColumnLines(long columnLines) {
        this.columnLines = columnLines;
    }

    public boolean inBetween(long startTimestamp, long endTimestamp) {
        return startTimestamp <= timestamp && endTimestamp >= timestamp;
    }

    public boolean containsAsteroid() {
        for (int row = 0; row < rowLines - 1; row++) {
            for (int column = 0; column < columnLines - 1; column++) {
                if (pozitivePixel(row, column)) {
                    if (pozitivePixel(row, column + 1)) {
                        return true;
                    }
                    if (pozitivePixel(row + 1, column))
                    {
                        return true;
                    }
                    if (pozitivePixel(row + 1, column + 1))
                    {
                        return true;
                    }
                    if (pozitivePixel(row + 1, column - 1))
                    {
                        return true;
                    }
                    if (pozitivePixel(row, column - 1))
                    {
                        return true;
                    }
                    if (pozitivePixel(row, column + 1))
                    {
                        return true;
                    }
                    if (pozitivePixel(row - 1, column - 1))
                    {
                        return true;
                    }
                    if (pozitivePixel(row - 1, column))
                    {
                        return true;
                    }
                    if (pozitivePixel(row - 1, column + 1))
                    {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean pozitivePixel(int row, int column) {
        if (row >= rowLines || row < 0){
            return false;
        }

        if (column >= columnLines || column < 0){
            return false;
        }
        return pixels[row][column] > 0;
    }

    @Override
    public int compareTo(Object o) {
        long timestamp = ((Images) o).timestamp;
        return Long.compare(this.timestamp, timestamp);
    }
}
