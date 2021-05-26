package fr.rqndomhax.cardbot.database.requests;

import fr.rqndomhax.cardbot.database.MUser;
import fr.rqndomhax.cardbot.database.trades.States;
import fr.rqndomhax.cardbot.utils.Inventory;

public class TradeRequest extends Request{

    private final Inventory user1Inventory;
    private final Inventory user2Inventory;
    private final String messageId;
    private States state;

    public TradeRequest(String messageId, Inventory user1Inventory, Inventory user2Inventory, States state) {
        super(Requests.TRADE);
        this.messageId = messageId;
        this.user1Inventory = user1Inventory;
        this.user2Inventory = user2Inventory;
        this.state = state;
    }

    public Inventory getUserInventoryFromUser(MUser user) {
        if (user1Inventory.getInventoryOwnerId().equalsIgnoreCase(user.getId()))
            return user1Inventory;
        if (user2Inventory.getInventoryOwnerId().equalsIgnoreCase(user.getId()))
            return user2Inventory;
        return null;
    }

    public Inventory getUser1Inventory() {
        return user1Inventory;
    }

    public Inventory getUser2Inventory() {
        return user2Inventory;
    }

    public String getMessageId() {
        return messageId;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }
}
