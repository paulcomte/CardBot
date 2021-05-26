package fr.rqndomhax.cardbot.database;

import fr.rqndomhax.cardbot.utils.Auction;

import java.util.ArrayList;

public class MAuction {
    private final String ownerId;
    private final ArrayList<Auction> auctions;

    public MAuction(String ownerId, ArrayList<Auction> auctions) {
        this.ownerId = ownerId;
        this.auctions = auctions;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public ArrayList<Auction> getAuctions() {
        return auctions;
    }
}
