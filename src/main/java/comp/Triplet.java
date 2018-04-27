package comp;

public class Triplet {
    private long firstValue;
    private long secondValue;
    private long thirdValue;

    public Triplet(long firstValue, long secondValue, long thirdValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
    }

    public long getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(long firstValue) {
        this.firstValue = firstValue;
    }

    public long getSecondValue() {
        return secondValue;
    }

    public void setSecondValue(long secondValue) {
        this.secondValue = secondValue;
    }

    public long getThirdValue() {
        return thirdValue;
    }

    public void setThirdValue(long thirdValue) {
        this.thirdValue = thirdValue;
    }
}

