package fr.rqndomhax.cardbot.cards;

public enum CardPacks {
    CLASSIC("Classique", 20),
    HALLOWEEN("Halouine", 50),
    NOWEL("Nowel", 100),
    PAQUES("Paque", 100),
    STVALENTIN("St-Valentin", 400),
    ITEM("0bjets", 300);

    private final String name;
    private final double coins;

    CardPacks(String name, double coins) {
        this.name = name;
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public double getCoins() {
        return coins;
    }
}
