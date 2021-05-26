package fr.rqndomhax.cardbot.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import fr.rqndomhax.cardbot.database.deserializers.MUserDeserializer;
import fr.rqndomhax.cardbot.database.requests.Request;
import fr.rqndomhax.cardbot.utils.Setup;

import java.util.HashSet;
import java.util.Set;

import static com.mongodb.client.model.Updates.set;

public class MUserManager {
    private final Setup setup;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(MUser.class, new MUserDeserializer())
            .create();

    private final MongoCollection<BasicDBObject> collection;

    public MUserManager(Setup setup) {
        this.collection = setup.getMongoDB().getMongoClient().getDatabase("bot").getCollection("users", BasicDBObject.class);
        this.setup = setup;
    }

    // init card
    public void initUser(MUser user) {
        collection.insertOne(BasicDBObject.parse(gson.toJson(user)));
    }

    // does user exist
    public boolean doesExist(String id) {
        BasicDBObject found = collection.find(new BasicDBObject("id", id)).first();
        return found != null;
    }

    // update last drop to current time
    public void updateLastDrop(String id) {
        BasicDBObject found = collection.find(new BasicDBObject("id", id)).first();
        if (found == null) {
            MUser user = getDefaultUser(id);
            user.setLastDrop(String.valueOf(System.currentTimeMillis()));
            initUser(user);
            return;
        }
        collection.updateOne(new BasicDBObject("id", id), set("lastDrop", System.currentTimeMillis()));
    }

    // set last drop time
    public void updateLastDrop(String id, String timestamp) {
        BasicDBObject found = collection.find(new BasicDBObject("id", id)).first();
        if (found == null) {
            MUser user = getDefaultUser(id);
            user.setLastDrop(timestamp);
            initUser(user);
            return;
        }
        collection.updateOne(new BasicDBObject("id", id), set("lastDrop", timestamp));
    }

    // get trade owner user by id
    public MUser getTrade(String messageId) {
        BasicDBObject found = this.collection.find(new BasicDBObject("requests.messageId", messageId)).first();
        if (found == null)
            return null;
        return gson.fromJson(String.valueOf(found), MUser.class);
    }

    // get trade owner user by userId
    public MUser getUserTrade(String userId) {
        BasicDBObject found = this.collection.find(new BasicDBObject("requests.user1Inventory.inventoryOwnerId", userId)).first();
        if (found == null) {
            found = this.collection.find(new BasicDBObject("requests.user2Inventory.inventoryOwnerId", userId)).first();
            if (found == null)
                return null;
            return gson.fromJson(String.valueOf(found), MUser.class);
        }
        return gson.fromJson(String.valueOf(found), MUser.class);
    }

    // update request
    public void updateRequest(String id, Request request) {
        BasicDBObject found = this.collection.find(new BasicDBObject("id", id)).first();
        if (found == null) {
            MUser user = getDefaultUser(id);
            user.getRequests().add(request);
            initUser(user);
            return;
        }
        MUser user = gson.fromJson(String.valueOf(found), MUser.class);
        user.getRequests().removeIf(requests -> requests.getRequestType() == request.getRequestType());
        user.getRequests().add(request);
        collection.updateOne(found, new BasicDBObject("$set", BasicDBObject.parse(gson.toJson(user))));
    }

    // remove request
    public void removeRequest(String id, Request request) {
        BasicDBObject found = this.collection.find(new BasicDBObject("id", id)).first();
        if (found == null) {
            MUser user = getDefaultUser(id);
            initUser(user);
            return;
        }
        MUser user = gson.fromJson(String.valueOf(found), MUser.class);
        user.getRequests().removeIf(requests -> requests.getRequestType() == request.getRequestType());
        collection.updateOne(found, new BasicDBObject("$set", BasicDBObject.parse(gson.toJson(user))));
    }

    public void updateCoins(String id, double newCoins) {
        BasicDBObject found = collection.find(new BasicDBObject("id", id)).first();
        if (found == null) {
            MUser user = getDefaultUser(id);
            user.setCoins(newCoins);
            initUser(user);
            return;
        }
        collection.updateOne(new BasicDBObject("id", id), set("coins", newCoins));
    }

    // get user
    public MUser getUser(String id) {
        BasicDBObject found = collection.find(new BasicDBObject("id", id)).first();
        if (found == null) {
            initUser(getDefaultUser(id));
            return gson.fromJson(String.valueOf(collection.find(new BasicDBObject("id", id)).first()), MUser.class);
        }
        return gson.fromJson(String.valueOf(found), MUser.class);
    }

    // remove user by MUser
    public void removeUser(MUser user) {
        this.collection.findOneAndDelete(new BasicDBObject("id", user.getId()));
    }

    // remove user by id
    public void removeUser(String id) {
        this.collection.findOneAndDelete(new BasicDBObject("id", id));
    }

    private MUser getDefaultUser(String id) {
        Set<Request> requests = new HashSet<>();

        return new MUser(id,0, "0", String.valueOf(System.currentTimeMillis()), requests);
    }
}
