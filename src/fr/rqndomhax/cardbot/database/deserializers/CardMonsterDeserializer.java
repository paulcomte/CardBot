package fr.rqndomhax.cardbot.database.deserializers;

import com.google.gson.*;
import fr.rqndomhax.cardbot.cards.Backgrounds;
import fr.rqndomhax.cardbot.cards.CardType;
import fr.rqndomhax.cardbot.cards.monsters.CardMonster;
import fr.rqndomhax.cardbot.cards.monsters.Monsters;
import fr.rqndomhax.cardbot.database.Card;

import java.lang.reflect.Type;

public class CardMonsterDeserializer implements JsonDeserializer<CardMonster> {
        @Override
        public CardMonster deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            return new CardMonster(new Card(CardType.valueOf(asJsonObject.get("cardType").getAsString()),
                    asJsonObject.get("cardName").getAsString(),
                    Backgrounds.valueOf(asJsonObject.get("cardBackground").getAsString()),
                    asJsonObject.get("ownerId").getAsString(),
                    asJsonObject.get("id").getAsString(),
                    asJsonObject.get("coins").getAsDouble(),
                    asJsonObject.get("timestamp").getAsString()),
                    Monsters.valueOf(asJsonObject.get("monsters").getAsString()),
                    asJsonObject.get("attack").getAsInt(),
                    asJsonObject.get("defense").getAsInt());
        }
}
