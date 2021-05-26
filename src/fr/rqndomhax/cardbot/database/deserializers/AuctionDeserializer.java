package fr.rqndomhax.cardbot.database.deserializers;

import com.google.gson.*;
import fr.rqndomhax.cardbot.cards.Backgrounds;
import fr.rqndomhax.cardbot.cards.CardType;
import fr.rqndomhax.cardbot.utils.Auction;
import fr.rqndomhax.cardbot.database.Card;

import java.lang.reflect.Type;

public class AuctionDeserializer implements JsonDeserializer<Auction> {
    @Override
    public Auction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        return new Auction(asJsonObject.get("position").getAsDouble(),
                new Card(CardType.valueOf(asJsonObject.getAsJsonObject("card").get("cardType").getAsString()),
                        asJsonObject.getAsJsonObject("card").get("cardName").getAsString(),
                        Backgrounds.valueOf(asJsonObject.getAsJsonObject("card").get("cardBackground").getAsString()),
                        asJsonObject.getAsJsonObject("card").get("ownerId").getAsString(),
                        asJsonObject.getAsJsonObject("card").get("id").getAsString(),
                        asJsonObject.getAsJsonObject("card").get("coins").getAsDouble(),
                        asJsonObject.getAsJsonObject("card").get("timestamp").getAsString()),
                asJsonObject.get("price").getAsDouble(),
                asJsonObject.get("buyerId").getAsString(),
                asJsonObject.get("startTimestamp").getAsString(),
                asJsonObject.get("endTimestamp").getAsString());
    }
}
