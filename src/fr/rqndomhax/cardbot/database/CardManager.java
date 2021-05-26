package fr.rqndomhax.cardbot.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import fr.rqndomhax.cardbot.cards.CardType;
import fr.rqndomhax.cardbot.cards.monsters.CardMonster;
import fr.rqndomhax.cardbot.database.deserializers.CardDeserializer;
import fr.rqndomhax.cardbot.database.deserializers.CardMonsterDeserializer;
import fr.rqndomhax.cardbot.utils.Setup;

import java.util.ArrayList;
import java.util.List;

public class CardManager {

    private final Setup setup;

    private final Gson cardGson = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardDeserializer())
            .create();
    private final Gson monsterGson = new GsonBuilder()
            .registerTypeAdapter(CardMonster.class, new CardMonsterDeserializer())
            .create();

    private final MongoCollection<BasicDBObject> collection;

    public CardManager(Setup setup) {
        this.collection = setup.getMongoDB().getMongoClient().getDatabase("bot").getCollection("cards", BasicDBObject.class);
        this.setup = setup;
    }

    // init card
    public void initCard(Card card) {
        collection.insertOne(BasicDBObject.parse(cardGson.toJson(card)));
    }

    // init monster card
    public void initMonster(CardMonster cardMonster) {
        collection.insertOne(BasicDBObject.parse(monsterGson.toJson(cardMonster)));
    }

    // get user cards by owner id
    public List<Card> getByOwnerId(String ownerId) {
        final List<Card> list = new ArrayList<>();
        for (BasicDBObject basicDBObject : this.collection.find(new BasicDBObject("ownerId", ownerId))) {
            list.add(cardGson.fromJson(basicDBObject.toString(), Card.class));
        }
        return list;
    }

    // get user monster cards by owner id
    public List<CardMonster> getMonstersByOwnerId(String ownerId) {
        final List<CardMonster> list = new ArrayList<>();
        final BasicDBObject search = new BasicDBObject("ownerId", ownerId);
        search.put("cardType", "MONSTER");
        for (BasicDBObject basicDBObject : this.collection.find(search)) {
            list.add(monsterGson.fromJson(basicDBObject.toString(), CardMonster.class));
        }
        return list;
    }

    // get monster cards by list of cards
    public List<CardMonster> getMonstersByCardsList(List<Card> cards) {
        final List<CardMonster> list = new ArrayList<>();
        for (Card card : cards) {
            if (card.getCardType() !=  CardType.MONSTER) continue;
            list.add((CardMonster) this.collection.find(new BasicDBObject("id", card.getId())));
        }
        return list;
    }

    // get monster card by card
    public CardMonster getMonstersByCard(Card card) {
        BasicDBObject search = new BasicDBObject("id", card.getId());
        search.put("cardType", "MONSTER");
        BasicDBObject found = collection.find(search).first();
        if (found != null) {
            return monsterGson.fromJson(String.valueOf(found), CardMonster.class);
        }
        return null;
    }

    // get card by id
    public Card getById(String id) {
        BasicDBObject found = collection.find(new BasicDBObject("id", id)).first();
        if (found != null) {
            return cardGson.fromJson(String.valueOf(found), Card.class);
        }
        return null;
    }

    // update card
    public void updateCard(Card card) {
        BasicDBObject cardO = this.collection.find(new BasicDBObject("id", card.getId())).first();
        if (cardO == null) return;
        collection.updateOne(cardO, new BasicDBObject("$set", BasicDBObject.parse(cardGson.toJson(card))));
    }

    // update card monster
    public void updateCard(CardMonster card) {
        BasicDBObject cardO = this.collection.find(new BasicDBObject("id", card.getId())).first();
        if (cardO == null) return;
        collection.updateOne(cardO, new BasicDBObject("$set", BasicDBObject.parse(monsterGson.toJson(card))));
    }

    // get monster card by id
    public CardMonster getMonsterById(String id) {
        BasicDBObject search = new BasicDBObject("id", id);
        search.put("cardType", "MONSTER");
        BasicDBObject found = collection.find(search).first();
        if (found != null) {
            return monsterGson.fromJson(String.valueOf(found), CardMonster.class);
        }
        return null;
    }

    // remove card by id
    public void deleteById(String id) {
        this.collection.findOneAndDelete(new BasicDBObject("id", id));
    }
}
