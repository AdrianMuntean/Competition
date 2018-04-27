package comp;

import java.util.Arrays;

public class Images implements Comparable{
    private long timestamp;
    private long rowLines;
    private long columnLines;
    private long[][] pixels;

    public Shape shape = new Shape();

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

    public void processShape() {
        int firstLine = 0;
        for (int i = 0; i < rowLines; i++) {
            for (int l = 0; l < columnLines; l++) {
                if (pozitivePixel(i, l)) {
                    firstLine = i;
                    i = (int) (rowLines + 5);
                    break;
                }
            }
        }

        int firstColumn = 0;
        for (int i = 0; i < columnLines; i++) {
            for (int l = 0; l < rowLines; l++) {
                if (pozitivePixel(l, i)) {
                    firstColumn = i;
                    i = (int) (columnLines + 5);
                    break;
                }
            }
        }

        int lastLine = -1;
        int lastColumn = -1;
        for (int i = firstLine; i <  rowLines; i++)
            for (int j = firstColumn; j < columnLines; j++)
            {
                if (pozitivePixel(i, j))
                {
                    if (lastLine < i) {
                        lastLine = i;
                    }

                    if (lastColumn < j)
                    {
                        lastColumn = j;
                    }
                }
            }



//        System.out.println(firstLine + " " + firstColumn);
//        System.out.println(pixels[firstLine][firstColumn]);
//        System.out.println(lastLine - firstLine + 1);
//        System.out.println( lastColumn - firstColumn + 1);

        shape.firstLine = firstLine;
        shape.firstColumn = firstColumn;
        shape.lineLenght = lastLine - firstLine + 1;
        shape.columnLength = lastColumn - firstColumn + 1;
    }

    public boolean equalsImage(Images images) {

        if (this.shape.lineLenght == 0 || this.shape.columnLength == 0) {
            return false;
        }

        if (images.equals(this))
        {
            return true;
        }

        int otherFirstLine = (int) images.shape.firstLine;
        int otherFirstColumn = (int) images.shape.firstColumn;
        for (long i = shape.firstLine; i < shape.lineLenght + shape.firstLine; i++)
        {
            for (long j = shape.firstColumn; j < shape.columnLength + shape.firstColumn; j++)
            {

                long thisPixelValue = this.pixels[(int) i][(int) j];
                long otherPixelValue = images.pixels[otherFirstLine][otherFirstColumn];
                if ((thisPixelValue == 0 && otherPixelValue != 0) || (thisPixelValue != 0 && otherPixelValue == 0))
                {
                    return false;
                }
                otherFirstColumn++;
            }

            otherFirstLine++;
            otherFirstColumn = (int) images.shape.firstColumn;
        }

        return true;
    }

//    public void fill(int x, int y, long newColor, long oldColor) {
//        if (!pozitivePixel(x, y)){
//            return;
//        }
////        if (x < 0) return;
////        if (y < 0) return;
////        if (x >= pixels.length) return;
////        if (y >= pixels[x].length) return;
//
//        asteroid[x][y] = newColor;
//
//        fill(x - 1, y, newColor, oldColor);
//        fill(x + 1, y, newColor, oldColor);
//        fill(x, y - 1, newColor, oldColor);
//        fill(x, y + 1, newColor, oldColor);
//    }

    public  class Shape {
        public boolean processed = false;
       public long firstLine;
       public long firstColumn;
       public long lineLenght;
       public long columnLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Images images = (Images) o;

        if (timestamp != images.timestamp) return false;
        if (rowLines != images.rowLines) return false;
        if (columnLines != images.columnLines) return false;
        if (!Arrays.deepEquals(pixels, images.pixels)) return false;
        return shape != null ? shape.equals(images.shape) : images.shape == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (int) (rowLines ^ (rowLines >>> 32));
        result = 31 * result + (int) (columnLines ^ (columnLines >>> 32));
        result = 31 * result + Arrays.deepHashCode(pixels);
        result = 31 * result + (shape != null ? shape.hashCode() : 0);
        return result;
    }
}
