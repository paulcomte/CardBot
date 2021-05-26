package fr.rqndomhax.cardbot.database;

import fr.rqndomhax.cardbot.cards.Backgrounds;
import fr.rqndomhax.cardbot.cards.CardType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Card {

    private final CardType cardType;
    private final String cardName;
    private final Backgrounds cardBackground;
    private String ownerId;
    private final String id;
    private final String timestamp;
    private final double coins;

    public Card(CardType cardType, String cardName, Backgrounds cardBackground, String ownerId, String id, double coins, String timestamp) {
        this.cardType = cardType;
        this.cardName = cardName;
        this.cardBackground = cardBackground;
        this.ownerId = ownerId;
        this.id = id;
        this.coins = coins;
        this.timestamp = timestamp;
    }

    public CardType getType() {
        return cardType;
    }

    public String getId() {
        return id;
    }

    public double getCoins() {
        return coins;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getCardName() {
        return cardName;
    }

    public Backgrounds getCardBackground() {
        return cardBackground;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Date date = new Date(Long.parseLong(this.timestamp));
        return format.format(date);
    }
}