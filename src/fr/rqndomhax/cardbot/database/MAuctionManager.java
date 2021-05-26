package fr.rqndomhax.cardbot.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import fr.rqndomhax.cardbot.database.deserializers.AuctionDeserializer;
import fr.rqndomhax.cardbot.database.deserializers.MAuctionDeserializer;
import fr.rqndomhax.cardbot.utils.Auction;
import fr.rqndomhax.cardbot.utils.Setup;

import java.util.ArrayList;

import static com.mongodb.client.model.Updates.*;

public class MAuctionManager {
    private final Setup setup;
    private final Gson mGson = new GsonBuilder()
            .registerTypeAdapter(MAuction.class, new MAuctionDeserializer())
            .create();
    private final Gson aGson = new GsonBuilder()
            .registerTypeAdapter(Auction.class, new AuctionDeserializer())
            .create();

    private final MongoCollection<BasicDBObject> collection;

    public MAuctionManager(Setup setup) {
        this.collection = setup.getMongoDB().getMongoClient().getDatabase("bot").getCollection("auction", BasicDBObject.class);
        this.setup = setup;
    }

    // init auctions
    public MAuction initAuctions(MAuction auction) {
        if (doesExist(auction.getOwnerId())) return null;
        collection.insertOne(BasicDBObject.parse(mGson.toJson(auction)));
        return auction;
    }

    // add auction
    public void addAuction(String ownerId, Auction auction) {
        if (!doesExist(ownerId)) {
            ArrayList<Auction> auctions = new ArrayList<>();
            auctions.add(auction);
            initAuctions(new MAuction(ownerId, auctions));
            return;
        }
        this.collection.updateOne(new BasicDBObject("ownerId", ownerId), addToSet("auctions", BasicDBObject.parse(aGson.toJson(auction))));
    }

    // remove auction
    public void removeAuction(Auction auction) {
        BasicDBObject found = this.collection.find(new BasicDBObject("auctions.card.id", auction.getCard().getId())).first();
        if (found == null)
            return;
        MAuction auctions = mGson.fromJson(String.valueOf(found), MAuction.class);
        auctions.getAuctions().removeIf(au -> au.getCard().getId().equalsIgnoreCase(auction.getCard().getId()));
        this.collection.updateOne(found, new BasicDBObject("$set", BasicDBObject.parse(mGson.toJson(auctions))));
    }

    // update auctions
    public void updateAuctions(MAuction auction) {
        if (doesExist(auction.getOwnerId()))
            removeAuctions(auction.getOwnerId());
        initAuctions(auction);
    }

    // does user exist
    public boolean doesExist(String id) {
        BasicDBObject found = collection.find(new BasicDBObject("ownerId", id)).first();
        return found != null;
    }

    // get auctions
    public MAuction getAuctions(String id) {
        BasicDBObject found = collection.find(new BasicDBObject("ownerId", id)).first();
        if (found == null) {
            return initAuctions(new MAuction(id, new ArrayList<>()));
        }
        return mGson.fromJson(String.valueOf(found), MAuction.class);
    }

    // remove auctions
    public void removeAuctions(String id) {
        this.collection.findOneAndDelete(new BasicDBObject("ownerId", id));
    }

    public void removeAuctions(MAuction auction) {
        if (auction == null)
            return;
        this.collection.findOneAndDelete(new BasicDBObject("ownerId", auction.getOwnerId()));
    }
}
