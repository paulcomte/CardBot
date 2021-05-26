package fr.rqndomhax.cardbot.utils;

import fr.rqndomhax.cardbot.database.Card;

public class Auction {

    private double position;
    private Card card;
    private double price;
    private String buyerId;
    private final String startTimestamp;
    private final String endTimestamp;

    public Auction(double position, Card card, double price, String buyerId, String startTimestamp, String endTimestamp) {
        this.position = position;
        this.card = card;
        this.price = price;
        this.buyerId = buyerId;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public String getEndTimestamp() {
        return endTimestamp;
    }
}
