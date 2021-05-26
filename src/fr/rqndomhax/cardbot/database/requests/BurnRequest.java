package fr.rqndomhax.cardbot.database.requests;

import fr.rqndomhax.cardbot.database.Card;

public class BurnRequest extends Request {

    private final Card burnCard;
    private final String burnMessage;

    public BurnRequest(Card burnCard, String burnMessage) {
        super(Requests.BURN);
        this.burnCard = burnCard;
        this.burnMessage = burnMessage;
    }

    public Card getBurnCard() {
        return burnCard;
    }

    public String getBurnMessage() {
        return burnMessage;
    }
}
