package fr.rqndomhax.cardbot.utils;

import fr.rqndomhax.cardbot.database.Card;

import java.util.Set;

public class Inventory {
    private final Set<Card> cards;
    private double coins;
    private final String inventoryOwnerId;

    public Inventory(String inventoryOwnerId, Set<Card> cards, double coins) {
        this.inventoryOwnerId = inventoryOwnerId;
        this.cards = cards;
        this.coins = coins;
    }

    public String getInventoryOwnerId() {
        return inventoryOwnerId;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public Set<Card> getCards() {
        return cards;
    }
}
