package fr.rqndomhax.cardbot.database.deserializers;

import com.google.gson.*;
import fr.rqndomhax.cardbot.cards.Backgrounds;
import fr.rqndomhax.cardbot.cards.CardType;
import fr.rqndomhax.cardbot.utils.Auction;
import fr.rqndomhax.cardbot.database.Card;
import fr.rqndomhax.cardbot.database.MAuction;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MAuctionDeserializer implements JsonDeserializer<MAuction> {
    @Override
    public MAuction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        return new MAuction(asJsonObject.get("ownerId").getAsString(),
                getAuctions(asJsonObject));
    }

    private ArrayList<Auction> getAuctions(JsonObject jsonObject) {
        ArrayList<Auction> auctions = new ArrayList<>();
        jsonObject.get("auctions").getAsJsonArray().forEach(e -> {
            auctions.add(new Auction(e.getAsJsonObject().get("position").getAsDouble(),
                    new Card(CardType.valueOf(e.getAsJsonObject().get("card").getAsJsonObject().get("cardType").getAsString()),
                            e.getAsJsonObject().get("card").getAsJsonObject().getAsJsonObject().get("cardName").getAsString(),
                            Backgrounds.valueOf(e.getAsJsonObject().get("card").getAsJsonObject().getAsJsonObject().get("cardBackground").getAsString()),
                            e.getAsJsonObject().get("card").getAsJsonObject().getAsJsonObject().get("ownerId").getAsString(),
                            e.getAsJsonObject().get("card").getAsJsonObject().getAsJsonObject().get("id").getAsString(),
                            e.getAsJsonObject().get("card").getAsJsonObject().getAsJsonObject().get("coins").getAsDouble(),
                            e.getAsJsonObject().get("card").getAsJsonObject().getAsJsonObject().get("timestamp").getAsString()),
                    e.getAsJsonObject().get("price").getAsDouble(),
                    e.getAsJsonObject().get("buyerId").getAsString(),
                    e.getAsJsonObject().get("startTimestamp").getAsString(),
                    e.getAsJsonObject().get("endTimestamp").getAsString()));
        });
        return auctions;
    }
}
