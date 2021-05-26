package fr.rqndomhax.cardbot.database.deserializers;

import com.google.gson.*;
import fr.rqndomhax.cardbot.cards.Backgrounds;
import fr.rqndomhax.cardbot.cards.CardType;
import fr.rqndomhax.cardbot.database.Card;

import java.lang.reflect.Type;

public class CardDeserializer implements JsonDeserializer<Card> {

    @Override
    public Card deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        return new Card(CardType.valueOf(asJsonObject.get("cardType").getAsString()),
                asJsonObject.get("cardName").getAsString(),
                Backgrounds.valueOf(asJsonObject.get("cardBackground").getAsString()),
                asJsonObject.get("ownerId").getAsString(),
                asJsonObject.get("id").getAsString(),
                asJsonObject.get("coins").getAsDouble(),
                asJsonObject.get("timestamp").getAsString());
    }
}
