package fr.rqndomhax.cardbot.database.requests;

import fr.rqndomhax.cardbot.database.Card;

public class AuctionRequest extends Request {
    private final String messageId;
    private final Card card;
    private final double price;
    private final int time;

    public AuctionRequest(String messageId, Card card, double price, int time) {
        super(Requests.AUCTION);
        this.messageId = messageId;
        this.card = card;
        this.price = price;
        this.time = time;
    }

    public String getMessageId() {
        return messageId;
    }

    public Card getCard() {
        return card;
    }

    public double getPrice() {
        return price;
    }

    public int getTime() {
        return time;
    }
}
