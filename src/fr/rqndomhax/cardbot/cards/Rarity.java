package fr.rqndomhax.cardbot.cards;

import java.awt.*;

public enum Rarity {
    COMMON("Commune", new Color(Color.GRAY.getRGB()), 63.545, 1),
    UNCOMMON("Peu commune", Color.LIGHT_GRAY, 25.0, 5),
    RARE("Rare", Color.GREEN, 10, 10),
    EPIC("Epique", Color.MAGENTA, 1, 25),
    LEGENDARY("Légendaire", Color.PINK, 0.3, 50),
    HEROIC("Héroique", Color.RED, 0.1, 100),
    DIVIN("Divine", Color.ORANGE, 0.05, 500),
    MAIN_DOFUS("Dofus primordial", Color.YELLOW, 0.005, 1000);

    private final String name;
    private final double dropRate;
    private final Color color;
    private final double coins;

    Rarity(String name, Color color, double dropRate, double coins) {
        this.name = name;
        this.color = color;
        this.dropRate = dropRate;
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public double getDropRate() {
        return dropRate;
    }

    public double getCoins() {
        return coins;
    }
}