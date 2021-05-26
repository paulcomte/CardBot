package fr.rqndomhax.cardbot.cards;

public enum Strengths {
    TRASH(0, 0),
    VERY_WEAK(1, 10),
    WEAK(11, 20),
    VERY_LOW(21, 30),
    LOW(31, 40),
    AVERAGE(41, 50),
    GOOD(51, 60),
    VERY_GOOD(61,70),
    STRONG(71, 80),
    VERY_STRONG(81, 90),
    INHUMAN(91, 99),
    GODLIKE(100, 100);

    private final int min;
    private final int max;

    Strengths(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
