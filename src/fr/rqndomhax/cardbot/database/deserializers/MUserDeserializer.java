package fr.rqndomhax.cardbot.database.deserializers;

import com.google.gson.*;
import fr.rqndomhax.cardbot.database.requests.Request;
import fr.rqndomhax.cardbot.database.requests.Requests;
import fr.rqndomhax.cardbot.database.MUser;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class MUserDeserializer implements JsonDeserializer<MUser> {
    @Override
    public MUser deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject e = jsonElement.getAsJsonObject();
        return new MUser(e.get("id").getAsString(),
                e.get("coins").getAsDouble(),
                e.get("lastDrop").getAsString(),
                e.get("createdDate").getAsString(),
                getRequests(e));
    }

    private Set<Request> getRequests(JsonObject jsonObject) {
        Set<Request> requests = new HashSet<>();
        Gson gson = new Gson();
        jsonObject.get("requests").getAsJsonArray().forEach(e -> {
            Requests requestType = Requests.valueOf(e.getAsJsonObject().get("requestType").getAsString());
            requests.add(gson.fromJson(e.toString(), (Type) requestType.getRequest()));
        });
        return requests;
    }
}
